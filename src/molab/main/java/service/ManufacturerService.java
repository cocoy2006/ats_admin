package molab.main.java.service;

import java.util.List;

import molab.main.java.dao.ManufacturerDao;
import molab.main.java.dao.ModelDao;
import molab.main.java.entity.Manufacturer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManufacturerService {

	@Autowired
	private ManufacturerDao dao;
	
	@Autowired
	private ModelDao modelDao;
	
	public Manufacturer findById(int id) {
		return dao.findById(id);
	}
	
	public List<Manufacturer> findAll() {
		return dao.findAll();
	}

	public void saveOrUpdate(int id, String name) {
		Manufacturer manufacturer;
		if(id == -1) {
			manufacturer = new Manufacturer();
		} else {
			manufacturer = dao.findById(id);
		}
		manufacturer.setName(name);
		dao.saveOrUpdate(manufacturer);
	}
	
}
