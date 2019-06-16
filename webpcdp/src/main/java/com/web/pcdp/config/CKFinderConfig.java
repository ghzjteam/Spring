/**
 * !/user/bin/new java
 * --*-- coding: utf-8 --*--
 *
 * @Time : 2019/6/16 上午 01:53
 * @Author : WenXuan
 * @File : CKFinderConfig.java
 * @SoftWare: IntelliJ IDEA
 **/

package com.web.pcdp.config;

import org.springframework.core.io.*;

public class CKFinderConfig {
    DefaultResourceLoader loader = new DefaultResourceLoader();
    Resource resource = loader.getResource("com.web.pcdp.ckfinder.xml");
}
