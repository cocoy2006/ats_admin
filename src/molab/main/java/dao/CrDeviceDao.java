package molab.main.java.dao;

import java.util.List;

import molab.main.java.entity.CrDevice;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * CrDevice entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see molab.main.java.entity.CrDevice
 * @author MyEclipse Persistence Tools
 */
@Repository
public class CrDeviceDao extends BaseDao<CrDevice> {
	private static final Logger log = LoggerFactory
			.getLogger(CrDeviceDao.class);
	// property constants
	public static final String SERVER = "server";
	public static final String PORT = "port";
	public static final String SN = "sn";
	public static final String OS = "os";
	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";
	public static final String STATE = "state";

	public CrDevice findById(java.lang.Integer id) {
		log.debug("getting CrDevice instance with id: " + id);
		try {
			CrDevice instance = (CrDevice) getHibernateTemplate().get(
					"molab.main.java.entity.CrDevice", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<CrDevice> findByExample(CrDevice instance) {
		log.debug("finding CrDevice instance by example");
		try {
			List<CrDevice> results = (List<CrDevice>) getHibernateTemplate()
					.findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding CrDevice instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from CrDevice as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<CrDevice> findByServer(Object server) {
		return findByProperty(SERVER, server);
	}

	public List<CrDevice> findByPort(Object port) {
		return findByProperty(PORT, port);
	}

	public List<CrDevice> findBySn(Object sn) {
		return findByProperty(SN, sn);
	}

	public List<CrDevice> findByOs(Object os) {
		return findByProperty(OS, os);
	}

	public List<CrDevice> findByWidth(Object width) {
		return findByProperty(WIDTH, width);
	}

	public List<CrDevice> findByHeight(Object height) {
		return findByProperty(HEIGHT, height);
	}

	public List<CrDevice> findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List findAll() {
		log.debug("finding all CrDevice instances");
		try {
			String queryString = "from CrDevice";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CrDevice merge(CrDevice detachedInstance) {
		log.debug("merging CrDevice instance");
		try {
			CrDevice result = (CrDevice) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CrDevice instance) {
		log.debug("attaching dirty CrDevice instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CrDevice instance) {
		log.debug("attaching clean CrDevice instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}