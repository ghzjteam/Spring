package com.web.pcdp.controller;

import com.web.pcdp.domain.Team;
import com.web.pcdp.domain.User;
import com.web.pcdp.domain.Teamposition;
import com.web.pcdp.domain.User_team;
import com.web.pcdp.service.TeamService;
import com.web.pcdp.service.UserService;
import com.web.pcdp.service.UserTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
       /* for (int i=0;i<teams.size();i++){
            System.out.println(teams.get(i).getTeam_name());
        }*/

        int maxteam_id = teamService.maxteam_id();
        if(teams == null){
           System.out.println("Team is null");
        }
        else{
            model.addAttribute("user_id",user_id);
            model.addAttribute("teams",teams);
            model.addAttribute("newteam_id",maxteam_id+1);
            //System.out.println("name"+teams.get(0).getTeam_name());
            //System.out.println("name"+meeting.get(1).getMeeting_name());
        }
        return "myteams";
    }



   /* //
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
    }*/

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


        if(teamService.findMyTeam(team_id) != null){
            return "redirect:/ERROR3";
        }else {
            //System.out.println(team_id+"\t"+team_name+"\t"+note+"\t"+date);
            teamService.insertTeam(team_id,team_name,note,date);
            teamService.insertUser_Team(create_user_id,team_id,0);
            return "redirect:/team?user_id=1";
        }


    }



    @GetMapping("/groupInformation")
    public String GetGroupInformation(@RequestParam("user_id") int user_id,
                                      @RequestParam("team_id") int team_id,
                                      Model model){

        List<User_team> user_teams = null;
        List<Teamposition> teampositions = new ArrayList<>();

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
            Teamposition teamposition = new Teamposition();
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

            //System.out.println(Teamposition.getTeam_id()+"\t"+Teamposition.getUser_name()+"\t"+Teamposition.getUser_position()+"\n");
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
                               @Param("user_id1") int user_id1,
                               Model model){

        userTeamService.deletemember(user_id,team_id);

        return "redirect:/groupInformation?user_id="+user_id1+"&team_id=" +team_id;

    }

    @RequestMapping("/updateMember")
    public String updateMember(@RequestParam("user_idalter") int user_id,
                              @RequestParam("team_idalter") int team_id,
                              @RequestParam("team_positionalter") int position,
                              @RequestParam("user_idalter1") int user_id1){

        //System.out.println(position+"\t"+user_id+"\t"+team_id+"\t");
        userTeamService.updateMember(position,user_id,team_id);

        return "redirect:/groupInformation?user_id="+user_id1+"&team_id=" +team_id;
    }
    @GetMapping("/ERROR1")
    public String ERROR1(){

        return "ERROR1";
    }

    @GetMapping("/ERROR2")
    public String ERROR2(){

        return "ERROR2";
    }

    @GetMapping("/ERROR3")
    public String ERROR3(){

        return "ERROR3";
    }

    @PostMapping("/Insertemember")
    public String Insertemember(@RequestParam("user_id1") int user_id1,
                                @RequestParam("team_id") int team_id,
                                @RequestParam("team_position") int position,
                                @RequestParam("user_id") int user_id){

        User user = userService.findUser(user_id1);
        List<User_team> users = new ArrayList<>();
        users = userTeamService.findmemberUser(team_id);
        if (user == null){
            return "redirect:/ERROR1";
        }

        for (int i=0;i<users.size();i++){
            if (users.get(i).getUser_id() == user_id1){
                return "redirect:/ERROR2";
            }
        }
        System.out.println(user_id+"\t"+user_id1+"\t"+team_id+"\t"+position);
        userTeamService.Insertemember(user_id1,team_id,position);
        return "redirect:/groupInformation?user_id="+user_id+"&team_id=" +team_id;
    }

    @PostMapping("/updateteam")
    public String updateteam(@RequestParam("user_id2") int user_id,
                           @RequestParam("team_id1") int team_id,
                           @RequestParam("team_name") String team_name,
                           @RequestParam("note") String note){


        teamService.updateteam(team_name,note,team_id);

        return "redirect:/team?user_id="+user_id;
    }


    //查看个人信息
    @GetMapping("/groupmemberinf")
    public String settings(@Param("user_id") int user_id,Model model) {
        User oUser = userService.findUser(user_id);
        if(oUser!=null) {
            model.addAttribute("curruser", oUser);
        }
        return "groupmemberinf";
    }


}
