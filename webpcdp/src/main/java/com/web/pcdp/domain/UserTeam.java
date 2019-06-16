package com.web.pcdp.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserTeam {

    @Id
    private int user_id;
    private int team_id;
    private int position;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
