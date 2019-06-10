package com.web.pcdp.repository;

import com.web.pcdp.domain.Meeting;
import com.web.pcdp.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    //根据meeting_Id 删除会议信息
    @Modifying
    @Transactional
    @Query(value = "delete from meeting where meeting_id=?",nativeQuery = true)
    void deleteMeeting(@Param("meeting_id") int id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO meeting(team_id,meeting_name,note,type,file,start_date,place) VALUES(?,?,?,?,?,?,?)",nativeQuery = true)
    void insertMeeting(@Param("team_id") int team_id,
                       @Param("meeting_name") String meeting_name,
                       @Param("note") String note,
                       @Param("type") String type,
                       @Param("file") String file,
                       @Param("start_date") String start_date,
                       @Param("place") String place);

    @Modifying
    @Transactional
    @Query(value = "UPDATE meeting SET meeting_name=?,type=?,place=?,note=?,start_date=? WHERE meeting_id=?;",nativeQuery = true)
    void updateMeeting(@Param("meeting_name") String meeting_name,
                       @Param("type") String type,
                       @Param("place") String place,
                       @Param("note") String note,
                       @Param("start_date") String start_date,
                       @Param("meeting_id") int meeting_id);

}
