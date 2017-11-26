package molab.main.java.service;

import java.util.List;

import molab.main.java.dao.ICtDeviceDao;
import molab.main.java.dao.ManufacturerDao;
import molab.main.java.dao.ModelDao;
import molab.main.java.entity.ICtDevice;
import molab.main.java.entity.Model;
import molab.main.java.util.Molab;
import molab.main.java.util.PropertiesUtil;
import molab.main.java.util.Status;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ICtDeviceService {

	@Autowired
	private ICtDeviceDao dao;
	
	@Autowired
	private ManufacturerDao manDao;
	
	@Autowired
	private ModelDao modDao;
	
	public List<ICtDevice> findAll() {
		String hql = "from ICtDevice order by State";
		return dao.find(hql);
	}
	
	public List<ICtDevice> findAll(int state) {
		String hql = "from ICtDevice where state=?";
		return dao.find(hql, state);
	}
	
	public void save(String server, int port, String sn, int modelId, String os) {
		ICtDevice device = new ICtDevice(server, port, sn);
		List<ICtDevice> exampleList = dao.findByExample(device);
		if(exampleList != null && exampleList.size() > 0) {
			// do nothing
		} else {
			Model model = modDao.findById(modelId);
			device.setModel(model);
			device.setOs(os);
			device.setImei(null);
			device.setUses(0);
			device.setState(Status.DeviceStatus.UNACTIVED.getInt());
			dao.save(device);
		}
	}
	
	public int active(String[] ids) {
		String hql = String.format(
				"update ICtDevice set state=%d where id in (%s)",
				Status.DeviceStatus.FREE.getInt(), StringUtils.join(ids, ","));
		return dao.executeUpdate(hql);
	}
	
	public int unactive(String[] ids) {
		String hql = String.format(
				"update ICtDevice set state=%d where id in (%s)",
				Status.DeviceStatus.UNACTIVED.getInt(), StringUtils.join(ids, ","));
		return dao.executeUpdate(hql);
	}
	
	public int remove(String[] ids) {
		String hql = null;
//		if(PropertiesUtil.getGlobalStrategyRemoved() == Molab.CFG_GLOBAL_STRATEGY_REMOVED_DEFAULT) { // mark only
//			hql = String.format(
//					"update ICtDevice set state=%d where id in (%s)",
//					Status.DeviceStatus.REMOVED.getInt(), StringUtils.join(ids, ","));
//		} else {
			hql = String.format(
					"delete from ICtDevice where id in (%s)", StringUtils.join(ids, ","));
//		}
		return dao.executeUpdate(hql);
	}
		
}
