package molab.main.java.web;

import javax.servlet.http.HttpServletRequest;

import molab.main.java.service.CtRunnerService;
import molab.main.java.util.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/ctRunner")
public class CtRunnerWeb {

	@Autowired
	private CtRunnerService service;

	@ResponseBody
	@RequestMapping(value = "/restart/{id}")
	public String restart(@PathVariable int id) {
		service.restart(id);
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/stop/{id}")
	public String stop(@PathVariable int id) {
		service.stop(id);
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
