package molab.main.java.web;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import molab.main.java.entity.Application;
import molab.main.java.entity.Script;
import molab.main.java.service.ApplicationService;
import molab.main.java.service.CrRecordService;
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

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.SyncException;
import com.android.ddmlib.TimeoutException;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/crRecord")
public class CrRecordWeb {
	
	private static final Logger log = Logger.getLogger(CrRecordWeb.class.getName());
	
	@Autowired
	private CrRecordService service;
	
	@Autowired
	private ApplicationService as;
	
	@ResponseBody
	@RequestMapping(value = "/{deviceId}/screenshot")
	public String screenshot(@PathVariable int deviceId, 
			HttpServletRequest request) throws InterruptedException, SyncException {
		try {
			float screenQ = Float.parseFloat(request.getParameter("screenQ"));
			return service.screenshot(deviceId, screenQ, request.getSession());
		} catch (TimeoutException e) {
			log.severe(e.getMessage());
		} catch (AdbCommandRejectedException e) {
			log.severe(e.getMessage());
		} catch (IOException e) {
			log.severe(e.getMessage());
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/{deviceId}/goon/{testcaseId}")
	public String goon(@PathVariable int deviceId, @PathVariable int testcaseId, 
			HttpSession session) {
		int result = service.goon(deviceId, testcaseId);
		if(result == Status.Common.SUCCESS.getInt()) {
			session.setAttribute(Molab.SESSION_CR_TESTCASE_ID, testcaseId);
		}
		return String.valueOf(result);
	}
	
	@ResponseBody
	@RequestMapping(value = "/{deviceId}/upload", method = RequestMethod.POST)
	public String upload(@PathVariable int deviceId, HttpServletRequest request) {
		request.getSession().setAttribute(Molab.SESSION_CT_APP_PARSE_RESULT, null);
		try {
			int projectId = Integer.parseInt(request.getParameter("projectId"));
			String testcaseName = request.getParameter("testcaseName");
			testcaseName = new String(testcaseName.getBytes("ISO-8859-1"), "UTF8");
			// get and parse apk file
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
			Application app = as.parse(file);
			// install
			HttpSession session = request.getSession();
			String installResult = null;
			if(app != null) {
				installResult = service.install(projectId, testcaseName, deviceId, app, session);
			} else {
				request.getSession().setAttribute(Molab.SESSION_CT_APP_PARSE_RESULT, Status.DispatcherStatus.APP_PARSE_FAILURE.getInt());
			}
			// force set percentDone to 100%
			FileUploadListener listener = (FileUploadListener) session.getAttribute("uploadProgressListener");
			if(listener != null) {
				listener.setPercentDone(100);
			}
			return installResult;
		} catch (Exception e) {
			log.severe(e.getMessage());
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
	@RequestMapping(value = "/{deviceId}/install")
	public String install(@PathVariable int deviceId, 
			HttpSession session) {
		Object obj = session.getAttribute(Molab.SESSION_CR_INSTALL_RESULT);
		if(obj != null) {
			return obj.toString();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/{deviceId}/op")
	public String op(@PathVariable int deviceId, HttpServletRequest request, HttpSession session) 
			throws IOException {
		// common parameters
		int testcaseId = 0;
		Object obj = session.getAttribute(Molab.SESSION_CR_TESTCASE_ID);
		if(obj != null) {
			testcaseId = Integer.parseInt(obj.toString());
		}
		boolean recordable = Boolean.parseBoolean(request.getParameter("recordable"));
		String action = request.getParameter("action");
		
		// execute action and build script if necessary
		List<Script> scriptList = service.op(deviceId, testcaseId, recordable, action, request);
		
		// operation session and return result
		if(testcaseId != 0) {
			session.setAttribute(Molab.SESSION_CR_CUR_SCRIPT, scriptList);
//			if(recordable && (scriptList == null || scriptList.size() == 0)) {
//				return String.valueOf(Status.RecordStatus.APP_NOT_FOUND.getInt());
//			}
			return new Gson().toJson(scriptList);
		} else {
			return String.valueOf(Status.RecordStatus.NO_TESTCASE_ID.getInt());
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/{deviceId}/record")
	public String record(@PathVariable int deviceId, HttpServletRequest request) throws SyncException, IOException, AdbCommandRejectedException, TimeoutException {
		int num = Integer.parseInt(request.getParameter("num"));
		String action = request.getParameter("action");
		String params = request.getParameter("params");
		String note = request.getParameter("note");
		return String.valueOf(service.record(deviceId, num, action, params, note, request.getSession()));
	}
	
}
