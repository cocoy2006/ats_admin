package molab.test.java;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class SecurityMain {
	
	public static void main(String[] args) throws NumberFormatException, UnsupportedEncodingException, Base64DecodingException {
//		System.out.println(System.getProperty("user.dir"));
//		System.out.println(System.getProperty("user.home"));
//		System.out.println(System.getProperty("user.name"));
		
		SecurityMain main = new SecurityMain();
		Calendar c = Calendar.getInstance();
		c.set(2017, Calendar.OCTOBER, 31, 0, 0, 0);
		System.out.println(c.getTime());
		main.encrypt(String.valueOf(c.getTimeInMillis()));
		
		main.decrypt("/lpB4F8INDSWu2sX6Jjm/g==");
		System.exit(0);
	}

	public void decrypt(String str) throws NumberFormatException, UnsupportedEncodingException, Base64DecodingException {
		long totalTime = Long.parseLong(new String(new Security().decrypt(Base64.decode(str)), "UTF8"));
		System.out.println(new Date(totalTime));
	}
	
	public void encrypt(String str) throws Base64DecodingException, UnsupportedEncodingException {
		String s = Base64.encode(new Security().encrypt(str.getBytes("UTF8")));
		System.out.println(s);
	}
}
