package molab.main.java.web;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import molab.main.java.util.Path;
import molab.main.java.util.Status;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@RequestMapping(value = "/ictApi")
public class ICtApiWeb {
	
	@ResponseBody
	@RequestMapping(value = "/file", method = RequestMethod.POST)
	public String file(HttpServletRequest request) throws IllegalStateException, IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
		String path = Path.ictSs(file.getOriginalFilename());
		file.transferTo(new File(path));
		return String.valueOf(Status.Common.SUCCESS.getInt());
	}
	
}
