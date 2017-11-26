package molab.main.java.dao;

import java.util.List;

import molab.main.java.entity.Manufacturer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ManufacturerDao extends BaseDao<Manufacturer> {

	private static final Logger log = LoggerFactory.getLogger(ManufacturerDao.class);
	// property constants
	public static final String NAME = "name";

	public Manufacturer load(int id) {
		return getHibernateTemplate().load(Manufacturer.class, id);
	}

	public List<Manufacturer> loadAll() {
		return getHibernateTemplate().loadAll(Manufacturer.class);
	}

	public void saveOrUpdate(Manufacturer manufacturer) {
		getHibernateTemplate().saveOrUpdate(manufacturer);
	}
	
	public void delete(Manufacturer persistentInstance) {
		log.debug("deleting Manufacturer instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public Manufacturer findById(java.lang.Integer id) {
		log.debug("getting Manufacturer instance with id: " + id);
		try {
			Manufacturer instance = (Manufacturer) getHibernateTemplate().get(
					"molab.main.java.entity.Manufacturer", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Manufacturer> findByExample(Manufacturer instance) {
		log.debug("finding Manufacturer instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<Manufacturer> findByProperty(String propertyName, Object value) {
		log.debug("finding Manufacturer instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Manufacturer as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Manufacturer> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<Manufacturer> findAll() {
		log.debug("finding all Manufacturer instances");
		try {
			String queryString = "from Manufacturer";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

}
