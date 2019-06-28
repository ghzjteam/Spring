/**
 * !/user/bin/new java
 * --*-- coding: utf-8 --*--
 *
 * @Time : 2019/6/13 下午 07:20
 * @Author : WenXuan
 * @File : File.java
 * @SoftWare: IntelliJ IDEA
 **/

package com.web.pcdp.domain;

import javax.persistence.*;
import java.sql.*;

public class File {

    @Id
    @Column(name = "file_id")
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer file_id;
    private Integer project_id;
    private String filename;
    private Date create_date;

    public Integer getFile_id() {
        return file_id;
    }

    public void setFile_id(Integer file_id) {
        this.file_id = file_id;
    }

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }
}
