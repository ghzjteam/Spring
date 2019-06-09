package com.web.pcdp.repository;

import com.web.pcdp.domain.user_team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: pcdp
 * @description: 联合表仓储层
 * @author: jiang
 * @create: 2019-06-09 16:44
 **/
@Repository
public interface UserTeamRepository extends JpaRepository<user_team,Integer> {
    //查询某一团队的所有用户信息
    @Query(value = "select * from user_team where team_id = ?",nativeQuery = true)
    List<user_team> findmemberUser(@Param("team_id") int id);


    //删除成员
    @Modifying
    @Transactional
    @Query(value = "delete from user_team where user_id = ? and team_id = ?",nativeQuery = true)
    void deletemember(@Param("user_id") int user_id,
                 @Param("team_id") int team_id);

}
