package molab.main.java.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import molab.main.java.entity.ICtDispatcher;
import molab.main.java.entity.ICtRunner;
import molab.main.java.service.ApplicationService;
import molab.main.java.service.CustomerService;
import molab.main.java.service.ICtDeviceService;
import molab.main.java.service.ICtDispatcherService;
import molab.main.java.service.ICtRunnerService;
import molab.main.java.util.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/ict")
public class ICtWeb {

	@Autowired
	private ICtDeviceService devS;
	
	@Autowired
	private ICtDispatcherService disS;
	
	@Autowired
	private ICtRunnerService rs;
	
	@Autowired
	private ApplicationService as;
	
	@Autowired
	private CustomerService cs;
	
	/**
	 * display ios compatibility test devices */
	@RequestMapping(value = "/home")
	public ModelAndView ictHome() {
		ModelAndView mav = new ModelAndView("ict/home");
		mav.addObject("disComList", disS.findAll());
		return mav;
	}
	
	/**
	 * build ios compatibility test dispatcher */
	@RequestMapping(value = "/build")
	public ModelAndView ictBuild() {
		ModelAndView mav = new ModelAndView("ict/build");
		mav.addObject("customerList", cs.findAll());
		mav.addObject("deviceList", devS.findAll(Status.DeviceStatus.FREE.getInt()));
		return mav;
	}
	
	/**
	 * display ios compatibility test build success */
	@RequestMapping(value = "/success")
	public String ictSuccess() {
		return "ict/success";
	}
	
	/**
	 * display ios compatibility test report 
	 * @param dispatcher id */
	@RequestMapping(value = "/report/{dispatcherId}")
	public ModelAndView ictReport(@PathVariable int dispatcherId) {
		ModelAndView mav = new ModelAndView("ict/report");
		ICtDispatcher dispatcher = disS.findById(dispatcherId);
		List<ICtRunner> runnerList = rs.findAll(dispatcherId);
		// for customer
		mav.addObject("customer", cs.findById(dispatcher.getCustomerId()));
		// for title
		mav.addObject("dispatcher", dispatcher);
		mav.addObject("application", as.findById(dispatcher.getApplicationId()));
		mav.addObject("runnerCount", runnerList.size());
		// for summary
		if(dispatcher.getState() == Status.DispatcherStatus.END.getInt()) {
			mav.addObject("summary", rs.findSummary(runnerList));
		}
		// for device list
		List<ICtRunner> deletedRunnerList = rs.findAll(dispatcherId, Status.RunnerStatus.REMOVED.getInt());
		runnerList.addAll(deletedRunnerList);
		mav.addObject("rcList", rs.findAll(runnerList));
		return mav;
	}
	
	/**
	 * display ios compatibility test detail
	 * @param id runner id 
	 * @throws SQLException 
	 * @throws IOException */
	@RequestMapping(value = "/detail/{runnerId}")
	public ModelAndView ictDetail(@PathVariable int runnerId) throws SQLException, IOException {
		ModelAndView mav = new ModelAndView("ict/detail");
		mav.addObject("rc", rs.findDetail(runnerId));
		return mav;
	}
	
}
