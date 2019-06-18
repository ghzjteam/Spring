package com.web.pcdp.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.pcdp.Preferences;
import com.web.pcdp.entity.FileSystem;
import com.web.pcdp.repository.FileSystemRepository;

@Service("filesystem")
public class FileSystemService {
	@Autowired
	private FileSystemRepository fileSystemRepository;
	
	public String findMaxID() {
		return fileSystemRepository.findMaxID();
	}
	public String generaterFileName() {
		String id = Preferences.generaterFileName();
		while (getByID(id)!=null) {
			System.out.println(id+"已存在，自动构建另一个ID");
			id = Preferences.generaterFileName();
		}
		return id;
	}
	public FileSystem getParent(String id) {
		return fileSystemRepository.getParent(id);
	}
	public FileSystem getByID(String id) {
		return fileSystemRepository.getByID(id);
	}
	public void deleteByID(String id) {
		fileSystemRepository.deleteFile(id);
	}
	public void deleteFolder(String id) {
		List<FileSystem> list = getChildren(id);
		System.out.println("子节点数"+list.size());
		if(list.size()>0) {
			for (FileSystem fs : list) {
				System.out.println("存在子节点"+fs.getName());
				deleteFolder(fs.getFileID());
			}
		}
		deleteByID(id);
	}
	
	public List<FileSystem> getChildren(String id) {
		return fileSystemRepository.getChildren(id);
	}
	public void createForder(FileSystem fs) {
		fs.setFileID(generaterFileName());
		fileSystemRepository.insertFile(fs.getFileID()
				,fs.getName()
				,fs.getType()
				,fs.getParentID()
				,fs.getNote());
	}
	public void setFile(FileSystem fs) {
		fileSystemRepository.alterFile(fs.getName()
				,fs.getNote()
				,fs.getFileID());
	}
}
