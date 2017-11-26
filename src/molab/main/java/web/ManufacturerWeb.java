package molab.main.java.web;

import javax.servlet.http.HttpServletRequest;

import molab.main.java.service.ManufacturerService;
import molab.main.java.util.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/manufacturer")
public class ManufacturerWeb {
	
	@Autowired
	private ManufacturerService service;
	
	
	@ResponseBody
	@RequestMapping(value = "/saveOrUpdate")
	public String saveOrUpdate(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("manufacturerId"));
		String name = request.getParameter("name");
		service.saveOrUpdate(id, name);
		return String.valueOf(Status.Common.SUCCESS.getInt());
	}
	
}
