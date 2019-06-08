package com.web.pcdp.repository;

import com.web.pcdp.domain.Meeting;
import com.web.pcdp.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {

    //根据会议ID查询会议信息
    @Query("select m from Meeting m where meeting_id=:meeting_id")
    Meeting findMeetingByMeeting_id(@Param("meeting_id") int id);

    //查询某用户所有团队的会议
    @Query(value = "select * from meeting  where team_id in(select team_id from user_team where user_id=?)",nativeQuery = true)
    List<Meeting> findUserAllMeeting(@Param("user_id") int id);

    //查询某个用户的所有团队的ID
    @Query(value = "select team_id from user_team where user_id =?",nativeQuery = true)
    List<Integer> findUserTeam(@Param("user_id") int id);


}
