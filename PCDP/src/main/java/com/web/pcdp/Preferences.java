package com.web.pcdp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Preferences {
	public static String EXTERNAL_PATH = "D:/TheCache/TEMP/PCDP";
	public static String HEAD_IMG_PATH = "/images/head_img/";
	public static String DEFAULT_PHOTO = "head.png";
	public static String getDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(new Date());
	}
	public static String getDateTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}
	public static String toHexString(int num) {
		String hex = Integer.toHexString(num);
		if(hex.length()<2) {
			hex="0"+hex;
		}
		return hex;
	}
	public static String generaterHexFileName() {
		Random ra =new Random();
		String raStr= Integer.toHexString(ra.nextInt(4095)+1);
		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR) % 100;
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DATE);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        //System.out.println(year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second);
        
        String dataStr = toHexString(year);
        dataStr += toHexString(month);
        dataStr += toHexString(day);
        dataStr += toHexString(hour);
        dataStr += toHexString(minute);
        dataStr += toHexString(second);		
		
//		SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
//		String data = df.format(new Date());
//		System.out.println(data);
		return (dataStr+raStr).toUpperCase();
	}
	public static String generaterFileName() {
		Random ra =new Random();
		String raStr= Integer.toHexString(ra.nextInt(4095)+1);
		SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");
		String data = df.format(new Date());
		return (data+raStr).toUpperCase();
	}	
//	public static void main(String[] args) {
//		//System.out.println(Integer.toHexString(19));
//		System.out.println(generaterFileName());
//	}
}
