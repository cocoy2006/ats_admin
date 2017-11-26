package molab.main.java.web;

import javax.servlet.http.HttpSession;

import molab.main.java.service.ScriptService;
import molab.main.java.util.Molab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/script")
public class ScriptWeb {
	
	@Autowired
	private ScriptService service;
	
	@ResponseBody
	@RequestMapping(value = "/all/{testcaseId}", produces = "application/json;charset=UTF-8")
	public String all(@PathVariable int testcaseId, HttpSession session) {
		Object obj = session.getAttribute(Molab.SESSION_CR_TESTCASE_ID);
		if(obj != null) {
			testcaseId = Integer.parseInt(obj.toString());
		}
		return new Gson().toJson(service.findByTestcaseId(testcaseId));
	}
	
}
