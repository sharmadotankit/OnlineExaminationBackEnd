package com.lti.repository;

import java.util.List;
import com.lti.entity.Course;
import com.lti.entity.Enrollment;
import com.lti.entity.Question;
import com.lti.entity.Report;
import com.lti.entity.Status;
import com.lti.entity.User;

public interface OnlineExamRepository {
	public User registerOrUpdateUser(User user);
	public List<String> getAllRegisteredEmails();
	public User loginUser(String email,String password);
	public List<Course> getAllCourses();
	public Enrollment enrollUserToACourse(int userId,int courseId);
	public List<Enrollment> getEnrollmentByUserId(int userId);
	public int getExamIdByLevelAndCourseId(int level,int courseId);
	public Enrollment getEnrollmentByUserIdAndCourseId(int userId,int courseId);
	public int getExamIdByCourseIdAndLevel(int courseId,int level);
	public List<Question> getQuestionsForExamId(int examId);
	public Report generateReport(Report report);
	public Enrollment updateLevelOfUser(Enrollment enrollment);
	public int getCourseIdByCourseName(String courseName);
	public List<Report> viewAllReports();
	public List<Question> getQuestionByCourseNameAndLevel(String courseName, int level);
	public boolean removeQuestionByQuestionId(int questionId);
	public Question addQuestion(Question question,String courseName,int level);
	public User getUserByEmail(String emailOfUser);
	public String resetPassword(String email,String password,String otp);
	public String generateOtp(int userId);
}
