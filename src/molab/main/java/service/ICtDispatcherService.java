package molab.main.java.service;

import java.util.ArrayList;
import java.util.List;

import molab.main.java.component.ICtDispatcherComponent;
import molab.main.java.dao.ApplicationDao;
import molab.main.java.dao.ICtDeviceDao;
import molab.main.java.dao.ICtDispatcherDao;
import molab.main.java.dao.ManufacturerDao;
import molab.main.java.dao.ModelDao;
import molab.main.java.entity.Application;
import molab.main.java.entity.ICtDispatcher;
import molab.main.java.util.Molab;
import molab.main.java.util.Status;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ICtDispatcherService {

	@Autowired
	private ICtDispatcherDao dao;
	
	@Autowired
	private ICtDeviceDao dd;
	
	@Autowired
	private ApplicationDao ad;
	
	@Autowired
	private ManufacturerDao manDao;
	
	@Autowired
	private ModelDao modDao;
	
	public ICtDispatcher save(int customerId, int applicationId, int holdTime) {
		ICtDispatcher dispatcher = new ICtDispatcher();
		dispatcher.setCustomerId(customerId);
		dispatcher.setApplicationId(applicationId);
		dispatcher.setHoldTime(holdTime);
		dispatcher.setOprTime(System.currentTimeMillis());
		dispatcher.setState(Status.DispatcherStatus.START.getInt());
		dao.save(dispatcher);
		return dispatcher;
	}
	
	public ICtDispatcher findById(int id) {
		return dao.findById(id);
	}
	
	public List<ICtDispatcherComponent> findAll() {
		List<ICtDispatcher> dispatchers = dao.findAll();
		if(dispatchers != null && dispatchers.size() > 0) {
			List<ICtDispatcherComponent> list = new ArrayList<ICtDispatcherComponent>();
			for(int i = dispatchers.size() - 1; i >= 0; i--) {
				ICtDispatcherComponent dac = new ICtDispatcherComponent();
				ICtDispatcher dispatcher = dispatchers.get(i);
				Application application = ad.findById(dispatcher.getApplicationId());
				dac.setDispatcher(dispatcher);
				dac.setApplication(application);
				dac.setOprTime(Molab.parseTime(dispatcher.getOprTime()));
				list.add(dac);
			}
			return list;
		}
		return null;
	}
	
	public void temporarilyDelete(String[] ids) {
		// runner
		String hql = String.format(
				"update ICtRunner set state=%d where dispatcherId in (%s)",
				Status.RunnerStatus.REMOVED.getInt(), StringUtils.join(ids, ","));
		dao.executeUpdate(hql);
		// dispatcher
		hql = String.format(
				"update ICtDispatcher set state=%d where id in (%s)",
				Status.DispatcherStatus.REMOVED.getInt(), StringUtils.join(ids, ","));
		dao.executeUpdate(hql);
	}
	
	public void permanentlyDelete(String[] ids) {
		// runner
		String hql = String.format(
				"delete from ICtRunner where dispatcherId in (%s)",
				StringUtils.join(ids, ","));
		dao.executeUpdate(hql);
		// dispatcher
		hql = String.format(
				"delete from ICtDispatcher where id in (%s)",
				StringUtils.join(ids, ","));
		dao.executeUpdate(hql);
	}
	
}
