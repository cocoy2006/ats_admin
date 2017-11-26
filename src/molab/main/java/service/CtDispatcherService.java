package molab.main.java.service;

import java.util.ArrayList;
import java.util.List;

import molab.main.java.component.CtDispatcherComponent;
import molab.main.java.dao.ApplicationDao;
import molab.main.java.dao.CtDeviceDao;
import molab.main.java.dao.CtDispatcherDao;
import molab.main.java.dao.CtRunnerDao;
import molab.main.java.dao.CtScreenshotDao;
import molab.main.java.dao.CtSubRunnerDao;
import molab.main.java.dao.ManufacturerDao;
import molab.main.java.dao.ModelDao;
import molab.main.java.entity.Application;
import molab.main.java.entity.CtDispatcher;
import molab.main.java.entity.CtRunner;
import molab.main.java.util.Molab;
import molab.main.java.util.Status;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CtDispatcherService {

	@Autowired
	private CtDispatcherDao dao;
	
	@Autowired
	private CtRunnerDao rd;
	
	@Autowired
	private CtScreenshotDao ssd;
	
	@Autowired
	private CtSubRunnerDao srd;
	
	@Autowired
	private CtDeviceDao devDao;
	
	@Autowired
	private ApplicationDao ad;
	
	@Autowired
	private ManufacturerDao manDao;
	
	@Autowired
	private ModelDao modDao;
	
	public CtDispatcher save(int customerId, int applicationId, int holdTime) {
		CtDispatcher dispatcher = new CtDispatcher();
		dispatcher.setCustomerId(customerId);
		dispatcher.setApplicationId(applicationId);
		dispatcher.setHoldTime(holdTime);
		dispatcher.setOprTime(System.currentTimeMillis());
		dispatcher.setState(Status.DispatcherStatus.START.getInt());
		dao.save(dispatcher);
		return dispatcher;
	}
	
	public CtDispatcher findById(int id) {
		return dao.findById(id);
	}
	
	public List<CtDispatcherComponent> findAll() {
		List<CtDispatcher> dispatchers = dao.findAll();
		if(dispatchers != null && dispatchers.size() > 0) {
			List<CtDispatcherComponent> list = new ArrayList<CtDispatcherComponent>();
			for(int i = dispatchers.size() - 1; i >= 0; i--) {
				CtDispatcherComponent dac = new CtDispatcherComponent();
				// dispatcher
				CtDispatcher dispatcher = dispatchers.get(i);
				dac.setDispatcher(dispatcher);
				// all kind of count
				List<CtRunner> runnerList = rd.findByDispatcherId(dispatcher.getId());
				if(runnerList != null && runnerList.size() > 0) {
					int successCount = 0, failureCount = 0, runningCount = 0, removedCount = 0;
					for(CtRunner runner : runnerList) {
						if(runner.getState() < Status.RunnerStatus.END.getInt()) {
							runningCount++;
						} else if(runner.getState() == Status.RunnerStatus.END.getInt()) {
							if(runner.getInstallTime() > 0 && runner.getLoadTime() > 0) {
								successCount++;
							} else {
								failureCount++;
							}
						} else if(runner.getState() == Status.RunnerStatus.REMOVED.getInt()) {
							removedCount++;
						}
					}
					dac.setSuccessCount(successCount);
					dac.setFailureCount(failureCount);
					dac.setRunningCount(runningCount);
					dac.setRemovedCount(removedCount);
					dac.setAllCount(successCount + failureCount + runningCount + removedCount);
				}
				// application
				Application application = ad.findById(dispatcher.getApplicationId());
				dac.setApplication(application);
				dac.setOprTime(Molab.parseTime(dispatcher.getOprTime()));
				list.add(dac);
			}
			return list;
		}
		return null;
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
				"update CtRunner set state=%d where dispatcherId=%d",
				Status.RunnerStatus.REMOVED.getInt(), id);
		rd.executeUpdate(hql);
		// dispatcher
		hql = String.format(
				"update CtDispatcher set state=%d where id=%d",
				Status.DispatcherStatus.REMOVED.getInt(), id);
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
				"update CtRunner set state=%d where dispatcherId in (%s)",
				Status.RunnerStatus.REMOVED.getInt(), StringUtils.join(ids, ","));
		rd.executeUpdate(hql);
		// dispatcher
		hql = String.format(
				"update CtDispatcher set state=%d where id in (%s)",
				Status.DispatcherStatus.REMOVED.getInt(), StringUtils.join(ids, ","));
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
				"delete from CtRunner where dispatcherId=%d", id);
		rd.executeUpdate(hql);
		// dispatcher
		hql = String.format(
				"delete from CtDispatcher where id=%d", id);
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
				"delete from CtRunner where dispatcherId in (%s)",
				StringUtils.join(ids, ","));
		rd.executeUpdate(hql);
		// dispatcher
		hql = String.format(
				"delete from CtDispatcher where id in (%s)",
				StringUtils.join(ids, ","));
		dao.executeUpdate(hql);
	}
	
}
