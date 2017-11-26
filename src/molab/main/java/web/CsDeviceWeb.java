package molab.main.java.web;

import javax.servlet.http.HttpServletRequest;

import molab.main.java.service.CsDeviceService;
import molab.main.java.util.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/csDevice")
public class CsDeviceWeb {
	
	@Autowired
	private CsDeviceService service;
	
	@ResponseBody
	@RequestMapping(value = "/dynatree", produces = "application/json;charset=UTF-8")
	public String dynatree(HttpServletRequest request) {
		return new Gson().toJson(service.findDynatree());
	}
	
	@ResponseBody
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request) {
		String server = request.getParameter("server");
		int port = Integer.parseInt(request.getParameter("port"));
		String sn = request.getParameter("sn");
		int modelId = Integer.parseInt(request.getParameter("modelId"));
		String os = request.getParameter("os");
		String imei = request.getParameter("imei");
		service.save(server, port, sn, modelId, os, imei);
		return String.valueOf(Status.Common.SUCCESS.getInt());
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
	@RequestMapping(value = "/delete")
	public String delete(HttpServletRequest request) {
		String[] ids = request.getParameterValues("ids");
		return String.valueOf(service.remove(ids));
	}
	
}
