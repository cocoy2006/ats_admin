package molab.main.java.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import molab.main.java.component.CtReportSummaryComponent;
import molab.main.java.component.ICtRunnerComponent;
import molab.main.java.dao.ApplicationDao;
import molab.main.java.dao.ICtDeviceDao;
import molab.main.java.dao.ICtDispatcherDao;
import molab.main.java.dao.ICtRunnerDao;
import molab.main.java.dao.ICtScreenshotDao;
import molab.main.java.dao.ManufacturerDao;
import molab.main.java.dao.ModelDao;
import molab.main.java.entity.ICtDispatcher;
import molab.main.java.entity.ICtRunner;
import molab.main.java.util.Status;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ICtRunnerService {
	
	@Autowired
	private ICtDeviceDao devDao;
	
	@Autowired
	private ICtDispatcherDao disDao;
	
	@Autowired
	private ICtRunnerDao dao;
	
	@Autowired
	private ICtScreenshotDao ssd;
	
	@Autowired
	private ApplicationDao ad;
	
	@Autowired
	private ModelDao modDao;
	
	@Autowired
	private ManufacturerDao manDao;
	
	public void restart(int id) {
		ICtRunner runner = dao.findById(id);
		if(runner != null) {
			runner.setInstallTime(0L);
			runner.setInstallResult(null);
			runner.setLoadTime(0L);
			runner.setLoadResult(null);
			runner.setUninstallTime(0L);
			runner.setState(Status.RunnerStatus.START.getInt());
			dao.update(runner);
			
			// handle dispatcher
			String hql = String.format(
					"update ICtDispatcher SET state=%d WHERE id=%d",
					Status.DispatcherStatus.START.getInt(),
					runner.getDispatcherId());
			disDao.executeUpdate(hql);
		}
	}

	public void stop(int id) {
		ICtRunner runner = dao.findById(id);
		if(runner != null) {
			runner.setState(Status.RunnerStatus.END.getInt());
			dao.update(runner);
		}
	}
	
	public ICtRunner save(int dispatcherId, int deviceId) {
		ICtRunner runner = new ICtRunner();
		runner.setDispatcherId(dispatcherId);
		runner.setDeviceId(deviceId);
		runner.setState(Status.RunnerStatus.START.getInt());
		dao.save(runner);
		return runner;
	}
	
	public List<ICtRunner> findAll(int dispatcherId) {
		String hql = "from ICtRunner where dispatcherId=? and state!=?";
		return dao.find(hql, dispatcherId, Status.RunnerStatus.REMOVED.getInt());
	}
	
	public List<ICtRunner> findAll(int dispatcherId, int state) {
		String hql = "from ICtRunner where dispatcherId=? and state=?";
		return dao.find(hql, dispatcherId, state);
	}
	
	public List<ICtRunnerComponent> findAll(List<ICtRunner> runnerList) {
		if(runnerList != null && runnerList.size() > 0) {
			List<ICtRunnerComponent> rcList = new ArrayList<ICtRunnerComponent>();
			for(ICtRunner runner : runnerList) {
				ICtRunnerComponent rc = new ICtRunnerComponent();
				rc.setRunner(runner);
				rc.setDevice(devDao.findById(runner.getDeviceId()));
				rcList.add(rc);
			}
			return rcList;
		}
		return null;
	}
	
	public CtReportSummaryComponent findSummary(List<ICtRunner> runnerList) {
		if(runnerList != null && runnerList.size() > 0) {
			int installFailureCount = 0, loadFailureCount = 0, 
					uninstallFailureCount = 0, passCount = 0;
			for(ICtRunner runner : runnerList) {
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
	
	public ICtRunnerComponent findDetail(int runnerId) throws SQLException, IOException {
		ICtRunner runner = dao.findById(runnerId);
		if(runner != null) {
			ICtDispatcher dispatcher = disDao.findById(runner.getDispatcherId());
			if(dispatcher != null) {
				ICtRunnerComponent rc = new ICtRunnerComponent();
				rc.setRunner(runner);
				rc.setApplication(ad.findById(dispatcher.getApplicationId()));
				rc.setDevice(devDao.findById(runner.getDeviceId()));
				rc.setSsList(ssd.findByRunnerId(runnerId));
				return rc;
			}
		}
		return null;
	}
	
	public void temporarilyDelete(String[] ids) {
		// runner
		String hql = String.format(
				"update ICtRunner set state=%d where id in (%s)",
				Status.RunnerStatus.REMOVED.getInt(), StringUtils.join(ids, ","));
		dao.executeUpdate(hql);
	}

	public void permanentlyDelete(String[] ids) {
		// runner
		String hql = String.format(
				"delete from ICtRunner where id in (%s)",
				StringUtils.join(ids, ","));
		dao.executeUpdate(hql);
	}
	
}
