package molab.main.java.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import molab.main.java.dao.ApplicationDao;
import molab.main.java.entity.Application;
import molab.main.java.util.MD5Util;
import molab.main.java.util.Molab;
import molab.main.java.util.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dd.plist.NSDictionary;
import com.dd.plist.NSString;
import com.dd.plist.PropertyListParser;
import com.sinaapp.msdxblog.apkUtil.entity.ApkInfo;
import com.sinaapp.msdxblog.apkUtil.utils.ApkUtil;
import com.sinaapp.msdxblog.apkUtil.utils.IconUtil;

@Service
public class ApplicationService {
	
	private static final Logger log = Logger.getLogger(ApplicationService.class.getName());

	@Autowired
	private ApplicationDao dao;
	
	public Application findById(int id) {
		return dao.findById(id);
	}
	
	public Application parse(MultipartFile file) throws IllegalStateException, IOException {
		String md5 = MD5Util.getFileMD5((FileInputStream) file.getInputStream());
		String aliasName = md5 + ".apk";
		File apk = move(file, aliasName);
		Application app = check(md5);
		if(app == null || !Molab.isApkExist(aliasName)) {
			app = new Application();
			app.setName(file.getOriginalFilename());
			app.setSize(file.getSize());
			app.setAliasname(aliasName);
			app.setMd5(md5);
			try {
				// apktool is replaced by aapt.exe
				ApkInfo apkInfo = ApkUtil.getApkInfo(apk.getAbsolutePath());
				if(apkInfo != null) {
					app.setVersion(apkInfo.getVersionName());
					app.setOs(apkInfo.getSdkVersion());
					app.setPackagename(apkInfo.getPackageName());
					app.setStartactivity(apkInfo.getLaunchableActivity());
					app.setLabel(apkInfo.getApplicationLable());
					String icon = null;
					TreeMap<String, String> map = (TreeMap<String, String>) apkInfo.getApplicationIcons();
					if(map.size() > 0) {
						icon = map.lastEntry().getValue();
					} else {
						icon = apkInfo.getApplicationIcon();
					}
					// extract icon to upload/apk directory
					if(icon != null) {
						IconUtil.extractFileFromApk(apk.getAbsolutePath(), icon, Path.apk(md5 + ".png"));
					}
				}
			} catch(Exception e) {
				log.severe(e.getMessage());
			}
			
			// save and return Application instance if and only if packageName and startActivity exist.
			if(app.getPackagename() != null && app.getStartactivity() != null) {
				int id = (Integer) dao.save(app);
				app.setId(id);
				log.info("Application with id " + id + " saved success");
			} else {
				return null;
			}
		} else {
			apk.deleteOnExit(); // apk file already exists. delete the later one.
			log.info("Application with id " + app.getId() + " found.");
		}
		return app;
	}

	private File move(MultipartFile file, String name) throws IllegalStateException, IOException {
		String path = Path.apk(name);
		File apk = new File(path);
		if(!apk.exists()) {
			file.transferTo(apk);
		}
		return apk;
	}
	
	public Application iparse(MultipartFile file) throws IOException {
		String md5 = MD5Util.getFileMD5((FileInputStream) file.getInputStream());
		String aliasName = md5 + ".ipa";
		File ipa = imove(file, aliasName);
		Application app = check(md5);
		if(app == null || !Molab.isIpaExist(aliasName)) {
			app = new Application();
			app.setName(file.getOriginalFilename());
			app.setSize(file.getSize());
			app.setAliasname(aliasName);
			app.setMd5(md5);
			try {
				InputStream is = new FileInputStream(ipa);
				ZipInputStream zipIns = new ZipInputStream(is);
				ZipEntry ze;
				InputStream infoIs = null;
				NSDictionary rootDict = null;
				// find and parse 'Info.plist' first
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
							rootDict = (NSDictionary) PropertyListParser.parse(infoIs);
							break;
						}
					}
				}
				NSString parameters = (NSString) rootDict.get("CFBundleIdentifier");
				app.setPackagename(parameters.toString());
				// 应用版本名
				parameters = (NSString) rootDict.get("CFBundleVersion");
				app.setVersion(parameters.toString());
				// 应用版本号
				parameters = (NSString) rootDict.get("MinimumOSVersion");
				app.setOs(parameters.toString());
				// 应用中文名
//				parameters = (NSString) rootDict.get("CFBundleDisplayName");
//				app.setLabel(parameters.toString());
				
				infoIs.close();
				is.close();
				zipIns.close();
			} catch(Exception e) {
				log.severe(e.getMessage());
			}
			// save and return Application instance if and only if packageName exist.
			if(app.getPackagename() != null) {
				int id = (Integer) dao.save(app);
				app.setId(id);
				log.info("Application with id " + id + " saved success");
			} else {
				return null;
			}
		} else {
			ipa.deleteOnExit(); // ipa file already exists. delete the later one.
			log.info("Application with id " + app.getId() + " found.");
		}
		return app;
	}
	
	private File imove(MultipartFile file, String name) throws IllegalStateException, IOException {
		String path = Path.ipa(name);
		File apk = new File(path);
		if(!apk.exists()) {
			file.transferTo(apk);
		}
		return apk;
	}

	private Application check(String md5) {
		List<Application> list = dao.findByMd5(md5);
		if(list != null && list.size() > 0) {
			return list.get(list.size() - 1);
		}
		return null;
	}
		
}
