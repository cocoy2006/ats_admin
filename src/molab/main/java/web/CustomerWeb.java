package molab.main.java.web;

import javax.servlet.http.HttpServletRequest;

import molab.main.java.service.CustomerService;
import molab.main.java.util.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/customer")
public class CustomerWeb {
	
	@Autowired
	private CustomerService service;
	
	@ResponseBody
	@RequestMapping(value = "/saveOrUpdate")
	public String saveOrUpdate(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String enName = request.getParameter("enName");
		String location = request.getParameter("location");
		String industry = request.getParameter("industry");
		String webSite = request.getParameter("webSite");
		service.saveOrUpdate(id, name, enName, location, industry, webSite);
		return String.valueOf(Status.Common.SUCCESS.getInt());
	}
	
}
