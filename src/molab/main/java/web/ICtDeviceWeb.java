package molab.main.java.web;

import javax.servlet.http.HttpServletRequest;

import molab.main.java.service.ICtDeviceService;
import molab.main.java.util.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/ictDevice")
public class ICtDeviceWeb {
	
	@Autowired
	private ICtDeviceService service;
	
	@ResponseBody
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request) {
		String server = request.getParameter("server");
		int port = Integer.parseInt(request.getParameter("port"));
		String sn = request.getParameter("sn");
		int modelId = Integer.parseInt(request.getParameter("modelId"));
		String os = request.getParameter("os");
		service.save(server, port, sn, modelId, os);
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
