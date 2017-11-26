package molab.main.java.web;

import javax.servlet.http.HttpServletRequest;

import molab.main.java.service.ProjectService;
import molab.main.java.util.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/project")
public class ProjectWeb {
	
	@Autowired
	private ProjectService service;
	
	@ResponseBody
	@RequestMapping(value = "/dynatree", produces = "application/json;charset=UTF-8")
	public String dynatree() {
		return new Gson().toJson(service.findDynatree());
	}
	
	@ResponseBody
	@RequestMapping(value = "/{customerId}")
	public String findByCustomerId(@PathVariable int customerId) {
		return new Gson().toJson(service.findByCustomerId(customerId));
	}
	
	@ResponseBody
	@RequestMapping(value = "/saveOrUpdate")
	public String saveOrUpdate(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		String name = request.getParameter("name");
		service.saveOrUpdate(id, customerId, name);
		return String.valueOf(Status.Common.SUCCESS.getInt());
	}
	
}
