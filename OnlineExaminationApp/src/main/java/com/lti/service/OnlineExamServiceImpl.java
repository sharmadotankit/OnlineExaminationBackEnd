package com.lti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.entity.Course;
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

}
