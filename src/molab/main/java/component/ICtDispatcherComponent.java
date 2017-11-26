package molab.main.java.component;

import molab.main.java.entity.Application;
import molab.main.java.entity.ICtDispatcher;

import org.springframework.stereotype.Component;

@Component
public class ICtDispatcherComponent {

	private Application application;
	private ICtDispatcher dispatcher;
	private String oprTime;

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
	 * @return the oprTime
	 */
	public String getOprTime() {
		return oprTime;
	}

	/**
	 * @param oprTime the oprTime to set
	 */
	public void setOprTime(String oprTime) {
		this.oprTime = oprTime;
	}

}
