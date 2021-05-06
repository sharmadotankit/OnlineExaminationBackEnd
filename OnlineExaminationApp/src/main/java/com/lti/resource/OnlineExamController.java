package com.lti.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.lti.dto.LoginDto;
import com.lti.entity.Course;
import com.lti.entity.User;
import com.lti.service.OnlineExamServiceImpl;

@RestController
@CrossOrigin
public class OnlineExamController {
	@Autowired
	OnlineExamServiceImpl olService;
	
	//http://localhost:9090/registeruser
	@PostMapping(value="/registeruser")
	public User registerOrUpdateUser(@RequestBody User user) {
		if(olService.getAllRegisteredEmails().contains(user.getUserEmail())) {
			return null;
		}
		User userPersisted =  olService.registerOrUpdateUser(user);
//		olService.sendEmailOnRegistration(userPersisted);
		return userPersisted;	
	}
	
	
	//http://localhost:9090/login
	@PostMapping(value="/login")
	public User loginUser(@RequestBody LoginDto loginDto) {
		return olService.loginUser(loginDto.getUserEmail(), loginDto.getPassword());
	}
	
	
	//http://localhost:9090/getAllCourses
	@GetMapping(value="/getAllCourses")
	public List<Course> getAllCourses(){
		return olService.getAllCourses();
	}
}
