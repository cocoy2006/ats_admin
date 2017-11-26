package molab.main.java.service;

import java.util.List;

import molab.main.java.dao.CrDeviceDao;
import molab.main.java.entity.CrDevice;
import molab.main.java.util.Molab;
import molab.main.java.util.PropertiesUtil;
import molab.main.java.util.Status;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrDeviceService {

	@Autowired
	private CrDeviceDao dao;
	
	public List<CrDevice> findAll() {
		String hql = "FROM CrDevice ORDER BY State";
		return dao.find(hql);
	}
	
	public void save(String server, int port, String sn) {
		CrDevice device = new CrDevice(server, port, sn);
		List<CrDevice> exampleList = dao.findByExample(device);
		if(exampleList != null && exampleList.size() > 0) {
			// do nothing
		} else {
			device.setOs(null);
			device.setWidth(0);
			device.setHeight(0);
			device.setState(Status.DeviceStatus.UNACTIVED.getInt());
			dao.save(device);
		}
	}
	
	public int active(String[] ids) {
		String hql = String.format(
				"UPDATE CrDevice SET state=%d WHERE id IN (%s)",
				Status.DeviceStatus.FREE.getInt(), StringUtils.join(ids, ","));
		return dao.executeUpdate(hql);
	}
	
	public int unactive(String[] ids) {
		String hql = String.format(
				"UPDATE CrDevice SET state=%d WHERE id IN (%s)",
				Status.DeviceStatus.UNACTIVED.getInt(), StringUtils.join(ids, ","));
		return dao.executeUpdate(hql);
	}
	
	public int remove(String[] ids) {
		String hql = null;
//		if(PropertiesUtil.getGlobalStrategyRemoved() == Molab.CFG_GLOBAL_STRATEGY_REMOVED_DEFAULT) {
//			hql = String.format(
//					"UPDATE CrDevice SET state=%d WHERE id IN (%s)",
//					Status.DeviceStatus.REMOVED.getInt(), StringUtils.join(ids, ","));
//		} else {
			hql = String.format(
					"DELETE FROM CrDevice WHERE id IN (%s)", StringUtils.join(ids, ","));
//		}
		return dao.executeUpdate(hql);
	}
	
}
