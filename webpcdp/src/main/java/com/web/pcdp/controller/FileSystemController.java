package com.web.pcdp.controller;

import com.web.pcdp.domain.*;
import com.web.pcdp.domain.*;
import com.web.pcdp.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class FileSystemController {
	@Autowired
	@Qualifier("filesystem")
	private FileSystemService fileSystemService;
	
	@GetMapping("/filesystem")
    public String filesystem(String file_id,Model model) {
    	String rootID = "001";
    	if(file_id != null) {
    		System.out.println("进入目录"+file_id);
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
}
