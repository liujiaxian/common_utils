package com.jihuiweb.utils;

import java.util.UUID;

public class GuidUtils {
    public static String appKey;
    /**
     * @description:随机获取key值
     * @return
     */
    public static String getGuid() {
        UUID uuid = UUID.randomUUID();
        String key = uuid.toString();
        return key;
    }
    /**
     * 这是其中一个url的参数，是GUID的，全球唯一标志符
     * @return
     */
    public static String getAppKey() {
        return getGuid();
    }
    /**
     * 根据md5加密
     * @return
     */
    public static String getAppScrect() {
        String mw = "key" + getAppKey() ;
        String appSign = SecurityKit.string2MD5(mw).toUpperCase();// 得到以后还要用MD5加密。
        return appSign;
    }

    public static void main(String[] args) {
//        GuidUtils gd = new GuidUtils();
//        String app_key=gd.getAppKey();
//        System.out.println("app_key: "+app_key);
//        String app_screct=gd.getAppScrect();
//        System.out.println("app_screct: "+app_screct);
    }
}
