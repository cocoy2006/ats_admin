package molab.main.java.service;

import java.util.ArrayList;
import java.util.List;

import molab.main.java.component.CsDispatcherComponent;
import molab.main.java.dao.ApplicationDao;
import molab.main.java.dao.CsDeviceDao;
import molab.main.java.dao.CsDispatcherDao;
import molab.main.java.dao.CsRunnerDao;
import molab.main.java.dao.CsScreenshotDao;
import molab.main.java.dao.CustomerDao;
import molab.main.java.dao.ManufacturerDao;
import molab.main.java.dao.ModelDao;
import molab.main.java.dao.ProjectDao;
import molab.main.java.dao.TestcaseDao;
import molab.main.java.entity.Application;
import molab.main.java.entity.CsDispatcher;
import molab.main.java.entity.Project;
import molab.main.java.entity.Testcase;
import molab.main.java.util.Molab;
import molab.main.java.util.Path;
import molab.main.java.util.PropertiesUtil;
import molab.main.java.util.Status;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CsDispatcherService {

	@Autowired
	private CsDispatcherDao dao;
	
	@Autowired
	private CsRunnerDao rd;
	
	@Autowired
	private CsScreenshotDao ssd;
	
	@Autowired
	private CsDeviceDao devDao;
	
	@Autowired
	private ApplicationDao ad;
	
	@Autowired
	private ManufacturerDao manDao;
	
	@Autowired
	private ModelDao modDao;
	
	@Autowired
	private TestcaseDao td;
	
	@Autowired
	private ProjectDao pd;
	
	@Autowired
	private CustomerDao cd;
	
	public CsDispatcher save(int testcaseId) {
		CsDispatcher dispatcher = new CsDispatcher();
		dispatcher.setTestcaseId(testcaseId);
		// timeout
		int timeout = Molab.CFG_CS_PLAYBACK_TIMEOUT_DEFAULT;
		String timeoutStr = PropertiesUtil.get(Path.cfg(), Molab.CFG_CS_PLAYBACK_TIMEOUT);
		if(timeoutStr != null) {
			timeout = Integer.valueOf(timeoutStr);
		}
		dispatcher.setTimeout(timeout);
		dispatcher.setOprTime(System.currentTimeMillis());
		dispatcher.setState(Status.DispatcherStatus.START.getInt());
		dao.save(dispatcher);
		return dispatcher;
	}

	public List<CsDispatcherComponent> findAll() {
		List<CsDispatcher> dispatchers = dao.findAll();
		if(dispatchers != null && dispatchers.size() > 0) {
			List<CsDispatcherComponent> list = new ArrayList<CsDispatcherComponent>();
			for(int i = dispatchers.size() - 1; i >= 0; i--) {
				CsDispatcherComponent dc = findByDispatcher(dispatchers.get(i));
				list.add(dc);
			}
			return list;
		}
		return null;
	}
	
	private CsDispatcherComponent findByDispatcher(CsDispatcher dispatcher) {
		if(dispatcher != null) {
			CsDispatcherComponent dac = new CsDispatcherComponent();
			dac.setDispatcher(dispatcher);
			// testcase
			Testcase t = td.findById(dispatcher.getTestcaseId());
			dac.setTestcase(t);
			if(t != null) {
				// application
				Application app = ad.findById(t.getApplicationId());
				dac.setApplication(app);
				// project
				Project p = pd.findById(t.getProjectId());
				dac.setProject(p);
				if(p != null) {
					// customer
					dac.setCustomer(cd.findById(p.getCustomerId()));
				}
			}
			dac.setOprTime(Molab.parseTime(dispatcher.getOprTime()));
			return dac;
		}
		return null;
	}
	
	public CsDispatcherComponent findById(int id) {
		CsDispatcher dispatcher = dao.findById(id);
		return findByDispatcher(dispatcher);
	}
	
	public void temporarilyDelete(int id) {
		// screenshot
		String hql = String.format(
				"update CsScreenshot set state=%d where runnerId in (select id from CsRunner where dispatcherId=%d)",
				Status.ScreenshotStatus.REMOVED.getInt(), id);
		ssd.executeUpdate(hql);
		// runner
		hql = String.format(
				"update CsRunner set state=%d where dispatcherId=%d",
				Status.RunnerStatus.REMOVED.getInt(), id);
		rd.executeUpdate(hql);
		// dispatcher
		hql = String.format(
				"update CsDispatcher set state=%d where id=%d",
				Status.DispatcherStatus.REMOVED.getInt(), id);
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
				"update CsRunner set state=%d where dispatcherId in (%s)",
				Status.RunnerStatus.REMOVED.getInt(), StringUtils.join(ids, ","));
		rd.executeUpdate(hql);
		// dispatcher
		hql = String.format(
				"update CsDispatcher set state=%d where id in (%s)",
				Status.DispatcherStatus.REMOVED.getInt(), StringUtils.join(ids, ","));
		dao.executeUpdate(hql);
	}
	
	public void permanentlyDelete(int id) {
		// screenshot
		String hql = String.format(
				"delete from CsScreenshot where runnerId in (select id from CsRunner where dispatcherId=%d)", id);
		ssd.executeUpdate(hql);
		// runner
		hql = String.format(
				"delete from CsRunner where dispatcherId=%d", id);
		rd.executeUpdate(hql);
		// dispatcher
		hql = String.format(
				"delete from CsDispatcher where id=%d", id);
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
				"delete from CsRunner where dispatcherId in (%s)",
				StringUtils.join(ids, ","));
		rd.executeUpdate(hql);
		// dispatcher
		hql = String.format(
				"delete from CsDispatcher where id in (%s)",
				StringUtils.join(ids, ","));
		dao.executeUpdate(hql);
	}
	
}
