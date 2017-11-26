package molab.main.java.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import molab.main.java.component.CsRunnerComponent;
import molab.main.java.dao.ApplicationDao;
import molab.main.java.dao.CsDispatcherDao;
import molab.main.java.dao.CsRunnerDao;
import molab.main.java.dao.CsScreenshotDao;
import molab.main.java.dao.DeviceDao;
import molab.main.java.dao.ManufacturerDao;
import molab.main.java.dao.ModelDao;
import molab.main.java.entity.CsDispatcher;
import molab.main.java.entity.CsRunner;
import molab.main.java.entity.CsScreenshot;
import molab.main.java.util.Path;
import molab.main.java.util.Status;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;

@Service
public class CsRunnerService {
	
	@Autowired
	private DeviceDao devDao;
	
	@Autowired
	private CsDispatcherDao disDao;
	
	@Autowired
	private CsRunnerDao dao;
	
	@Autowired
	private CsScreenshotDao ssd;
	
	@Autowired
	private ApplicationDao ad;
	
	@Autowired
	private ModelDao modDao;
	
	@Autowired
	private ManufacturerDao manDao;
	
	public void restart(int id) {
		CsRunner runner = dao.findById(id);
		if(runner != null) {
			runner.setInstallResult(null);
			runner.setLoadResult(null);
			runner.setLogcat(null);
			runner.setState(Status.RunnerStatus.START.getInt());
			dao.update(runner);
			
			// handle dispatcher
			String hql = String.format(
					"UPDATE CsDispatcher SET state=%d WHERE id=%d",
					Status.DispatcherStatus.START.getInt(),
					runner.getDispatcherId());
			disDao.executeUpdate(hql);
			// handle screenshot
			hql = String.format("DELETE FROM CsScreenshot WHERE runnerId=%d", id);
			ssd.executeUpdate(hql);
		}
	}
	
	public void stop(int id) {
		CsRunner runner = dao.findById(id);
		if(runner != null) {
			runner.setState(Status.RunnerStatus.END.getInt());
			dao.update(runner);
		}
	}
	
	public CsRunner save(int dispatcherId, int deviceId) {
		CsRunner runner = new CsRunner();
		runner.setDispatcherId(dispatcherId);
		runner.setDeviceId(deviceId);
		runner.setState(Status.RunnerStatus.START.getInt());
		dao.save(runner);
		return runner;
	}
	
	public List<CsRunnerComponent> findAll(int dispatcherId) {
		List<CsRunner> runnerList = dao.findByDispatcherId(dispatcherId);//dao.findAll(dispatcherId);
		if(runnerList != null && runnerList.size() > 0) {
			List<CsRunnerComponent> rcList = new ArrayList<CsRunnerComponent>();
			for(CsRunner runner : runnerList) {
				CsRunnerComponent rc = new CsRunnerComponent();
				rc.setRunner(runner);
				rc.setDevice(devDao.findById(runner.getDeviceId()));
				rcList.add(rc);
			}
			return rcList;
		}
		return null;
	}
	
	public CsRunnerComponent findDetail(int runnerId) throws SQLException, IOException {
		CsRunner runner = dao.findById(runnerId);
		if(runner != null) {
			CsDispatcher dispatcher = disDao.findById(runner.getDispatcherId());
			if(dispatcher != null) {
				CsRunnerComponent rc = new CsRunnerComponent();
				rc.setRunner(runner);
				Blob logcat = runner.getLogcat();
				if(logcat != null) {
					rc.setLogcat(new String(logcat.getBytes(1L, (int) logcat.length())));
				}
				rc.setDevice(devDao.findById(runner.getDeviceId()));
				List<CsScreenshot> ssList = ssd.findByRunnerId(runnerId);
				if(ssList != null && ssList.size() > 0) {
					for(CsScreenshot ss : ssList) {
						String newSs = transform(ss.getId(), ss.getImage());
						if(newSs != null) {
							ss.setImage(newSs);
						}
					}
					rc.setSsList(ssList);
				}
				return rc;
			}
		}
		return null;
	}
	
	private String transform(int id, String data) throws IOException {
		if(data.length() < 50) {
			return null;
		}
		
		byte[] bytes = new BASE64Decoder().decodeBuffer(data);
        // 生成png图片
		String name = id + ".png";
		String png = Path.csSs(name);
		OutputStream out = new FileOutputStream(png);
        out.write(bytes);
        out.flush();
        out.close();
		return "/admin/upload/csSs/" + name;
	}
	
	public void temporarilyDelete(int id) {
		// screenshot
		String hql = String.format(
				"update CsScreenshot set state=%d where runnerId in (select id from CsRunner where dispatcherId=%d)",
				Status.ScreenshotStatus.REMOVED.getInt(), id);
		ssd.executeUpdate(hql);
		// runner
		hql = String.format(
				"update CsRunner set state=%d where id=%d",
				Status.RunnerStatus.REMOVED.getInt(), id);
		dao.executeUpdate(hql);
	}
	
	public void batchTemporarilyDelete(String[] ids) {
		// screenshot
		String hql = String.format(
				"update CsScreenshot set state=%d where runnerId in (select id from CsRunner where dispatcherId in (%s))",
				Status.ScreenshotStatus.REMOVED.getInt(), StringUtils.join(ids, ","));
		ssd.executeUpdate(hql);
		// runner
		hql = String.format(
				"update CsRunner set state=%d where id in (%s)",
				Status.RunnerStatus.REMOVED.getInt(), StringUtils.join(ids, ","));
		dao.executeUpdate(hql);
	}

	public void permanentlyDelete(int id) {
		// screenshot
		String hql = String.format(
				"delete from CsScreenshot where runnerId in (select id from CsRunner where dispatcherId=%d)", id);
		ssd.executeUpdate(hql);
		// runner
		hql = String.format(
				"delete from CsRunner where id=%d", id);
		dao.executeUpdate(hql);
	}
	
	public void batchPermanentlyDelete(String[] ids) {
		// screenshot
		String hql = String.format(
				"delete from CsScreenshot where runnerId in (select id from CsRunner where dispatcherId in (%s))",
				StringUtils.join(ids, ","));
		ssd.executeUpdate(hql);
		// runner
		hql = String.format(
				"delete from CsRunner where id in (%s)",
				StringUtils.join(ids, ","));
		dao.executeUpdate(hql);
	}
	
}
