package molab.main.java.component;

import molab.main.java.entity.Application;
import molab.main.java.entity.CsDispatcher;
import molab.main.java.entity.Customer;
import molab.main.java.entity.Project;
import molab.main.java.entity.Testcase;

import org.springframework.stereotype.Component;

@Component
public class CsDispatcherComponent {

	private Application application;
	private CsDispatcher dispatcher;
	private Customer customer;
	private Project project;
	private Testcase testcase;
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
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @param customer
	 *            the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * @param project
	 *            the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * @return the testcase
	 */
	public Testcase getTestcase() {
		return testcase;
	}

	/**
	 * @param testcase
	 *            the testcase to set
	 */
	public void setTestcase(Testcase testcase) {
		this.testcase = testcase;
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

}
