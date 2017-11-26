package molab.main.java.service.timer;

import java.util.List;
import java.util.logging.Logger;

import molab.main.java.dao.ICtDispatcherDao;
import molab.main.java.dao.ICtRunnerDao;
import molab.main.java.entity.ICtDispatcher;
import molab.main.java.entity.ICtRunner;
import molab.main.java.util.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ICtDispatcherTimer {

	private static final Logger log = Logger.getLogger(ICtDispatcherTimer.class.getName());
	
	@Autowired
	private ICtDispatcherDao dao;
	
	@Autowired
	private ICtRunnerDao rd;
	
	@Scheduled(cron = "0 0/1 * * * ?")
    public void run() {
		String hql = "from ICtDispatcher where state!=?";
		List<ICtDispatcher> dispatcherList = dao.find(hql, Status.DispatcherStatus.END.getInt());
		if(dispatcherList != null && dispatcherList.size() > 0) {
			for(ICtDispatcher dispatcher : dispatcherList) {
				hql = "from ICtRunner where dispatcherId=? and state!=?";
				List<ICtRunner> runnerList = rd.find(hql, dispatcher.getId(), Status.RunnerStatus.END.getInt());
				if(runnerList == null || runnerList.size() == 0) {
					log.info(String.format("ICtDispatcher with id %d ended successfully.", dispatcher.getId()));
					dispatcher.setState(Status.DispatcherStatus.END.getInt());
					dao.update(dispatcher);
				}
			}
		}
    }
	
}
