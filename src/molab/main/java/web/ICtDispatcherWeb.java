package molab.main.java.web;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import molab.main.java.entity.Application;
import molab.main.java.entity.ICtDispatcher;
import molab.main.java.service.ApplicationService;
import molab.main.java.service.ICtDispatcherService;
import molab.main.java.service.ICtRunnerService;
import molab.main.java.util.Status;
import molab.main.java.util.fileupload.FileUploadListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@RequestMapping(value = "/ictDispatcher")
public class ICtDispatcherWeb {
	
	private static final Logger log = Logger.getLogger(ICtDispatcherWeb.class.getName());

	@Autowired
	private ICtDispatcherService service;
	
	@Autowired
	private ICtRunnerService rs;
	
	@Autowired
	private ApplicationService as;

	@ResponseBody
	@RequestMapping(value = "/build", method = RequestMethod.POST)
	public String build(HttpServletRequest request) {
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
			Application app = as.iparse(file);
			if(app != null && app.getId() > 0) {
				int customerId = Integer.parseInt(request.getParameter("customerId"));
				int holdTime = 0;
				String holdTimeStr = request.getParameter("holdTime");
				if(holdTimeStr != null && !holdTimeStr.equals("")) {
					holdTime = Integer.parseInt(holdTimeStr);
				}
				ICtDispatcher dispatcher = service.save(customerId, app.getId(), holdTime);
				// handle idList
				String[] idList = request.getParameterValues("id");
				for(String s : idList) {
					int deviceId = Integer.parseInt(s);
					rs.save(dispatcher.getId(), deviceId);
				}
			}
		} catch (Exception e) {
			log.severe(e.getMessage());
		} finally {
			// force set percentDone to 100%
			FileUploadListener listener = (FileUploadListener) request.getSession().getAttribute("uploadProgressListener");
			if(listener != null) {
				listener.setPercentDone(100);
			}
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/percentDone", method = RequestMethod.GET)
	public String loadPercent(HttpServletRequest request) {
		HttpSession session = request.getSession();
		FileUploadListener listener = (FileUploadListener) session.getAttribute("uploadProgressListener");
		if(listener == null) {
			return null;
		}
		return String.valueOf(listener.getPercentDone());
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
