package molab.main.java.dao;

import java.util.List;

import molab.main.java.entity.Logcat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * Logcat entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see molab.main.java.entity.Logcat
 * @author MyEclipse Persistence Tools
 */
@Repository
public class LogcatDao extends BaseDao<Logcat> {
	private static final Logger log = LoggerFactory.getLogger(LogcatDao.class);
	// property constants
	public static final String RUNNER_ID = "runnerId";
	public static final String TAG = "tag";
	public static final String LEVEL = "level";
	public static final String TYPE = "type";
	public static final String CONTENT = "content";

	public Logcat findById(java.lang.Integer id) {
		log.debug("getting Logcat instance with id: " + id);
		try {
			Logcat instance = (Logcat) getHibernateTemplate().get(
					"molab.main.java.entity.Logcat", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Logcat> findByExample(Logcat instance) {
		log.debug("finding Logcat instance by example");
		try {
			List<Logcat> results = (List<Logcat>) getHibernateTemplate()
					.findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<Logcat> findByProperty(String propertyName, Object value) {
		log.debug("finding Logcat instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Logcat as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Logcat> findByRunnerId(Object runnerId) {
		return findByProperty(RUNNER_ID, runnerId);
	}
	
	public List<Logcat> findByTag(Object tag) {
		return findByProperty(TAG, tag);
	}

	public List<Logcat> findByLevel(Object level) {
		return findByProperty(LEVEL, level);
	}

	public List<Logcat> findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List<Logcat> findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List<Logcat> findAll() {
		log.debug("finding all Logcat instances");
		try {
			String queryString = "from Logcat";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List<Logcat> findByRunnerIdOrderByType(int runnerId) {
		log.debug("finding all Logcat instances with runner.id");
		try {
			String queryString = "from Logcat where runnerId=? order by type";
			return getHibernateTemplate().find(queryString, runnerId);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

}