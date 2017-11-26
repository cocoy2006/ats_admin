package molab.main.java.component;

import java.util.List;

import molab.main.java.entity.CtScreenshot;
import molab.main.java.entity.Device;

import org.springframework.stereotype.Component;

@Component
public class CtReportSsComponent {

	private Device device;
	private List<CtScreenshot> ssList;

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

}
