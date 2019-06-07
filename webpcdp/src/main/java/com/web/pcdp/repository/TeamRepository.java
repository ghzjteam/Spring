package com.web.pcdp.repository;

import com.web.pcdp.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team,Integer> {

    //根据用户ID查询某用户的所有团队信息
    @Query(value = "SELECT * FROM team WHERE team_id IN(SELECT team_id FROM user_team WHERE user_id =?)",nativeQuery = true)
    List<Team> findUserAllTeam(@Param("user_id") int id);
}
