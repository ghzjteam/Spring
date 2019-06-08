package com.web.pcdp.service;

import com.web.pcdp.domain.Team;
import com.web.pcdp.repository.MeetingRepository;
import com.web.pcdp.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;

@Service("team")
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    //根据用户ID查询某用户所有团队的信息
    public List<Team> findUserAllTeam(int user_id){
        List<Team> team = null;
        team = teamRepository.findUserAllTeam(user_id);
        if (team==null){
            return null;
        }
        else {
            return team;
        }
    }

    public Team findMyTeam(int team_id){
        Team team = null;
        team = teamRepository.findMyTeam(team_id);
        if (team==null){
            return null;
        }
        else {
            return team;
        }

    }

    public void insertTeam(int team_id, String team_name, String note, Date create_date){
        teamRepository.insertTeam(team_id,team_name,note,create_date);
        System.out.println("成功添加" + team_id + "||" + team_name);
    }

    public void insertUser_Team(int user_id,int team_id,int position){
        teamRepository.insertUser_Team(user_id,team_id,position);
        System.out.println("成功添加" + user_id + "||" + team_id);
    }
}
