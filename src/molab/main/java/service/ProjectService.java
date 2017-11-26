package molab.main.java.service;

import java.util.ArrayList;
import java.util.List;

import molab.main.java.component.DynatreeComponent;
import molab.main.java.dao.CustomerDao;
import molab.main.java.dao.ProjectDao;
import molab.main.java.dao.TestcaseDao;
import molab.main.java.entity.Customer;
import molab.main.java.entity.Project;
import molab.main.java.entity.Testcase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

	@Autowired
	private ProjectDao dao;
	
	@Autowired
	private CustomerDao cd;
	
	@Autowired
	private TestcaseDao td;
	
	public List<DynatreeComponent> findDynatree() {
		// handle customer
		List<Customer> cusList = cd.findAll();
		if(cusList != null && cusList.size() > 0) {
			List<DynatreeComponent> cusDcList = new ArrayList<DynatreeComponent>();
			for(Customer cus : cusList) {
				DynatreeComponent cusDc = new DynatreeComponent();
				cusDc.setTitle(cus.getName());
				cusDc.setFolder(true);
				cusDc.setHideCheckbox(true);
				// handle project -- main --
				List<Project> proList = dao.findByCustomerId(cus.getId());
				if(proList != null && proList.size() > 0) {
					List<DynatreeComponent> proDcList = new ArrayList<DynatreeComponent>();
					for(Project pro : proList) {
						DynatreeComponent proDc = new DynatreeComponent();
						proDc.setTitle(pro.getName());
						proDc.setKey(String.valueOf(pro.getId()));
						proDc.setFolder(true);
						// handle testcase
						List<Testcase> tcList = td.findByProjectId(pro.getId());
						if(tcList != null && tcList.size() > 0) {
							List<DynatreeComponent> tcDcList = new ArrayList<DynatreeComponent>();
							for(Testcase tc : tcList) {
								DynatreeComponent tcDc = new DynatreeComponent();
								tcDc.setTitle(tc.getName());
								tcDc.setKey(String.valueOf(tc.getId()));
								tcDc.setFolder(false);
								tcDc.setChildren(null);
								tcDc.setHideCheckbox(true);
								tcDcList.add(tcDc);
							}
							proDc.setChildren(tcDcList);
						}
						proDcList.add(proDc);
					}
					cusDc.setChildren(proDcList);
				}
				cusDcList.add(cusDc);
			}
			return cusDcList;
		}
		return null;
	}
	
	public Project findById(int id) {
		return dao.findById(id);
	}
	
	public List<Project> findByCustomerId(int customerId) {
		return dao.findByCustomerId(customerId);
	}
	
	public void saveOrUpdate(int id, int customerId, String name) {
		Project p;
		if(id == -1) {
			p = new Project();
			p.setCreateTime(System.currentTimeMillis());
		} else {
			p = dao.findById(id);
		}
		p.setCustomerId(customerId);
		p.setName(name);
		p.setModifyTime(System.currentTimeMillis());
		dao.saveOrUpdate(p);
	}
	
}
