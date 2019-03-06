package com.jihuiweb.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 描述：日期处理常用类
 *
 * @author tongbiao
 */

public class DateUtils {

    /**
     * 字符串转LocalDateTime时间
     *
     * @param currentTime
     * @return
     */
    public static LocalDateTime stringToLocalDateTime(String currentTime) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(currentTime, df);
        return ldt;
    }

    /**
     * 字符串转LocalDate时间
     *
     * @param currentTime
     * @return
     */
    public static LocalDate stringToLocalDate(String currentTime) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ldt = LocalDate.parse(currentTime, df);
        return ldt;
    }

    /**
     * 字符串转LocalDateTime时间
     *
     * @param currentTime
     * @return
     */
    public static String localDateTimeToString(LocalDateTime currentTime) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String localTime = df.format(currentTime);
        return localTime;
    }

    /**
     * 字符串转LocalDateTime时间
     *
     * @param currentTime
     * @return
     */
    public static String localDateToString(LocalDate currentTime) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String localTime = df.format(currentTime);
        return localTime;
    }

    //由出生日期获得年龄
    public static int getAge(LocalDate birthDay){
        LocalDate now = LocalDate.now();

        //int age = birthDay.until(now).getYears();
        int age = now.getYear() - birthDay.getYear();
        return age;
    }


    private final static int[] dayArr = new int[]{20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22};
    private final static String[] constellationArr = new String[]{"摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"};

    /**
     * Java通过生日计算星座
     *
     * @param month
     * @param day
     * @return
     */
    public static String getConstellation(LocalDate birthDay) {
        int month = birthDay.getMonthValue();
        int day = birthDay.getDayOfMonth();
        return day < dayArr[month - 1] ? constellationArr[month - 1] : constellationArr[month];
    }

    public static void main(String[] args) {
        //getSubTwoTime("2016-04-13","2016-04-15");
        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        //System.out.println(getAge(LocalDate.parse("1998-06-13")));
        //System.out.println(getSubTwoTimeYY("2016-04-14","2016-04-21"));
    }

}
