package molab.main.java.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import molab.main.java.service.TestcaseService;
import molab.main.java.util.Molab;
import molab.main.java.util.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/testcase")
public class TestcaseWeb {
	
	@Autowired
	private TestcaseService service;
	
	@ResponseBody
	@RequestMapping(value = "/dynatree", produces = "application/json;charset=UTF-8")
	public String dynatree() {
		return new Gson().toJson(service.findDynatree());
	}
	
	@ResponseBody
	@RequestMapping(value = "/save")
	public String save(HttpSession session) {
		Object idObj = session.getAttribute(Molab.SESSION_CR_TESTCASE_ID);
		if(idObj != null) {
			int id = Integer.parseInt(idObj.toString());
			service.save(id);
		}
		session.removeAttribute(Molab.SESSION_CR_CUR_SCRIPT);
		session.removeAttribute(Molab.SESSION_CR_TESTCASE_ID);
		return String.valueOf(Status.Common.SUCCESS.getInt());
	}
	
	@ResponseBody
	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		service.update(id, name);
		return String.valueOf(Status.Common.SUCCESS.getInt());
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete/{id}")
	public String remove(@PathVariable int id) {
		service.delete(id);
		return String.valueOf(Status.Common.SUCCESS.getInt());
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
