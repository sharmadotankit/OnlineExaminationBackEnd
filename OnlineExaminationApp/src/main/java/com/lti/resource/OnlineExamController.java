package com.lti.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lti.entity.User;
import com.lti.repository.OnlineExamRepositoryImplementation;
import com.lti.service.OnlineExamServiceImpl;

@RestController
@CrossOrigin
public class OnlineExamController {
	@Autowired
	OnlineExamServiceImpl olService;
	
	//http://localhost:9090/registerorupdateuser
	@PostMapping(value="/registerorupdateuser")
	public User registerOrUpdateUser(@RequestBody User user) {
		User userPersisted =  olService.registerOrUpdateUser(user);
		olService.sendEmailOnRegistration(userPersisted);
		return userPersisted;
		
	}
}
