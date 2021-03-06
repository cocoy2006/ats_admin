package molab.main.java.dao;

import java.util.List;

import molab.main.java.entity.CsRunner;
import molab.main.java.util.Status;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * CsRunner entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see molab.main.java.entity.CsRunner
 * @author MyEclipse Persistence Tools
 */
@Repository
public class CsRunnerDao extends BaseDao<CsRunner> {
	private static final Logger log = LoggerFactory
			.getLogger(CsRunnerDao.class);
	// property constants
	public static final String DISPATCHER_ID = "dispatcherId";
	public static final String DEVICE_ID = "deviceId";
	public static final String AVERAGE_CPU = "averageCpu";
	public static final String AVERAGE_MEMORY = "averageMemory";
	public static final String INSTALL_TIME = "installTime";
	public static final String INSTALL_RESULT = "installResult";
	public static final String LOAD_TIME = "loadTime";
	public static final String UNINSTALL_TIME = "uninstallTime";
	public static final String LOGCAT = "logcat";
	public static final String STATE = "state";
	
	public CsRunner findById(java.lang.Integer id) {
		log.debug("getting CsRunner instance with id: " + id);
		try {
			CsRunner instance = (CsRunner) getHibernateTemplate().get(
					"molab.main.java.entity.CsRunner", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<CsRunner> findByExample(CsRunner instance) {
		log.debug("finding CsRunner instance by example");
		try {
			List<CsRunner> results = (List<CsRunner>) getHibernateTemplate()
					.findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<CsRunner> findByProperty(String propertyName, Object value) {
		log.debug("finding CsRunner instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from CsRunner as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<CsRunner> findByDispatcherId(Object dispatcherId) {
		return findByProperty(DISPATCHER_ID, dispatcherId);
	}

	public List<CsRunner> findByDeviceId(Object deviceId) {
		return findByProperty(DEVICE_ID, deviceId);
	}

	public List<CsRunner> findByAverageCpu(Object averageCpu) {
		return findByProperty(AVERAGE_CPU, averageCpu);
	}

	public List<CsRunner> findByAverageMemory(Object averageMemory) {
		return findByProperty(AVERAGE_MEMORY, averageMemory);
	}

	public List<CsRunner> findByInstallTime(Object installTime) {
		return findByProperty(INSTALL_TIME, installTime);
	}

	public List<CsRunner> findByInstallResult(Object installResult) {
		return findByProperty(INSTALL_RESULT, installResult);
	}

	public List<CsRunner> findByLoadTime(Object loadTime) {
		return findByProperty(LOAD_TIME, loadTime);
	}

	public List<CsRunner> findByUninstallTime(Object uninstallTime) {
		return findByProperty(UNINSTALL_TIME, uninstallTime);
	}

	public List<CsRunner> findByLogcat(Object logcat) {
		return findByProperty(LOGCAT, logcat);
	}

	public List<CsRunner> findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List<CsRunner> findAll() {
		log.debug("finding all CsRunner instances");
		try {
			String queryString = "from CsRunner";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CsRunner merge(CsRunner detachedInstance) {
		log.debug("merging CsRunner instance");
		try {
			CsRunner result = (CsRunner) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CsRunner instance) {
		log.debug("attaching dirty CsRunner instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CsRunner instance) {
		log.debug("attaching clean CsRunner instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<CsRunner> findAll(int dispatcherId) {
		log.debug("finding all CsRunner instances");
		try {
			String queryString = "from CsRunner where dispatcherId=? and state!=?";
			return getHibernateTemplate().find(queryString, dispatcherId, Status.Common.REMOVED.getInt());
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
}