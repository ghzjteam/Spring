package com.web.pcdp.controller;

import com.web.pcdp.domain.Project;
import com.web.pcdp.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import java.text.*;
import java.util.*;

@Controller
public class ProjectController {

    @Autowired
    @Qualifier("project")
    private ProjectService projectService;

    @Autowired
    @Qualifier("team")
    private TeamService teamService;

    @Autowired
    @Qualifier("user")
    private UserService userService;

    private Date date;

    //查询用户所有项目
    @GetMapping("/projects")
    public String GetUserAllProject(@RequestParam("user_id") int user_id, Model model) {
        List<Project> projects = null;
        projects = projectService.findUserAllProject(user_id);
        for (int i=0; i<projects.size(); i++){
            //System.out.println(projects.get(i).getProject_name());
        }
        if(projects == null){
            // System.out.println("None Project");
        }
        else{
            model.addAttribute("user_id",user_id);
        }

        return "projects";
    }

    //查询团队所有项目
    @GetMapping("/gprojects")
    public String GetTeamAllProject(@RequestParam("team_id") int team_id, Model model) {
        List<Project> projects = null;
        projects = projectService.findTeamAllProject(team_id);
        for (int i=0; i<projects.size(); i++){
            //System.out.println(projects.get(i).getProject_name());
        }
        if(projects == null){
            // System.out.println("None Project");
        }
        else{
            model.addAttribute("team_id",team_id);
        }

        return "projects";
    }

    //添加项目
    @PostMapping("/InsertProject")
    public String InsertProject(@RequestParam("project_id") int project_id,
                                @RequestParam("project_name") String project_name,
                                @RequestParam("team_id") int team_id,
                                @RequestParam("create_date") String create_date,
                                @RequestParam("note") String note){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        format.setTimeZone(gmtTime);
        try {
            date = format.parse(create_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //System.out.println(team_id+"\t"+team_name+"\t"+note+"\t"+date);
        projectService.insertProject(project_id, project_name, team_id, date, note);
        return "redirect:/projectAdmin?project_id="+project_id;
    }

    //删除项目
    @GetMapping("/DeleteProject")
    public String DeleteProject(@RequestParam("user_id") int user_id,@RequestParam("project_id") int project_id) {
        projectService.deleteProject(project_id);
        return "redirect:/projects?user_id="+user_id;
    }

    //修改项目信息
    @PostMapping("/UpdateProject")
    public String UpdateProject(@RequestParam("project_id") int project_id,
                                @RequestParam("project_name") String project_name,
                                @RequestParam("team_id") int team_id,
                                @RequestParam("create_date") Date create_date,
                                @RequestParam("note") String note){
        projectService.updateProject(project_id, project_name, team_id, create_date, note);
        return "redirect:/projectAdmin?project_id="+project_id;
    }

    //上传项目文件
    @PostMapping("/UploadFile")
    public String UploadFile(@RequestParam("file_id") int file_id,
                             @RequestParam("project_id") int project_id,
                             @RequestParam("filename") String filename,
                             @RequestParam("create_date") Date create_date){
        projectService.uploadFile(file_id, project_id, filename, create_date);
        return "redirect:/projectAdmin?project_id="+project_id;
    }
}