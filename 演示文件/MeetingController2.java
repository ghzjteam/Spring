package com.web.pcdp.controller;

import com.web.pcdp.domain.Meeting;
import com.web.pcdp.domain.Team;
import com.web.pcdp.domain.User_team;
import com.web.pcdp.service.MeetingService;
import com.web.pcdp.service.TeamService;
import com.web.pcdp.service.UserTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.web.pcdp.config.Preferences.MeetingFile_PATH;

/**
 * 会议controller类
 **/
@Controller
public class MeetingController {
    @Autowired//授权
    @Qualifier("meetingTEST")
    private MeetingTESTService meetingTESTService;

    @Autowired//授权
    @Qualifier("user_team")
    private UserTeamService userTeamTESTService;

    @Autowired
    @Qualifier("team")
    private TeamService teamService;

    /*
    @Autowired//授权
    @Qualifier("meeting")
    private MeetingService meetingService;

    @Autowired//授权
    @Qualifier("user_team")
    private UserTeamService userTeamService;

    @Autowired
    @Qualifier("team")
    private TeamService teamService;
    *
    */


    /**
     * 根据id查询会议
     *
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
     */

    //删除会议 用的重定向 拼接地址
    @GetMapping("/DeleteMeeting")
    public String deleteMeeting(@RequestParam("user_id") int user_id,@RequestParam("meeting_id") int meeting_id){
        meetingService.deleteMeeting(meeting_id);
        return "redirect:/GroupMeeting?user_id=" + user_id;
    }


    @PostMapping("/InsertMeeting")
    public String insertMeeting(@RequestParam("user_id") int user_id,
                                @RequestParam("team_id") int team_id,
                                @RequestParam("meeting_name") String meeting_name,
                                @RequestParam("note") String note,
                                @RequestParam("type") String type,
                                //@RequestParam("file") String file,
                                @RequestParam("start_date") String start_date,
                                @RequestParam("place") String place){
        //System.out.println("From form:"+team_id+"|"+meeting_name+"|"+note+"|"+type+"|"+start_date+"|"+place);
        meetingService.insertMeeting(team_id,meeting_name,note,type,null,start_date,place);
        return "redirect:/GroupMeeting?user_id=" + user_id;
    }


    @GetMapping("/GroupMeeting")
    public String groupMeeting(@RequestParam("user_id") int user_id,Model model){

        List<User_team> user_teamList = null;
        user_teamList = userTeamService.findPosition(user_id);
        System.out.println(user_teamList.get(0).getTeam_id());
        System.out.println(user_teamList.get(1).getTeam_id());
        model.addAttribute("user_teamLists",user_teamList);

        List<Meeting> meeting=null;
        meeting = meetingService.findUserAllMeeting(user_id);

        List<Integer> teamid = null;
        teamid = userTeamService.findUserTeam(user_id);
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

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("user_id") int user_id,
                         @RequestParam("file") MultipartFile file,
                         @RequestParam("meeting_id") int meeting_id) {

        meetingService.updateMeetingFile(file.getOriginalFilename(),meeting_id);
        if (file.isEmpty()) {
            System.out.println("上传失败，请选择文件");
        }

        String fileName = file.getOriginalFilename();
        //String filePath = "D:\\IdeaProjects\\webpcdp\\src\\main\\resources\\static\\MeetingFile\\";
        String filePath = MeetingFile_PATH;

        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            System.out.println("上传成功！");
        } catch (IOException e) {

        }
        return "redirect:/GroupMeeting?user_id=" + user_id;
    }


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
}