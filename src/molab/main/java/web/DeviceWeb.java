package molab.main.java.web;

import javax.servlet.http.HttpServletRequest;

import molab.main.java.service.DeviceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/device")
public class DeviceWeb {
	
	@Autowired
	private DeviceService service;
	
	@ResponseBody
	@RequestMapping(value = "/dynatree/{type}", produces = "application/json;charset=UTF-8")
	public String dynatree(@PathVariable int type) {
		return new Gson().toJson(service.findDynatree(type));
	}
	
	@ResponseBody
	@RequestMapping(value = "/active")
	public String active(HttpServletRequest request) {
		String[] ids = request.getParameterValues("ids");
		return String.valueOf(service.active(ids));
	}
	
	@ResponseBody
	@RequestMapping(value = "/unactive")
	public String unactive(HttpServletRequest request) {
		String[] ids = request.getParameterValues("ids");
		return String.valueOf(service.unactive(ids));
	}
	
	@ResponseBody
	@RequestMapping(value = "/remove/{id}")
	public String remove(@PathVariable int id) {
		return String.valueOf(service.remove(id));
	}
	
	@ResponseBody
	@RequestMapping(value = "/batchRemove")
	public String batchRemove(HttpServletRequest request) {
		String[] ids = request.getParameterValues("ids");
		return String.valueOf(service.batchRemove(ids));
	}
	
	@ResponseBody
	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("deviceId"));
		String label = request.getParameter("label");
		return String.valueOf(service.update(id, label));
	}
	
}
