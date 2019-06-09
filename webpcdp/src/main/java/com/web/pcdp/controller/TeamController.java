package com.web.pcdp.controller;

import com.web.pcdp.domain.*;
import com.web.pcdp.service.TeamService;
import com.web.pcdp.service.UserService;
import com.web.pcdp.service.UserTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Controller
public class TeamController {

    @Autowired
    @Qualifier("team")
    private TeamService teamService;

    @Autowired
    @Qualifier("user_team")
    private UserTeamService userTeamService;

    @Autowired
    @Qualifier("user")
    private UserService userService;

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

        List<user_team> user_teams = null;
        List<teamposition> teampositions = new ArrayList<>();

        model.addAttribute("team_id",team_id);
        model.addAttribute("user_id",user_id);


        int myposition;

        user_teams = userTeamService.findmemberUser(team_id);

        for (int i = 0;i<user_teams.size();i++){


            User user = userService.findUser(user_teams.get(i).getUser_id());
            String name = user.getUserName();
            int user_id1 = user.getUserId();
            int team_id1 = user_teams.get(i).getTeam_id();
            int position = user_teams.get(i).getPosition();

            if (user_id == user_id1){
                myposition = position;
                model.addAttribute("myposition",myposition);
            }
            teamposition teamposition = new teamposition();
            teamposition.setUser_id(user_id1);
            teamposition.setTeam_id(team_id1);
            teamposition.setUser_name(name);
            if (position == 0){
                teamposition.setUser_position("队长");
            }else if (position == 1){
                teamposition.setUser_position("副队长");
            }else if (position == 2){
                teamposition.setUser_position("队员");
            }

            //System.out.println(teamposition.getTeam_id()+"\t"+teamposition.getUser_name()+"\t"+teamposition.getUser_position()+"\n");
            teampositions.add(teamposition);

//            users.add(user);
//            System.out.println(users.get(i).toString());
        }


        model.addAttribute("teampositions",teampositions);
        String flag = "队长";
        model.addAttribute("flag",flag);

        return "groupInformation";


    }

    @GetMapping("/deletemember")
    public String deletemember(@Param("user_id") int user_id,
                               @Param("team_id") int team_id,
                               Model model){

        userTeamService.deletemember(user_id,team_id);

        return "redirect:/groupInformation?user_id="+user_id+"&team_id=" +team_id;

    }

    @GetMapping("/Insertemember")
    public void
}