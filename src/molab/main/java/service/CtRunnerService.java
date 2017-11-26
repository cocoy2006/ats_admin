package molab.main.java.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import molab.main.java.component.CtReportAnalysisComponent;
import molab.main.java.component.CtReportSummaryComponent;
import molab.main.java.component.CtRunnerComponent;
import molab.main.java.dao.ApplicationDao;
import molab.main.java.dao.CtDispatcherDao;
import molab.main.java.dao.CtRunnerDao;
import molab.main.java.dao.CtScreenshotDao;
import molab.main.java.dao.CtSubRunnerDao;
import molab.main.java.dao.DeviceDao;
import molab.main.java.dao.LogcatDao;
import molab.main.java.entity.CtDispatcher;
import molab.main.java.entity.CtRunner;
import molab.main.java.entity.CtScreenshot;
import molab.main.java.util.Path;
import molab.main.java.util.Status;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;

@Service
public class CtRunnerService {
	
	@Autowired
	private DeviceDao dd;
	
	@Autowired
	private CtDispatcherDao disDao;
	
	@Autowired
	private CtRunnerDao dao;
	
	@Autowired
	private CtScreenshotDao ssd;
	
	@Autowired
	private CtSubRunnerDao srd;
	
	@Autowired
	private ApplicationDao ad;
	
	@Autowired
	private LogcatDao ld;
	
	public void restart(int id) {
		CtRunner runner = dao.findById(id);
		if(runner != null) {
			runner.setAverageCpu(0F);
			runner.setAverageMemory(0L);
			runner.setInstallTime(0L);
			runner.setInstallResult(null);
			runner.setLoadTime(0L);
			runner.setUninstallTime(0L);
			runner.setLogcat(null);
			runner.setState(Status.RunnerStatus.START.getInt());
			dao.update(runner);
			
			// handle dispatcher
			String hql = String.format(
					"update CtDispatcher set state=%d where id=%d",
					Status.DispatcherStatus.START.getInt(),
					runner.getDispatcherId());
			disDao.executeUpdate(hql);
			// handle screenshot
			hql = String.format("delete from CtScreenshot where runnerId=%d", id);
			ssd.executeUpdate(hql);
			// handle subrunner
			hql = String.format("delete from CtSubRunner where runnerId=%d", id);
			srd.executeUpdate(hql);
			// handle logcat
			hql = String.format("delete from Logcat where runnerId=%d", id);
			ld.executeUpdate(hql);
		}
	}

	public void stop(int id) {
		CtRunner runner = dao.findById(id);
		if(runner != null) {
			runner.setState(Status.RunnerStatus.END.getInt());
			dao.update(runner);
		}
	}
	
	public CtRunner save(int dispatcherId, int deviceId) {
		CtRunner runner = new CtRunner();
		runner.setDispatcherId(dispatcherId);
		runner.setDeviceId(deviceId);
		runner.setState(Status.RunnerStatus.START.getInt());
		dao.save(runner);
		return runner;
	}
	
	public List<CtRunner> findAll(int dispatcherId) {
		String hql = "from CtRunner where dispatcherId=? and state!=?";
		return dao.find(hql, dispatcherId, Status.RunnerStatus.REMOVED.getInt());
	}
	
	public List<CtRunner> findAll(int dispatcherId, int state) {
		String hql = "from CtRunner where dispatcherId=? and state=?";
		return dao.find(hql, dispatcherId, state);
	}
	
	public List<CtRunnerComponent> findAll(List<CtRunner> runnerList) {
		if(runnerList != null && runnerList.size() > 0) {
			List<CtRunnerComponent> rcList = new ArrayList<CtRunnerComponent>();
			for(CtRunner runner : runnerList) {
				CtRunnerComponent rc = new CtRunnerComponent();
				rc.setRunner(runner);
				rc.setDevice(dd.findById(runner.getDeviceId()));
				List<CtScreenshot> ssList = ssd.findByRunnerId(runner.getId());
				if(ssList != null) {
					rc.setSsCount(ssList.size());
				}
				rcList.add(rc);
			}
			return rcList;
		}
		return null;
	}
	
	public CtReportSummaryComponent findSummary(List<CtRunner> runnerList) {
		if(runnerList != null && runnerList.size() > 0) {
			int installFailureCount = 0, loadFailureCount = 0, 
					uninstallFailureCount = 0, passCount = 0;
			for(CtRunner runner : runnerList) {
				if(runner.getInstallTime() == 0) {
					installFailureCount++;
					continue;
				}
				if(runner.getLoadTime() == 0) {
					loadFailureCount++;
					continue;
				}
				if(runner.getUninstallTime() == 0) {
					uninstallFailureCount++;
					continue;
				}
				passCount++;
			}
			CtReportSummaryComponent rsc = new CtReportSummaryComponent();
			rsc.setTotal(runnerList.size());
			rsc.setInstallFailureCount(installFailureCount);
			rsc.setLoadFailureCount(loadFailureCount);
			rsc.setUninstallFailureCount(uninstallFailureCount);
			rsc.setPassCount(passCount);
			return rsc;
		}
		return null;
	}
	
	public CtReportAnalysisComponent findAnalysis(List<CtRunner> runnerList) {
		if(runnerList != null && runnerList.size() > 0) {
			long avgInstallTime = 0,
					maxInstallTime = Long.MIN_VALUE,
					minInstallTime = Long.MAX_VALUE;
			int maxInstallTimeRunnerId = Integer.MIN_VALUE,
					minInstallTimeRunnerId = Integer.MAX_VALUE;
			
			long avgLoadTime = 0,
					maxLoadTime = Long.MIN_VALUE,
					minLoadTime = Long.MAX_VALUE;
			int maxLoadTimeRunnerId = Integer.MIN_VALUE,
					minLoadTimeRunnerId = Integer.MAX_VALUE;
			
			float avgCpu = 0,
					maxCpu = -1,
					minCpu = Float.MAX_VALUE;
			int maxCpuRunnerId = Integer.MIN_VALUE,
					minCpuRunnerId = Integer.MAX_VALUE;
			
			long avgMemory = 0,
					maxMemory = Long.MIN_VALUE,
					minMemory = Long.MAX_VALUE;
			int maxMemoryRunnerId = Integer.MIN_VALUE, 
					minMemoryRunnerId = Integer.MAX_VALUE;
			
			long avgUptraffic = 0,
					maxUptraffic = Long.MIN_VALUE, 
					minUptraffic = Long.MAX_VALUE;
			int maxUptrafficRunnerId = Integer.MIN_VALUE, 
					minUptrafficRunnerId = Integer.MAX_VALUE;
			
			long avgDowntraffic = 0,
					maxDowntraffic = Long.MIN_VALUE, 
					minDowntraffic = Long.MAX_VALUE;
			int maxDowntrafficRunnerId = Integer.MIN_VALUE, 
					minDowntrafficRunnerId = Integer.MAX_VALUE;
			
			for(CtRunner runner : runnerList) {
				// install time
				long installTime = runner.getInstallTime();
				avgInstallTime += installTime;
				if(installTime > maxInstallTime) {
					maxInstallTime = installTime;
					maxInstallTimeRunnerId = runner.getId();
				}
				if(installTime < minInstallTime) {
					minInstallTime = installTime;
					minInstallTimeRunnerId = runner.getId();
				}
				// load time
				long loadTime = runner.getLoadTime();
				avgLoadTime += loadTime;
				if(loadTime > maxLoadTime) {
					maxLoadTime = loadTime;
					maxLoadTimeRunnerId = runner.getId();
				}
				if(loadTime < minLoadTime) {
					minLoadTime = loadTime;
					minLoadTimeRunnerId = runner.getId();
				}
				// cpu
				float cpu = runner.getAverageCpu();
				avgCpu += cpu;
				if(Float.compare(cpu, maxCpu) > 0) {
					maxCpu = cpu;
					maxCpuRunnerId = runner.getId();
				}
				if(Float.compare(cpu, minCpu) < 0) {
					minCpu = cpu;
					minCpuRunnerId = runner.getId();
				}
				// memory
				long memory = runner.getAverageMemory();
				avgMemory += memory;
				if(memory > maxMemory) {
					maxMemory = memory;
					maxMemoryRunnerId = runner.getId();
				}
				if(memory < minMemory) {
					minMemory = memory;
					minMemoryRunnerId = runner.getId();
				}
				// uptraffic
				long uptraffic = runner.getUptraffic();
				avgUptraffic += uptraffic;
				if(uptraffic > maxUptraffic) {
					maxUptraffic = uptraffic;
					maxUptrafficRunnerId = runner.getId();
				}
				if(uptraffic < minUptraffic) {
					minUptraffic = uptraffic;
					minUptrafficRunnerId = runner.getId();
				}
				// downtraffic
				long downtraffic = runner.getDowntraffic();
				avgDowntraffic += downtraffic;
				if(downtraffic > maxDowntraffic) {
					maxDowntraffic = downtraffic;
					maxDowntrafficRunnerId = runner.getId();
				}
				if(downtraffic < minDowntraffic) {
					minDowntraffic = downtraffic;
					minDowntrafficRunnerId = runner.getId();
				}
			}
			try {
				CtReportAnalysisComponent analysis = new CtReportAnalysisComponent();
				int size = runnerList.size();
				analysis.setAvgInstallTime(avgInstallTime / size);
				analysis.setMaxInstallTime(maxInstallTime);
				analysis.setMinInstallTime(minInstallTime);
				CtRunner runner = dao.findById(maxInstallTimeRunnerId);
				analysis.setMaxInstallTimeRunner(runner);
				analysis.setMaxInstallTimeDevice(dd.findById(runner.getDeviceId()));
				runner = dao.findById(minInstallTimeRunnerId);
				analysis.setMinInstallTimeRunner(runner);
				analysis.setMinInstallTimeDevice(dd.findById(runner.getDeviceId()));
				// load time
				analysis.setAvgLoadTime(avgLoadTime / size);
				analysis.setMaxLoadTime(maxLoadTime);
				analysis.setMinLoadTime(minLoadTime);
				runner = dao.findById(maxLoadTimeRunnerId);
				analysis.setMaxLoadTimeRunner(runner);
				analysis.setMaxLoadTimeDevice(dd.findById(runner.getDeviceId()));
				runner = dao.findById(minLoadTimeRunnerId);
				analysis.setMinLoadTimeRunner(runner);
				analysis.setMinLoadTimeDevice(dd.findById(runner.getDeviceId()));
				// cpu
				analysis.setAvgCpu(avgCpu / size);
				analysis.setMaxCpu(maxCpu);
				analysis.setMinCpu(minCpu);
				runner = dao.findById(maxCpuRunnerId);
				analysis.setMaxCpuRunner(runner);
				analysis.setMaxCpuDevice(dd.findById(runner.getDeviceId()));
				runner = dao.findById(minCpuRunnerId);
				analysis.setMinCpuRunner(runner);
				analysis.setMinCpuDevice(dd.findById(runner.getDeviceId()));
				// memory
				analysis.setAvgMemory(avgMemory / size);
				analysis.setMaxMemory(maxMemory);
				analysis.setMinMemory(minMemory);
				runner = dao.findById(maxMemoryRunnerId);
				analysis.setMaxMemoryRunner(runner);
				analysis.setMaxMemoryDevice(dd.findById(runner.getDeviceId()));
				runner = dao.findById(minMemoryRunnerId);
				analysis.setMinMemoryRunner(runner);
				analysis.setMinMemoryDevice(dd.findById(runner.getDeviceId()));
				// uptraffic
				analysis.setAvgUptraffic(avgUptraffic / size);
				analysis.setMaxUptraffic(maxUptraffic);
				analysis.setMinUptraffic(minUptraffic);
				runner = dao.findById(maxUptrafficRunnerId);
				analysis.setMaxUptrafficRunner(runner);
				analysis.setMaxUptrafficDevice(dd.findById(runner.getDeviceId()));
				runner = dao.findById(minUptrafficRunnerId);
				analysis.setMinUptrafficRunner(runner);
				analysis.setMinUptrafficDevice(dd.findById(runner.getDeviceId()));
				// downtraffic
				analysis.setAvgDowntraffic(avgDowntraffic / size);
				analysis.setMaxDowntraffic(maxDowntraffic);
				analysis.setMinDowntraffic(minDowntraffic);
				runner = dao.findById(maxDowntrafficRunnerId);
				analysis.setMaxDowntrafficRunner(runner);
				analysis.setMaxDowntrafficDevice(dd.findById(runner.getDeviceId()));
				runner = dao.findById(minDowntrafficRunnerId);
				analysis.setMinDowntrafficRunner(runner);
				analysis.setMinDowntrafficDevice(dd.findById(runner.getDeviceId()));
				// done
				return analysis;
			} catch(Exception e) {
				System.err.println(e.getMessage());
			}
		}
		return null;
	}

	public int findAnalysisCount(int dispatcherId, String propertyName, Object minValue, Object maxValue) {
		String hql = "from CtRunner where dispatcherId=? and " + propertyName + " between ? and ?";
		List<CtRunner> list = dao.find(hql, dispatcherId, propertyName, minValue, maxValue);
		if(list != null && list.size() > 0) {
			return list.size();
		}
		return 0;
	}
	
	public List<CtRunner> findWorstFive(int dispatcherId, String propertyName) {
		String hql = "from CtRunner where dispatcherId=? order by " + propertyName + " desc limit 5";
		return dao.find(hql, dispatcherId);
	}
	
	public CtRunnerComponent findDetail(int runnerId) throws SQLException, IOException {
		CtRunner runner = dao.findById(runnerId);
		if(runner != null) {
			CtDispatcher dispatcher = disDao.findById(runner.getDispatcherId());
			if(dispatcher != null) {
				CtRunnerComponent rc = new CtRunnerComponent();
				rc.setRunner(runner);
//				Blob logcat = runner.getLogcat();
//				if(logcat != null) {
//					rc.setLogcat(new String(logcat.getBytes(1L, (int) logcat.length())));
//				}
				rc.setLogcatList(ld.findByRunnerIdOrderByType(runner.getId()));
				rc.setApplication(ad.findById(dispatcher.getApplicationId()));
				rc.setDevice(dd.findById(runner.getDeviceId()));
				rc.setSrList(srd.findByRunnerId(runnerId));
				List<CtScreenshot> ssList = ssd.findByRunnerId(runnerId);
				if(ssList != null && ssList.size() > 0) {
					for(CtScreenshot ss : ssList) {
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
		String png = Path.ctSs(name);
		OutputStream out = new FileOutputStream(png);
        out.write(bytes);
        out.flush();
        out.close();
		return "/admin/upload/ctSs/" + name;
	}

	public void temporarilyDelete(int id) {
		// screenshot
		String hql = String.format(
				"update CtScreenshot set state=%d where runnerId in (select id from CtRunner where dispatcherId=%d)",
				Status.ScreenshotStatus.REMOVED.getInt(), id);
		ssd.executeUpdate(hql);
		// sub runner
		hql = String.format(
				"update CtSubRunner set state=%d where runnerId in (select id from CtRunner where dispatcherId=%d)",
				Status.SubRunnerStatus.REMOVED.getInt(), id);
		srd.executeUpdate(hql);
		// runner
		hql = String.format(
				"update CtRunner set state=%d where id=%d",
				Status.RunnerStatus.REMOVED.getInt(), id);
		dao.executeUpdate(hql);
	}
	
	public void batchTemporarilyDelete(String[] ids) {
		// screenshot
		String hql = String.format(
				"update CtScreenshot set state=%d where runnerId in (select id from CtRunner where dispatcherId in (%s))",
				Status.ScreenshotStatus.REMOVED.getInt(), StringUtils.join(ids, ","));
		ssd.executeUpdate(hql);
		// sub runner
		hql = String.format(
				"update CtSubRunner set state=%d where runnerId in (select id from CtRunner where dispatcherId in (%s))",
				Status.SubRunnerStatus.REMOVED.getInt(), StringUtils.join(ids, ","));
		srd.executeUpdate(hql);
		// runner
		hql = String.format(
				"update CtRunner set state=%d where id in (%s)",
				Status.RunnerStatus.REMOVED.getInt(), StringUtils.join(ids, ","));
		dao.executeUpdate(hql);
	}

	public void permanentlyDelete(int id) {
		// screenshot
		String hql = String.format(
				"delete from CtScreenshot where runnerId in (select id from CtRunner where dispatcherId=%d)", id);
		ssd.executeUpdate(hql);
		// sub runner
		hql = String.format(
				"delete from CtSubRunner where runnerId in (select id from CtRunner where dispatcherId=%d)", id);
		srd.executeUpdate(hql);
		// runner
		hql = String.format(
				"delete from CtRunner where id=%d", id);
		dao.executeUpdate(hql);
	}
	
	public void batchPermanentlyDelete(String[] ids) {
		// screenshot
		String hql = String.format(
				"delete from CtScreenshot where runnerId in (select id from CtRunner where dispatcherId in (%s))",
				StringUtils.join(ids, ","));
		ssd.executeUpdate(hql);
		// sub runner
		hql = String.format(
				"delete from CtSubRunner where runnerId in (select id from CtRunner where dispatcherId in (%s))",
				StringUtils.join(ids, ","));
		srd.executeUpdate(hql);
		// runner
		hql = String.format(
				"delete from CtRunner where id in (%s)",
				StringUtils.join(ids, ","));
		dao.executeUpdate(hql);
	}
	
}
