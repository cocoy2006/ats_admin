package molab.main.java.util.init;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import molab.main.java.entity.Device;
import molab.main.java.util.Path;
import molab.main.java.util.Status.CommandType;
import molab.main.java.util.shell.Result;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.InstallException;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.SyncException;
import com.android.ddmlib.TimeoutException;
import com.android.monkeyrunner.adb.AdbBackend;

public class Adb {

	private static final Logger log = Logger.getLogger(Adb.class.getName());
	private static final String TEMP_PNG = "/data/local/tmp/tmp.png";
	private static final String SCREENCAP = "/system/bin/screencap -p " + TEMP_PNG;
	private static final String TEMP_TXT = "/data/local/tmp/tmp.txt";
	private static final String GET_PROP_TXT = "getprop > " + TEMP_TXT;
	private static final String GET_PROP = "getprop %s";
	
	private static Adb adb = null;
	private static AdbBackend backend = null;

	private Adb() {
		backend = new AdbBackend();
	}

	public static Adb getInstance() {
		if (adb == null) {
			synchronized (Adb.class) {
				adb = new Adb();
			}
		}
		return adb;
	}
	
	public AdbBackend getBackend() {
		return backend;
	}
	
	public static void getprop(IDevice iDevice, Device device) throws IOException, SyncException, AdbCommandRejectedException, TimeoutException {
		executeSync(iDevice, CommandType.SHELL, GET_PROP_TXT);
		String fileName = device.getServer() + "." + device.getPort() + "." + device.getSn() + ".txt";
		String path = Path.device(fileName);
		iDevice.pullFile(TEMP_TXT, path);
	}
	
	public static String getprop(IDevice iDevice, String prop) {
		try {
			if(prop == null) {
				prop = IDevice.PROP_BUILD_DISPLAY;
			}
			Result result = executeSync(iDevice, CommandType.SHELL, String.format(GET_PROP, prop));
			if(result != null && result.getResult() != null && !result.getResult().trim().equals("")) {
				return result.getResult().trim();
			}
		} catch (IOException e) {
			log.severe(String.format("Property %s cannot be found.", prop));
		}
		return null;
	}
	
	public static File screencap(IDevice iDevice, String path) throws IOException, SyncException, AdbCommandRejectedException, TimeoutException {
		screencap(iDevice);
		iDevice.pullFile(TEMP_PNG, path);
		return new File(path);
	}
	
	public static Result screencap(IDevice iDevice) throws IOException {
		return executeSync(iDevice, CommandType.SHELL, SCREENCAP);
	}
	
	public static Result executeSync(IDevice iDevice, CommandType cmdType, String cmd) throws IOException {
		synchronized(Adb.class) {
			long start = System.currentTimeMillis();
			Result result = null;
			try {
				String r = execute(iDevice, cmdType, cmd);
				long time = System.currentTimeMillis() - start;
				result = new Result(r, time);
			} catch (Exception e) {
				restartAdb(iDevice);
				throw new IOException("Adb crash.");
			}
			return result;
		}
	}
	
	private static String execute(IDevice iDevice, CommandType cmdType, String param) 
			throws InstallException, SyncException, IOException, AdbCommandRejectedException, TimeoutException, ShellCommandUnresponsiveException {
		String result = null;
		switch(cmdType) {
			case INSTALL:
				result = iDevice.installPackage(param, "/data/local/tmp/", true);
				break;
			case SHELL:
				result = iDevice.shell(param);
				break;
			default:
				break;
		}
		return result;
	}
	
	private static void restartAdb(IDevice iDevice) {
		log.severe("Adb has down, try to reboot device " + iDevice.getSerialNumber());
		try {
			iDevice.shell("reboot");
			Thread.sleep(60000);
		} catch (IOException | InterruptedException | TimeoutException | AdbCommandRejectedException | ShellCommandUnresponsiveException e) {}
		log.severe("Device has restarted.");
	}

}
