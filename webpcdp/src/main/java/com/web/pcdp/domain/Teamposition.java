package com.web.pcdp.domain;

import javax.persistence.Id;

/**
 * 成员职位实体类
 **/
public class Teamposition {

    @Id
    private int user_id;
    private String user_name;
    private String user_position;
    private int team_id;


    public int getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_position() {
        return user_position;
    }

    public int getTeam_id() {
        return team_id;
    }


    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_position(String user_position) {
        this.user_position = user_position;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

}
