package molab.main.java.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.android.ddmlib.IDevice;
import com.android.uiautomator.UiAutomatorHelper;
import com.android.uiautomator.UiAutomatorModel;
import com.android.uiautomator.UiAutomatorHelper.UiAutomatorException;
import com.android.uiautomator.tree.BasicTreeNode;
import com.android.uiautomator.tree.RootWindowNode;
import com.android.uiautomator.tree.UiNode;

/**
 * 1, mark mSelfMatches and fill mChildrenMatches;
 * 2, find leaf most nodes at point (x, y);
 * 3, add path info {@link mPath} into found nodes and return
 * */
public class UiAutomatorUtil {
	
	private static List<BasicTreeNode> mFoundNodes = null;

	public static List<UiNode> findNodes(IDevice iDevice, int px, int py) {
		UiAutomatorModel model = null;
		try {
			model = UiAutomatorHelper.takeModel(iDevice, false);
			return findNodes(model, px, py);
		} catch (UiAutomatorException e) {}
		return null;
	}
	
	public static List<UiNode> findNodes(UiAutomatorModel model, int px, int py) {
		if(model != null) {
			RootWindowNode root = (RootWindowNode) model.getXmlRootNode();
			markTree(root, px, py);
			findInTree(root);
			return addMpath();
        }
        return null;
	}
	
	private static void markTree(RootWindowNode root, int px, int py) {
		List<BasicTreeNode> nodeList = root.getChildrenList();
		for(BasicTreeNode node : nodeList) {
			markNode(node, px, py);
		}
	}
	
	private static boolean markNode(BasicTreeNode node, int px, int py) {
		if(isFound(node, px, py)) {
			node.mSelfMatches = true;
			if(node.getChildCount() > 0) {
				node.mChildrenMatches = new Boolean[node.getChildCount()];
				for(int i = 0; i < node.getChildCount(); i++) {
					node.mChildrenMatches[i] = markNode(node.getChildren()[i], px, py);
				}
			}
		}
		return node.mSelfMatches;
	}

	private static boolean isFound(BasicTreeNode node, int px, int py) {
		if (node.x <= px && px <= node.x + node.width && node.y <= py && py <= node.y + node.height) {
			return true;
		} else {
			return false;
		}
	}
	
	private static void findInTree(RootWindowNode root) {
		List<BasicTreeNode> nodeList = root.getChildrenList();
		if(nodeList != null && nodeList.size() > 0) {
			mFoundNodes = new ArrayList<BasicTreeNode>();
			for(BasicTreeNode node : nodeList) {
				findNode(node);
			}
		}
	}
	
	private static void findNode(BasicTreeNode node) {
		if(!node.mSelfMatches) {
			return;
		} else {
			if(node.getChildCount() == 0) {
				mFoundNodes.add(node);
			} else {
				for(int i = 0; i < node.getChildCount(); i++) {
					if(node.mChildrenMatches[i]) {
						findNode(node.getChildren()[i]);
					}
				}
			}
		}
	}
	
	private static List<UiNode> addMpath() {
		if(mFoundNodes.size() > 0) {
			List<UiNode> foundNodes = new ArrayList<UiNode>();
			for(BasicTreeNode node : mFoundNodes) {
				UiNode uNode = (UiNode) node;
				
				StringBuffer sb = new StringBuffer();
				sb.append(uNode.getAttribute("index")).append(",");
				BasicTreeNode parent = uNode.getParent();
				while(!(parent instanceof RootWindowNode)) {
					UiNode tempNode = (UiNode) parent;
					sb.append(tempNode.getAttribute("index")).append(",");
					parent = parent.getParent();
				}
				
				String path = sb.toString();
				node.mPath = StringUtils.reverse(path);
				
				foundNodes.add((UiNode) node);
			}
			return foundNodes;
		}
		return null;
	}
	
}
