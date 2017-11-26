package molab.main.java.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import molab.main.java.component.CtReportSsComponent;
import molab.main.java.entity.Application;
import molab.main.java.entity.CtDispatcher;
import molab.main.java.entity.CtRunner;
import molab.main.java.service.ApplicationService;
import molab.main.java.service.CtDispatcherService;
import molab.main.java.service.CtExcelService;
import molab.main.java.service.CtPdfService;
import molab.main.java.service.CtRunnerService;
import molab.main.java.service.CtSsService;
import molab.main.java.service.CustomerService;
import molab.main.java.util.Status;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/ct")
public class CtWeb {
	
	@Autowired
	private CtDispatcherService ds;
	
	@Autowired
	private CtRunnerService rs;
	
	@Autowired
	private CtSsService ss;
	
	@Autowired
	private ApplicationService as;
	
	@Autowired
	private CustomerService cs;

	@Autowired
	private CtExcelService es;

	@Autowired
	private CtPdfService ps;
	
	/**
	 * display cloud/compatibility test devices */
	@RequestMapping(value = "/home")
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("ct/home");
		mav.addObject("disComList", ds.findAll());
		return mav;
	}
	
	/**
	 * build cloud/compatibility test dispatcher */
	@RequestMapping(value = "/build")
	public ModelAndView build() {
		ModelAndView mav = new ModelAndView("ct/build");
		mav.addObject("customerList", cs.findAll());
		return mav;
	}
	
	/**
	 * display cloud/compatibility test build success */
	@RequestMapping(value = "/success")
	public String success() {
		return "ct/success";
	}
	
	/**
	 * display cloud/compatibility test report 
	 * @param dispatcher id */
	@RequestMapping(value = "/report/{dispatcherId}")
	public ModelAndView report(@PathVariable int dispatcherId) {
		ModelAndView mav = new ModelAndView("ct/report");
		CtDispatcher dispatcher = ds.findById(dispatcherId);
		List<CtRunner> runnerList = rs.findAll(dispatcherId);
		// for customer
		mav.addObject("customer", cs.findById(dispatcher.getCustomerId()));
		// for title
		mav.addObject("dispatcher", dispatcher);
		mav.addObject("application", as.findById(dispatcher.getApplicationId()));
		mav.addObject("runnerCount", runnerList.size());
		// for summary & analysis
		if(dispatcher.getState() == Status.DispatcherStatus.END.getInt()) {
			mav.addObject("summary", rs.findSummary(runnerList));
			mav.addObject("analysis", rs.findAnalysis(runnerList));
		}
		// for device list
		List<CtRunner> deletedRunnerList = rs.findAll(dispatcherId, Status.RunnerStatus.REMOVED.getInt());
		runnerList.addAll(deletedRunnerList);
		mav.addObject("rcList", rs.findAll(runnerList));
		// for activity list and screenshot list
		List<String> activityList = ss.findActivityList(dispatcherId);
		if(activityList != null && activityList.size() > 0) {
			mav.addObject("activityList", activityList);
			mav.addObject("activityName", activityList.get(0));
			mav.addObject("rscList", ss.findScreenshotList(dispatcherId, activityList.get(0)));
		}
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "/ss/{activityName}/{dispatcherId}")
	public String screenshot(@PathVariable String activityName, @PathVariable int dispatcherId) {
		List<CtReportSsComponent> rscList = ss.findScreenshotList(dispatcherId, activityName);
		return new Gson().toJson(rscList);
	}
	
	/**
	 * display cloud/compatibility test detail
	 * @param id runner id 
	 * @throws SQLException 
	 * @throws IOException */
	@RequestMapping(value = "/detail/{runnerId}")
	public ModelAndView detail(@PathVariable int runnerId) throws SQLException, IOException {
		ModelAndView mav = new ModelAndView("ct/detail");
		mav.addObject("rc", rs.findDetail(runnerId));
		return mav;
	}
	
	@RequestMapping(value = "/excel/{dispatcherId}")
	public void excel(@PathVariable int dispatcherId, 
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		CtDispatcher dispatcher = ds.findById(dispatcherId);
		Application application = as.findById(dispatcher.getApplicationId());
		String filename = "report@cfca.xls";
		response.setContentType ( "application/ms-excel" ) ;
		response.setHeader ( "Content-Disposition" ,
				"attachment;filename=" + new String(filename.getBytes(), "UTF-8"));
		Workbook wb = es.loadExcel(dispatcher, application);
		ServletOutputStream out = response.getOutputStream();
		wb.write(out);
		out.flush();
	}
	
	@RequestMapping(value = "/pdf/{dispatcherId}")
	public void pdf(@PathVariable int dispatcherId, 
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		CtDispatcher dispatcher = ds.findById(dispatcherId);
		Application application = as.findById(dispatcher.getApplicationId());
		String filename = "report@cfca.pdf";
		response.setContentType ( "application/pdf" ) ;
		response.setHeader ( "Content-Disposition" ,
				"attachment;filename=" + new String(filename.getBytes(), "UTF-8"));
		ByteArrayOutputStream pdf = ps.loadPdf(dispatcher, application);
		response.setContentLength(pdf.size());
		ServletOutputStream out = response.getOutputStream();
	    pdf.writeTo(out);
	    out.flush();
	}
	
}
