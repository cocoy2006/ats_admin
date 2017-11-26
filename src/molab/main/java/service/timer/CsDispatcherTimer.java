package molab.main.java.service.timer;

import java.util.List;
import java.util.logging.Logger;

import molab.main.java.dao.CsDispatcherDao;
import molab.main.java.dao.CsRunnerDao;
import molab.main.java.entity.CsDispatcher;
import molab.main.java.entity.CsRunner;
import molab.main.java.util.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CsDispatcherTimer {

	private static final Logger log = Logger.getLogger(CsDispatcherTimer.class.getName());
	
	@Autowired
	private CsDispatcherDao dao;
	
	@Autowired
	private CsRunnerDao rd;
	
	@Scheduled(cron = "0 0/1 * * * ?")
    public void run() {
		String hql = "from CsDispatcher where state!=?";
		List<CsDispatcher> dispatcherList = dao.find(hql, Status.DispatcherStatus.END.getInt());
		if(dispatcherList != null && dispatcherList.size() > 0) {
			for(CsDispatcher dispatcher : dispatcherList) {
				hql = "from CsRunner where dispatcherId=? and state!=?";
				List<CsRunner> runnerList = rd.find(hql, dispatcher.getId(), Status.RunnerStatus.END.getInt());
				if(runnerList == null || runnerList.size() == 0) {
					log.info(String.format("Dispatcher with id %d ended successfully.", dispatcher.getId()));
					dispatcher.setState(Status.DispatcherStatus.END.getInt());
					dao.update(dispatcher);
				}
			}
		}
    }
	
}
