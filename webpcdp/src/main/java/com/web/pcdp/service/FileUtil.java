package com.web.pcdp.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
 
public class FileUtil {
	//无用
	public static List<String> fileToStrList(File file) {
		List<String> strList = new ArrayList<String>();
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr,1024 * 1024);
			String str;
			while((str = br.readLine())!=null) {
				strList.add(str);
			}
		} catch (Exception e) {
			
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
		}
		return strList;
	}
	public static boolean saveMultiFileDB(String basePath, MultipartFile[] files,String[] idArray) {
		int len = files.length;
		boolean flag = false;
		if (files == null || files.length == 0|| len!= idArray.length) {
			return flag;
		}
		if (basePath.endsWith("/")) {
			basePath = basePath.substring(0, basePath.length() - 1);
		}
		
		for(int i = 0 ;i < len;i++) {
			MultipartFile file =  files[i];
			String id = idArray[i];
			
			String filePath = basePath + "/" + id + getFileType(file.getOriginalFilename());
			makeDir(filePath);
			File dest = new File(filePath);
			try {
				file.transferTo(dest);
				flag = true;
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
				
			}
		}
		
		return flag;
	}
	public static String getFileType(String fileName) {
		int n = fileName.lastIndexOf('.');
		if(n>-1)
			return fileName.substring(fileName.lastIndexOf('.'));
		else {
			return fileName;
		}
	}
	public static boolean deleteFileByPath(String filePath) {
		File file = new File(filePath);
		boolean flag = false;
		if(file.exists()) {
			flag = file.delete();
		}
		return flag;
	}
	
	//在数据库中插入数据
	private static void makeDir(String filePath) {
		if (filePath.lastIndexOf('/') > 0) {
			String dirPath = filePath.substring(0, filePath.lastIndexOf('/'));
			File dir = new File(dirPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
	}
	//弃用
	public static void saveMultiFile(String basePath,String parentID,String note, MultipartFile[] files) {
		if (files == null || files.length == 0) {
			return;
		}
		if (basePath.endsWith("/")) {
			basePath = basePath.substring(0, basePath.length() - 1);
		}
		for (MultipartFile file : files) {
			String filePath = basePath + "/" + file.getOriginalFilename()+".txt";
			System.out.println("Original="+file.getOriginalFilename());
			System.out.println(filePath);
			System.out.println("---------------------\n");
			makeDir(filePath);
			File dest = new File(filePath);
			try {
				file.transferTo(dest);
				
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}