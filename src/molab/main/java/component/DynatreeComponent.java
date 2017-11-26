package molab.main.java.component;

import java.util.List;

public class DynatreeComponent {

	private String title;
	private String key;
	private boolean isFolder;
	private String tooltip;
	private boolean hideCheckbox;
	private List<DynatreeComponent> children;

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the isFolder
	 */
	public boolean isFolder() {
		return isFolder;
	}

	/**
	 * @param isFolder
	 *            the isFolder to set
	 */
	public void setFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}

	/**
	 * @return the tooltip
	 */
	public String getTooltip() {
		return tooltip;
	}

	/**
	 * @param tooltip
	 *            the tooltip to set
	 */
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	/**
	 * @return the hideCheckbox
	 */
	public boolean isHideCheckbox() {
		return hideCheckbox;
	}

	/**
	 * @param hideCheckbox
	 *            the hideCheckbox to set
	 */
	public void setHideCheckbox(boolean hideCheckbox) {
		this.hideCheckbox = hideCheckbox;
	}

	/**
	 * @return the children
	 */
	public List<DynatreeComponent> getChildren() {
		return children;
	}

	/**
	 * @param children
	 *            the children to set
	 */
	public void setChildren(List<DynatreeComponent> children) {
		this.children = children;
	}

}
