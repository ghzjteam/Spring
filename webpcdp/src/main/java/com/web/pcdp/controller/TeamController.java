package com.web.pcdp.controller;

import com.web.pcdp.domain.Meeting;
import com.web.pcdp.domain.Team;
import com.web.pcdp.domain.User;
import com.web.pcdp.service.TeamService;
import com.web.pcdp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Controller
public class TeamController {

    @Autowired
    @Qualifier("team")
    private TeamService teamService;
    private Date date;


    @GetMapping("/team")
    public String GetAllTeam(@RequestParam("user_id") int user_id,Model model){
        List<Team> teams = null;
        teams = teamService.findUserAllTeam(user_id);
        for (int i=0;i<teams.size();i++){
            //System.out.println(teams.get(i).getTeam_name());
        }
        if(teams == null){
           // System.out.println("Team is null");
        }
        else{
            model.addAttribute("user_id",user_id);
            model.addAttribute("teams",teams);
            //System.out.println("name"+teams.get(0).getTeam_name());
            //System.out.println("name"+meeting.get(1).getMeeting_name());
        }
        return "myteams";
    }




    @GetMapping("/group")
    public String GetMyTeam(@RequestParam("team_id") int team_id,
                            @RequestParam("user_id") int user_id,
                            Model model){
        Team team = null;
        //User user = null;
        team = teamService.findMyTeam(team_id);
        //ser = UserService.findUser(user_id);
        //System.out.println(team.getTeam_name());
        if(team == null){
            System.out.println("Team is null");
        }
        else{
            model.addAttribute("team",team);
            model.addAttribute("user_id",user_id);

        }
        return "groups";
    }

    @PostMapping("/InsertTeam")
    public String InsertMeeting(@RequestParam("team_id") int team_id,
                                @RequestParam("team_name") String team_name,
                                @RequestParam("note") String note,
                                @RequestParam("create_date") String create_date,
                                @RequestParam("create_user_id")int create_user_id){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        format.setTimeZone(gmtTime);
        try {
            date = format.parse(create_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //System.out.println(team_id+"\t"+team_name+"\t"+note+"\t"+date);
        teamService.insertTeam(team_id,team_name,note,date);
        teamService.insertUser_Team(create_user_id,team_id,2);
        return "redirect:/team?user_id=1";
    }



    @GetMapping("/groupInformation")
    public String GetGroupInformation(@RequestParam("user_id") int user_id,
                                      @RequestParam("team_id") int team_id,
                                      Model model){


        return "groupInformation";


    }
}
