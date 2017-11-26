package molab.main.java.util;

import java.util.List;

import molab.main.java.component.CtReportSummaryComponent;
import molab.main.java.entity.CtRunner;

public class ReportUtil {
	
	public static String loadPassrate(List<CtRunner> runners) {
		int size = runners.size();
		int count = 0;
		for (CtRunner runner : runners) {
			if (runner.getState() == Status.RunnerStatus.END.getInt()
					&& runner.getInstallTime() > 0 && runner.getLoadTime() > 0
					&& runner.getUninstallTime() > 0) {
				count++;
			}
		}
		return Molab.setAccuracy((float) count / size * 100, 0);
	}
	
	public static CtReportSummaryComponent loadSummary(List<CtRunner> runners) {
		int installFailureCount = 0, loadFailureCount = 0, uninstallFailureCount = 0, passCount = 0;
		for (CtRunner runner : runners) {
			if (runner.getInstallTime() == 0) {
				installFailureCount++;
				continue;
			}
			if (runner.getLoadTime() == 0) {
				loadFailureCount++;
				continue;
			}
			if (runner.getUninstallTime() == 0) {
				uninstallFailureCount++;
				continue;
			}
			passCount++;
		}
		CtReportSummaryComponent rsc = new CtReportSummaryComponent();
		rsc.setInstallFailureCount(installFailureCount);
		rsc.setLoadFailureCount(loadFailureCount);
		rsc.setUninstallFailureCount(uninstallFailureCount);
		rsc.setPassCount(passCount);
//		rsc.setPassRate(passCount / runners.size() * 100);
		return rsc;
	}
	
}
