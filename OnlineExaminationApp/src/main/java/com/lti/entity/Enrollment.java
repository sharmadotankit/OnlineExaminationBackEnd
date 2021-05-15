package com.lti.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Ol_Enrollment")
public class Enrollment {
	@Id
	@SequenceGenerator(name="ol_enrollment_seq",initialValue=1000,allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "ol_enrollment_seq")
	int enrollmentId;
	int levelOfUser;
	
	@ManyToOne
	User user;
	
	@ManyToOne
	Course course;
	
	@OneToMany(mappedBy = "enrollment",cascade = CascadeType.ALL)
	List<Report> report;

	
	public int getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(int enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public int getLevelOfUser() {
		return levelOfUser;
	}

	public void setLevelOfUser(int levelOfUser) {
		this.levelOfUser = levelOfUser;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@JsonIgnore
	public List<Report> getReport() {
		return report;
	}

	public void setReport(List<Report> report) {
		this.report = report;
	}
	
	
	

}
