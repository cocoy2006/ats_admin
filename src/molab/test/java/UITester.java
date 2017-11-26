package molab.test.java;

import java.io.IOException;

import molab.main.java.util.init.Adb;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;
import com.android.uiautomator.tree.BasicTreeNode;
import com.android.uiautomator.tree.RootWindowNode;
import com.android.uiautomator.tree.UiNode;

public class UITester {

	public static void main(String[] args) throws InterruptedException, TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {

		Adb adb = Adb.getInstance();
		Thread.sleep(500);
		final IDevice device = adb.getBackend().getBridge().getDevices()[0];
		
		String resp = device.shell("cat /proc/net/xt_qtaguid/stats | grep 10124");
		System.out.println(resp);
		
//		UiAutomatorModel model = null;
//		try {
//			model = UiAutomatorHelper.takeModel(device, false);
//		} catch (UiAutomatorException e) {
//			return;
//		}
		
//		List<BasicTreeNode> nodeList = findNodes(model, 600, 1300);
//		if(nodeList != null && nodeList.size() > 0) {
//			for(BasicTreeNode node : nodeList) {
//				UiNode uiNode = (UiNode) node;
//				System.out.println(uiNode.getAttribute("resource-id"));
//				System.out.println(uiNode.getAttribute("class"));
//				System.out.println(uiNode.getAttribute("text"));
//			}
//		}
		
//		UiNode node = (UiNode) model.updateSelectionForCoordinates(300, 600);
//		System.out.println(node.getAttribute("resource-id"));
//		System.out.println(node.getAttribute("class"));
//		System.out.println(node.getAttribute("text"));
		
//		List<BasicTreeNode> nodeList = model.getAllNodes();
//		if(nodeList != null && nodeList.size() > 0) {
//			UiNode node = (UiNode) nodeList.get(0);
//			node = (UiNode) node.getChildren()[0].getChildren()[0].getChildren()[0].getChildren()[1].getChildren()[0].getChildren()[0].getChildren()[2];
//			
//			System.out.println(node.getAttribute("resource-id"));
//		}
		
//		List<BasicTreeNode> nodeList = model.searchNode("9");
//		if(nodeList != null && nodeList.size() > 0) {
//			for(BasicTreeNode node : nodeList) {
//				UiNode uiNode = (UiNode) node;
//				System.out.println(uiNode.getAttribute("resource-id"));
//				System.out.println(uiNode.getAttribute("class"));
//				System.out.println(uiNode.getAttribute("text"));
//			}
//		}
		
//		RootWindowNode root = (RootWindowNode) model.getXmlRootNode();
//		UiNode node = findNode(root, ",0,0,0,0,0,0,0,1,1");

		System.out.println("Done.");
		System.exit(0);
	}
	
	private static UiNode findNode(RootWindowNode root, String mPath) {
		if(mPath != null) {
			String[] indexList = mPath.split(",");
			BasicTreeNode node = root.getChildren()[0];
			for(int i = 2; i < indexList.length; i++) {
				int index = Integer.parseInt(indexList[i]);
				node = node.getChildren()[index];
			}
			if(node != null) {
				return (UiNode) node;
			}
		}
		return null;
	}

}
