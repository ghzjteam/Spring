package com.web.pcdp.controller;

import com.web.pcdp.repository.MeetingRepository;
import com.web.pcdp.domain.Meeting;
import com.web.pcdp.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MeetingController {
    @Autowired//授权
    private MeetingService meetingService;
    //MeetingRepository meetingRepository;

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
}
