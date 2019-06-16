package com.web.pcdp.constant;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Preferences {
	public static String MeetingFile_PATH ="D:\\IdeaProjects\\webpcdp\\src\\main\\resources\\static\\MeetingFile\\";
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
//	public static void main(String[] args) {
//		System.out.println(getDateTime());
//	}
}
