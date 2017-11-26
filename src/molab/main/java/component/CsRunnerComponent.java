package molab.main.java.component;

import java.util.List;

import molab.main.java.entity.CsDispatcher;
import molab.main.java.entity.CsRunner;
import molab.main.java.entity.CsScreenshot;
import molab.main.java.entity.Device;

public class CsRunnerComponent {

	private CsRunner runner;
	private String logcat;
	private CsDispatcher dispatcher;
	private Device device;
	private List<CsScreenshot> ssList;

	/**
	 * @return the runner
	 */
	public CsRunner getRunner() {
		return runner;
	}

	/**
	 * @param runner
	 *            the runner to set
	 */
	public void setRunner(CsRunner runner) {
		this.runner = runner;
	}

	/**
	 * @return the logcat
	 */
	public String getLogcat() {
		return logcat;
	}

	/**
	 * @param logcat
	 *            the logcat to set
	 */
	public void setLogcat(String logcat) {
		this.logcat = logcat;
	}

	/**
	 * @return the dispatcher
	 */
	public CsDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * @param dispatcher
	 *            the dispatcher to set
	 */
	public void setDispatcher(CsDispatcher dispatcher) {
		this.dispatcher = dispatcher;
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
	 * @return the ssList
	 */
	public List<CsScreenshot> getSsList() {
		return ssList;
	}

	/**
	 * @param ssList
	 *            the ssList to set
	 */
	public void setSsList(List<CsScreenshot> ssList) {
		this.ssList = ssList;
	}

}
