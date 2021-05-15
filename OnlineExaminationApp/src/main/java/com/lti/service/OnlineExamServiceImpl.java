package com.lti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.entity.Course;
import com.lti.entity.Enrollment;
import com.lti.entity.Question;
import com.lti.entity.Report;
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
	public List<Report> viewAllReports() {
		return olDao.viewAllReports();
	}

}
