package com.web.pcdp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.pcdp.entity.FileSystem;
import com.web.pcdp.service.FileSystemService;

@Controller
public class FileSystemController {
	@Autowired
	@Qualifier("filesystem")
	private FileSystemService fileSystemService;
	@GetMapping("/delfilesystem")
    public String delFileSystem(String file_id,Model model) {
    	FileSystem delFile = fileSystemService.getByID(file_id);
    	FileSystem parFile = fileSystemService.getByID(delFile.getParentID());
    	fileSystemService.deleteFolder(file_id);
    	return "redirect:filesystem?file_id="+parFile.getFileID();
    }
	
	@GetMapping("/filesystem")
    public String filesystem(String file_id,Model model) {
    	String rootID = "001";
    	if(file_id != null) {
    		rootID = file_id;
    	}
    	FileSystem currFile = fileSystemService.getByID(rootID);
    	List<FileSystem> pathList = new ArrayList<FileSystem>();
    	pathList.add(currFile);
    	while (true) {
    		String flag = pathList.get(0).getParentID();
    		if(flag !=null) {
    			FileSystem no = fileSystemService.getByID(flag);
    			pathList.add(0, no);
    		}
    		else {
				break;
			}
		}
    	pathList.remove(pathList.size()-1);
    	List<FileSystem> list = fileSystemService.getChildren(rootID);
    	model.addAttribute("filelist", list);
    	model.addAttribute("currfile", currFile);
    	model.addAttribute("pathList", pathList);
    	return "filesystem";
    }
	@PostMapping("/createFolder")
    public String createFolder(String parentID ,String fileName,String fileNote,String fileType,Model model) {
    	System.out.println("/createFolder>" + parentID+fileName+fileNote);
    	FileSystem fs = new FileSystem();
    	fs.setName(fileName);
    	fs.setNote(fileNote);
    	fs.setParentID(parentID);
    	fs.setType(fileType);
    	fileSystemService.createForder(fs);
    	return "redirect:filesystem?file_id="+parentID;
    }
	@PostMapping("/setFile")
    public String setFile(String parentID, String fileID, String fileName,String fileNote,Model model) {
    	FileSystem fs = new FileSystem();
    	fs.setFileID(fileID);
    	fs.setName(fileName);
    	fs.setNote(fileNote);
    	fileSystemService.setFile(fs);
    	return "redirect:filesystem?file_id="+parentID;
    }
	
	//通过ID值获得文件信息
    @RequestMapping("/getFileByID/{id}")
    @ResponseBody
    public FileSystem getFileByID(@PathVariable (value="id") String id) {
    	System.out.println(id);
		return fileSystemService.getByID(id);
    }
}
