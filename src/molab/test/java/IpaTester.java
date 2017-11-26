package molab.test.java;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.dd.plist.NSDictionary;
import com.dd.plist.NSString;
import com.dd.plist.PropertyListParser;

public class IpaTester {

	public static void main(String[] args) {
		String base = "C:\\Development\\ipa\\";
		String ipaURL = base + "com.womai.iphone.ipa";
//		String iconURL = base + "icon.png";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			File file = new File(ipaURL);
			InputStream is = new FileInputStream(file);
//			InputStream is2 = new FileInputStream(file);
			ZipInputStream zipIns = new ZipInputStream(is);
//			ZipInputStream zipIns2 = new ZipInputStream(is2);
			ZipEntry ze;
//			ZipEntry ze2;
			InputStream infoIs = null;
			NSDictionary rootDict = null;
//			String icon = null;
			while ((ze = zipIns.getNextEntry()) != null) {
				if (!ze.isDirectory()) {
					String name = ze.getName();
					if (null != name
							&& name.toLowerCase().contains("info.plist")) {
						ByteArrayOutputStream _copy = new ByteArrayOutputStream();
						int chunk = 0;
						byte[] data = new byte[1024];
						while (-1 != (chunk = zipIns.read(data))) {
							_copy.write(data, 0, chunk);
						}
						infoIs = new ByteArrayInputStream(_copy.toByteArray());
						rootDict = (NSDictionary) PropertyListParser
								.parse(infoIs);

						// 我们可以根据info.plist结构获取任意我们需要的东西
						// 比如下面我获取图标名称，图标的目录结构请下面图片
						// 获取图标名称
//						NSDictionary iconDict = (NSDictionary) rootDict
//								.get("CFBundleIcons");
//
//						while (null != iconDict) {
//							if (iconDict.containsKey("CFBundlePrimaryIcon")) {
//								NSDictionary CFBundlePrimaryIcon = (NSDictionary) iconDict
//										.get("CFBundlePrimaryIcon");
//								if (CFBundlePrimaryIcon
//										.containsKey("CFBundleIconFiles")) {
//									NSArray CFBundleIconFiles = (NSArray) CFBundlePrimaryIcon
//											.get("CFBundleIconFiles");
//									icon = CFBundleIconFiles.getArray()[0]
//											.toString();
//									if (icon.contains(".png")) {
//										icon = icon.replace(".png", "");
//									}
//									System.out.println("获取icon名称:" + icon);
//									break;
//								}
//							}
//						}
						break;
					}
				}
			}

			// 根据图标名称下载图标文件到指定位置
//			while ((ze2 = zipIns2.getNextEntry()) != null) {
//				if (!ze2.isDirectory()) {
//					String name = ze2.getName();
//					System.out.println(name);
//					if (name.contains(icon.trim())) {
//						System.out.println(11111);
//						FileOutputStream fos = new FileOutputStream(new File(
//								iconURL));
//						int chunk = 0;
//						byte[] data = new byte[1024];
//						while (-1 != (chunk = zipIns2.read(data))) {
//							fos.write(data, 0, chunk);
//						}
//						fos.close();
//						break;
//					}
//				}
//			}

			// //////////////////////////////////////////////////////////////
			// 如果想要查看有哪些key ，可以把下面注释放开
//			for (String keyName : rootDict.allKeys()) {
//				System.out.println(keyName + ":"
//						+ rootDict.get(keyName).toString());
//			}

			// 应用包名
			NSString parameters = (NSString) rootDict.get("CFBundleIdentifier");
			map.put("package", parameters.toString());
			// 应用版本名
			parameters = (NSString) rootDict.get("CFBundleVersion");
			map.put("version", parameters.toString());
			// 应用版本号
			parameters = (NSString) rootDict.get("MinimumOSVersion");
			map.put("os", parameters.toString());
			// 应用中文名
			parameters = (NSString) rootDict.get("CFBundleDisplayName");
			map.put("label", parameters.toString());
			
			for(Object value : map.values()) {
				System.out.println(value.toString());
			}

			infoIs.close();
			is.close();
			zipIns.close();
//			zipIns2.close();

		} catch (Exception e) {
			map.put("code", "fail");
			map.put("error", "读取ipa文件失败");
		}
	}

}
