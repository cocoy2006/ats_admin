package molab.main.java.dao;

import java.util.List;

import molab.main.java.entity.Device;
import molab.main.java.util.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * Device entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see molab.main.java.entity.Device
 * @author MyEclipse Persistence Tools
 */
@Repository
public class DeviceDao extends BaseDao<Device> {
	private static final Logger log = LoggerFactory.getLogger(DeviceDao.class);
	// property constants
	public static final String SERVER = "server";
	public static final String PORT = "port";
	public static final String SN = "sn";
	public static final String CODE = "code";
	public static final String MANUFACTURER = "manufacturer";
	public static final String MODEL = "model";
	public static final String OS = "os";
	public static final String SDK = "sdk";
	public static final String ROM = "rom";
	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";
	public static final String TYPE = "type";
	public static final String USES = "uses";
	public static final String STATE = "state";

	public Device findById(java.lang.Integer id) {
		log.debug("getting Device instance with id: " + id);
		try {
			Device instance = (Device) getHibernateTemplate().get(
					"molab.main.java.entity.Device", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Device> findByExample(Device instance) {
		log.debug("finding Device instance by example");
		try {
			List<Device> results = (List<Device>) getHibernateTemplate()
					.findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<Device> findByProperty(String propertyName, Object value) {
		log.debug("finding Device instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Device as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Device> findByServer(Object server) {
		return findByProperty(SERVER, server);
	}

	public List<Device> findByPort(Object port) {
		return findByProperty(PORT, port);
	}

	public List<Device> findBySn(Object sn) {
		return findByProperty(SN, sn);
	}
	
	public List<Device> findByCode(Object code) {
		return findByProperty(CODE, code);
	}

	public List<Device> findByManufacturer(Object manufacturer) {
		return findByProperty(MANUFACTURER, manufacturer);
	}

	public List<Device> findByModel(Object model) {
		return findByProperty(MODEL, model);
	}

	public List<Device> findByOs(Object os) {
		return findByProperty(OS, os);
	}

	public List<Device> findBySdk(Object sdk) {
		return findByProperty(SDK, sdk);
	}

	public List<Device> findByRom(Object rom) {
		return findByProperty(ROM, rom);
	}

	public List<Device> findByWidth(Object width) {
		return findByProperty(WIDTH, width);
	}

	public List<Device> findByHeight(Object height) {
		return findByProperty(HEIGHT, height);
	}

	public List<Device> findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List<Device> findByUses(Object uses) {
		return findByProperty(USES, uses);
	}

	public List<Device> findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List<Device> findAll() {
		log.debug("finding all Device instances");
		try {
			String queryString = "from Device order by state";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List<Device> findAll(int type, int state) {
		log.debug("finding all Device instances");
		try {
			String queryString = "from Device where type=? and state=?";
			return getHibernateTemplate().find(queryString, type, state);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List<Device> findAll(int dispatcherId) {
		log.debug("finding all CtDevice instances");
		try {
			String queryString = "from Device where id in (select deviceId from CtRunner where dispatcherId=? and state!=?))";
			return getHibernateTemplate().find(queryString, dispatcherId, Status.Common.REMOVED.getInt());
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
}