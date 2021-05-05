package com.lti.service;

import com.lti.entity.User;

public interface OnlineExamService {
	public User registerOrUpdateUser(User user); 
	public void sendEmailOnRegistration(User user);
}
