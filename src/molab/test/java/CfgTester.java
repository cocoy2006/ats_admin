package molab.test.java;

import java.util.HashMap;
import java.util.Map;

import molab.main.java.util.Molab;

public class CfgTester {

	public static void main(String[] args) {
		Molab molab = Molab.getInstance();
		
		Map<String, String> romInfoMap = new HashMap<String, String>();
		String romInfo = molab.getProperty(Molab.CFG_ROM_INFO);
		System.out.println(romInfo);
		String[] romList = romInfo.split("\\|");
		for(String rom : romList) {
			String[] brandList = rom.split(",");
			System.out.println(brandList[0] + " " + brandList[1]);
		}
		
		System.out.println("Done.");
		System.exit(0);
	}

}
