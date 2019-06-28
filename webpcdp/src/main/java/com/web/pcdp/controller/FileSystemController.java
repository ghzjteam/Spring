package com.web.pcdp.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.web.pcdp.service.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.web.pcdp.config.Preferences;
import com.web.pcdp.domain.FileSystem;
import com.web.pcdp.domain.User;
import com.web.pcdp.service.FileSystemService;

@Controller
public class FileSystemController {
	@Autowired
	@Qualifier("filesystem")
	private FileSystemService fileSystemService;

	@GetMapping("/delfilesystem")
	public String delFileSystem(String fileID, String parentID) {
		fileSystemService.deleteFolder(fileID);
		return "redirect:filesystem?file_id=" + parentID;
	}

	@GetMapping("/filesystem")
	public String filesystem(String file_id, Model model,HttpSession session) {
		String rootID = "001";
		if (file_id != null) {
			rootID = file_id;
		}
		FileSystem currFile = fileSystemService.getByID(rootID);

		List<FileSystem> pathList = fileSystemService.getParentList(rootID);

		List<FileSystem> list = fileSystemService.getChildren(rootID);
		model.addAttribute("filelist", list);
		model.addAttribute("currfile", currFile);
		model.addAttribute("pathList", pathList);
		User users =(User) session.getAttribute("currLoginUser");
		model.addAttribute("curruser", users);
		return "filesystem";
	}

	@PostMapping("/createFolder")
	public String createFolder(String parentID, String fileName, String fileNote, String fileType, Model model) {
		System.out.println("/createFolder>" + parentID + fileName + fileNote);
		FileSystem fs = new FileSystem();
		fs.setName(fileName);
		fs.setNote(fileNote);
		fs.setParentID(parentID);
		fs.setType(fileType);
		fileSystemService.createFolderNoID(fs);
		return "redirect:filesystem?file_id=" + parentID;
	}

	@PostMapping("/setFile")
	public String setFile(String parentID, String fileID, String fileName, String fileNote, Model model) {
		FileSystem fs = new FileSystem();
		fs.setFileID(fileID);
		fs.setName(fileName);
		fs.setNote(fileNote);
		fileSystemService.setFile(fs);
		return "redirect:filesystem?file_id=" + parentID;
	}

	// 通过ID值获得文件信息
	@RequestMapping("/getFileByID/{id}")
	@ResponseBody
	public FileSystem getFileByID(@PathVariable(value = "id") String id) {
		System.out.println(id);
		return fileSystemService.getByID(id);
	}

	@RequestMapping("/isSameName")
	@ResponseBody
	public boolean isSameName(String parentID, String fileID,String name) {
		System.out.println(parentID + " > " + name);
		List<FileSystem> fss = fileSystemService.getChild(parentID, name);
		boolean flag = false;
		
		if(fss.size() > 1) {
			flag = true;
		}
		return flag;
	}

	@PostMapping(value = "/uploadSFolder")
	public String uploadSFolder(MultipartFile[] folder, String parentID, String note) {
		// System.out.println(folder[0].getOriginalFilename());
		uploadFolder(folder, parentID, note);
		return "redirect:filesystem?file_id=" + parentID;
	}

	@RequestMapping(value = "/uploadFolder", method = RequestMethod.POST)
	public @ResponseBody String[] uploadFolder(MultipartFile[] folder, String parentID, String note) {
		System.out.println(parentID);
		System.out.println(note);
		System.out.println(folder.length);
		String[] nameArray = {"未选中文件"};

		if (folder != null && folder.length > 0) {
			nameArray = getFileOriginalNameArray(folder);
			String[] idArray = fileSystemService.saveFile(parentID, note, nameArray);//同步到数据库
			FileUtil.saveMultiFileDB(Preferences.get_FILE_SYSTEM_PATH(), folder, idArray);//同步到服务器磁盘

			// return "上传文件数量："+folder.length;
		}
		return nameArray;
	}

	// 无用
	public String[] getFileDBNameArray(String parentID, String note, MultipartFile[] folder) {
		List<String> list = new ArrayList<String>();
		for (MultipartFile mf : folder) {
			String id = fileSystemService.makeDir(parentID, mf.getOriginalFilename(), note);
			System.out.println(id);
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	public String[] getFileOriginalNameArray(MultipartFile[] folder) {
		List<String> list = new ArrayList<String>();
		for (MultipartFile mf : folder) {
			list.add(mf.getOriginalFilename());
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	@RequestMapping("/fileDownload")
	public String fileDownload(HttpServletRequest request, HttpServletResponse response, String fileID) {
		System.out.println("Download:" + fileID);
		String filePath = fileSystemService.getFileSysPathByID(fileID);
		String fileName = fileSystemService.getByID(fileID).getName();
		File file = new File(filePath);
		System.out.println("DownloadPath:" + filePath);
		if (file.exists()) {// 如果文件名存在，则进行下载
			// 配置文件下载
			response.setHeader("content-type", "application/octet-stream");
			response.setContentType("application/octet-stream");
			response.setCharacterEncoding("UTF-8");
			// 下载文件能正常显示中文
			try {
				response.setHeader("Content-Disposition",
						"attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				// e1.printStackTrace();
				return e1.getMessage();
			}
			fileSystemService.httpFileOutputStream(file, response);
		} else {
			return fileName + ">不存在！";
		}
		return fileName + ">下载成功！";
	}

	@GetMapping("/codeMergely")
	public String codeMergely(String fileIDs[], Model model) {
		
		String path1 = fileSystemService.getFileSysPathByID(fileIDs[0]);
		String path2 = fileSystemService.getFileSysPathByID(fileIDs[1]);
		// String[] paths = {p1,p2};
		System.out.println(path1);
		//System.out.println(p1 + "\n" + p2);
		model.addAttribute("fileName1", fileSystemService.getByID(fileIDs[0]).getName());
		model.addAttribute("fileName2", fileSystemService.getByID(fileIDs[1]).getName());
		
		model.addAttribute("path1", path1);
		model.addAttribute("path2", path2);
		
		model.addAttribute("fileID1", fileIDs[0]);
		model.addAttribute("fileID2", fileIDs[1]);
		return "mergely";
	}
	
	@RequestMapping(value = "/getFileString")
	public @ResponseBody String getFileString(String fileID) {
		String path = fileSystemService.getFileSysPathByID(fileID);
		File file = new File(path) ;
		return fileSystemService.fileToString(file);
	}
	@GetMapping("/preview")
	public void preview(String fileID, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		//response.setContentType("image/jpeg");
		String fullFileName = fileSystemService.getFileSysPathByID(fileID);
		//fullFileName = "D:/TheCache/TEMP/PCDP/file/190620004609D2B.txt";
		File file= new File(fullFileName);
		if(file.exists()) {
			fileSystemService.httpFileOutputStream(file, response);
		}
	}
	@RequestMapping(value = "/saveToServer")
	public @ResponseBody String saveToServer(String fileID,String text) {
		System.out.println(fileID);
		System.out.println(text);
		String fullFileName = fileSystemService.getFileSysPathByID(fileID);
		File file= new File(fullFileName);
		String msg = "保存失败";
		if(file.exists()) {
			if(fileSystemService.stringToFile(file, text)) {
				msg="保存成功";
			}
		}
		return msg;
	}
	@GetMapping("/test")
	public String test() {
		
		return "test";
	}
}
