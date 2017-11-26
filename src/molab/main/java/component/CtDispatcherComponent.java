package molab.main.java.component;

import molab.main.java.entity.Application;
import molab.main.java.entity.CtDispatcher;

import org.springframework.stereotype.Component;

@Component
public class CtDispatcherComponent {

	private Application application;
	private CtDispatcher dispatcher;
	private String oprTime;
	private int allCount;
	private int successCount;
	private int failureCount;
	private int runningCount;
	private int removedCount;

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
	 * @return the oprTime
	 */
	public String getOprTime() {
		return oprTime;
	}

	/**
	 * @param oprTime
	 *            the oprTime to set
	 */
	public void setOprTime(String oprTime) {
		this.oprTime = oprTime;
	}

	/**
	 * @return the allCount
	 */
	public int getAllCount() {
		return allCount;
	}

	/**
	 * @param allCount
	 *            the allCount to set
	 */
	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}

	/**
	 * @return the successCount
	 */
	public int getSuccessCount() {
		return successCount;
	}

	/**
	 * @param successCount
	 *            the successCount to set
	 */
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	/**
	 * @return the failureCount
	 */
	public int getFailureCount() {
		return failureCount;
	}

	/**
	 * @param failureCount
	 *            the failureCount to set
	 */
	public void setFailureCount(int failureCount) {
		this.failureCount = failureCount;
	}

	/**
	 * @return the runningCount
	 */
	public int getRunningCount() {
		return runningCount;
	}

	/**
	 * @param runningCount
	 *            the runningCount to set
	 */
	public void setRunningCount(int runningCount) {
		this.runningCount = runningCount;
	}

	/**
	 * @return the removedCount
	 */
	public int getRemovedCount() {
		return removedCount;
	}

	/**
	 * @param removedCount
	 *            the removedCount to set
	 */
	public void setRemovedCount(int removedCount) {
		this.removedCount = removedCount;
	}

}
