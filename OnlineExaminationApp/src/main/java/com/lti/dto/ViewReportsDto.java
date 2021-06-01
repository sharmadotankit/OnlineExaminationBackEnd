package com.lti.dto;

import com.lti.entity.Status;

public class ViewReportsDto {
	String courseName;
	int levelOfUser;
	Status status;
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getLevelOfUser() {
		return levelOfUser;
	}
	public void setLevelOfUser(int levelOfUser) {
		this.levelOfUser = levelOfUser;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
