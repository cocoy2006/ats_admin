package molab.main.java.component;

import molab.main.java.entity.CtRunner;
import molab.main.java.entity.Device;

import org.springframework.stereotype.Component;

@Component
public class CtReportAnalysisComponent {

	private long avgInstallTime;
	private long maxInstallTime;
	private CtRunner maxInstallTimeRunner;
	private Device maxInstallTimeDevice;
	private long minInstallTime;
	private CtRunner minInstallTimeRunner;
	private Device minInstallTimeDevice;

	private long avgLoadTime;
	private long maxLoadTime;
	private CtRunner maxLoadTimeRunner;
	private Device maxLoadTimeDevice;
	private long minLoadTime;
	private CtRunner minLoadTimeRunner;
	private Device minLoadTimeDevice;

	private float avgCpu;
	private float maxCpu;
	private CtRunner maxCpuRunner;
	private Device maxCpuDevice;
	private float minCpu;
	private CtRunner minCpuRunner;
	private Device minCpuDevice;

	private long avgMemory;
	private long maxMemory;
	private CtRunner maxMemoryRunner;
	private Device maxMemoryDevice;
	private long minMemory;
	private CtRunner minMemoryRunner;
	private Device minMemoryDevice;

	private long avgUptraffic;
	private long maxUptraffic;
	private CtRunner maxUptrafficRunner;
	private Device maxUptrafficDevice;
	private long minUptraffic;
	private CtRunner minUptrafficRunner;
	private Device minUptrafficDevice;

	private long avgDowntraffic;
	private long maxDowntraffic;
	private CtRunner maxDowntrafficRunner;
	private Device maxDowntrafficDevice;
	private long minDowntraffic;
	private CtRunner minDowntrafficRunner;
	private Device minDowntrafficDevice;

	/**
	 * @return the avgInstallTime
	 */
	public long getAvgInstallTime() {
		return avgInstallTime;
	}

	/**
	 * @param avgInstallTime
	 *            the avgInstallTime to set
	 */
	public void setAvgInstallTime(long avgInstallTime) {
		this.avgInstallTime = avgInstallTime;
	}

	/**
	 * @return the maxInstallTime
	 */
	public long getMaxInstallTime() {
		return maxInstallTime;
	}

	/**
	 * @param maxInstallTime
	 *            the maxInstallTime to set
	 */
	public void setMaxInstallTime(long maxInstallTime) {
		this.maxInstallTime = maxInstallTime;
	}

	/**
	 * @return the maxInstallTimeRunner
	 */
	public CtRunner getMaxInstallTimeRunner() {
		return maxInstallTimeRunner;
	}

	/**
	 * @param maxInstallTimeRunner
	 *            the maxInstallTimeRunner to set
	 */
	public void setMaxInstallTimeRunner(CtRunner maxInstallTimeRunner) {
		this.maxInstallTimeRunner = maxInstallTimeRunner;
	}

	/**
	 * @return the maxInstallTimeDevice
	 */
	public Device getMaxInstallTimeDevice() {
		return maxInstallTimeDevice;
	}

	/**
	 * @param maxInstallTimeDevice
	 *            the maxInstallTimeDevice to set
	 */
	public void setMaxInstallTimeDevice(Device maxInstallTimeDevice) {
		this.maxInstallTimeDevice = maxInstallTimeDevice;
	}

	/**
	 * @return the minInstallTime
	 */
	public long getMinInstallTime() {
		return minInstallTime;
	}

	/**
	 * @param minInstallTime
	 *            the minInstallTime to set
	 */
	public void setMinInstallTime(long minInstallTime) {
		this.minInstallTime = minInstallTime;
	}

	/**
	 * @return the minInstallTimeRunner
	 */
	public CtRunner getMinInstallTimeRunner() {
		return minInstallTimeRunner;
	}

	/**
	 * @param minInstallTimeRunner
	 *            the minInstallTimeRunner to set
	 */
	public void setMinInstallTimeRunner(CtRunner minInstallTimeRunner) {
		this.minInstallTimeRunner = minInstallTimeRunner;
	}

	/**
	 * @return the minInstallTimeDevice
	 */
	public Device getMinInstallTimeDevice() {
		return minInstallTimeDevice;
	}

	/**
	 * @param minInstallTimeDevice
	 *            the minInstallTimeDevice to set
	 */
	public void setMinInstallTimeDevice(Device minInstallTimeDevice) {
		this.minInstallTimeDevice = minInstallTimeDevice;
	}

	/**
	 * @return the avgLoadTime
	 */
	public long getAvgLoadTime() {
		return avgLoadTime;
	}

	/**
	 * @param avgLoadTime
	 *            the avgLoadTime to set
	 */
	public void setAvgLoadTime(long avgLoadTime) {
		this.avgLoadTime = avgLoadTime;
	}

	/**
	 * @return the maxLoadTime
	 */
	public long getMaxLoadTime() {
		return maxLoadTime;
	}

	/**
	 * @param maxLoadTime
	 *            the maxLoadTime to set
	 */
	public void setMaxLoadTime(long maxLoadTime) {
		this.maxLoadTime = maxLoadTime;
	}

	/**
	 * @return the maxLoadTimeRunner
	 */
	public CtRunner getMaxLoadTimeRunner() {
		return maxLoadTimeRunner;
	}

	/**
	 * @param maxLoadTimeRunner
	 *            the maxLoadTimeRunner to set
	 */
	public void setMaxLoadTimeRunner(CtRunner maxLoadTimeRunner) {
		this.maxLoadTimeRunner = maxLoadTimeRunner;
	}

	/**
	 * @return the maxLoadTimeDevice
	 */
	public Device getMaxLoadTimeDevice() {
		return maxLoadTimeDevice;
	}

	/**
	 * @param maxLoadTimeDevice
	 *            the maxLoadTimeDevice to set
	 */
	public void setMaxLoadTimeDevice(Device maxLoadTimeDevice) {
		this.maxLoadTimeDevice = maxLoadTimeDevice;
	}

	/**
	 * @return the minLoadTime
	 */
	public long getMinLoadTime() {
		return minLoadTime;
	}

	/**
	 * @param minLoadTime
	 *            the minLoadTime to set
	 */
	public void setMinLoadTime(long minLoadTime) {
		this.minLoadTime = minLoadTime;
	}

	/**
	 * @return the minLoadTimeRunner
	 */
	public CtRunner getMinLoadTimeRunner() {
		return minLoadTimeRunner;
	}

	/**
	 * @param minLoadTimeRunner
	 *            the minLoadTimeRunner to set
	 */
	public void setMinLoadTimeRunner(CtRunner minLoadTimeRunner) {
		this.minLoadTimeRunner = minLoadTimeRunner;
	}

	/**
	 * @return the minLoadTimeDevice
	 */
	public Device getMinLoadTimeDevice() {
		return minLoadTimeDevice;
	}

	/**
	 * @param minLoadTimeDevice
	 *            the minLoadTimeDevice to set
	 */
	public void setMinLoadTimeDevice(Device minLoadTimeDevice) {
		this.minLoadTimeDevice = minLoadTimeDevice;
	}

	/**
	 * @return the avgCpu
	 */
	public float getAvgCpu() {
		return avgCpu;
	}

	/**
	 * @param avgCpu
	 *            the avgCpu to set
	 */
	public void setAvgCpu(float avgCpu) {
		this.avgCpu = avgCpu;
	}

	/**
	 * @return the maxCpu
	 */
	public float getMaxCpu() {
		return maxCpu;
	}

	/**
	 * @param maxCpu
	 *            the maxCpu to set
	 */
	public void setMaxCpu(float maxCpu) {
		this.maxCpu = maxCpu;
	}

	/**
	 * @return the maxCpuRunner
	 */
	public CtRunner getMaxCpuRunner() {
		return maxCpuRunner;
	}

	/**
	 * @param maxCpuRunner
	 *            the maxCpuRunner to set
	 */
	public void setMaxCpuRunner(CtRunner maxCpuRunner) {
		this.maxCpuRunner = maxCpuRunner;
	}

	/**
	 * @return the maxCpuDevice
	 */
	public Device getMaxCpuDevice() {
		return maxCpuDevice;
	}

	/**
	 * @param maxCpuDevice
	 *            the maxCpuDevice to set
	 */
	public void setMaxCpuDevice(Device maxCpuDevice) {
		this.maxCpuDevice = maxCpuDevice;
	}

	/**
	 * @return the minCpu
	 */
	public float getMinCpu() {
		return minCpu;
	}

	/**
	 * @param minCpu
	 *            the minCpu to set
	 */
	public void setMinCpu(float minCpu) {
		this.minCpu = minCpu;
	}

	/**
	 * @return the minCpuRunner
	 */
	public CtRunner getMinCpuRunner() {
		return minCpuRunner;
	}

	/**
	 * @param minCpuRunner
	 *            the minCpuRunner to set
	 */
	public void setMinCpuRunner(CtRunner minCpuRunner) {
		this.minCpuRunner = minCpuRunner;
	}

	/**
	 * @return the minCpuDevice
	 */
	public Device getMinCpuDevice() {
		return minCpuDevice;
	}

	/**
	 * @param minCpuDevice
	 *            the minCpuDevice to set
	 */
	public void setMinCpuDevice(Device minCpuDevice) {
		this.minCpuDevice = minCpuDevice;
	}

	/**
	 * @return the avgMemory
	 */
	public long getAvgMemory() {
		return avgMemory;
	}

	/**
	 * @param avgMemory
	 *            the avgMemory to set
	 */
	public void setAvgMemory(long avgMemory) {
		this.avgMemory = avgMemory;
	}

	/**
	 * @return the maxMemory
	 */
	public long getMaxMemory() {
		return maxMemory;
	}

	/**
	 * @param maxMemory
	 *            the maxMemory to set
	 */
	public void setMaxMemory(long maxMemory) {
		this.maxMemory = maxMemory;
	}

	/**
	 * @return the maxMemoryRunner
	 */
	public CtRunner getMaxMemoryRunner() {
		return maxMemoryRunner;
	}

	/**
	 * @param maxMemoryRunner
	 *            the maxMemoryRunner to set
	 */
	public void setMaxMemoryRunner(CtRunner maxMemoryRunner) {
		this.maxMemoryRunner = maxMemoryRunner;
	}

	/**
	 * @return the maxMemoryDevice
	 */
	public Device getMaxMemoryDevice() {
		return maxMemoryDevice;
	}

	/**
	 * @param maxMemoryDevice
	 *            the maxMemoryDevice to set
	 */
	public void setMaxMemoryDevice(Device maxMemoryDevice) {
		this.maxMemoryDevice = maxMemoryDevice;
	}

	/**
	 * @return the minMemory
	 */
	public long getMinMemory() {
		return minMemory;
	}

	/**
	 * @param minMemory
	 *            the minMemory to set
	 */
	public void setMinMemory(long minMemory) {
		this.minMemory = minMemory;
	}

	/**
	 * @return the minMemoryRunner
	 */
	public CtRunner getMinMemoryRunner() {
		return minMemoryRunner;
	}

	/**
	 * @param minMemoryRunner
	 *            the minMemoryRunner to set
	 */
	public void setMinMemoryRunner(CtRunner minMemoryRunner) {
		this.minMemoryRunner = minMemoryRunner;
	}

	/**
	 * @return the minMemoryDevice
	 */
	public Device getMinMemoryDevice() {
		return minMemoryDevice;
	}

	/**
	 * @param minMemoryDevice
	 *            the minMemoryDevice to set
	 */
	public void setMinMemoryDevice(Device minMemoryDevice) {
		this.minMemoryDevice = minMemoryDevice;
	}

	/**
	 * @return the avgUptraffic
	 */
	public long getAvgUptraffic() {
		return avgUptraffic;
	}

	/**
	 * @param avgUptraffic
	 *            the avgUptraffic to set
	 */
	public void setAvgUptraffic(long avgUptraffic) {
		this.avgUptraffic = avgUptraffic;
	}

	/**
	 * @return the maxUptraffic
	 */
	public long getMaxUptraffic() {
		return maxUptraffic;
	}

	/**
	 * @param maxUptraffic
	 *            the maxUptraffic to set
	 */
	public void setMaxUptraffic(long maxUptraffic) {
		this.maxUptraffic = maxUptraffic;
	}

	/**
	 * @return the maxUptrafficRunner
	 */
	public CtRunner getMaxUptrafficRunner() {
		return maxUptrafficRunner;
	}

	/**
	 * @param maxUptrafficRunner
	 *            the maxUptrafficRunner to set
	 */
	public void setMaxUptrafficRunner(CtRunner maxUptrafficRunner) {
		this.maxUptrafficRunner = maxUptrafficRunner;
	}

	/**
	 * @return the maxUptrafficDevice
	 */
	public Device getMaxUptrafficDevice() {
		return maxUptrafficDevice;
	}

	/**
	 * @param maxUptrafficDevice
	 *            the maxUptrafficDevice to set
	 */
	public void setMaxUptrafficDevice(Device maxUptrafficDevice) {
		this.maxUptrafficDevice = maxUptrafficDevice;
	}

	/**
	 * @return the minUptraffic
	 */
	public long getMinUptraffic() {
		return minUptraffic;
	}

	/**
	 * @param minUptraffic
	 *            the minUptraffic to set
	 */
	public void setMinUptraffic(long minUptraffic) {
		this.minUptraffic = minUptraffic;
	}

	/**
	 * @return the minUptrafficRunner
	 */
	public CtRunner getMinUptrafficRunner() {
		return minUptrafficRunner;
	}

	/**
	 * @param minUptrafficRunner
	 *            the minUptrafficRunner to set
	 */
	public void setMinUptrafficRunner(CtRunner minUptrafficRunner) {
		this.minUptrafficRunner = minUptrafficRunner;
	}

	/**
	 * @return the minUptrafficDevice
	 */
	public Device getMinUptrafficDevice() {
		return minUptrafficDevice;
	}

	/**
	 * @param minUptrafficDevice
	 *            the minUptrafficDevice to set
	 */
	public void setMinUptrafficDevice(Device minUptrafficDevice) {
		this.minUptrafficDevice = minUptrafficDevice;
	}

	/**
	 * @return the avgDowntraffic
	 */
	public long getAvgDowntraffic() {
		return avgDowntraffic;
	}

	/**
	 * @param avgDowntraffic
	 *            the avgDowntraffic to set
	 */
	public void setAvgDowntraffic(long avgDowntraffic) {
		this.avgDowntraffic = avgDowntraffic;
	}

	/**
	 * @return the maxDowntraffic
	 */
	public long getMaxDowntraffic() {
		return maxDowntraffic;
	}

	/**
	 * @param maxDowntraffic
	 *            the maxDowntraffic to set
	 */
	public void setMaxDowntraffic(long maxDowntraffic) {
		this.maxDowntraffic = maxDowntraffic;
	}

	/**
	 * @return the maxDowntrafficRunner
	 */
	public CtRunner getMaxDowntrafficRunner() {
		return maxDowntrafficRunner;
	}

	/**
	 * @param maxDowntrafficRunner
	 *            the maxDowntrafficRunner to set
	 */
	public void setMaxDowntrafficRunner(CtRunner maxDowntrafficRunner) {
		this.maxDowntrafficRunner = maxDowntrafficRunner;
	}

	/**
	 * @return the maxDowntrafficDevice
	 */
	public Device getMaxDowntrafficDevice() {
		return maxDowntrafficDevice;
	}

	/**
	 * @param maxDowntrafficDevice
	 *            the maxDowntrafficDevice to set
	 */
	public void setMaxDowntrafficDevice(Device maxDowntrafficDevice) {
		this.maxDowntrafficDevice = maxDowntrafficDevice;
	}

	/**
	 * @return the minDowntraffic
	 */
	public long getMinDowntraffic() {
		return minDowntraffic;
	}

	/**
	 * @param minDowntraffic
	 *            the minDowntraffic to set
	 */
	public void setMinDowntraffic(long minDowntraffic) {
		this.minDowntraffic = minDowntraffic;
	}

	/**
	 * @return the minDowntrafficRunner
	 */
	public CtRunner getMinDowntrafficRunner() {
		return minDowntrafficRunner;
	}

	/**
	 * @param minDowntrafficRunner
	 *            the minDowntrafficRunner to set
	 */
	public void setMinDowntrafficRunner(CtRunner minDowntrafficRunner) {
		this.minDowntrafficRunner = minDowntrafficRunner;
	}

	/**
	 * @return the minDowntrafficDevice
	 */
	public Device getMinDowntrafficDevice() {
		return minDowntrafficDevice;
	}

	/**
	 * @param minDowntrafficDevice
	 *            the minDowntrafficDevice to set
	 */
	public void setMinDowntrafficDevice(Device minDowntrafficDevice) {
		this.minDowntrafficDevice = minDowntrafficDevice;
	}

}
