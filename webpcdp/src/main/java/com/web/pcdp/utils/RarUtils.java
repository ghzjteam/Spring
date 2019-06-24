/*
 * Copyright (c) 2014 www.diligrp.com All rights reserved.
 * 本软件源代码版权归地利集团所有,未经许可不得任意复制与传播.
 */
package com.web.pcdp.utils;

import com.github.junrar.*;
import com.github.junrar.exception.*;
import com.github.junrar.rarfile.*;

import java.io.*;

public class RarUtils {

    public static void unRarFile(File srcRar, String dstDirectoryPath) throws IOException, RarException {

        Archive archive = new Archive(srcRar);
        FileHeader fh = archive.nextFileHeader();
        while (fh != null) {
            String path = fh.getFileNameString().replaceAll("\\\\", "/");
            File dirFile = new File(dstDirectoryPath + File.separator
                    + path);
            if (fh.isDirectory()) { // 文件夹
                dirFile.mkdirs();
            } else { // 文件
                try {// 之所以这么写try，是因为万一这里面有了异常，不影响继续解压.
                    if (!dirFile.exists()) {
                        if (!dirFile.getParentFile().exists()) {// 相对路径可能多级，可能需要创建父目录.
                            dirFile.getParentFile().mkdirs();
                        }
                        dirFile.createNewFile();
                    }
                    FileOutputStream os = new FileOutputStream(dirFile);
                    archive.extractFile(fh, os);
                    os.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            fh = archive.nextFileHeader();
        }
        archive.close();

    }
}
