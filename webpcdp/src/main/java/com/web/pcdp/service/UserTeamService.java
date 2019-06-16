package com.web.pcdp.service;

import com.web.pcdp.domain.User_team;
import com.web.pcdp.repository.UserTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: pcdp
 * @description: 联合表service
 **/

@Service("user_team")
public class UserTeamService {

    @Autowired
    private UserTeamRepository userTeamRepository;

    //根据获取团队所有成员
    public List<User_team> findmemberUser(int team_id){
        List<User_team> user_teams = null;
        user_teams = userTeamRepository.findmemberUser(team_id);
        if (user_teams==null){
            return null;
        }else {
            return user_teams;
        }
    }

    //根据获取团队所有成员
    public List<User_team> findPosition(int user_id){
        List<User_team> user_teams = null;
        user_teams = userTeamRepository.findPosition(user_id);
        System.out.println(user_teams.get(0).getTeam_id());
        System.out.println(user_teams.get(1).getTeam_id());
        if (user_teams==null){
            return null;
        }else {
            return user_teams;
        }

    }

    //删除团队成员
    public void deletemember(int user_id,int team_id){
        userTeamRepository.deletemember(user_id,team_id);
    }

    //添加团队成员
    public void Insertemember(int user_id,int team_id,int position){
        userTeamRepository.Insertemember(user_id,team_id,position);
    }

    //修改成员职位
    public void updateMember(int position,int user_id,int team_id){
        //System.out.println("xxx"+user_id+"\t"+team_id+"\t"+position);
        userTeamRepository.updateMember(position,user_id,team_id);
    }
}
