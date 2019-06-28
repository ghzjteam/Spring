package com.web.pcdp.utils;

import org.apache.commons.lang3.*;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.*;

public class FileUtils {

	public static String getExtension(String fileName) {
		if (StringUtils.INDEX_NOT_FOUND == StringUtils.indexOf(fileName, "."))
			return StringUtils.EMPTY;
		String ext = StringUtils.substring(fileName, StringUtils.lastIndexOf(fileName, "."));
		return StringUtils.trimToEmpty(ext);
	}

	public static String getFileName(String header) {
		String[] tempArr1 = header.split(";");
		String[] tempArr2 = tempArr1[2].split("=");
		// 获取文件名，兼容各种浏览器的写法
		return tempArr2[1].substring(tempArr2[1].lastIndexOf("\\") + 1).replaceAll("\"", "");

	}

	/**
	 * 获取文件权限
	 * 
	 * <pre>
	 * 当前仅支持posix系统，需要修改为windows格式
	 * </pre>
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String getPermissions(Path path) throws IOException {
		PosixFileAttributeView fileAttributeView = Files.getFileAttributeView(path, PosixFileAttributeView.class);
		PosixFileAttributes readAttributes = fileAttributeView.readAttributes();
		Set<PosixFilePermission> permissions = readAttributes.permissions();
		return PosixFilePermissions.toString(permissions);
	}

	/**
	 * 修改文件权限
	 * 
	 * @param file
	 * @param permsCode
	 * @param recursive
	 * @return
	 * @throws IOException
	 */
	public static String setPermissions(File file, String permsCode, boolean recursive) throws IOException {
		PosixFileAttributeView fileAttributeView = Files.getFileAttributeView(file.toPath(),
				PosixFileAttributeView.class);
		fileAttributeView.setPermissions(PosixFilePermissions.fromString(permsCode));
		if (file.isDirectory() && recursive && file.listFiles() != null) {
			for (File f : file.listFiles()) {
				setPermissions(f, permsCode, true);
			}
		}
		return permsCode;
	}

	public static boolean write(InputStream inputStream, File f) {
		boolean ret = false;

		try (OutputStream outputStream = new FileOutputStream(f)) {

			int read;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			ret = true;

		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}

	public static void mkFolder(String fileName) {
		File f = new File(fileName);
		if (!f.exists()) {
			f.mkdir();
		}
	}

	public static File mkFile(String fileName) {
		File f = new File(fileName);
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f;
	}

	public static void fileProber(File dirFile) {

		File parentFile = dirFile.getParentFile();
		if (!parentFile.exists()) {

			// 递归寻找上级目录
			fileProber(parentFile);

			parentFile.mkdir();
		}

	}
}
