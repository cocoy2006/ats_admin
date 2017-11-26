package molab.main.java.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import molab.main.java.dao.ApplicationDao;
import molab.main.java.dao.DeviceDao;
import molab.main.java.dao.ScriptDao;
import molab.main.java.dao.TestcaseDao;
import molab.main.java.entity.Application;
import molab.main.java.entity.Device;
import molab.main.java.entity.Script;
import molab.main.java.entity.Testcase;
import molab.main.java.util.ImageUtil;
import molab.main.java.util.Molab;
import molab.main.java.util.Path;
import molab.main.java.util.Status;
import molab.main.java.util.UiAutomatorUtil;
import molab.main.java.util.init.Adb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.IDevice.DeviceState;
import com.android.ddmlib.RawImage;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.SyncException;
import com.android.ddmlib.TimeoutException;
import com.android.uiautomator.UiAutomatorHelper;
import com.android.uiautomator.UiAutomatorHelper.UiAutomatorException;
import com.android.uiautomator.UiAutomatorModel;
import com.android.uiautomator.tree.UiNode;

@Service
public class CrRecordService {
	
	private static final Logger log = Logger.getLogger(CrRecordService.class.getName());

	@Autowired
	private ApplicationDao ad;
	
	@Autowired
	private DeviceDao dd;
	
	@Autowired
	private ScriptDao sd;
	
	@Autowired
	private TestcaseDao td;
	
	public Device lock(int id) {
		Device device = dd.findById(id);
		if(device != null && device.getState() == Status.DeviceStatus.FREE.getInt()) {
			// create temp folder
			File temp = new File(Path.temp(device.getSn()));
			if(!temp.exists()) {
				temp.mkdir();
			}
			// build IDevice instance
			IDevice iDevice = Adb.getInstance().getBackend().findAttacedDevice(device.getSn());
			if(iDevice != null && iDevice.getState() == DeviceState.ONLINE) {
				device.setState(Status.DeviceStatus.BUSY.getInt());
				try {
					if(device.getWidth() == 0 || device.getHeight() == 0) {
						RawImage rImage = iDevice.getScreenshot();
						device.setWidth(rImage.width);
						device.setHeight(rImage.height);
						device.setOs(iDevice.getProperty(IDevice.PROP_BUILD_VERSION));
					}
				} catch (Exception e) {}
				dd.update(device);
				return device;
			}
		}
		return null;
	}
	
	public void release(int id) {
		Device device = dd.findById(id);
		if(device != null && device.getState() != Status.DeviceStatus.FREE.getInt()) {
			// clear temp folder
			File temp = new File(Path.temp(device.getSn()));
			if(temp.exists()) {
				for(File png: temp.listFiles()) {
					png.delete();
				}
			}
			// dispose device
			device.setState(Status.DeviceStatus.FREE.getInt());
			dd.update(device);
			IDevice iDevice = Adb.getInstance().getBackend().findAttacedDevice(device.getSn());
			if(iDevice != null && iDevice.getState() != DeviceState.ONLINE) {
				iDevice.setState(DeviceState.ONLINE);
			}
		}
	}
	
	public String screenshot(int deviceId, float screenQ, HttpSession session) throws TimeoutException, AdbCommandRejectedException, IOException, InterruptedException, SyncException {
		Device device = dd.findById(deviceId);
		if(device != null) {
			IDevice iDevice = (IDevice) Adb.getInstance().getBackend().findAttacedDevice(device.getSn());
			String name = System.currentTimeMillis() + ".png";
			String path = Path.temp(device.getSn() + File.separator + name);
			File file = Adb.screencap(iDevice, path);
			BufferedImage bImage = ImageIO.read(file);
			session.setAttribute(Molab.SESSION_CR_SCREENSHOT, bImage);
			if(bImage != null) {
				// zoom
				bImage = ImageUtil.zoom(bImage, screenQ);
				// compare
				int y = (int) (bImage.getHeight() * 0.04);
				BufferedImage pImage = getScreenshot(session);
				if (pImage != null && ImageUtil.isSame(
						pImage.getSubimage(0, y, pImage.getWidth(), pImage.getHeight() - y),
						bImage.getSubimage(0, y, bImage.getWidth(), bImage.getHeight() - y), 1.0)) {
					file.deleteOnExit();
				} else {
					
					ImageIO.write(bImage, "png", file);
					return "/admin/temp/" + device.getSn() + "/" + name;
				}
			}
		}
		return "";
	}
	
	private BufferedImage getScreenshot(HttpSession session) {
		if(session != null) {
			Object obj = session.getAttribute(Molab.SESSION_CR_SCREENSHOT);
			if(obj != null && obj instanceof BufferedImage) {
				return (BufferedImage) obj;
			}
		}
		return null;
	}
	
	public int goon(int deviceId, int testcaseId) {
		Device device = dd.findById(deviceId);
		if(device != null) {
			IDevice iDevice = (IDevice) Adb.getInstance().getBackend().findAttacedDevice(device.getSn());
			Testcase t = td.findById(testcaseId);
			if(t != null) {
				Application app = ad.findById(t.getApplicationId());
				if(app != null) {
					// try to launch MAIN activity
					try {
						startActivity(iDevice, app);
						return Status.Common.SUCCESS.getInt();
					} catch (Exception e) {
						log.severe(e.getMessage());
						return Status.RecordStatus.APP_START_FAILURE.getInt();
					}
				}
			}
		}
		return Status.Common.ERROR.getInt();
	}
	
	public String install(int projectId, String testcaseName, 
			int deviceId, Application app, HttpSession session) {
		Device device = dd.findById(deviceId);
		String result = "INSTALL_FAILURE";
		if(device != null) {
			IDevice iDevice = (IDevice) Adb.getInstance().getBackend().findAttacedDevice(device.getSn());
			String apk = Path.apk(app.getAliasname());
			try {
				result = iDevice.installPackage(apk, "/data/local/tmp/", true);
				log.info("Install result is : " + result);
				if(result == null) {
					result = "INSTALL_SUCCESS";
					// first build the testcase in order to get the 'SESSION_CR_TESTCASE_ID'
					Testcase t = new Testcase();
					t.setProjectId(projectId);
					t.setApplicationId(app.getId());
					t.setName(testcaseName);
					t.setTestcase("");
					
					RawImage rImage = iDevice.getScreenshot();
					t.setWidth(rImage.width);
					t.setHeight(rImage.height);
					
					t.setCreateTime(System.currentTimeMillis());
					t.setState(Status.TestcaseStatus.START.getInt());
					int testcaseId = (Integer) td.save(t);
					session.setAttribute(Molab.SESSION_CR_TESTCASE_ID, testcaseId);
					
					// try to launch MAIN activity
					startActivity(iDevice, app);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				session.setAttribute(Molab.SESSION_CR_INSTALL_RESULT, result);
			}
		}
		return result;
	}
	
	/**
	 * launch MAIN activity */
	private void startActivity(IDevice iDevice, Application app) 
			throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {
		iDevice.shell("am start -W -n "
				+ app.getPackagename() + "/"
				+ app.getStartactivity());
	}
	
	public List<Script> op(int deviceId, int testcaseId, boolean recordable, String action,
			HttpServletRequest request) {
		List<Script> scriptList = null;
		Device device = dd.findById(deviceId);
		if(device != null) {
			IDevice iDevice = (IDevice) Adb.getInstance().getBackend().findAttacedDevice(device.getSn());
			UiAutomatorModel model = findModel(iDevice);
			
			if(action.equalsIgnoreCase("tap")) {
				scriptList = tap(model, iDevice, testcaseId, recordable, request);
			} else if(action.equalsIgnoreCase("text")) {
				scriptList = text(iDevice, testcaseId, recordable, request);
			} else if(action.equalsIgnoreCase("swipe")) {
				scriptList = swipe(device, iDevice, testcaseId, recordable, request);
			} else if(action.equalsIgnoreCase("keyevent")) {
				scriptList = keyevent(iDevice, testcaseId, recordable, request);
			}
		}
		return scriptList;
	}
	
	public List<Script> tap(UiAutomatorModel model, IDevice iDevice, 
			int testcaseId, boolean recordable, 
			HttpServletRequest request) {
		List<Script> scriptList = null;
		// get parameters
		String params = request.getParameter("params");
		String[] params_s = params.split(" ");
		int x = Integer.parseInt(params_s[0]);
		int y = Integer.parseInt(params_s[1]);
		
		// parse ui if necessary -- uiautomator!!
		if(testcaseId != 0 && recordable) { // record operation
			List<UiNode> foundNodes = UiAutomatorUtil.findNodes(model, x, y);
			if(foundNodes != null && foundNodes.size() > 0) {
				scriptList = new ArrayList<Script>();
				int step = findStep(testcaseId);
				for(UiNode node : foundNodes) {
					Script script = new Script();
					script.setTestcaseId(testcaseId);
					script.setStep(step);
					script.setMid(node.getAttribute("resource-id"));
					script.setMclass(node.getAttribute("class"));
					script.setMtext(node.getAttribute("text"));
					script.setMleft(node.x);
					script.setMtop(node.y);
					script.setMwidth(node.width);
					script.setMheight(node.height);
					script.setMpath(node.mPath);
					scriptList.add(script);
				}
			}
		}
		
		// execute action any way
		try {
			iDevice.shell(String.format("input tap %d %d", x, y));
		} catch (Exception e) {}
		
		return scriptList;
	}
	
	private UiAutomatorModel findModel(IDevice device) {
		try {
			return UiAutomatorHelper.takeModel(device, false);
        } catch (UiAutomatorException e) {
            return null;
        }
	}
	
	public List<Script> text(IDevice iDevice, int testcaseId, boolean recordable, 
			HttpServletRequest request) {
		List<Script> scriptList = null;
		// get parameters
		String typeText = request.getParameter("params");
		
		if(testcaseId != 0 && recordable) { // record operation
			scriptList = new ArrayList<Script>();
			Script script = new Script();
			script.setTestcaseId(testcaseId);
			script.setStep(findStep(testcaseId));
			scriptList.add(script);
		}
		
		// execute action any way
		try {
			iDevice.shell(String.format("input text %s", typeText));
		} catch (Exception e) {}
		
		return scriptList;
	}
	
	public List<Script> swipe(Device device, IDevice iDevice, int testcaseId, boolean recordable, 
			HttpServletRequest request) {
		List<Script> scriptList = null;
		// get parameters
		String param = request.getParameter("params");
		int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
		if(param.indexOf(" ") > -1) {
			String[] params = param.split(" ");
			x1 = Integer.parseInt(params[0]);
			y1 = Integer.parseInt(params[1]);
			x2 = Integer.parseInt(params[2]);
			y2 = Integer.parseInt(params[3]);
		} else {
			switch(param) {
				case "left":
					x1 = device.getWidth() - 50;
					x2 = 50;
					y1 = y2 = device.getHeight() / 2;
					break;
				case "right":
					x1 = 50;
					x2 = device.getWidth() - 50;
					y1 = y2 = device.getHeight() / 2;
					break;
				case "up":
					x1 = x2 = device.getWidth() / 2;
					y1 = device.getHeight() - 50;
					y2 = 50;
					break;
				case "down":
					x1 = x2 = device.getWidth() / 2;
					y1 = 50;
					y2 = device.getHeight() - 50;
					break;
				default:
					break;
			}
		}
		
		if(testcaseId != 0 && recordable) { // record operation
			scriptList = new ArrayList<Script>();
			Script script = new Script();
			script.setTestcaseId(testcaseId);
			script.setStep(findStep(testcaseId));
			scriptList.add(script);
		}
		
		// execute action any way
		try {
			iDevice.shell(String.format("input swipe %d %d %d %d", x1, y1, x2, y2));
		} catch (Exception e) {}
		
		return scriptList;
	}
	
	public List<Script> keyevent(IDevice iDevice, int testcaseId, boolean recordable, 
			HttpServletRequest request) {
		List<Script> scriptList = null;
		// get parameters
		String keyCode = request.getParameter("params");
		
		if(testcaseId != 0 && recordable) { // record operation
			scriptList = new ArrayList<Script>();
			Script script = new Script();
			script.setTestcaseId(testcaseId);
			script.setStep(findStep(testcaseId));
			scriptList.add(script);
		}
		
		// execute action any way
		try {
			iDevice.shell(String.format("input keyevent %s", keyCode));
		} catch (Exception e) {}
		
		return scriptList;
	}
	
	private int findStep(int testcaseId) {
		// find current step
		List<Script> scriptList = sd.findAll(testcaseId);
		int step = 1;
		if(scriptList != null && scriptList.size() > 0) {
			Script instance = scriptList.get(0);
			step = instance.getStep() + 1;
		}
		return step;
	}
	
	@SuppressWarnings("unchecked")
	public int record(int deviceId, int num, String action, String params, String note, 
			HttpSession session) throws SyncException, IOException, AdbCommandRejectedException, TimeoutException {
		Object testcaseObj = session.getAttribute(Molab.SESSION_CR_TESTCASE_ID);
		Object scriptListObj = session.getAttribute(Molab.SESSION_CR_CUR_SCRIPT);
		if(testcaseObj != null && scriptListObj != null) {
			List<Script> scriptList = (ArrayList<Script>) scriptListObj;
			Script script = scriptList.get(num); // number of widgets
			script.setAction(action);
			script.setParams(params);
			script.setNote(note);
			script.setCreateTime(System.currentTimeMillis());
			// screenshot
			BufferedImage bImage = getScreenshot(session);
			int testcaseId = Integer.parseInt(testcaseObj.toString());
			String name = testcaseId + "." + script.getStep() + ".png";
			String path = Path.crSs(name);
			ImageIO.write(bImage, "png", new File(path));
			script.setScreenshot(name);
			
			sd.save(script);
			return Status.Common.SUCCESS.getInt();
		}
		return Status.Common.ERROR.getInt();
	}
	
}
