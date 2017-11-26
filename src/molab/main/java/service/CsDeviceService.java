package molab.main.java.service;

import java.util.ArrayList;
import java.util.List;

import molab.main.java.component.DynatreeComponent;
import molab.main.java.dao.CsDeviceDao;
import molab.main.java.dao.ManufacturerDao;
import molab.main.java.dao.ModelDao;
import molab.main.java.entity.CsDevice;
import molab.main.java.entity.Manufacturer;
import molab.main.java.entity.Model;
import molab.main.java.util.Molab;
import molab.main.java.util.PropertiesUtil;
import molab.main.java.util.Status;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CsDeviceService {

	@Autowired
	private CsDeviceDao dao;
	
	@Autowired
	private ManufacturerDao manDao;
	
	@Autowired
	private ModelDao modDao;

	public List<DynatreeComponent> findDynatree() {
		// handle manufacturer
		List<Manufacturer> manList = manDao.findAll();
		if(manList != null && manList.size() > 0) {
			List<DynatreeComponent> manDcList = new ArrayList<DynatreeComponent>();
			for(Manufacturer man : manList) {
				// handle model
				List<Model>	modList = modDao.findByManufacturerId(man.getId());
				if(modList != null && modList.size() > 0) {
					List<DynatreeComponent> modDcList = new ArrayList<DynatreeComponent>();
					for(Model mod : modList) {
						// handle device
						List<CsDevice> deviceList = dao.findByModelId(mod.getId());
						if(deviceList != null && deviceList.size() > 0) {
							List<DynatreeComponent> deviceDcList = new ArrayList<DynatreeComponent>();
							for(CsDevice device : deviceList) {
								if(device.getState() == Status.DeviceStatus.FREE.getInt()) {
									DynatreeComponent deviceDc = new DynatreeComponent();
									deviceDc.setTitle(device.getSn() + "@" + device.getServer());
									deviceDc.setKey(String.valueOf(device.getId()));
									deviceDc.setFolder(false);
									deviceDc.setChildren(null);
									deviceDcList.add(deviceDc);
								}
							}
							if(deviceDcList.size() > 0) {
								DynatreeComponent modDc = new DynatreeComponent();
								modDc.setTitle(mod.getName());
								modDc.setFolder(true);
								modDc.setChildren(deviceDcList);
								modDcList.add(modDc);
							}
						}
					}
					if(modDcList.size() > 0) {
						DynatreeComponent manDc = new DynatreeComponent();
						manDc.setTitle(man.getName());
						manDc.setFolder(true);
						manDc.setChildren(modDcList);
						manDcList.add(manDc);
					}
				}
			}
			if(manDcList.size() > 0) {
				return manDcList;
			}
		}
		return null;
	}
	
	public List<CsDevice> findAll() {
		String hql = "FROM CsDevice ORDER BY State";
		return dao.find(hql);
	}
	
	public void save(String server, int port, String sn, 
			int modelId, String os, String imei) {
		CsDevice device = new CsDevice(server, port, sn);
		List<CsDevice> exampleList = dao.findByExample(device);
		if(exampleList != null && exampleList.size() > 0) {
			// do nothing
		} else {
			Model model = modDao.findById(modelId);
			device.setModel(model);
			device.setOs(os);
			device.setImei(imei);
			device.setUses(0);
			device.setState(Status.DeviceStatus.UNACTIVED.getInt());
			dao.save(device);
		}
	}
	
	public int active(String[] ids) {
		String hql = String.format(
				"UPDATE CsDevice SET state=%d WHERE id IN (%s)",
				Status.DeviceStatus.FREE.getInt(), StringUtils.join(ids, ","));
		return dao.executeUpdate(hql);
	}
	
	public int unactive(String[] ids) {
		String hql = String.format(
				"UPDATE CsDevice SET state=%d WHERE id IN (%s)",
				Status.DeviceStatus.UNACTIVED.getInt(), StringUtils.join(ids, ","));
		return dao.executeUpdate(hql);
	}
	
	public int remove(String[] ids) {
		String hql = null;
//		if(PropertiesUtil.getGlobalStrategyRemoved() == Molab.CFG_GLOBAL_STRATEGY_REMOVED_DEFAULT) { // mark only
//			hql = String.format(
//					"UPDATE CsDevice SET state=%d WHERE id IN (%s)",
//					Status.DeviceStatus.REMOVED.getInt(), StringUtils.join(ids, ","));
//		} else {
			hql = String.format(
					"DELETE FROM CsDevice WHERE id IN (%s)", StringUtils.join(ids, ","));
//		}
		return dao.executeUpdate(hql);
	}
	
}
