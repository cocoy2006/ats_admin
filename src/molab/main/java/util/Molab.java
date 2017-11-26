package molab.main.java.util;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

import com.android.ddmlib.IDevice;

public class Molab {
	
	private static final Logger log = Logger.getLogger(Molab.class.getName());
	public static final String LAST_TIME = "opoL8fPcOoiJWZf594oE5Q=="; // 2016.04.29, useless but for atsgz
	public static final String EXPIRE_TIME = "/lpB4F8INDSWu2sX6Jjm/g=="; // 2017.11.01
	public static final String SESSION_CR_SCREENSHOT = "cr_screenshot";
	public static final String SESSION_CR_INSTALL_RESULT = "cr_install_result";
	public static final String SESSION_CR_TESTCASE_ID = "cr_testcase_id";
	public static final String SESSION_CR_CUR_SCRIPT = "cr_cur_script";
	public static final String SESSION_CT_APP_PARSE_RESULT = "ct_app_parse_result";
	
	public static final String CFG_SELF_SERVER = "self_server";
	public static final String CFG_SELF_PORT = "self_port";
	public static final String CFG_WEB_SERVER = "web_server";
	public static final String CFG_WEB_PORT = "web_port";
	public static final String CFG_WEB_NAME = "web_name";
	public static final String CFG_ROM_INFO = "rom_info";
//	public static final String CFG_GLOBAL_STRATEGY_REMOVED = "global_strategy_removed";
//	public static final int CFG_GLOBAL_STRATEGY_REMOVED_DEFAULT = 0;
	public static final String CFG_CS_PLAYBACK_TIMEOUT = "cs_playback_timeout";
	public static final int CFG_CS_PLAYBACK_TIMEOUT_DEFAULT = 120;
	
//	public static final Map<String, String> BRAND_ROM = new HashMap<String, String>() {
//		{
//			put("meizu", "ro.build.display.id");
//			put("oppo", "ro.rom.different.version");
//			put("vivo", "ro.vivo.os.build.display.id");
//			put("xiaomi", "ro.miui.ui.version.name");
//		}
//	};
	
	private static Molab molab = null;
	private static Properties props = null;

	public static Molab getInstance() {
		if (molab == null) {
			synchronized (Molab.class) {
				molab = new Molab();
				props = PropertiesUtil.load(Path.cfg());
			}
		}
		return molab;
	}
	
	public String getProperty(String key) {
		if(props.containsKey(key)) {
			return props.getProperty(key);
		}
		return null;
	}
	
	public static boolean isApkExist(String aliasName) {
		String apk = Path.apk(aliasName);
		return new File(apk).exists();
	}
	
	public static boolean isIpaExist(String aliasName) {
		String ipa = Path.ipa(aliasName);
		return new File(ipa).exists();
	}
	
	public static boolean isLaterVersion(IDevice iDevice) {
		String apiLevel = iDevice.getProperty(IDevice.PROP_BUILD_API_LEVEL);
		if(apiLevel != null) {
			int api = Integer.parseInt(apiLevel.trim());
			if(api > 12) {
				return true;
			}
		}
		return false;
	}

	public static String parseTime(long time) {
		try {
			Date date = new Date(time);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return sdf.format(date);
		} catch (Exception e) {
			log.severe(e.getMessage());
			return String.valueOf(time);
		}
	}
	
	public static String rename(String suffix) {
		String prefix = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		return prefix + suffix;
	}
	
	// 删除文件夹
	public static void deleteFolder(String folderPath) {
		deleteFiles(folderPath); // 删除完里面所有内容
		String filePath = folderPath;
        filePath = filePath.toString();
        File folderFile = new File(filePath);
        folderFile.delete(); //删除空文件夹
	}

	// 删除指定文件夹下所有文件
	private static void deleteFiles(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				deleteFiles(path + File.separator + tempList[i]);// 先删除文件夹里面的文件
				deleteFolder(path + File.separator + tempList[i]);// 再删除空文件夹
			}
		}
	}
	
	public static String setAccuracy(Float f) {
		return setAccuracy(f, 2);
	}
	
	public static String setAccuracy(Float f, int scale) {
		if(f.compareTo(0f) == 0) {
			return "-";
		}
		if(scale == 0) {
			return String.valueOf(Math.round(f));
		}
		String pattern = "0.";
		for(int i = 0; i < scale; i++) {
			pattern += "#";
		}
		DecimalFormat format = new DecimalFormat(pattern);
		return format.format(f);
	}
	
	public static float rescale(float f) {
		return rescale(f, 2);
	}
	
	public static float rescale(float f, int scale) {
		String pattern = "0.";
		for(int i = 0; i < scale; i++) {
			pattern += "#";
		}
		DecimalFormat format = new DecimalFormat(pattern);
		return Float.valueOf(format.format(f));
	}
	
	public static String host() {
		return String.format(
				"http://%s:%s/%s/", 
				getInstance().getProperty(CFG_WEB_SERVER), 
				getInstance().getProperty(CFG_WEB_PORT), 
				getInstance().getProperty(CFG_WEB_NAME));
	}
	
	public static String parseSize(long size) {
		if(size > 1073741824) {
			return String.valueOf(size / 1073741824).concat("GB");
		} else if(size > 1048576) {
			return String.valueOf(size / 1048576).concat("MB");
		} else if(size > 1024) {
			return String.valueOf(size / 1024).concat("KB");
		} else {
			return String.valueOf(size).concat("B");
		}
	}

}
