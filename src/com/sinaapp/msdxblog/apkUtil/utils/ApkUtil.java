/*
 * @(#)ApkUtil.java		       version: 0.2.1 
 * Date:2012-1-9
 *
 * Copyright (c) 2011 CFuture09, Institute of Software, 
 * Guangdong Ocean University, Zhanjiang, GuangDong, China.
 * All rights reserved.
 */
package com.sinaapp.msdxblog.apkUtil.utils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import molab.main.java.util.Path;

import com.sinaapp.msdxblog.apkUtil.entity.ApkInfo;
import com.sinaapp.msdxblog.apkUtil.entity.ImpliedFeature;

/**
 * apk工具类。封装了获取Apk信息的方法。
 * 
 * @author CFuture.Geek_Soledad(66704238@51uc.com)
 * 
 *         <p>
 *         <b>version description</b><br />
 *         V0.2.1 修改程序名字为从路径中获得。
 *         </p>
 */
public class ApkUtil {
	public static final String VERSION_CODE = "versionCode";
	public static final String VERSION_NAME = "versionName";
	public static final String SDK_VERSION = "sdkVersion";
	public static final String TARGET_SDK_VERSION = "targetSdkVersion";
	public static final String USES_PERMISSION = "uses-permission";
	public static final String APPLICATION_LABEL = "application-label";
	public static final String APPLICATION_ICON = "application-icon";
	public static final String USES_FEATURE = "uses-feature";
	public static final String USES_IMPLIED_FEATURE = "uses-implied-feature";
	public static final String SUPPORTS_SCREENS = "supports-screens";
	public static final String SUPPORTS_ANY_DENSITY = "supports-any-density";
	public static final String DENSITIES = "densities";
	public static final String PACKAGE = "package";
	public static final String APPLICATION = "application:";
	public static final String LAUNCHABLE_ACTIVITY = "launchable-activity";

	private static final String SPLIT_REGEX = "(: )|(=')|(' )|'";
	private static final String FEATURE_SPLIT_REGEX = "(:')|(',')|'";

	/**
	 * 返回一个apk程序的信息。
	 * 
	 * @param apkPath
	 *            apk的路径。
	 * @return apkInfo 一个Apk的信息。
	 */
	public static ApkInfo getApkInfo(String apkPath) throws Exception {
		ProcessBuilder mBuilder = new ProcessBuilder();
		mBuilder.redirectErrorStream(true);
		Process process = mBuilder.command(Path.apk("aapt.exe"), "d", "badging", apkPath).start();
		InputStream is = null;
		is = process.getInputStream();
		BufferedReader br = new BufferedReader(
				new InputStreamReader(is, "utf8"));
		String tmp = br.readLine();
		try {
			if (tmp == null || !tmp.startsWith("package")) {
				throw new IllegalArgumentException("Argument error, because of:\n" + tmp + "...");
			}
			ApkInfo apkInfo = new ApkInfo();
			do {
				setApkInfoProperty(apkInfo, tmp);
			} while ((tmp = br.readLine()) != null);
			return apkInfo;
		} catch (IllegalArgumentException e) {
			throw e;
		} finally {
			process.destroy();
			closeIO(is);
			closeIO(br);
		}
	}

	/**
	 * 设置APK的属性信息。
	 * 
	 * @param apkInfo
	 * @param source
	 */
	private static void setApkInfoProperty(ApkInfo apkInfo, String source) {
		if (source.startsWith(PACKAGE)) {
			splitPackageInfo(apkInfo, source);
		} else if(source.startsWith(LAUNCHABLE_ACTIVITY)){
//			apkInfo.setLaunchableActivity(getPropertyInQuote(source));
			splitLaunchableInfo(apkInfo, source);
		} else if (source.startsWith(SDK_VERSION)) {
			apkInfo.setSdkVersion(getPropertyInQuote(source));
		} else if (source.startsWith(TARGET_SDK_VERSION)) {
			apkInfo.setTargetSdkVersion(getPropertyInQuote(source));
		} else if (source.startsWith(USES_PERMISSION)) {
			apkInfo.addToUsesPermissions(getPropertyInQuote(source));
		} else if (source.startsWith(APPLICATION_LABEL)) {
			apkInfo.setApplicationLable(getPropertyInQuote(source));
		} else if (source.startsWith(APPLICATION_ICON)) {
			apkInfo.addToApplicationIcons(getKeyBeforeColon(source),
					getPropertyInQuote(source));
		} else if (source.startsWith(APPLICATION)) {
//			String[] rs = source.split("( icon=')|'");
//			apkInfo.setApplicationIcon(rs[rs.length - 1]);
			splitApplicationInfo(apkInfo, source);
		} else if (source.startsWith(USES_FEATURE)) {
			apkInfo.addToFeatures(getPropertyInQuote(source));
		} else if (source.startsWith(USES_IMPLIED_FEATURE)) {
			apkInfo.addToImpliedFeatures(getFeature(source));
		} else {
//			System.out.println(source);
		}
	}

	private static ImpliedFeature getFeature(String source) {
		String[] result = source.split(FEATURE_SPLIT_REGEX);
		ImpliedFeature impliedFeature = new ImpliedFeature(result[1], result[2]);
		return impliedFeature;
	}

	/**
	 * 返回出格式为name: 'value'中的value内容。
	 * 
	 * @param source
	 * @return
	 */
	private static String getPropertyInQuote(String source) {
		int index = source.indexOf("'") + 1;
		return source.substring(index, source.indexOf('\'', index));
	}

	/**
	 * 返回冒号前的属性名称
	 * 
	 * @param source
	 * @return
	 */
	private static String getKeyBeforeColon(String source) {
		return source.substring(0, source.indexOf(':'));
	}

	/**
	 * 分离出包名、版本等信息。
	 * 
	 * @param apkInfo
	 * @param packageSource
	 */
	private static void splitPackageInfo(ApkInfo apkInfo, String packageSource) {
		String[] packageInfo = packageSource.split(SPLIT_REGEX);
		apkInfo.setPackageName(packageInfo[2]);
		apkInfo.setVersionCode(packageInfo[4]);
		apkInfo.setVersionName(packageInfo[6]);
	}
	
	/**
	 * 分离出启动activity、中文标签名等信息。
	 * 
	 * @author ykk
	 * @param apkInfo
	 * @param source
	 */
	private static void splitLaunchableInfo(ApkInfo apkInfo, String source) {
		String[] info = source.split(SPLIT_REGEX);
		apkInfo.setLaunchableActivity(info[2]);
		apkInfo.setApplicationLable(info[4]);
	}
	
	/**
	 * 分离出中文标签名、icon等信息。
	 * 
	 * @author ykk
	 * @param apkInfo
	 * @param source
	 */
	private static void splitApplicationInfo(ApkInfo apkInfo, String source) {
		String[] info = source.split(SPLIT_REGEX);
		apkInfo.setApplicationLable(info[2]);
		apkInfo.setApplicationIcon(info[4]);
	}
	
	

	/**
	 * 释放资源。
	 * 
	 * @param c
	 *            将关闭的资源
	 */
	private final static void closeIO(Closeable c) {
		if (c != null) {
			try {
				c.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
