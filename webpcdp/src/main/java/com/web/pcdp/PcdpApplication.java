package com.web.pcdp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.*;

import org.springframework.scheduling.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;

import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
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
import java.util.Properties;
import java.util.zip.ZipOutputStream;

import static com.web.pcdp.PcdpApplication.*;
import static com.web.pcdp.utils.RarUtils.unRarFile;
import static com.web.pcdp.utils.TargzUtils.unTargzFile;
import static com.web.pcdp.utils.ZipUtils.unZipFiles;
import static com.web.pcdp.utils.ZipUtils.zipFiles;
import static org.aspectj.bridge.MessageUtil.*;

@RestController
@SpringBootApplication
@ServletComponentScan
public class PcdpApplication {

    public static String ROOT = "c:/";

    public static void main(String[] args) {
        init();
        SpringApplication.run(PcdpApplication.class, args);
    }

    /**
     * 新增通过配置文件读取根目录
     */
    public static void init() {
        try {

            Properties props = new Properties();
            props.load(new FileInputStream(ResourceUtils.getFile("classpath:application.properties")));
            ROOT = props.getProperty("ROOT", ROOT);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
