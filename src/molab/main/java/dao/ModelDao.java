package molab.main.java.dao;

import java.io.Serializable;
import java.util.List;

import molab.main.java.entity.Model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository
public class ModelDao extends BaseDao<Model> {
	
	private static final Logger log = LoggerFactory.getLogger(ModelDao.class);
	// property constants
	public static final String MANUFACTURER_ID = "manufacturer.id";
	public static final String NAME = "name";
	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";
	public static final String OS = "os";
	
	public void saveOrUpdate(Model model) {
		getHibernateTemplate().saveOrUpdate(model);
	}

	public Serializable save(Model transientInstance) {
		log.debug("saving Model instance");
		try {
			Integer id = (Integer) getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
			return id;
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Model persistentInstance) {
		log.debug("deleting Model instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Model findById(java.lang.Integer id) {
		log.debug("getting Model instance with id: " + id);
		try {
			Model instance = (Model) getHibernateTemplate().get(
					"molab.main.java.entity.Model", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Model instance) {
		log.debug("finding Model instance by example");
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

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Model instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Model as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByManufacturerId(Object manufacturerId) {
		return findByProperty(MANUFACTURER_ID, manufacturerId);
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByWidth(Object width) {
		return findByProperty(WIDTH, width);
	}

	public List findByHeight(Object height) {
		return findByProperty(HEIGHT, height);
	}

	public List findByOs(Object os) {
		return findByProperty(OS, os);
	}

	public List findAll() {
		log.debug("finding all Model instances");
		try {
			String queryString = "from Model";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
}
