package com.web.pcdp.repository;

import com.web.pcdp.domain.Key;
import com.web.pcdp.domain.User_team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户团队联合表JPA接口
 **/

@Repository
public interface UserTeamRepository extends JpaRepository<User_team, Key> {
    //查询某一团队的所有用户信息
    @Query(value = "select * from user_team where team_id = ?",nativeQuery = true)
    List<User_team> findMemberUser(@Param("team_id") int id);

    //查询用户在团队中的职位
    @Query(value = "SELECT * FROM user_team WHERE user_id =?",nativeQuery = true)
    List<User_team> findPosition(@Param("user_id") int user_id);

    //查询某个用户的所有团队的ID
    @Query(value = "select team_id from user_team where user_id =?",nativeQuery = true)
    List<Integer> findUserTeam(@Param("user_id") int id);

    //删除成员
    @Modifying
    @Transactional
    @Query(value = "delete from user_team where user_id = ? and team_id = ?",nativeQuery = true)
    void deleteMember(@Param("user_id") int user_id,
                      @Param("team_id") int team_id);

    //添加成员
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_team values (?,?,?)",nativeQuery = true)
    void InserteMember(@Param("user_id") int user_id,
                       @Param("team_id") int team_id,
                       @Param("position") int position);

    //修改成员权限
    @Modifying
    @Transactional
    @Query(value = "update user_team set position = ? where user_id = ? and team_id = ?",nativeQuery = true)
    void updateMember(@Param("position") int position,
                      @Param("user_id") int user_id,
                      @Param("team_id") int team_id);


}
