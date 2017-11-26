package molab.main.java.web;

import javax.servlet.http.HttpServletRequest;

import molab.main.java.service.ICtRunnerService;
import molab.main.java.util.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/ictRunner")
public class ICtRunnerWeb {

	@Autowired
	private ICtRunnerService service;

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
	@RequestMapping(value = "/temporarilyDelete", method = RequestMethod.POST)
	public String temporarilyDelete(HttpServletRequest request) {
		String[] ids = request.getParameterValues("ids");
		service.temporarilyDelete(ids);
		return String.valueOf(Status.Common.SUCCESS.getInt());
	}
	
	@ResponseBody
	@RequestMapping(value = "/permanentlyDelete", method = RequestMethod.POST)
	public String permanentlyDelete(HttpServletRequest request) {
		String[] ids = request.getParameterValues("ids");
		service.permanentlyDelete(ids);
		return String.valueOf(Status.Common.SUCCESS.getInt());
	}
	
}
