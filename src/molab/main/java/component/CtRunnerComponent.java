package molab.main.java.component;

import java.util.List;

import molab.main.java.entity.Application;
import molab.main.java.entity.CtDispatcher;
import molab.main.java.entity.CtRunner;
import molab.main.java.entity.CtScreenshot;
import molab.main.java.entity.CtSubRunner;
import molab.main.java.entity.Device;
import molab.main.java.entity.Logcat;
import molab.main.java.util.Molab;

public class CtRunnerComponent {

	private CtRunner runner;
	private String displayAverageCpu;
	private String displayTopCpu;
	private String displayAverageMemory;
	private String displayTopMemory;
	private String displayInstallTime;
	private String displayLoadTime;
	private String displayUninstallTime;
	private List<Logcat> logcatList;
	private CtDispatcher dispatcher;
	private Application application;
	private Device device;
	private List<CtSubRunner> srList;
	private List<CtScreenshot> ssList;
	private int ssCount;

	/**
	 * @return the runner
	 */
	public CtRunner getRunner() {
		return runner;
	}

	/**
	 * @param runner
	 *            the runner to set
	 */
	public void setRunner(CtRunner runner) {
		this.runner = runner;
		if (runner != null) {
			this.displayAverageCpu = Molab.setAccuracy(runner.getAverageCpu());
			this.displayAverageMemory = Molab.setAccuracy((float) runner
					.getAverageMemory() / 1024);
			this.displayInstallTime = Molab.setAccuracy((float) runner
					.getInstallTime() / 1000);
			this.displayLoadTime = Molab.setAccuracy((float) runner
					.getLoadTime() / 1000);
			this.displayUninstallTime = Molab.setAccuracy((float) runner
					.getUninstallTime() / 1000);
		}
	}

	/**
	 * @return the displayAverageCpu
	 */
	public String getDisplayAverageCpu() {
		return displayAverageCpu;
	}

	/**
	 * @param displayAverageCpu
	 *            the displayAverageCpu to set
	 */
	public void setDisplayAverageCpu(String displayAverageCpu) {
		this.displayAverageCpu = displayAverageCpu;
	}

	/**
	 * @return the displayTopCpu
	 */
	public String getDisplayTopCpu() {
		return displayTopCpu;
	}

	/**
	 * @param displayTopCpu
	 *            the displayTopCpu to set
	 */
	public void setDisplayTopCpu(String displayTopCpu) {
		this.displayTopCpu = displayTopCpu;
	}

	/**
	 * @return the displayAverageMemory
	 */
	public String getDisplayAverageMemory() {
		return displayAverageMemory;
	}

	/**
	 * @param displayAverageMemory
	 *            the displayAverageMemory to set
	 */
	public void setDisplayAverageMemory(String displayAverageMemory) {
		this.displayAverageMemory = displayAverageMemory;
	}

	/**
	 * @return the displayTopMemory
	 */
	public String getDisplayTopMemory() {
		return displayTopMemory;
	}

	/**
	 * @param displayTopMemory
	 *            the displayTopMemory to set
	 */
	public void setDisplayTopMemory(String displayTopMemory) {
		this.displayTopMemory = displayTopMemory;
	}

	/**
	 * @return the displayInstallTime
	 */
	public String getDisplayInstallTime() {
		return displayInstallTime;
	}

	/**
	 * @param displayInstallTime
	 *            the displayInstallTime to set
	 */
	public void setDisplayInstallTime(String displayInstallTime) {
		this.displayInstallTime = displayInstallTime;
	}

	/**
	 * @return the displayLoadTime
	 */
	public String getDisplayLoadTime() {
		return displayLoadTime;
	}

	/**
	 * @param displayLoadTime
	 *            the displayLoadTime to set
	 */
	public void setDisplayLoadTime(String displayLoadTime) {
		this.displayLoadTime = displayLoadTime;
	}

	/**
	 * @return the displayUninstallTime
	 */
	public String getDisplayUninstallTime() {
		return displayUninstallTime;
	}

	/**
	 * @param displayUninstallTime
	 *            the displayUninstallTime to set
	 */
	public void setDisplayUninstallTime(String displayUninstallTime) {
		this.displayUninstallTime = displayUninstallTime;
	}

	/**
	 * @return the logcatList
	 */
	public List<Logcat> getLogcatList() {
		return logcatList;
	}

	/**
	 * @param logcatList
	 *            the logcatList to set
	 */
	public void setLogcatList(List<Logcat> logcatList) {
		this.logcatList = logcatList;
	}

	/**
	 * @return the dispatcher
	 */
	public CtDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * @param dispatcher
	 *            the dispatcher to set
	 */
	public void setDispatcher(CtDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	/**
	 * @return the application
	 */
	public Application getApplication() {
		return application;
	}

	/**
	 * @param application
	 *            the application to set
	 */
	public void setApplication(Application application) {
		this.application = application;
	}

	/**
	 * @return the device
	 */
	public Device getDevice() {
		return device;
	}

	/**
	 * @param device
	 *            the device to set
	 */
	public void setDevice(Device device) {
		this.device = device;
	}

	/**
	 * @return the srList
	 */
	public List<CtSubRunner> getSrList() {
		return srList;
	}

	/**
	 * @param srList
	 *            the srList to set
	 */
	public void setSrList(List<CtSubRunner> srList) {
		this.srList = srList;
	}

	/**
	 * @return the ssList
	 */
	public List<CtScreenshot> getSsList() {
		return ssList;
	}

	/**
	 * @param ssList
	 *            the ssList to set
	 */
	public void setSsList(List<CtScreenshot> ssList) {
		this.ssList = ssList;
	}

	/**
	 * @return the ssCount
	 */
	public int getSsCount() {
		return ssCount;
	}

	/**
	 * @param ssCount
	 *            the ssCount to set
	 */
	public void setSsCount(int ssCount) {
		this.ssCount = ssCount;
	}

}
