package com.lti.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.lti.entity.Course;
import com.lti.entity.Enrollment;
import com.lti.entity.Question;
import com.lti.entity.Report;
import com.lti.entity.User;

public interface OnlineExamService {
	public User registerOrUpdateUser(User user); 
	public void sendEmailOnRegistration(User user);
	public List<String> getAllRegisteredEmails();
	public User loginUser(String email,String password);
	public List<Course> getAllCourses();
	public Enrollment enrollUserToACourse(int userId,int courseId);
	public List<Enrollment> getEnrollmentByUserId(int userId);
	public int getExamIdByLevelAndCourseId(int level, int courseId);
	public Enrollment getEnrollmentByUserIdAndCourseId(int userId, int courseId);
	public int getExamIdByCourseIdAndLevel(int courseId,int level);
	public List<Question> getQuestionsForExamId(int examId);
	public Report generateReport(Report report);
	public Enrollment updateLevelOfUser(Enrollment enrollment);
	public List<Report> viewAllReports();
}
