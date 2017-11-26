package molab.main.java.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import molab.main.java.component.CtReportSsComponent;
import molab.main.java.dao.CtRunnerDao;
import molab.main.java.dao.CtScreenshotDao;
import molab.main.java.dao.DeviceDao;
import molab.main.java.entity.CtScreenshot;
import molab.main.java.entity.Device;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

@Service
public class CtSsService {
	
	@Autowired
	private CtScreenshotDao dao;
	
	@Autowired
	private CtRunnerDao rd;
	
	@Autowired
	private DeviceDao dd;
	
	public void delete(int id) {
		String hql = String.format("delete from CtScreenshot where id=%d", id);
		dao.executeUpdate(hql);
	}
	
	public List<String> findActivityList(final int dispatcherId) {
		final String sql = "select distinct s.activityName from CtScreenshot as s where runnerId in (select id from CtRunner where dispatcherId=?)";
		List list = dao.getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery(sql).setParameter(0, dispatcherId).list();
			}
			
		});
		if(list != null && list.size() > 0) {
			List<String> activityList = new ArrayList<String>();
			for(Object object : list) {
				if(object != null) {
					activityList.add(object.toString());
				}
			}
			return activityList;
		}
		return null;
	}
	
	public List<CtReportSsComponent> findScreenshotList(final int dispatcherId, final String activityName) {
		final String sql = "select d, s from CtScreenshot as s, CtRunner as r, CtDispatcher as dis, Device as d where s.activityName=? and r.dispatcherId=dis.id and dis.id=? and s.runnerId=r.id and r.deviceId=d.id";
		List list = dao.getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery(sql).setParameter(0, activityName).setParameter(1, dispatcherId).list();
			}
			
		});
		if(list != null && list.size() > 0) {
			Map<Integer, CtReportSsComponent> map = new HashMap<Integer, CtReportSsComponent>();
			for(Object object : list) {
				Object[] objs = (Object[]) object;
				Device device = (Device) objs[0];
				CtScreenshot ss = (CtScreenshot) objs[1];
				
				CtReportSsComponent rsc = new CtReportSsComponent();
				List<CtScreenshot> ssList = new ArrayList<CtScreenshot>();
				if(map.containsKey(device.getId())) {
					rsc = map.get(device.getId());
					ssList = rsc.getSsList();
				} else {
					rsc.setDevice(device);
				}
				ssList.add(ss);
				rsc.setSsList(ssList);
				map.put(device.getId(), rsc);
			}
			return new ArrayList<CtReportSsComponent>(map.values());
		}
		return null;
	}
	
}
