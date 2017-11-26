package molab.test.java;

import java.io.File;
import java.io.IOException;

import molab.main.java.util.MD5Util;

public class Md5Tester {

	public static void main(String[] args) throws IOException {
		File f = new File("C:\\Download\\qqpim_3.5.0.3580_iphone.deb");
		System.out.println(MD5Util.getFileMD5(f));
		
		f = new File("C:\\Download\\1");
		System.out.println(MD5Util.getFileMD5(f));
		
		System.out.println("Done.");
		System.exit(0);
	}

}
