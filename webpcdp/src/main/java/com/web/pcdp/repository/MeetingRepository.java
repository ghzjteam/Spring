package com.web.pcdp.repository;

import com.web.pcdp.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
    @Query("select m from Meeting m where meeting_id=:meeting_id")
    Meeting findMeetingByMeeting_id(@Param("meeting_id") int id);

}
