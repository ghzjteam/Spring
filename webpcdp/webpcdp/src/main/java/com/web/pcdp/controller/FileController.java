/**
 * !/user/bin/new java
 * --*-- coding: utf-8 --*--
 *
 * @Time : 2019/6/18 下午 04:21
 * @Author : WenXuan
 * @File : FileController.java
 * @SoftWare: IntelliJ IDEA
 **/

package com.web.pcdp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.autoconfigure.*;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipOutputStream;

import static com.web.pcdp.PcdpApplication.*;
import static com.web.pcdp.utils.RarUtils.unRarFile;
import static com.web.pcdp.utils.TargzUtils.unTargzFile;
import static com.web.pcdp.utils.ZipUtils.unZipFiles;
import static com.web.pcdp.utils.ZipUtils.zipFiles;

@RestController
@RequestMapping(value = "fileManager")
public class FileController {

    //标注第一次访问过滤其他项目文件

    /**
     * 展示文件列表
     */
    @RequestMapping("/list")
    public Object list(@RequestBody JSONObject json, HttpServletRequest request) throws ServletException {

        try {
            // 需要显示的目录路径
            String path = json.getString("path");

            HttpSession session = request.getSession();
            String project_id = String.valueOf(session.getAttribute("project_id"));
            String position = String.valueOf(session.getAttribute("position"));

            // 返回的结果集
            List<JSONObject> fileItems = new ArrayList<>();

            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(ROOT, path))) {

                String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat dt = new SimpleDateFormat(DATE_FORMAT);
                for (Path pathObj : directoryStream) {
                    // 获取文件基本属性
                    BasicFileAttributes attrs = Files.readAttributes(pathObj, BasicFileAttributes.class);

                    // 封装返回JSON数据
                    JSONObject fileItem = new JSONObject();

                    String str = pathObj.getFileName().toString();

                    if(path.equals("/") && !project_id.equals(str.substring(str.length()-1))) {
                        continue;
                    }


                    fileItem.put("name", str);

                    // 禁用 文件权限加载
                    // fileItem.put("rights", org.shaofan.utils.FileUtils.getPermissions(pathObj));

                    fileItem.put("date", dt.format(new Date(attrs.lastModifiedTime().toMillis())));
                    fileItem.put("size", attrs.size());
                    fileItem.put("type", attrs.isDirectory() ? "dir" : "file");
                    fileItems.add(fileItem);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", fileItems);
            return jsonObject;
        } catch (Exception e) {
            System.err.println("获取文件列表失败！");
            e.printStackTrace();
            return error(e.getMessage());
        }
    }

    /**
     * 文件上传
     */
    @RequestMapping("upload")
    public Object upload(@RequestParam("destination") String destination, HttpServletRequest request) {

        try {
            // Servlet3.0方式上传文件
            Collection<Part> parts = request.getParts();

            for (Part part : parts) {
                if (part.getContentType() != null) { // 忽略路径字段,只处理文件类型
                    String path = ROOT + destination;

                    File f = new File(path,
                            com.web.pcdp.utils.FileUtils.getFileName(part.getHeader("content-disposition")));
                    if (!com.web.pcdp.utils.FileUtils.write(part.getInputStream(), f)) {
                        throw new Exception("文件上传失败");
                    }
                }
            }
            return success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 文件下载/预览
     */
    @RequestMapping("preview")
    public void preview(HttpServletResponse response, String path) throws IOException {

        File file = new File(ROOT, path);
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource Not Found");
            return;
        }

        /*
         * 获取mimeType
         */
        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        response.setHeader("Content-disposition",
                String.format("attachment; filename=\"%s\"", URLEncoder.encode(file.getName(), "UTF-8")));
        response.setContentLength((int) file.length());

        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        }
    }

    /**
     * 创建目录
     */
    @RequestMapping("createFolder")
    public Object createFolder(@RequestBody JSONObject json) {
        try {
            String newPath = json.getString("newPath");
            File newDir = new File(ROOT + newPath);
            if (!newDir.mkdir()) {
                throw new Exception("不能创建目录: " + newPath);
            }
            return success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 修改文件或目录权限
     */
    @RequestMapping("changePermissions")
    public Object changePermissions(@RequestBody JSONObject json) {
        try {

            String perms = json.getString("perms"); // 权限
            boolean recursive = json.getBoolean("recursive"); // 子目录是否生效

            JSONArray items = json.getJSONArray("items");
            for (int i = 0; i < items.size(); i++) {
                String path = items.getString(i);
                File f = new File(ROOT, path);
                com.web.pcdp.utils.FileUtils.setPermissions(f, perms, recursive); // 设置权限
            }
            return success();
        } catch (Exception e) {
            System.err.println("修改权限失败！");
            e.printStackTrace();
            return error(e.getMessage());
        }
    }

    /**
     * 复制文件或目录
     */
    @RequestMapping("copy")
    public Object copy(@RequestBody JSONObject json, HttpServletRequest request) {
        try {
            String newpath = json.getString("newPath");
            JSONArray items = json.getJSONArray("items");

            for (int i = 0; i < items.size(); i++) {
                String path = items.getString(i);

                File srcFile = new File(ROOT, path);
                File destFile = new File(ROOT + newpath, srcFile.getName());

                FileCopyUtils.copy(srcFile, destFile);
            }
            return success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 移动文件或目录
     */
    @RequestMapping("move")
    public Object move(@RequestBody JSONObject json) {
        try {
            String newpath = json.getString("newPath");
            JSONArray items = json.getJSONArray("items");

            for (int i = 0; i < items.size(); i++) {
                String path = items.getString(i);

                File srcFile = new File(ROOT, path);
                File destFile = new File(ROOT + newpath, srcFile.getName());

                if (srcFile.isFile()) {
                    FileUtils.moveFile(srcFile, destFile);
                } else {
                    FileUtils.moveDirectory(srcFile, destFile);
                }
            }
            return success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 删除文件或目录
     */
    @RequestMapping("remove")
    public Object remove(@RequestBody JSONObject json) {
        try {
            JSONArray items = json.getJSONArray("items");
            for (int i = 0; i < items.size(); i++) {
                String path = items.getString(i);
                File srcFile = new File(ROOT, path);
                if (!FileUtils.deleteQuietly(srcFile)) {
                    throw new Exception("删除失败: " + srcFile.getAbsolutePath());
                }
            }
            return success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 重命名文件或目录
     */
    @RequestMapping("rename")
    public Object rename(@RequestBody JSONObject json) {
        try {
            String path = json.getString("item");
            String newPath = json.getString("newItemPath");

            File srcFile = new File(ROOT, path);
            File destFile = new File(ROOT, newPath);
            if (srcFile.isFile()) {
                FileUtils.moveFile(srcFile, destFile);
            } else {
                FileUtils.moveDirectory(srcFile, destFile);
            }
            return success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 查看文件内容,针对html、txt等可编辑文件
     */
    @RequestMapping("getContent")
    public Object getContent(@RequestBody JSONObject json) {

        try {
            String path = json.getString("item");
            File srcFile = new File(ROOT, path);

            String content = FileUtils.readFileToString(srcFile);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", content);
            return jsonObject;
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 修改文件内容,针对html、txt等可编辑文件
     */
    @RequestMapping("edit")
    public Object edit(@RequestBody JSONObject json) {
        try {
            String path = json.getString("item");
            String content = json.getString("content");

            File srcFile = new File(ROOT, path);
            FileUtils.writeStringToFile(srcFile, content);

            return success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 文件压缩
     */
    @RequestMapping("compress")
    public Object compress(@RequestBody JSONObject json) {
        try {
            String destination = json.getString("destination");
            String compressedFilename = json.getString("compressedFilename");
            JSONArray items = json.getJSONArray("items");
            List<File> files = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                File f = new File(ROOT, items.getString(i));
                files.add(f);
            }

            File zip = new File(ROOT + destination, compressedFilename);

            try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zip))) {
                zipFiles(out, "", files.toArray(new File[files.size()]));
            }
            return success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 文件解压
     */
    @RequestMapping("extract")
    public Object extract(@RequestBody JSONObject json) {
        try {
            String destination = json.getString("destination");
            String zipName = json.getString("item");
            // String folderName = json.getString("folderName");
            File file = new File(ROOT, zipName);

            String extension = com.web.pcdp.utils.FileUtils.getExtension(zipName);
            switch (extension) {
                case ".zip":
                    unZipFiles(file, ROOT + destination);
                    break;
                case ".gz":
                    unTargzFile(file, ROOT + destination);
                    break;
                case ".rar":
                    unRarFile(file, ROOT + destination);
            }
            return success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    private JSONObject error(String msg) {

        // { "result": { "success": false, "error": "msg" } }
        JSONObject result = new JSONObject();
        result.put("success", false);
        result.put("error", msg);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result);
        return jsonObject;

    }

    private JSONObject success() {
        // { "result": { "success": true, "error": null } }
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("error", null);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result);
        return jsonObject;
    }
}
