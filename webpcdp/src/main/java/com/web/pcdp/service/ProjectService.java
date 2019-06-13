package com.web.pcdp.service;

import com.web.pcdp.domain.*;
import com.web.pcdp.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service("project")
public class ProjectService {

    @Autowired(required = true)
    private ProjectRepository projectRepository;

    //根据用户ID查询用户所有项目信息
    public List<Project> findUserAllProject(int user_id) {
        List<Project> project = null;
        project = projectRepository.findUserAllProject(user_id);
        if (project==null){
            return null;
        }
        else {
            return project;
        }
    }

    //根据团队ID查询团队所有项目信息
    public List<Project> findTeamAllProject(int team_id) {
        List<Project> project = null;
        project = projectRepository.findTeamAllProject(team_id);
        if (project==null){
            return null;
        }
        else {
            return project;
        }
    }

    //添加项目
    public void insertProject(int project_id, String project_name, int team_id, Date create_date, String note) {
        projectRepository.insertProject(project_id, project_name, team_id, create_date, note);
        //System.out.println("成功添加"+project_id);
    }

    //删除项目
    public void deleteProject(int project_id) {
        projectRepository.deleteProject(project_id);
        //System.out.println("成功删除"+project_id);
    }

    //修改项目信息
    public void updateProject(int project_id, String project_name, int team_id, Date create_date, String note) {
        projectRepository.updateProject(project_id, project_name, team_id, create_date, note);
    }

    //上传项目文件
    public void uploadFile(int file_id, int project_id, String filename, Date create_date) {
        projectRepository.uploadFile(file_id, project_id, filename, create_date);
    }
}
