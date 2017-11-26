package molab.main.java.service;

import java.util.ArrayList;
import java.util.List;

import molab.main.java.component.DynatreeComponent;
import molab.main.java.dao.CsDispatcherDao;
import molab.main.java.dao.CsRunnerDao;
import molab.main.java.dao.CsScreenshotDao;
import molab.main.java.dao.CustomerDao;
import molab.main.java.dao.ProjectDao;
import molab.main.java.dao.ScriptDao;
import molab.main.java.dao.TestcaseDao;
import molab.main.java.entity.Customer;
import molab.main.java.entity.Project;
import molab.main.java.entity.Testcase;
import molab.main.java.util.Status;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestcaseService {

	@Autowired
	private TestcaseDao dao;
	
	@Autowired
	private ScriptDao sd;
	
	@Autowired
	private CustomerDao cd;
	
	@Autowired
	private ProjectDao pd;
	
	@Autowired
	private CsDispatcherDao dd;
	
	@Autowired
	private CsRunnerDao rd;
	
	@Autowired
	private CsScreenshotDao ssd;
	
	public List<DynatreeComponent> findDynatree() {
		// handle customer
		List<Customer> cusList = cd.findAll();
		List<DynatreeComponent> cusDcList = new ArrayList<DynatreeComponent>();
		if(cusList != null && cusList.size() > 0) {
			
			for(Customer cus : cusList) {
				// handle project -- main --
				List<Project> proList = pd.findByCustomerId(cus.getId());
				List<DynatreeComponent> proDcList = new ArrayList<DynatreeComponent>();
				if(proList != null && proList.size() > 0) {
					
					for(Project pro : proList) {
						// handle testcase
						List<Testcase> tcList = dao.findByProjectId(pro.getId());
						List<DynatreeComponent> tcDcList = new ArrayList<DynatreeComponent>();
						if(tcList != null && tcList.size() > 0) {
							
							for(Testcase tc : tcList) {
								DynatreeComponent tcDc = new DynatreeComponent();
								tcDc.setTitle(tc.getName());
								tcDc.setKey(String.valueOf(tc.getId()));
								tcDc.setFolder(false);
								tcDc.setTooltip(tc.getTestcase());
								tcDc.setChildren(null);
								tcDcList.add(tcDc);
							}
							
						}
						DynatreeComponent proDc = new DynatreeComponent();
						proDc.setTitle(pro.getName());
						proDc.setFolder(true);
						proDc.setChildren(tcDcList);
						proDc.setHideCheckbox(true);
						proDcList.add(proDc);
					}
					
				}
				DynatreeComponent cusDc = new DynatreeComponent();
				cusDc.setTitle(cus.getName());
				cusDc.setFolder(true);
				cusDc.setChildren(proDcList);
				cusDc.setHideCheckbox(true);
				cusDcList.add(cusDc);
			}
			return cusDcList;
		}
		return null;
	}
	
	public Testcase findById(int id) {
		return dao.findById(id);
	}
	
	public List<Testcase> findByProjectId(int projectId) {
		return dao.findByProjectId(projectId);
	}
	
	public void save(int id) {
		Testcase t = dao.findById(id);
		t.setState(Status.TestcaseStatus.END.getInt());
		dao.update(t);
	}
	
	public void update(int id, String name) {
		Testcase t = dao.findById(id);
		t.setName(name);
		dao.update(t);
	}
	
	public void delete(int id) {
		Testcase testcase = dao.findById(id);
		if(testcase != null) {
			dao.remove(testcase);
		}
	}
	
	public void temporarilyDelete(String[] ids) {
		// script
		// TODO add state in script
		// testcase
		String hql = String.format(
				"update Testcase set state=%d where id in (%s)",
				Status.TestcaseStatus.REMOVED.getInt(), StringUtils.join(ids, ","));
		dao.executeUpdate(hql);
	}

	public void permanentlyDelete(String[] ids) {
		// TODO delete pngs in csSs folder
		String hql = null;
		// screenshot
		hql = String.format(
				"delete from CsScreenshot where runnerId in (select id from CsRunner where dispatcherId in (select id from CsDispatcher where testcaseId in (%s)))",
				StringUtils.join(ids, ","));
		ssd.executeUpdate(hql);
		// runner
		hql = String.format(
				"delete from CsRunner where dispatcherId in (select id from CsDispatcher where testcaseId in (%s))",
				StringUtils.join(ids, ","));
		rd.executeUpdate(hql);
		// dispatcher
		hql = String.format(
				"delete from CsDispatcher where testcaseId in (%s)",
				StringUtils.join(ids, ","));
		dd.executeUpdate(hql);
		// script
		hql = String.format(
				"delete from Script where testcaseId in (%s)",
				StringUtils.join(ids, ","));
		sd.executeUpdate(hql);
		// runner
		hql = String.format(
				"delete from Testcase where id in (%s)",
				StringUtils.join(ids, ","));
		dao.executeUpdate(hql);
	}
	
}
