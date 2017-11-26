package molab.test.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.SyncException;
import com.android.ddmlib.TimeoutException;

public class LabelTester {

	public static void main(String[] args) throws InterruptedException, IOException, SyncException, AdbCommandRejectedException, TimeoutException, ShellCommandUnresponsiveException {
		
//		Adb adb = Adb.getInstance();
//		Thread.sleep(500);
//		IDevice device = adb.getBackend().getBridge().getDevices()[0];
//		
//		String str = device.shell("getprop ro.build.version.sdka");
//		if(str != null && !str.trim().equals(""))
//			System.out.println(str.trim());
//		else 
//			System.out.println("ERROR");
		String base = "C:\\Development\\workspace_ats\\.metadata\\.me_tcat7\\webapps\\admin\\upload\\apk\\";
		String cmd = String.format("%s dump badging %s", base + "aapt.exe", base + "0d9404a816d39b812f124358aa29f412.apk");
		Process p = Runtime.getRuntime().exec(cmd);
		InputStream fis=p.getInputStream();
		InputStreamReader isr=new InputStreamReader(fis);
		BufferedReader br=new BufferedReader(isr);
		String line=null;
		while((line=br.readLine())!=null)    
        {    
            System.out.println(line);    
        }
		
//		long time1 = System.currentTimeMillis();
//		String path1 = "C:\\Development\\cfca\\temp\\";
//		for(int i = 0; i < 20; i++) {
//			UiAutomatorModel model = null;
//			try {
//				model = UiAutomatorHelper.takeModel(device, true);
//				RootWindowNode root = (RootWindowNode) model.getXmlRootNode();
//				if(root != null) {
//					UiNode node = (UiNode) root.getChildren()[0];
//					if(node != null && packageName.equalsIgnoreCase(node.getAttribute("package"))) {
//						Adb.screencap(device, path1 + i + "." + System.currentTimeMillis() + ".png");
//					}
//				}
//				
//			} catch (UiAutomatorException e) {
//				System.err.println("error.");
//			}
//		}
//		System.out.println(System.currentTimeMillis() - time1);
		
//		long time2 = System.currentTimeMillis();
//		String path2 = "C:\\Development\\temp\\2\\";
//		for(int i = 0; i < 20; i++) {
//			UiAutomatorModel model = null;
//			try {
//				model = UiAutomatorHelper.takeModel(device, true);
//				RootWindowNode root = (RootWindowNode) model.getXmlRootNode();
//				UiNode node = (UiNode) root.getChildren()[0];
//				if(packageName.equalsIgnoreCase(node.getAttribute("package"))) {
//					RawImage rImage = device.getScreenshot();
//					MonkeyImage mImage = new AdbMonkeyImage(rImage);
//					BufferedImage bImage = mImage.createBufferedImage();
//					ImageUtil.writeToFile(bImage, path2 + i + "." + System.currentTimeMillis() + ".png");
//				}
//			} catch (UiAutomatorException e) {
//				System.err.println("error.");
//			}
//		}
//		System.out.println(System.currentTimeMillis() - time2);
		
		System.out.println("Done.");
		System.exit(0);
	}

}
