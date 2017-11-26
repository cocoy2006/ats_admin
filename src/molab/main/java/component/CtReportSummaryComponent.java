package molab.main.java.component;

import org.springframework.stereotype.Component;

@Component
public class CtReportSummaryComponent {

	private int total;
	private int installFailureCount;
	private int loadFailureCount;
	private int uninstallFailureCount;
	private int passCount;

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * @return the installFailureCount
	 */
	public int getInstallFailureCount() {
		return installFailureCount;
	}

	/**
	 * @param installFailureCount
	 *            the installFailureCount to set
	 */
	public void setInstallFailureCount(int installFailureCount) {
		this.installFailureCount = installFailureCount;
	}

	/**
	 * @return the loadFailureCount
	 */
	public int getLoadFailureCount() {
		return loadFailureCount;
	}

	/**
	 * @param loadFailureCount
	 *            the loadFailureCount to set
	 */
	public void setLoadFailureCount(int loadFailureCount) {
		this.loadFailureCount = loadFailureCount;
	}

	/**
	 * @return the uninstallFailureCount
	 */
	public int getUninstallFailureCount() {
		return uninstallFailureCount;
	}

	/**
	 * @param uninstallFailureCount
	 *            the uninstallFailureCount to set
	 */
	public void setUninstallFailureCount(int uninstallFailureCount) {
		this.uninstallFailureCount = uninstallFailureCount;
	}

	/**
	 * @return the passCount
	 */
	public int getPassCount() {
		return passCount;
	}

	/**
	 * @param passCount
	 *            the passCount to set
	 */
	public void setPassCount(int passCount) {
		this.passCount = passCount;
	}

}
