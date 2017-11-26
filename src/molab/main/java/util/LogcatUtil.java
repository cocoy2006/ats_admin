package molab.main.java.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import molab.main.java.entity.Logcat;

public class LogcatUtil {
	
	private static final Logger log = Logger.getLogger(LogcatUtil.class.getName());
	
	public static List<Logcat> findLogcat(String filename, String regex) {
		File file = new File(filename);
		if(file.exists()) {
			BufferedReader reader = null;
	        try {
	        	List<Logcat> logcatList = new ArrayList<Logcat>();
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            boolean found = false;
	            Logcat logcat = null;
	            StringBuffer sb = null;
	            String tag = null;
	            while ((tempString = reader.readLine()) != null) {
	            	// exception start
	            	if(tempString != null && tempString.indexOf(regex) > -1) { //"ative crash"
	            		found = true;
	            		sb = new StringBuffer();
	            		tag = tempString.substring(0, tempString.indexOf(":"));
	            	}
	            	// exception end
	            	if(found && !tempString.startsWith(tag)) {
	            		logcat = new Logcat(tag, String.valueOf(tag.charAt(0)), sb.toString());
	            		logcatList.add(logcat);
	            		found = false;
	            	}
	            	// exception happening
	            	if(found) {
	            		sb.append(tempString.substring(tag.length() + 1))
	            			.append(System.getProperty("line.separator"));
	            	}
	            }
	            if(sb != null && sb.length() > 0) {
	            	logcat = new Logcat(tag, String.valueOf(tag.charAt(0)), sb.toString());
	        		logcatList.add(logcat);
	            }
	            if(logcatList.size() > 0) {
	            	return logcatList;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
		} else {
			log.info(filename + " does not exist.");
		}
		return null;
    }
	
	public static List<Logcat> findAnr(String filename) {
		File file = new File(filename);
		if(file.exists()) {
			BufferedReader reader = null;
	        try {
	        	List<Logcat> logcatList = new ArrayList<Logcat>();
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            boolean found = false;
	            Logcat logcat = null;
	            StringBuffer sb = null;
	            while ((tempString = reader.readLine()) != null) {
	            	if(tempString != null && tempString.indexOf("\"main\" prio") > -1) {
	            		found = true;
	            		sb = new StringBuffer();
	            	}
	            	
	            	if(found && tempString.equals("")) {
	            		logcat = new Logcat();
	            		logcat.setContent(sb.toString());
	            		logcatList.add(logcat);
	            		found = false;
	            	}
	            	if(found) {
	            		sb.append(tempString).append(System.getProperty("line.separator"));
	            	}
	            }
	            if(sb != null && sb.length() > 0) {
	            	logcat = new Logcat();
	        		logcat.setContent(sb.toString());
	        		logcatList.add(logcat);
	            }
	            if(logcatList.size() > 0) {
	            	return logcatList;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
		} else {
			log.info(filename + " does not exist.");
		}
		return null;
    }
	
}
