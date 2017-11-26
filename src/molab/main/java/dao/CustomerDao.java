package molab.main.java.dao;

import java.util.List;

import molab.main.java.entity.Customer;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * Customer entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see molab.main.java.entity.Customer
 * @author MyEclipse Persistence Tools
 */
@Repository
public class CustomerDao extends BaseDao<Customer> {
	private static final Logger log = LoggerFactory
			.getLogger(CustomerDao.class);
	// property constants
	public static final String NAME = "name";
	public static final String EN_NAME = "enName";
	public static final String LOCATION = "location";
	public static final String INDUSTRY = "industry";
	public static final String WEB_SITE = "webSite";

	public Customer findById(java.lang.Integer id) {
		log.debug("getting Customer instance with id: " + id);
		try {
			Customer instance = (Customer) getHibernateTemplate().get(
					"molab.main.java.entity.Customer", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Customer> findByExample(Customer instance) {
		log.debug("finding Customer instance by example");
		try {
			List<Customer> results = (List<Customer>) getHibernateTemplate()
					.findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<Customer> findByProperty(String propertyName, Object value) {
		log.debug("finding Customer instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Customer as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Customer> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<Customer> findByEnName(Object enName) {
		return findByProperty(EN_NAME, enName);
	}

	public List<Customer> findByLocation(Object location) {
		return findByProperty(LOCATION, location);
	}

	public List<Customer> findByIndustry(Object industry) {
		return findByProperty(INDUSTRY, industry);
	}

	public List<Customer> findByWebSite(Object webSite) {
		return findByProperty(WEB_SITE, webSite);
	}

	public List<Customer> findAll() {
		log.debug("finding all Customer instances");
		try {
			String queryString = "from Customer";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Customer merge(Customer detachedInstance) {
		log.debug("merging Customer instance");
		try {
			Customer result = (Customer) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Customer instance) {
		log.debug("attaching dirty Customer instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Customer instance) {
		log.debug("attaching clean Customer instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public Customer findByCsDispatcherId(int dispatcherId) {
		String hql = "from Customer where id=(select customerId from Project where id=(select projectId from Testcase where id=(select testcaseId from CsDispatcher where id=?)))";
		List<Customer> customerList = getHibernateTemplate().find(hql, dispatcherId);
		if(customerList != null && customerList.size() > 0) {
			return customerList.get(0);
		}
		return null;
	}
	
}