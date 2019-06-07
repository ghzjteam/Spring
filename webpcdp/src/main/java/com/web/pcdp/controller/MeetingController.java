package com.web.pcdp.controller;

import com.web.pcdp.domain.Team;
import com.web.pcdp.repository.MeetingRepository;
import com.web.pcdp.domain.Meeting;
import com.web.pcdp.service.MeetingService;
import com.web.pcdp.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MeetingController {
    @Autowired//授权
    @Qualifier("meeting")
    private MeetingService meetingService;

    @Autowired
    @Qualifier("team")
    private TeamService teamService;

    /*
    *jason格式返回测试
    @RequestMapping(value = "/findAllMeeting",method = RequestMethod.GET)
    public List<Meeting> findAllMeeting(){
        List<Meeting> MeetingList = meetingRepository.findAll();
        for(int i=0;i<MeetingList.size();i++) {
            System.out.println(MeetingList.get(i));
        }
        return MeetingList;
        }
    */

   /**
    * 根据id查询会议
    */
    @GetMapping("/findMeetingById")
    @ResponseBody
        public String findMeetingById(@RequestParam("meeting_id") int meeting_id){
        System.out.println("controller：接到传递的参数是 meeting_id = " + meeting_id);

        Meeting meeting = null;
        meeting = meetingService.findMeetingByMeeting_id(meeting_id);
        if(meeting == null){
            return "查找的会议不存在";
        }
        else {
            return meeting.toString();
        }
    }

    //删除会议 用的重定向 拼接地址
    @GetMapping("/DeleteMeeting")
    public String DeleteMeeting(@RequestParam("user_id") int user_id,@RequestParam("meeting_id") int meeting_id){
        meetingService.deleteMeeting(meeting_id);
        return "redirect:/GroupMeeting?user_id="+user_id;
    }

    @PostMapping("/InsertMeeting")
    public String InsertMeeting(@RequestParam("team_id") int team_id,
                                @RequestParam("meeting_name") String meeting_name,
                                @RequestParam("note") String note,
                                @RequestParam("type") String type,
                                @RequestParam("file") String file,
                                @RequestParam("start_date") String start_date,
                                @RequestParam("place") String place){
        System.out.println("From form:"+team_id+"|"+meeting_name+"|"+note+"|"+type+"|"+file+"|"+start_date+"|"+place);
        meetingService.insertMeeting(team_id,meeting_name,note,type,file,start_date,place);
        return "redirect:/GroupMeeting?user_id=1";
    }
    @GetMapping("/GroupMeeting")
    public String GMeeting(@RequestParam("user_id") int user_id,Model model){
        List<Meeting> meeting=null;
        meeting = meetingService.findUserAllMeeting(user_id);

        List<Integer> teamid = null;
        teamid = meetingService.findUserTeam(user_id);
        model.addAttribute("teamids",teamid);
        //System.out.println("team->"+team.get(0));

        List<Team> team =null;
        team = teamService.findUserAllTeam(user_id);
        model.addAttribute("teams",team);


        if(meeting == null){
            System.out.println("meeting is null");
        }
        else{
            model.addAttribute("meetings",meeting);
            //System.out.println("name"+meeting.get(0).getMeeting_name());
            //System.out.println("name"+meeting.get(1).getMeeting_name());
        }
        return "meetings";
    }
}
