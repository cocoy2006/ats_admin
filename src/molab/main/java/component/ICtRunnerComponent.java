package molab.main.java.component;

import java.util.List;

import molab.main.java.entity.Application;
import molab.main.java.entity.ICtDevice;
import molab.main.java.entity.ICtDispatcher;
import molab.main.java.entity.ICtRunner;
import molab.main.java.entity.ICtScreenshot;
import molab.main.java.util.Molab;

public class ICtRunnerComponent {

	private ICtRunner runner;
	private String displayInstallTime;
	private String displayLoadTime;
	private String displayUninstallTime;
	private ICtDispatcher dispatcher;
	private Application application;
	private ICtDevice device;
	private List<ICtScreenshot> ssList;

	/**
	 * @return the runner
	 */
	public ICtRunner getRunner() {
		return runner;
	}

	/**
	 * @param runner
	 *            the runner to set
	 */
	public void setRunner(ICtRunner runner) {
		this.runner = runner;
		if (runner != null) {
			this.displayInstallTime = Molab.setAccuracy((float) runner
					.getInstallTime() / 1000);
			this.displayLoadTime = Molab.setAccuracy((float) runner
					.getLoadTime() / 1000);
			this.displayUninstallTime = Molab.setAccuracy((float) runner
					.getUninstallTime() / 1000);
		}
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
	 * @return the dispatcher
	 */
	public ICtDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * @param dispatcher
	 *            the dispatcher to set
	 */
	public void setDispatcher(ICtDispatcher dispatcher) {
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
	public ICtDevice getDevice() {
		return device;
	}

	/**
	 * @param device
	 *            the device to set
	 */
	public void setDevice(ICtDevice device) {
		this.device = device;
	}

	/**
	 * @return the ssList
	 */
	public List<ICtScreenshot> getSsList() {
		return ssList;
	}

	/**
	 * @param ssList
	 *            the ssList to set
	 */
	public void setSsList(List<ICtScreenshot> ssList) {
		this.ssList = ssList;
	}

}
