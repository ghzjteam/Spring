package com.web.pcdp.controller;

import com.web.pcdp.domain.*;
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

    private Date date;

    //根据id查询项目
    @GetMapping("/findProjectById")
    @ResponseBody
    public String findProjectById(@RequestParam("project_id") int project_id){
        System.out.println("controller：接到传递的参数是 project_id = " + project_id);

        Project project = null;
        project = projectService.findProjectByProject_id(project_id);
        if(project == null){
            return "查找的项目不存在";
        }
        else {
            return project.toString();
        }
    }

    //查询用户所有项目
    @GetMapping("/project")
    public String GetUserAllProject(@RequestParam("user_id") int user_id, Model model) {
        List<Project> project = null;
        project = projectService.findUserAllProject(user_id);

        List<Integer> teamid = null;
        teamid = projectService.findUserTeam(user_id);
        model.addAttribute("teamids",teamid);
        //System.out.println("team->"+team.get(0));

        List<Team> team =null;
        team = teamService.findUserAllTeam(user_id);
        model.addAttribute("teams",team);

        if(project == null){
            System.out.println("None Project");
        }
        else{
            model.addAttribute("projects", project);
        }

        return "projects";
    }

    //查询团队所有项目
    @GetMapping("/gproject")
    public String GetTeamAllProject(@RequestParam("team_id") int team_id,
                                    @RequestParam("user_id") int user_id,
                                    Model model) {

        model.addAttribute("user_id", user_id);
        model.addAttribute("team_id", team_id);

        List<Project> gproject = null;
        gproject = projectService.findTeamAllProject(team_id);

        Team team = teamService.findMyTeam((team_id));
        model.addAttribute("team",team);

        if(gproject == null){
            System.out.println("None Project");
        }
        else{
            model.addAttribute("gprojects", gproject);
        }

        int position = projectService.findUserPosition(user_id, team_id);
        model.addAttribute("userposition", position);

        return "gprojects";
    }

    //添加项目
    @PostMapping("/InsertProject")
    public String InsertProject(@RequestParam("project_id") int project_id,
                                @RequestParam("project_name") String project_name,
                                @RequestParam("team_id") int team_id,
                                @RequestParam("create_date") String create_date,
                                @RequestParam("note") String note){

        System.out.println(project_id+"\t"+project_name+"\t"+team_id+"\t"+create_date+"\t"+note);
        projectService.insertProject(project_id, project_name, team_id, date, note);
        return "redirect:/projectAdmin?project_id="+project_id+"&user_id=1";
    }

    //删除项目
    @GetMapping("/DeleteProject")
    public String DeleteProject(@RequestParam("user_id") int user_id,@RequestParam("project_id") int project_id) {
        projectService.deleteProject(project_id);
        System.out.println(user_id+project_id);
        return "redirect:/project?user_id=1";
    }

    //修改项目信息
    @PostMapping("/UpdateProject")
    public String UpdateProject(@RequestParam("project_id") int project_id,
                                @RequestParam("project_name") String project_name,
                                @RequestParam("note") String note){

        System.out.println(project_id+project_name);

        projectService.updateProject(project_name, note, project_id);
        return "redirect:/projectAdmin?project_id="+project_id+"&user_id=1";
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

    //进入某项目信息管理界面
    @GetMapping("/projectAdmin")
    public String projectAdmin(@RequestParam("project_id") int project_id,
                               @RequestParam("user_id") int user_id,
                               Model model) {

        Project project = projectService.findProjectByProject_id((project_id));

        model.addAttribute("project_id", project_id);
        model.addAttribute("project_name", project.getProject_name());
        model.addAttribute("team_id", project.getTeam_id());
        model.addAttribute("create_date", project.getCreate_date());
        model.addAttribute("note", project.getNote());
        model.addAttribute("user_id", user_id);

        int position = projectService.findUserPosition(user_id, project.getTeam_id());
        model.addAttribute("userposition", position);

        return "projectAdmin";
    }

    //进入某项目管理界面
    @GetMapping("/projectFile")
    public String projectFile(@RequestParam("project_id") int project_id,
                               @RequestParam("user_id") int user_id,
                               Model model) {

        model.addAttribute("project_id", project_id);
        model.addAttribute("user_id", user_id);

        return "projectFile";
    }
}