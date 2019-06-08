package com.web.pcdp.repository;

import com.web.pcdp.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface TeamRepository extends JpaRepository<Team,Integer> {

    //根据用户ID查询某用户的所有团队信息
    @Query(value = "SELECT * FROM team WHERE team_id IN(SELECT team_id FROM user_team WHERE user_id =?)", nativeQuery = true)
    List<Team> findUserAllTeam(@Param("user_id") int id);

    //根据团队ID查询团队信息
    @Query(value = "SELECT * FROM team WHERE team_id = ?", nativeQuery = true)
    Team findMyTeam(@Param("team_id") int id);


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO team(team_id,team_name,note,create_date) VALUES(?,?,?,?)", nativeQuery = true)
    void insertTeam(@Param("team_id") int team_id,
                    @Param("team_name") String team_name,
                    @Param("note") String note,
                    @Param("create_date") Date create_date);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_team(user_id,team_id,position) VALUES(?,?,?)", nativeQuery = true)
    void insertUser_Team(@Param("user_id") int user_id,
                    @Param("team_id") int team_id,
                    @Param("position") int position);

}