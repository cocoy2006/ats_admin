package molab.main.java.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertiesUtil {
	
	private static final Logger log = Logger.getLogger(PropertiesUtil.class.getName());

	public static Properties load(String file) {
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(file));
			return load(is);
		} catch (Exception e) {
			log.severe(e.getMessage());
			return null;
		}
	}

	public static Properties load(InputStream is) {
		Properties props = new Properties();
		try {
			props.load(is);
			return props;
		} catch (Exception e) {
			log.severe(e.getMessage());
			return null;
		}
	}
	
	public static String get(String file, String key) {
		Properties props = load(file);
		if(props != null) {
			Object obj = props.get(key);
			if(obj != null) {
				return String.valueOf(obj);
			}
		}
		return null;
	}
	
//	public static int getGlobalStrategyRemoved() {
//		String str = get(Path.cfg(), Molab.CFG_GLOBAL_STRATEGY_REMOVED);
//		if(str != null) {
//			return Integer.parseInt(str);
//		}
//		return Molab.CFG_GLOBAL_STRATEGY_REMOVED_DEFAULT;
//	}
	
	public static int set(String file, String key, String value) {
		Properties props = load(file);
		if(props != null) {
			props.setProperty(key, value);
			try {
				OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
				props.store(os, "Copyright (c) MoCloud");
				os.close();
				return Status.Common.SUCCESS.getInt();
			} catch (FileNotFoundException e) {
				log.severe(e.getMessage());
			} catch (IOException e) {
				log.severe(e.getMessage());
			}
		}
		return Status.Common.ERROR.getInt();
	}
	
}
