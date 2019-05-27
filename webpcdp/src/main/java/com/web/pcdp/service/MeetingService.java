package com.web.pcdp.service;

import com.web.pcdp.domain.Meeting;
import com.web.pcdp.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeetingService {
    @Autowired
    private MeetingRepository meetingRepository;

    /*
    * 根据id查询会议
    * */
    public Meeting findMeetingByMeeting_id(int meeting_id){
        System.out.println("Service 成功获取 meeting_id = "+meeting_id);
        Meeting meeting = null;
        meeting = meetingRepository.findMeetingByMeeting_id(meeting_id);
        if (meeting == null || meeting.getMeeting_name().equals("")){
            return null;
        }
        else{
            return meeting;
        }
    }
}
