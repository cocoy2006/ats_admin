package molab.main.java.service;

import java.util.ArrayList;
import java.util.List;

import molab.main.java.component.DynatreeComponent;
import molab.main.java.dao.DeviceDao;
import molab.main.java.entity.Device;
import molab.main.java.util.Status;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

	@Autowired
	private DeviceDao dao;
	
	
	public List<Device> findAll() {
		return dao.findAll();
	}
	
	public List<Device> findByType(int type) {
		String hql = "from Device where type=? order by State";
		return dao.find(hql, type);
	}
	
	public List<DynatreeComponent> findDynatree(int type) {
		List<Device> deviceList = dao.findAll(type, Status.DeviceStatus.FREE.getInt());
		if(deviceList != null && deviceList.size() > 0) {
			List<DynatreeComponent> manDcList = new ArrayList<DynatreeComponent>();
			for(Device device : deviceList) {
				DynatreeComponent manDc = check(manDcList, device.getManufacturer());
				List<DynatreeComponent> modDcList = null;
				if(manDc != null) { // manufacturer exist already
					modDcList = manDc.getChildren();
				} else { // manufacturer first occur
					modDcList = new ArrayList<DynatreeComponent>();
					// handle manufacturer
					manDc = new DynatreeComponent();
					manDc.setTitle(device.getManufacturer());
					manDc.setFolder(true);
					manDc.setChildren(modDcList);
					manDcList.add(manDc);
				}
				DynatreeComponent modDc = check(modDcList, device.getModel());
				List<DynatreeComponent> deviceDcList = null;
				if(modDc != null) { // model exist already
					deviceDcList = modDc.getChildren();
				} else { // model first occur
					deviceDcList = new ArrayList<DynatreeComponent>();
					// handle model
					modDc = new DynatreeComponent();
					modDc.setTitle(device.getModel());
					modDc.setFolder(true);
					modDc.setChildren(deviceDcList);
					modDcList.add(modDc);
				}
				// handle device
				DynatreeComponent deviceDc = new DynatreeComponent();
				deviceDc.setTitle(device.getSn() + "@" + device.getServer() + "#" + device.getLabel());
				deviceDc.setKey(String.valueOf(device.getId()));
				deviceDc.setFolder(false);
				deviceDc.setChildren(null);
				deviceDcList.add(deviceDc);
			}
			return manDcList;
		}
		return null;
	}
	
	private DynatreeComponent check(List<DynatreeComponent> list, String value) {
		if(list != null && list.size() > 0) {
			for(DynatreeComponent dc : list) {
				if(dc.getTitle().equalsIgnoreCase(value)) {
					return dc;
				}
			}
		}
		return null;
	}
	
	public int active(String[] ids) {
		String hql = String.format(
				"update Device set state=%d where id in (%s)",
				Status.DeviceStatus.FREE.getInt(), StringUtils.join(ids, ","));
		return dao.executeUpdate(hql);
	}
	
	public int unactive(String[] ids) {
		String hql = String.format(
				"update Device set state=%d where id in (%s)",
				Status.DeviceStatus.UNACTIVED.getInt(), StringUtils.join(ids, ","));
		return dao.executeUpdate(hql);
	}
	
	public int remove(int id) {
		String hql = String.format(
				"delete from Device where id=%d", id);
		return dao.executeUpdate(hql);
	}
	
	public int batchRemove(String[] ids) {
		String hql = String.format(
				"delete from Device where id in (%s)", StringUtils.join(ids, ","));
		return dao.executeUpdate(hql);
	}
	
	public int update(int id, String label) {
		String hql = String.format(
				"update Device set label='%s' where id=%d", label, id);
		return dao.executeUpdate(hql);
	}
	
}
