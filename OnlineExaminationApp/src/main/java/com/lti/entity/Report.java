package com.lti.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Ol_Report")
public class Report {
	@Id
	@SequenceGenerator(name="ol_report_seq",initialValue=8000,allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "ol_report_seq")
	int reportId;
	int score;
	Status status;
	
	@ManyToOne
	@JoinColumn(name = "enrollmentId")
	Enrollment enrollment;

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Enrollment getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}
	
	

}
