package molab.main.java.util;

import java.io.File;
import java.util.logging.Logger;

public class Path {
	
	private static final Logger log = Logger.getLogger(Path.class.getName());
	
	public static String atsgz() {
		try {
			return getWebInf() + File.separator + "atsgz";
		} catch(NullPointerException e) {
			log.severe("Important files miss.");
			System.exit(0);
		}
		return null;
	}
	
	public static String xls() {
		return getWebInf() + "/cfca.xls";
	}
	
	public static String cfg() {
		return getWebInf() + "/cfg.properties";
	}
	
	private static String getWebInf() {
		return new File(Path.class.getResource("/").getFile()).getParent();
	}
	
	public static String temp(String name) {
		return getWebRoot() + "/temp/" + name;
	}
	
	public static String apk(String name) {
		return getWebRoot() + "/upload/apk/" + name;
	}
	
	public static String ipa(String name) {
		return getWebRoot() + "/upload/ipa/" + name;
	}
	
	public static String crSs(String name) {
		return getWebRoot() + "/upload/crSs/" + name;
	}
	
	public static String csSs(String name) {
		return getWebRoot() + "/upload/csSs/" + name;
	}

	public static String ctLog(String name) {
		return getWebRoot() + "/upload/ctLog/" + name;
	}
	
	public static String ctSs(String name) {
		return getWebRoot() + "/upload/ctSs/" + name;
	}
	
	public static String ictSs(String name) {
		return getWebRoot() + "/upload/ictSs/" + name;
	}
	
	public static String device(String name) {
		return getWebRoot() + "/upload/device/" + name;
	}
	
	public static String uploadTempFolder() {
		return getWebRoot() + "/upload/temp";
	}
	
	public static String font(String name) {
		return getWebRoot() + "/resources/font/" + name;
	}
	
	private static String getWebRoot() {
		return new File(Path.class.getResource("/").getFile()).getParentFile().getParent();
	}

}
