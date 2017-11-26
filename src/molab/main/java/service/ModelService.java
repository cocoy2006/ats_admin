package molab.main.java.service;

import java.util.List;

import molab.main.java.dao.ManufacturerDao;
import molab.main.java.dao.ModelDao;
import molab.main.java.entity.Manufacturer;
import molab.main.java.entity.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelService {

	@Autowired
	private ModelDao dao;
	
	@Autowired
	private ManufacturerDao md;
	
	public List<Model> findAll(int manufacturerId) {
		return dao.findByManufacturerId(manufacturerId);
	}
	
	public void saveOrUpdate(int id, int manufacturerId, String name, int width, int height, String os) {
		Model model;
		if(id == -1) {
			model = new Model();
		} else {
			model = dao.findById(id);
		}
		Manufacturer manufacturer = md.findById(manufacturerId);
		model.setManufacturer(manufacturer);
		model.setName(name);
		model.setWidth(width);
		model.setHeight(height);
		model.setOs(os);
		dao.saveOrUpdate(model);
	}

}