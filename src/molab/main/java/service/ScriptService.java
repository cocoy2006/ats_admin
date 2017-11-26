package molab.main.java.service;

import java.util.List;

import molab.main.java.dao.ScriptDao;
import molab.main.java.entity.Script;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScriptService {

	@Autowired
	private ScriptDao dao;
	
	public List<Script> findByTestcaseId(int testcaseId) {
		return dao.findByTestcaseId(testcaseId);
	}
	
}
