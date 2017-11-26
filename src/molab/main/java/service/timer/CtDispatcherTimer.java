package molab.main.java.service.timer;

import java.util.List;
import java.util.logging.Logger;

import molab.main.java.dao.CtDispatcherDao;
import molab.main.java.dao.CtRunnerDao;
import molab.main.java.dao.LogcatDao;
import molab.main.java.entity.CtDispatcher;
import molab.main.java.entity.CtRunner;
import molab.main.java.entity.Logcat;
import molab.main.java.util.LogcatUtil;
import molab.main.java.util.Path;
import molab.main.java.util.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CtDispatcherTimer {

	private static final Logger log = Logger.getLogger(CtDispatcherTimer.class.getName());
	
	@Autowired
	private CtDispatcherDao dao;
	
	@Autowired
	private CtRunnerDao rd;
	
	@Autowired
	private LogcatDao ld;
	
	@Scheduled(cron = "0 0/1 * * * ?")
    public void run() {
		String hql = "from CtDispatcher where state!=?";
		List<CtDispatcher> dispatcherList = dao.find(hql, Status.DispatcherStatus.END.getInt());
		if(dispatcherList != null && dispatcherList.size() > 0) {
			for(CtDispatcher dispatcher : dispatcherList) {
				hql = "from CtRunner where dispatcherId=? and state!=?";
				List<CtRunner> runnerList = rd.find(hql, dispatcher.getId(), Status.RunnerStatus.END.getInt());
				if(runnerList == null || runnerList.size() == 0) {
					// first analysis logcat.log & anr.log
					try {
						analysis(dispatcher.getId());
					} catch(Exception e) {}
					// then end the dispatcher
					log.info(String.format("Dispatcher with id %d ended successfully.", dispatcher.getId()));
					dispatcher.setState(Status.DispatcherStatus.END.getInt());
					dao.update(dispatcher);
				}
			}
		}
    }
	
	private void analysis(int dispatcherId) {
		String hql = "from CtRunner where dispatcherId=?";
		List<CtRunner> runnerList = rd.find(hql, dispatcherId);
		if(runnerList != null && runnerList.size() > 0) {
			for(CtRunner runner : runnerList) {
				// logcat.log
				String logcatFile = Path.ctLog(runner.getId() + ".logcat.log");
				analysisCrash(logcatFile, runner.getId());
				analysisException(logcatFile, runner.getId());
				// anr.log
				String anrFile = Path.ctLog(runner.getId() + ".anr.log");
				analysisAnr(anrFile, runner.getId());
				log.info(String.format("Log analysis(runner.id=%d) done.", runner.getId()));
			}
		}
	}
	
	private void analysisCrash(String file, int runnerId) {
		List<Logcat> logcatList = LogcatUtil.findLogcat(file, "ative crash");
		if(logcatList != null && logcatList.size() > 0) {
			for(Logcat logcat : logcatList) {
				logcat.setRunnerId(runnerId);
				logcat.setType(Status.LogcatType.CRASH.getInt());
				ld.save(logcat);
			}
		}
	}
	
	private void analysisException(String file, int runnerId) {
		List<Logcat> logcatList = LogcatUtil.findLogcat(file, "Exception:");
		if(logcatList != null && logcatList.size() > 0) {
			for(Logcat logcat : logcatList) {
				logcat.setRunnerId(runnerId);
				logcat.setType(Status.LogcatType.EXCEPTION.getInt());
				ld.save(logcat);
			}
		}
	}
	
	private void analysisAnr(String file, int runnerId) {
		List<Logcat> logcatList = LogcatUtil.findAnr(file);
		if(logcatList != null && logcatList.size() > 0) {
			for(Logcat logcat : logcatList) {
				logcat.setRunnerId(runnerId);
				logcat.setType(Status.LogcatType.ANR.getInt());
				ld.save(logcat);
			}
		}
	}
	
}
