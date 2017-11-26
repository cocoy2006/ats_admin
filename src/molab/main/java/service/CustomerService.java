package molab.main.java.service;

import java.util.List;

import molab.main.java.dao.CustomerDao;
import molab.main.java.entity.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

	@Autowired
	private CustomerDao dao;
	
	public List<Customer> findAll() {
		return dao.findAll();
	}
	
	public Customer findById(int id) {
		return dao.findById(id);
	}
	
	public void saveOrUpdate(int id, String name, String enName,
			String location, String industry, String webSite) {
		Customer c;
		if(id == -1) {
			c = new Customer();
		} else {
			c = dao.findById(id);
		}
		c.setName(name);
		c.setEnName(enName);
		c.setLocation(location);
		c.setIndustry(industry);
		c.setWebSite(webSite);
		dao.saveOrUpdate(c);
	}
	
	public Customer findByCsDispatcherId(int dispatcherId) {
		return dao.findByCsDispatcherId(dispatcherId);
	}
	
}
