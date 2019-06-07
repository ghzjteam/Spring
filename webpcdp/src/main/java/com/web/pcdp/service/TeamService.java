package com.web.pcdp.service;

import com.web.pcdp.domain.Team;
import com.web.pcdp.repository.MeetingRepository;
import com.web.pcdp.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
}
