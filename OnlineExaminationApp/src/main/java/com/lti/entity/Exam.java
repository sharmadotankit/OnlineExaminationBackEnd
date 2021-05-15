package com.lti.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table(name="Ol_Exam")
public class Exam {
	@Id
	@SequenceGenerator(name="ol_exam_seq",initialValue=2000,allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "ol_exam_seq")
	int examId;
	
	@Column(name="levelForExam")
	int level;
	
	@ManyToOne
	Course course;
	
	@OneToMany(mappedBy = "exam",cascade = CascadeType.ALL)
	List<Question> question;
	
	
	@JsonIgnore
	public List<Question> getQuestion() {
		return question;
	}

	public void setQuestion(List<Question> question) {
		this.question = question;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	

}
