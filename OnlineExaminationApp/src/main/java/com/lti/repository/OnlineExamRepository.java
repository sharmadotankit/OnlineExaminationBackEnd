package com.lti.repository;

import java.util.List;

import com.lti.entity.Course;
import com.lti.entity.User;

public interface OnlineExamRepository {
	public User registerOrUpdateUser(User user);
	public List<String> getAllRegisteredEmails();
	public User loginUser(String email,String password);
	public List<Course> getAllCourses();
}
