package molab.main.java.web;

import java.util.Map;

import javax.servlet.http.HttpSession;

import molab.main.java.entity.Device;
import molab.main.java.service.ApplicationService;
import molab.main.java.service.CfgService;
import molab.main.java.service.CrDeviceService;
import molab.main.java.service.CrRecordService;
import molab.main.java.service.CsDeviceService;
import molab.main.java.service.CtDeviceService;
import molab.main.java.service.CustomerService;
import molab.main.java.service.DeviceService;
import molab.main.java.service.ICtDeviceService;
import molab.main.java.service.ManufacturerService;
import molab.main.java.service.ModelService;
import molab.main.java.service.ProjectService;
import molab.main.java.service.ScriptService;
import molab.main.java.service.TestcaseService;
import molab.main.java.util.Molab;
import molab.main.java.util.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexWeb {

	@Autowired
	private CrDeviceService crds;
	
	@Autowired
	private CrRecordService crrs;
	
	@Autowired
	private DeviceService ds;
	
	@Autowired
	private CsDeviceService csds;
	
	@Autowired
	private CtDeviceService ctds;
	
	@Autowired
	private ICtDeviceService ictds;
	
	@Autowired
	private ApplicationService as;

	@Autowired
	private ManufacturerService manS;
	
	@Autowired
	private ModelService modS;
	
	@Autowired
	private CustomerService cs;
	
	@Autowired
	private ProjectService ps;
	
	@Autowired
	private TestcaseService ts;
	
	@Autowired
	private ScriptService ss;
	
	@Autowired
	private CfgService cfgS;
	
	@RequestMapping(value = "/")
	public String _() {
		return "forward:/ct/home";
	}
	
	@RequestMapping(value = "/center/device")
	public ModelAndView device() {
		ModelAndView mav = new ModelAndView("center/device");
		mav.addObject("deviceList", ds.findAll());
		return mav;
	}
	
	/**
	 * @deprecated */
	@RequestMapping(value = "/center/ctDevice")
	public ModelAndView ctDevice() {
		ModelAndView mav = new ModelAndView("center/ctDevice");
		mav.addObject("deviceList", ctds.findAll());
		mav.addObject("manufacturerList", manS.findAll());
		return mav;
	}
	
	/**
	 * @deprecated */
	@RequestMapping(value = "/center/ictDevice")
	public ModelAndView ictDevice() {
		ModelAndView mav = new ModelAndView("center/ictDevice");
		mav.addObject("deviceList", ictds.findAll());
		mav.addObject("manufacturerList", manS.findAll());
		return mav;
	}
	
	/**
	 * @deprecated */
	@RequestMapping(value = "/center/crDevice")
	public ModelAndView crDevice() {
		ModelAndView mav = new ModelAndView("center/crDevice");
		mav.addObject("deviceList", crds.findAll());
		return mav;
	}
	
	/**
	 * @deprecated */
	@RequestMapping(value = "/center/csDevice")
	public ModelAndView csDevice() {
		ModelAndView mav = new ModelAndView("center/csDevice");
		mav.addObject("deviceList", csds.findAll());
		mav.addObject("manufacturerList", manS.findAll());
		return mav;
	}
	
	@RequestMapping(value = "/center/cfg")
	public ModelAndView cfg() {
		ModelAndView mav = new ModelAndView("center/cfg");
		Map<String, String> map = cfgS.findAll();
		// global
//		mav.addObject(Molab.CFG_GLOBAL_STRATEGY_REMOVED, map.get(Molab.CFG_GLOBAL_STRATEGY_REMOVED));
		// cs
		mav.addObject(Molab.CFG_CS_PLAYBACK_TIMEOUT, map.get(Molab.CFG_CS_PLAYBACK_TIMEOUT));
		return mav;
	}
	
	@RequestMapping(value = "/center/mm")
	public ModelAndView mm() {
		ModelAndView mav = new ModelAndView("center/mm");
		return mav;
	}
	
	@RequestMapping(value = "/center/mm_manufacturer")
	public ModelAndView mmManufacturer() {
		ModelAndView mav = new ModelAndView("center/snippet/mm_manufacturer");
		mav.addObject("manufacturerList", manS.findAll());
		return mav;
	}
	
	@RequestMapping(value = "/center/mm_model/{manufacturerId}")
	public ModelAndView mmModel(@PathVariable int manufacturerId) {
		ModelAndView mav = new ModelAndView("center/snippet/mm_model");
		mav.addObject("manufacturer", manS.findById(manufacturerId));
		mav.addObject("modelList", modS.findAll(manufacturerId));
		return mav;
	}
	
	@RequestMapping(value = "/center/cpts")
	public ModelAndView cpts() {
		ModelAndView mav = new ModelAndView("center/cpts");
		mav.addObject("customerList", cs.findAll());
		return mav;
	}
	
	@RequestMapping(value = "/center/cpts/{customerId}")
	public ModelAndView cpts(@PathVariable int customerId) {
		ModelAndView mav = new ModelAndView("center/cpts_customer");
		mav.addObject("customer", cs.findById(customerId));
		mav.addObject("projectList", ps.findByCustomerId(customerId));
		return mav;
	}
	
	@RequestMapping(value = "/center/cpts/{customerId}/{projectId}")
	public ModelAndView cpts(@PathVariable int customerId, @PathVariable int projectId) {
		ModelAndView mav = new ModelAndView("center/cpts_project");
		mav.addObject("customer", cs.findById(customerId));
		mav.addObject("project", ps.findById(projectId));
		mav.addObject("testcaseList", ts.findByProjectId(projectId));
		return mav;
	}
	
	@RequestMapping(value = "/center/cpts/{customerId}/{projectId}/{testcaseId}")
	public ModelAndView cpts(@PathVariable int customerId, 
			@PathVariable int projectId, @PathVariable int testcaseId) {
		ModelAndView mav = new ModelAndView("center/cpts_testcase");
		mav.addObject("customer", cs.findById(customerId));
		mav.addObject("project", ps.findById(projectId));
		mav.addObject("testcase", ts.findById(testcaseId));
		mav.addObject("scriptList", ss.findByTestcaseId(testcaseId));
		return mav;
	}
	
	@RequestMapping(value = "/center/info")
	public String info() {
		return "center/info";
	}
	
	/**
	 * display cloud record devices */
	@RequestMapping(value = "/cr/home")
	public ModelAndView crHome() {
		ModelAndView mav = new ModelAndView("cr/home");
//		mav.addObject("deviceList", crds.findAll());
		mav.addObject("deviceList", ds.findByType(Status.DeviceType.CR.getInt()));
		return mav;
	}
	
	/**
	 * cloud record main page */
	@RequestMapping(value = "/cr/record/{id}")
	public ModelAndView crRecord(@PathVariable int id, HttpSession session) {
		ModelAndView mav = null;
		Device device = crrs.lock(id);
		if(device != null) {
			session.removeAttribute(Molab.SESSION_CR_SCREENSHOT);
			session.removeAttribute(Molab.SESSION_CR_INSTALL_RESULT);
			session.removeAttribute(Molab.SESSION_CR_TESTCASE_ID);
			session.removeAttribute(Molab.SESSION_CR_CUR_SCRIPT);
			mav = new ModelAndView("cr/record");
			mav.addObject("device", device);
			mav.addObject("customerList", cs.findAll());
		} else {
			mav = new ModelAndView("cr/home");
			mav.addObject("deviceList", crds.findAll());
		}
		return mav;
	}
	
	/**
	 * cloud record exist */
	@RequestMapping(value = "/cr/release/{id}")
	public String crRelease(@PathVariable int id) {
		crrs.release(id);
		return "forward:/cr/home";
	}
	
}
