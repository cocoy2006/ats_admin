package molab.main.java.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import molab.main.java.entity.Model;
import molab.main.java.service.ModelService;
import molab.main.java.util.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/model")
public class ModelWeb {
	
	@Autowired
	private ModelService service;
	
	@ResponseBody
	@RequestMapping(value = "/findAll/{manufacturerId}")
	public String findAll(@PathVariable int manufacturerId) {
		List<Model> modelList = service.findAll(manufacturerId);
		if(modelList != null && modelList.size() > 0) {
			return new Gson().toJson(modelList);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/saveOrUpdate")
	public String saveOrUpdate(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("modelId"));
		int manufacturerId = Integer.parseInt(request.getParameter("manufacturerId"));
		String name = request.getParameter("name");
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		String os = request.getParameter("os");
		service.saveOrUpdate(id, manufacturerId, name, width, height, os);
		return String.valueOf(Status.Common.SUCCESS.getInt());
	}
	
}
