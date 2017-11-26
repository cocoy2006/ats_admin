package molab.test.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import molab.main.java.entity.Logcat;
import molab.main.java.util.LogcatUtil;
import molab.main.java.util.Status;

public class LogcatTester {
	
	public static void main(String[] args) {
		String file = "C:\\Development\\cfca\\1145.logcat.log";
		List<Logcat> sbList = LogcatUtil.findLogcat(file, "Exception:");
//		List<Logcat> sbList = LogcatUtil.findAnr(file);
		if(sbList != null && sbList.size() > 0) {
        	int i = 1;
        	for(Logcat s : sbList) {
        		s.setType(Status.LogcatType.EXCEPTION.getInt());
        		System.out.println(i++);
        		System.out.println("Tag: " + s.getTag());
        		System.out.println("Level: " + s.getLevel());
        		System.out.println("Type: " + s.getType());
        		System.out.println("Content: " + s.getContent());
        	}
        }
		
		System.out.println("Done.");
		System.exit(0);
	}
	
	public static void readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
        	List<StringBuffer> sbList = new ArrayList<StringBuffer>();
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            boolean found = false;
            StringBuffer sb = null;
            while ((tempString = reader.readLine()) != null) {
            	if(tempString != null && tempString.indexOf("\"main\" prio") > -1) {
            		found = true;
            		sb = new StringBuffer();
            	}
            	if(found) {
            		sb.append(tempString).append(System.getProperty("line.separator"));
            	}
            	if(found && tempString.equals("")) {
            		sbList.add(sb);
            		found = false;
            	}
            }
            if(sb.length() > 0) {
            	sbList.add(sb);
            }
            if(sbList.size() > 0) {
            	int i = 1;
            	for(StringBuffer s : sbList) {
            		System.out.println(i++);
            		System.out.println(s.toString());
            	}
            }
            reader.close();
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
    }
}
