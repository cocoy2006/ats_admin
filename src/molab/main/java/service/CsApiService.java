package molab.main.java.service;

import java.util.List;

import molab.main.java.dao.CtRunnerDao;
import molab.main.java.entity.CtRunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CsApiService {
	
	@Autowired
	private CtRunnerDao rd;
	
	public List<CtRunner> findRunnerList(int state, String server, String port) {
		String hql = String
				.format("from CtRunner where state=%d and deviceId in (select id from CtDevice where server='%s' and port=%s)",
						state, server, port);
		return rd.find(hql);
	}
	
	public void updateRunnerState(int id, int state) {
		CtRunner runner = rd.findById(id);
		if(runner.getState() != state) {
			runner.setState(state);
			rd.update(runner);
		}
	}
	
}
