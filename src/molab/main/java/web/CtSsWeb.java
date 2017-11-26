package molab.main.java.web;

import molab.main.java.service.CtSsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/ctSs")
public class CtSsWeb {

	@Autowired
	private CtSsService service;

	@ResponseBody
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable int id) {
		service.delete(id);
		return null;
	}

	
}
