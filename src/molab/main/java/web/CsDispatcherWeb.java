package molab.main.java.web;

import javax.servlet.http.HttpServletRequest;

import molab.main.java.entity.CsDispatcher;
import molab.main.java.service.CsDispatcherService;
import molab.main.java.service.CsRunnerService;
import molab.main.java.util.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/csDispatcher")
public class CsDispatcherWeb {

	@Autowired
	private CsDispatcherService service;
	
	@Autowired
	private CsRunnerService rs;
	
	@ResponseBody
	@RequestMapping(value = "/build", method = RequestMethod.POST)
	public String upload(HttpServletRequest request) {
		int testcaseId = Integer.parseInt(request.getParameter("testcaseId"));
		CsDispatcher dispatcher = service.save(testcaseId);
		String[] deviceIdList = request.getParameterValues("deviceId");
		for(String s : deviceIdList) {
			int deviceId = Integer.parseInt(s);
			rs.save(dispatcher.getId(), deviceId);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/temporarilyDelete/{id}")
	public String temporarilyDelete(@PathVariable int id) {
		service.temporarilyDelete(id);
		return String.valueOf(Status.Common.SUCCESS.getInt());
	}
	
	@ResponseBody
	@RequestMapping(value = "/batchTemporarilyDelete", method = RequestMethod.POST)
	public String batchTemporarilyDelete(HttpServletRequest request) {
		String[] ids = request.getParameterValues("ids");
		service.batchTemporarilyDelete(ids);
		return String.valueOf(Status.Common.SUCCESS.getInt());
	}
	
	@ResponseBody
	@RequestMapping(value = "/permanentlyDelete/{id}")
	public String permanentlyDelete(@PathVariable int id) {
		service.permanentlyDelete(id);
		return String.valueOf(Status.Common.SUCCESS.getInt());
	}
	
	@ResponseBody
	@RequestMapping(value = "/batchPermanentlyDelete", method = RequestMethod.POST)
	public String batchPermanentlyDelete(HttpServletRequest request) {
		String[] ids = request.getParameterValues("ids");
		service.batchPermanentlyDelete(ids);
		return String.valueOf(Status.Common.SUCCESS.getInt());
	}

}
