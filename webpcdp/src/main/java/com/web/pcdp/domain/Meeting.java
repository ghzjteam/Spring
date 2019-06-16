package com.web.pcdp.domain;

import javax.persistence.*;

@Entity
public class Meeting {

    @Id
    @Column(name = "meeting_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int meeting_id;

    private int team_id;
    private String meeting_name;
    private String note;
    private String type;
    private String file;
    private String start_date;
    private String place;

    protected Meeting(){

    }

    public Integer getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(Integer meeting_id) {
        this.meeting_id = meeting_id;
    }

    public Integer getTeam_id() {
        return team_id;
    }

    public void setTeam_id(Integer team_id) {
        this.team_id = team_id;
    }

    public String getMeeting_name() {
        return meeting_name;
    }

    public void setMeeting_name(String meeting_name) {
        this.meeting_name = meeting_name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString(){
        return "Meeting【 " + "\n" +
                "meeting_id = [" + meeting_id + "]、" +
                "team_id = [" + team_id + "]、" +
                "meeting_name = [" + meeting_name + "]、" +
                "note = [" + note + "]、" +
                "file = [" + file + "]、" +
                "start_date = [" +start_date + "]、" +
                "place = [" +place + "]】";
    }
}
