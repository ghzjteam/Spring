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
    public List<User_team> findMemberUser(int team_id){
        List<User_team> user_teams = null;
        user_teams = userTeamRepository.findMemberUser(team_id);
        if (user_teams==null){
            return null;
        }else {
            return user_teams;
        }
    }

    //查询用户的所有团队的id
    public List<Integer> findUserTeam(int user_id){
        List<Integer> team = null;
        team = userTeamRepository.findUserTeam(user_id);
        if (team==null){
            return null;
        }
        else {
            return team;
        }
    }

    //删除团队成员
    public void deleteMember(int user_id,int team_id){
        userTeamRepository.deleteMember(user_id,team_id);
    }

    //添加团队成员
    public void InserteMember(int user_id,int team_id,int position){
        userTeamRepository.InserteMember(user_id,team_id,position);
    }

    public List<User_team> findPosition(int user_id){
        List<User_team> userTeam = null;
        userTeam = userTeamRepository.findPosition(user_id);
        System.out.println("AAA"+userTeam.get(0).getTeam_id());
        System.out.println("AAA"+userTeam.get(1).getTeam_id());
        if(userTeam == null)
            return null;
        else
            return userTeam;
    }

    //修改成员职位
    public void updateMember(int position,int user_id,int team_id){
        //System.out.println("xxx"+user_id+"\t"+team_id+"\t"+position);
        userTeamRepository.updateMember(position,user_id,team_id);
    }

}
