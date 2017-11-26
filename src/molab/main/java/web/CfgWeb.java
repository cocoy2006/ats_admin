package molab.main.java.web;

import javax.servlet.http.HttpServletRequest;

import molab.main.java.service.CfgService;
import molab.main.java.util.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/cfg")
public class CfgWeb {
	
	@Autowired
	private CfgService service;
	
	@ResponseBody
	@RequestMapping(value = "/set")
	public String set(HttpServletRequest request) {
		String key = request.getParameter("key");
		String value = request.getParameter("value");
		service.set(key, value);
		return String.valueOf(Status.Common.SUCCESS.getInt());
	}
	
}
