package molab.main.java.util;

public class Status {

	public static enum Common {
		ERROR(0), SUCCESS(1), REMOVED(9);
		private int value;

		private Common(int value) {
			this.value = value;
		}

		public int getInt() {
			return value;
		}
	}
	
	public static enum DispatcherStatus {
		APP_PARSE_FAILURE(-3), START(0), REPORTING(1), END(2), REMOVED(9);
		private int value;

		private DispatcherStatus(int value) {
			this.value = value;
		}

		public int getInt() {
			return value;
		}
	}

	public static enum RunnerStatus {
		START(0), DOWNLOAD(1), READY(2), RUNNING(3), INSTALL(4), LAUNCH(5), MONKEY(6), UNINSTALL(7), END(8), REMOVED(9);
		private int value;

		private RunnerStatus(int value) {
			this.value = value;
		}

		public int getInt() {
			return value;
		}
	}
	
	public static enum ScreenshotStatus {
		COMMON(0), REMOVED(9);
		private int value;

		private ScreenshotStatus(int value) {
			this.value = value;
		}

		public int getInt() {
			return value;
		}
	}
	
	public static enum SubRunnerStatus {
		COMMON(0), REMOVED(9);
		private int value;

		private SubRunnerStatus(int value) {
			this.value = value;
		}

		public int getInt() {
			return value;
		}
	}
	
	public static enum DeviceType {
		UNASSIGNED(0), CT(1), ICT(2), CR(3), CS(4);
		private int value;

		private DeviceType(int value) {
			this.value = value;
		}

		public int getInt() {
			return value;
		}
	}
	
	public static enum DeviceStatus {
		FREE(0), BUSY(1), RESERVED(2), UNACTIVED(3), PRELOCK(4), OFFLINE(5), UNAUTHORIZED(6), REMOVED(9);
		private int value;

		private DeviceStatus(int value) {
			this.value = value;
		}

		public int getInt() {
			return value;
		}
	}
	
	public static enum TestcaseStatus {
		START(0), END(1), REMOVED(9);
		private int value;

		private TestcaseStatus(int value) {
			this.value = value;
		}

		public int getInt() {
			return value;
		}
	}
	
	public static enum RecordStatus {
		APP_START_FAILURE(-3), APP_NOT_FOUND(-2), NO_TESTCASE_ID(-1);
		private int value;

		private RecordStatus(int value) {
			this.value = value;
		}

		public int getInt() {
			return value;
		}
	}
	
	public static enum CommandType {
		INSTALL, SHELL;
	}
	
	public static enum LogcatType {
		CRASH(1), ANR(2), EXCEPTION(3);
		private int value;

		private LogcatType(int value) {
			this.value = value;
		}

		public int getInt() {
			return value;
		}
	}

}