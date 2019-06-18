package com.web.pcdp.config;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Preferences {
	public static String MeetingFile_PATH ="D:\\课程学习\\大三下学期\\Web高级编程\\大作业\\Gitea和GitHub提交\\webpcdp\\src\\main" +
			"\\resources\\static\\MeetingFile\\";
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
