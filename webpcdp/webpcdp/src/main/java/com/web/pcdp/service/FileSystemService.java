package com.web.pcdp.service;

import com.web.pcdp.domain.*;
import com.web.pcdp.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service("filesystem")
public class FileSystemService {
	@Autowired
	private FileSystemRepository fileSystemRepository;

	public FileSystem getParent(String id) {
		return fileSystemRepository.getParent(id);
	}
	public FileSystem getByID(String id) {
		return fileSystemRepository.getByID(id);
	}
	public List<FileSystem> getChildren(String id) {
		return fileSystemRepository.getChildren(id);
	}
	
}
