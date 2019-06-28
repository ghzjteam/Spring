package com.web.pcdp.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.pcdp.config.Preferences;
import com.web.pcdp.domain.FileSystem;
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

	public String[] saveFile(String parentID,String note, String[] folder) {
		List<String> list = new ArrayList<String>();
		for(String s:folder) {
			System.out.println("创建路径："+s);
			String id = makeDir(parentID, s , note);
			System.out.println("得到文件ID："+id+"\n-------------------\n\n");
			list.add(id);
		}
		return (String[])list.toArray(new String[list.size()]);
	}
	public String makeDir(String parentID,String path,String note) {
		System.out.println(parentID+" > 构造 > "+path);
		String fileID = "";
		String [] strA = path.split("/",2);
		String name = strA[0];
		List<FileSystem> fsChild = getChild(parentID, name);
		if(fsChild == null || fsChild.size()==0) { //此节点不存在,构造
			System.out.println("	此节点不存在,构造"+name);
			String type = "";
			if(strA.length==1) {
				type =FileUtil.getFileType(name);
			}
			else {
				type ="folder";
			}
			//String name,String type,String parentID,String note
			fileID = createFolderNoID(name,type,parentID,note);
		}
		if(strA.length==2) { //有下一级
			System.out.println("	有下一级>"+fileID+">"+name);
			String nextPath = strA[1];
			String nextParentID = fileID;//此节点不存在,使构造后的id
			if(fileID == "") { //此节点存在,使用已存在的id
				nextParentID = fsChild.get(0).getFileID();
			}
			fileID = makeDir(nextParentID,nextPath, note);
		}
		System.out.println("");
		return fileID;
	}
	public FileSystem getParent(String id) {
		return fileSystemRepository.getParent(id);
	}
	public FileSystem getByID(String id) {
		return fileSystemRepository.getByID(id);
	}
	public String getFileSysPathByID(String fileID) {
		FileSystem fs = getByID(fileID);
		return getFileSysPathByFS(fs);
	}
	public String getFileSysPathByFS(FileSystem fs) {
		String path = Preferences.get_FILE_SYSTEM_PATH();
		path+=fs.getFileID()+fs.getType();
		return path;
	}
	
	public FileSystem getByPath_IDNames(String path) {//  001/会议视频2/成本控制
		String[] pathS = path.split("/");
		List<FileSystem> list = getChildren(pathS[0]);
		boolean flag = true;
		FileSystem reFS = new FileSystem();
		for(int i = 1;i<pathS.length&&flag;i++) {
			boolean flagR = false;
			for(FileSystem fs:list) {
				if(fs.getName().equals(pathS[i])) {
					reFS = fs;
					list= getChildren(fs.getFileID());
					flagR = true;
					break;
				}
			}
			if(!flagR) {
				return null;
			}
		}
		return reFS;
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
		FileSystem fs = getByID(id);
		if(fs.getType()!="folder") {
			FileUtil.deleteFileByPath(getFileSysPathByFS(fs));
		}
		deleteByID(id);
	}

	public void createFolder(FileSystem fs) {
		fileSystemRepository.insertFile(
				fs.getFileID()
				,fs.getName()
				,fs.getType()
				,fs.getParentID()
				,fs.getNote());
	}
	public String createFolderNoID(String name,String type,String parentID,String note) {
		FileSystem fs = new FileSystem(name, type, parentID, note);
		return  createFolderNoID(fs);
	}
	public String createFolderNoID(FileSystem fs) {
		String id = generaterFileName();
		fs.setFileID(id);
		createFolder(fs);
		return id;
	}
	public void setFile(FileSystem fs) {
		fileSystemRepository.alterFile(fs.getName()
				,fs.getNote()
				,fs.getFileID());
	}
	public List<FileSystem> getChildren(String id) {
		return fileSystemRepository.getChildren(id);
	}
	public List<FileSystem> getChild(String parentID,String childName) {
		return fileSystemRepository.getChild(parentID, childName);
	}
	public List<FileSystem>  getParentList(String id) {
		List<FileSystem> pathList = new ArrayList<>();
		FileSystem fs = getParent(id);
		if(fs!=null) {
			pathList.add(getByID(getParent(id).getFileID()));
	    	while (true) {
	    		String flag = pathList.get(0).getParentID();
	    		if(flag !=null) {
	    			FileSystem no = getByID(flag);
	    			pathList.add(0, no);
	    		}
	    		else {
					break;
				}
			}
		}
    	return pathList;
	}
	/////
	
	public void httpFileOutputStream(File file, HttpServletResponse response) {
		FileInputStream fis = null;
		OutputStream os = null;
		try {
			fis = new FileInputStream(file);
			os = response.getOutputStream();
			int count = 0;
			byte[] buffer = new byte[1024 * 1024];
			while ((count = fis.read(buffer)) != -1)
				os.write(buffer, 0, count);
			os.flush();
		} catch (Exception e) {
			
		} finally {
			if (os != null)
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
		}
	}
	public String fileToString(File file) {
		FileReader fr = null;
		StringBuffer buffer = new StringBuffer();
		try {
			fr = new FileReader(file);
			char [] buf = new char[1024*1024];
			int n=0;
			String str;
			while((n = fr.read(buf))!=0) {
				str = new String(buf, 0, n);
				buffer.append(str);
			}
		} catch (Exception e) {
			
		} finally {
			
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
		}
		return buffer.toString();
	}
	
	public boolean stringToFile(File file,String text) {
		FileWriter fw = null;
		boolean flag = false;
		try {
			fw = new FileWriter(file);
			fw.write(text);
			flag = true;
		} catch (Exception e) {
			
		} finally {
			
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
				}
			}
		}
		return flag;
	}
}
