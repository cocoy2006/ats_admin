package molab.main.java.web;

import java.io.IOException;
import java.sql.SQLException;

import molab.main.java.component.CsDispatcherComponent;
import molab.main.java.service.ApplicationService;
import molab.main.java.service.CsDispatcherService;
import molab.main.java.service.CsRunnerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/cs")
public class CsWeb {
	
	@Autowired
	private CsDispatcherService ds;
	
	@Autowired
	private CsRunnerService rs;
	
	@Autowired
	private ApplicationService as;
	
	/**
	 * display cloud script devices */
	@RequestMapping(value = "/home")
	public ModelAndView csHome() {
		ModelAndView mav = new ModelAndView("cs/home");
		mav.addObject("disComList", ds.findAll());
		return mav;
	}
	
	/**
	 * cloud scripts management */
	@RequestMapping(value = "/scripts")
	public String csScripts() {
		return "cs/scripts";
	}
	
	/**
	 * build cloud script dispatcher */
	@RequestMapping(value = "/build")
	public String csBuild() {
		return "cs/build";
	}
	
	/**
	 * display cloud script build success */
	@RequestMapping(value = "/success")
	public String csSuccess() {
		return "cs/success";
	}
	
	/**
	 * display cloud script report
	 * @param dispatcher id */
	@RequestMapping(value = "/report/{dispatcherId}")
	public ModelAndView csReport(@PathVariable int dispatcherId) {
		ModelAndView mav = new ModelAndView("cs/report");
		// customer
		CsDispatcherComponent disCom = ds.findById(dispatcherId);
		mav.addObject("disCom", disCom);
		//
		mav.addObject("rcList", rs.findAll(dispatcherId));
		return mav;
	}
	
	/**
	 * display cloud script detail
	 * @param id runner id 
	 * @throws SQLException 
	 * @throws IOException */
	@RequestMapping(value = "/detail/{runnerId}")
	public ModelAndView csDetail(@PathVariable int runnerId) throws SQLException, IOException {
		ModelAndView mav = new ModelAndView("cs/detail");
		mav.addObject("rc", rs.findDetail(runnerId));
		return mav;
	}
	
}
