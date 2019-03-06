package com.jihuiweb.utils;

import javax.servlet.http.HttpServletRequest;

public class NetUtils {
	/**
	 * 判断是不是Ajax请求
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request){
		 String header = request.getHeader("X-Requested-With");
	     boolean isAjax = "XMLHttpRequest".equalsIgnoreCase(header);
	     return isAjax;
	}
	/**
	 * 获取客户端IP
	 * @param request
	 * @return
	 */
	public static String getIP(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_REAL_IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	/**
	 * 判断是不是移动端
	 * @param request
	 * @return
	 */
	public static boolean isMobile(HttpServletRequest request){
		String userAgent = request.getHeader("User-Agent");
		//System.out.println(userAgent);
		return (userAgent.contains("Mi-Pad") || userAgent.contains("iPad") || userAgent.contains("Android") || userAgent.contains("iPhone"));
	}

	public static String getResponseFileName(HttpServletRequest request, String realFileName) {
		String browName = null;
		String clientInfo = request.getHeader("User-agent");
		try{
			if (clientInfo != null && clientInfo.indexOf("MSIE") > 0) {//
				// IE采用URLEncoder方式处理
				if (clientInfo.indexOf("MSIE 6") > 0
						|| clientInfo.indexOf("MSIE 5") > 0) {// IE6，用GBK，此处实现由局限性
					browName = new String(realFileName.getBytes("UTF8"), "ISO-8859-1");
				} else {// ie7+用URLEncoder方式
					browName = java.net.URLEncoder.encode(realFileName, "UTF-8");
				}
			} else {// 其他浏览器
				browName = new String(realFileName.getBytes("UTF8"), "ISO-8859-1");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return browName;
	}
}
