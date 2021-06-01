package com.lti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.entity.Course;
import com.lti.entity.Enrollment;
import com.lti.entity.Question;
import com.lti.entity.Report;
import com.lti.entity.Status;
import com.lti.entity.User;
import com.lti.repository.OnlineExamRepositoryImplementation;

@Service
public class OnlineExamServiceImpl implements OnlineExamService {

	@Autowired
	OnlineExamRepositoryImplementation olDao;

	@Autowired
	EmailService emailservice;

	public User registerOrUpdateUser(User user) {
		return olDao.registerOrUpdateUser(user);
	}

	@Override
	public void sendEmailOnRegistration(User user) {
		String subject = "Registration confirmation";

		String text = "Hi " + user.getUserName() + "\n " + " You have been Successfully registered. \n"
				+ "Your registered mail is " + user.getUserEmail() + ".\n "
				+ "Please use this email and your password to login.";

		emailservice.sendEmailForNewRegistration(user.getUserEmail(), text, subject);
		System.out.println("Mail sent");

	}

	@Override
	public List<String> getAllRegisteredEmails() {
		return olDao.getAllRegisteredEmails();

	}

	@Override
	public User loginUser(String email, String password) {
		return olDao.loginUser(email, password);
	}

	@Override
	public List<Course> getAllCourses() {
		return olDao.getAllCourses();
	}

	public Enrollment enrollUserToACourse(int userId, int courseId) {
		return olDao.enrollUserToACourse(userId, courseId);
	}

	@Override
	public List<Enrollment> getEnrollmentByUserId(int userId) {
		return olDao.getEnrollmentByUserId(userId);
	}

	@Override
	public int getExamIdByLevelAndCourseId(int level, int courseId) {
		return olDao.getExamIdByLevelAndCourseId(level, courseId);
	}

	@Override
	public Enrollment getEnrollmentByUserIdAndCourseId(int userId, int courseId) {
		return olDao.getEnrollmentByUserIdAndCourseId(userId, courseId);
	}

	@Override
	public int getExamIdByCourseIdAndLevel(int courseId, int level) {
		return olDao.getExamIdByCourseIdAndLevel(courseId, level);
	}

	@Override
	public List<Question> getQuestionsForExamId(int examId) {
		return olDao.getQuestionsForExamId(examId);
	}

	@Override
	public Report generateReport(Report report) {
		return olDao.generateReport(report);
	}

	@Override
	public Enrollment updateLevelOfUser(Enrollment enrollment) {
		return olDao.updateLevelOfUser(enrollment);
	}

	@Override
	public List<Report> viewAllReports(){
		return olDao.viewAllReports();
	}

	@Override
	public int getCourseIdByCourseName(String courseName) {
		return olDao.getCourseIdByCourseName(courseName);
	}
	
	public List<Question> getQuestionByCourseNameAndLevel(String courseName, int level){
		return olDao.getQuestionByCourseNameAndLevel(courseName, level);
	}
	
	public boolean removeQuestionByQuestionId(int questionId) {
		return olDao.removeQuestionByQuestionId(questionId);
	}

	@Override
	public Question addQuestion(Question question, String courseName, int level) {
		return olDao.addQuestion(question, courseName, level);
	}

	@Override
	public boolean sendMailForRestPassword(String emailOfUser) {
		User user = olDao.getUserByEmail(emailOfUser);
		String otp = olDao.generateOtp(user.getUserId());
		String subject = "OTP to reset your password at Hexadecimal Code";
		String text = "Hello "+user.getUserName()
					+", \n Please enter the given otp to reset your password.\n\n"
					+"Your otp is : "+ user.getOneTimePassword()
					+"\n This otp is valid for 10 minutes only."
					+"\n\n\n\n (Do not disclose the otp to anyone)";
		emailservice.sendEmailForForgetPassword(emailOfUser, subject, text);
		System.out.println("Mail sent");
		return false;
	}

	@Override
	public String resetPassword(String email,String password,String otp) {
		return olDao.resetPassword(email,password,otp);
	}

}
