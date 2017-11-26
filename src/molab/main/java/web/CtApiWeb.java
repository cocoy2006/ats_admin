package molab.main.java.web;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import molab.main.java.service.CtApiService;
import molab.main.java.util.Path;
import molab.main.java.util.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/ctApi")
public class CtApiWeb {

	@Autowired
	private CtApiService service;
	
	@ResponseBody
	@RequestMapping(value = "/findRunnerList")
	public String findRunnerList(HttpServletRequest request) {
		int state = Integer.parseInt(request.getParameter("state"));
		String server = request.getParameter("server");
		String port = request.getParameter("port");
		return new Gson().toJson(service.findRunnerList(state, server, port));
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateRunnerState")
	public String updateRunnerState(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		int state = Integer.parseInt(request.getParameter("state"));
		service.updateRunnerState(id, state);
		return String.valueOf(Status.Common.SUCCESS.getInt());
	}
	
	@ResponseBody
	@RequestMapping(value = "/log", method = RequestMethod.POST)
	public String log(HttpServletRequest request) throws IllegalStateException, IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
		String path = Path.ctLog(file.getOriginalFilename());
		file.transferTo(new File(path));
		return String.valueOf(Status.Common.SUCCESS.getInt());
	}
	
	@ResponseBody
	@RequestMapping(value = "/ss", method = RequestMethod.POST)
	public String ss(HttpServletRequest request) throws IllegalStateException, IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
		String path = Path.ctSs(file.getOriginalFilename());
		file.transferTo(new File(path));
		return String.valueOf(Status.Common.SUCCESS.getInt());
	}
	
	/**
	 * @deprecated
	 *  */
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value = "/files", method = RequestMethod.POST)
	public String files(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> files = multipartRequest.getFiles("files");
		return null;
	}

}
