package com.lti.service;

import java.util.List;

import com.lti.entity.Course;
import com.lti.entity.User;

public interface OnlineExamService {
	public User registerOrUpdateUser(User user); 
	public void sendEmailOnRegistration(User user);
	public List<String> getAllRegisteredEmails();
	public User loginUser(String email,String password);
	public List<Course> getAllCourses();
}
