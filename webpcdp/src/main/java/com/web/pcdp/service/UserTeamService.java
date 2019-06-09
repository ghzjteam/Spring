package com.web.pcdp.service;

import com.web.pcdp.domain.user_team;
import com.web.pcdp.repository.UserTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: pcdp
 * @description: 联合表service
 * @author: jiang
 * @create: 2019-06-09 16:46
 **/

@Service("user_team")
public class UserTeamService {


    @Autowired
    private UserTeamRepository userTeamRepository;

    public List<user_team> findmemberUser(int team_id){

        List<user_team> user_teams = null;
        user_teams = userTeamRepository.findmemberUser(team_id);
        if (user_teams==null){
            return null;
        }else {
            return user_teams;
        }
    }


    public void deletemember(int user_id,int team_id){
        userTeamRepository.deletemember(user_id,team_id);
    }
}
