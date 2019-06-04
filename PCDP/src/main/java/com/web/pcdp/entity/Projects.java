package com.web.pcdp.entity;

import java.util.ArrayList;

public class Projects {
	private String projectId;
	private String projectName;
	private String teamId;
	private String createDate;
	private String note;
	
	private Teams team;
	private ArrayList<Meetings> meetings;
	private Files fileDB;
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Teams getTeam() {
		return team;
	}
	public void setTeam(Teams team) {
		this.team = team;
	}
	public ArrayList<Meetings> getMeetings() {
		return meetings;
	}
	public void setMeetings(ArrayList<Meetings> meetings) {
		this.meetings = meetings;
	}
	public Files getFileDB() {
		return fileDB;
	}
	public void setFileDB(Files fileDB) {
		this.fileDB = fileDB;
	}

	
}
