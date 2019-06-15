package com.web.pcdp.repository;

import com.web.pcdp.domain.*;
import com.web.pcdp.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    //根据项目ID查询项目信息
    @Query("select p from Project p where project_id=:project_id")
    Project findProjectByProject_id(@Param("project_id") int id);

    //查询某用户所有团队的项目
    @Query(value = "select * from project where team_id in(select team_id from user_team where user_id=?)",nativeQuery = true)
    List<Project> findUserAllProject(@Param("user_id") int id);

    //查询某个用户的所有团队的ID
    @Query(value = "select team_id from user_team where user_id =?",nativeQuery = true)
    List<Integer> findUserTeam(@Param("user_id") int id);

    //查询团队所有项目
    @Query(value = "select * from project where team_id = ?",
            nativeQuery = true)
    List<Project> findTeamAllProject(@Param("team_id") int id);

    //添加项目
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO project(project_id, project_name, team_id, create_date, note) " +
             "VALUES(?,?,?,?,?)", nativeQuery = true)
    void insertProject(@Param("project_id") int project_id,
                       @Param("project_name") String project_name,
                       @Param("team_id") int team_id,
                       @Param("create_date") Date create_date,
                       @Param("note") String note);

    //删除项目
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM project WHERE project_id=?", nativeQuery = true)
    void deleteProject(@Param("project_id") int id);

    //修改项目信息
    @Modifying
    @Transactional
    @Query(value = "UPDATE project SET project_name=?, note=? " +
            "WHERE project_id=?;", nativeQuery = true)
    void updateProject(@Param("project_name") String project_name,
                       @Param("note") String note,
                       @Param("project_id") int project_id);

    //上传项目文件
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO file(file_id, project_id, filename, create_date)" +
            "VALUES(?,?,?,?)", nativeQuery = true)
    void uploadFile(@Param("file_id") int file_id,
                    @Param("project_id") int project_id,
                    @Param("filename") String filename,
                    @Param("create_date") Date create_date);
}
