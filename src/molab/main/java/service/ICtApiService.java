package molab.main.java.service;

import molab.main.java.dao.CtRunnerDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ICtApiService {
	
	@Autowired
	private CtRunnerDao rd;
	
}
