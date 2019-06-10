package com.web.pcdp.service;

import com.web.pcdp.domain.Meeting;
<<<<<<< HEAD
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
=======
import com.web.pcdp.domain.Team;
import com.web.pcdp.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("meeting")
public class MeetingService {
    @Autowired

    private MeetingRepository meetingRepository;


    //根据会议id查询会议
>>>>>>> c03938eddc2acb005e8a348f4ae7b480aae981f6
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
<<<<<<< HEAD
=======

    //查询某用户所有的会议信息
    public List<Meeting> findUserAllMeeting(int user_id){
        //System.out.println("Service 成功获取 user_id = "+user_id);
        List<Meeting> meeting = null;
        meeting = meetingRepository.findUserAllMeeting(user_id);
        if (meeting == null){
            return null;
        }
        else{
            return meeting;
        }
    }

    //查询用户的所有团队的id
    public List<Integer> findUserTeam(int user_id){
        List<Integer> team = null;
        team = meetingRepository.findUserTeam(user_id);
        if (team==null){
            return null;
        }
        else {
            return team;
        }
    }

    //删除会议
    public void  deleteMeeting(int meeting_id){
        meetingRepository.deleteMeeting(meeting_id);
        System.out.println("成功删除"+meeting_id);
    }

    public void insertMeeting(int team_id,String meeting_name,String note,String type,String file,String start_date,String place){
        meetingRepository.insertMeeting(team_id,meeting_name,note,type,file,start_date,place);
        System.out.println("成功添加" + team_id + "||" + meeting_name);
    }

    public void updateMeeting(String meeting_name,String type,String place,String note,String start_date,int meeting_id){
        meetingRepository.updateMeeting(meeting_name,type,place,note,start_date,meeting_id);
        System.out.println("成功修改" + meeting_name);
    }


>>>>>>> c03938eddc2acb005e8a348f4ae7b480aae981f6
}
