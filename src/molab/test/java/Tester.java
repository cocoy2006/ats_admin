package molab.test.java;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Pattern p = Pattern.compile("\\d+.\\d+");
//		Matcher m = p.matcher("qty = '100.000'");
//		while(m.find()) { 
//			System.out.println(m.group());
//		}
		
//		String str = "asdf  asdf Native crash";
//		System.out.println(str.indexOf("ative crash"));
		
//		Pattern p=Pattern.compile("userId=\\d+"); 
//		Matcher m=p.matcher("    userId=10124 gids=[3003, 1028, 1015, 3002, 3001]"); 
//		while(m.find()) { 
//		     System.out.println(m.group()); 
//		}
		
		String str = "        TOTAL    27253    19112     2916        0    18728    18514      193";
		String[] list = str.trim().split("\\ +");
//		String[] list = str.split(System.getProperty("line.separator"));
		for(String i : list) {
			System.out.println(i);
		}
//		str = str.substring(7);
//		str = str.substring(0, str.indexOf(" "))args;
//		System.out.println(str);
		
		System.out.println("Done.");
		System.exit(0);
	}

}
