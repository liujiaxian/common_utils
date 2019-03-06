package com.jihuiweb.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

public class SystemKit {
	private static Set<String> localAddress = null;

	//获取随机数
	public static int getRandom(int min, int max) {
		int rann = (int)(Math.random() * (min+1) + (max - min));
		return rann;
	}
	//获取Calsspath
	public static String getClassPath(){
		return  SystemKit.class.getResource("/").toString().substring(6);
	}
	public static URI getResource(String resource){
		try {
			return new URI(SystemKit.class.getResource("/")+resource);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 获取系统临时目录
	 * @return
	 */
	public static String getTempDir(){
		return System.getProperty("java.io.tmpdir") + "/";
	}

}