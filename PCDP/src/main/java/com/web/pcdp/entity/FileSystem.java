package com.web.pcdp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class FileSystem {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "file_id")
	private String fileID;
	private String name;
	private String type;
	@Column(name = "parent_id")
	private String parentID;
	private String note;
	
	
	public FileSystem() {
		super();
	}
	public String getFileID() {
		return fileID;
	}
	public void setFileID(String fileID) {
		this.fileID = fileID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getParentID() {
		return parentID;
	}
	public void setParentID(String parentID) {
		this.parentID = parentID;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@Override
	public String toString() {
		return "FileSystem [fileID=" + fileID + ", name=" + name + ", type=" + type + ", parentID=" + parentID
				+ ", note=" + note + "]";
	}
	
}
