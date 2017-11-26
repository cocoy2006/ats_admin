package molab.main.java.web;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import molab.main.java.entity.Application;
import molab.main.java.entity.CtDispatcher;
import molab.main.java.service.ApplicationService;
import molab.main.java.service.CtDispatcherService;
import molab.main.java.service.CtExcelService;
import molab.main.java.service.CtRunnerService;
import molab.main.java.util.Molab;
import molab.main.java.util.Status;
import molab.main.java.util.fileupload.FileUploadListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@RequestMapping(value = "/ctDispatcher")
public class CtDispatcherWeb {
	
	private static final Logger log = Logger.getLogger(CtDispatcherWeb.class.getName());

	@Autowired
	private CtDispatcherService service;
	
	@Autowired
	private CtRunnerService rs;
	
	@Autowired
	private CtExcelService es;
	
	@Autowired
	private ApplicationService as;

	@ResponseBody
	@RequestMapping(value = "/build", method = RequestMethod.POST)
	public String build(HttpServletRequest request) {
		request.getSession().setAttribute(Molab.SESSION_CT_APP_PARSE_RESULT, null);
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
			Application app = as.parse(file);
			if(app != null && app.getId() > 0) {
				int customerId = Integer.parseInt(request.getParameter("customerId"));
				int holdTime = 0;
				String holdTimeStr = request.getParameter("holdTime");
				if(holdTimeStr != null && !holdTimeStr.equals("")) {
					holdTime = Integer.parseInt(holdTimeStr);
				}
				CtDispatcher dispatcher = service.save(customerId, app.getId(), holdTime);
				// handle idList
				String[] idList = request.getParameterValues("id");
				for(String s : idList) {
					int deviceId = Integer.parseInt(s);
					rs.save(dispatcher.getId(), deviceId);
				}
			} else {
				request.getSession().setAttribute(Molab.SESSION_CT_APP_PARSE_RESULT, Status.DispatcherStatus.APP_PARSE_FAILURE.getInt());
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
		if(session.getAttribute(Molab.SESSION_CT_APP_PARSE_RESULT) != null) {
			return String.valueOf(Status.DispatcherStatus.APP_PARSE_FAILURE.getInt());
		}
		FileUploadListener listener = (FileUploadListener) session.getAttribute("uploadProgressListener");
		if(listener == null) {
			return null;
		}
		return String.valueOf(listener.getPercentDone());
	}
	
	@ResponseBody
	@RequestMapping(value = "/temporarilyDelete/{id}")
	public String temporarilyDelete(@PathVariable int id) {
		service.temporarilyDelete(id);
		return String.valueOf(Status.Common.SUCCESS.getInt());
	}
	
	@ResponseBody
	@RequestMapping(value = "/batchTemporarilyDelete", method = RequestMethod.POST)
	public String batchTemporarilyDelete(HttpServletRequest request) {
		String[] ids = request.getParameterValues("ids");
		service.batchTemporarilyDelete(ids);
		return String.valueOf(Status.Common.SUCCESS.getInt());
	}
	
	@ResponseBody
	@RequestMapping(value = "/permanentlyDelete/{id}")
	public String permanentlyDelete(@PathVariable int id) {
		service.permanentlyDelete(id);
		return String.valueOf(Status.Common.SUCCESS.getInt());
	}
	
	@ResponseBody
	@RequestMapping(value = "/batchPermanentlyDelete", method = RequestMethod.POST)
	public String batchPermanentlyDelete(HttpServletRequest request) {
		String[] ids = request.getParameterValues("ids");
		service.batchPermanentlyDelete(ids);
		return String.valueOf(Status.Common.SUCCESS.getInt());
	}

}
