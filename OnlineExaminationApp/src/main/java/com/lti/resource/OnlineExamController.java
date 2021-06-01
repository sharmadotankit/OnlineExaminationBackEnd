package com.lti.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lti.dto.LoginDto;
import com.lti.dto.ResetPasswordDto;
import com.lti.dto.ViewReportsDto;
import com.lti.entity.Course;
import com.lti.entity.Enrollment;
import com.lti.entity.Question;
import com.lti.entity.Report;
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
		olService.sendEmailOnRegistration(userPersisted);
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
	
	//http://localhost:9090/enroll
	@PostMapping(value="enroll")
	public Enrollment enrollUserToACourse(@RequestParam int userId ,@RequestParam int courseId) {
		return olService.enrollUserToACourse(userId, courseId);
	}
	
	//http://localhost:9090/getenrollmentofuser
	@GetMapping(value = "getenrollmentofuser")
	public List<Enrollment> getEnrollmentByUserId(@RequestParam int userId) {
		return olService.getEnrollmentByUserId(userId);
	}
	
	
	//http://localhost:9090/getexamid
	@GetMapping(value="getexamid")
	public int getExamIdByLevelAndCourseId(@RequestParam int level,@RequestParam int courseId) {
		return olService.getExamIdByLevelAndCourseId(level, courseId);
	}
	
	
	//http://localhost:9090/getenrollmentbyuseridandcourseid
	@GetMapping(value = "getenrollmentbyuseridandcourseid")
	public Enrollment getEnrollmentByUserIdAndCourseId(@RequestParam int userId, @RequestParam int courseId) {
		return olService.getEnrollmentByUserIdAndCourseId(userId, courseId);
		
	}
	
	//http://localhost:9090/getexamidbycourseidandlevel
	@GetMapping(value = "getexamidbycourseidandlevel")
	public int getExamIdByCourseIdAndLevel(@RequestParam int courseId , @RequestParam int level) {
		return olService.getExamIdByCourseIdAndLevel(courseId, level);
	}
	
	//http://localhost:9090/getquestionsforexamid
	@GetMapping(value="getquestionsforexamid")
	public List<Question> getQuestionsForExamId(@RequestParam int examId){
		return olService.getQuestionsForExamId(examId);
	}
	
	
	//http://localhost:9090/generatereport
	@PostMapping(value="generatereport")
	public Report generateReport(@RequestBody Report report) {
		return olService.generateReport(report);
	}
	
	
	//http://localhost:9090/updatelevelofuser
	@PutMapping(value="updatelevelofuser")
	public Enrollment updateLevelOfUser(@RequestBody Enrollment enrollment) {
		return olService.updateLevelOfUser(enrollment);
	}
	
	
	//http://localhost:9090/viewreports
	@GetMapping(value = "viewreports")
	public List<Report> viewReports(){
		return olService.viewAllReports();
	}
	
	
	//http://localhost:9090/getcourseidbycoursename
	@GetMapping(value="getcourseidbycoursename")
	public int getCourseIdByCourseName(@RequestParam String courseName) {
		return olService.getCourseIdByCourseName(courseName);
	}
	
	//http://localhost:9090/getquestionbycoursenameandlevel
	@GetMapping(value = "getquestionbycoursenameandlevel")
	public List<Question> getQuestionByCourseNameAndLevel(@RequestParam String courseName , @RequestParam int level){
		return olService.getQuestionByCourseNameAndLevel(courseName, level);
	}
	
	
	//http://localhost:9090/addquestion
	@PostMapping(value="addquestion")
	public Question addQuestion(@RequestBody Question questionObj , @RequestParam String courseName,@RequestParam int level) {
		return olService.addQuestion(questionObj, courseName, level);
	}
	
	
	//http://localhost:9090/removequestionbyquestionid
	@PutMapping(value="removequestionbyquestionid")
	public boolean removeQuestion(@RequestParam int questionId) {
		return olService.removeQuestionByQuestionId(questionId);
	}
	
	//http://localhost:9090/sendmailforresetpassword
	@PostMapping(value="sendmailforresetpassword")
	public boolean sendMailForRestPassword(@RequestParam String emailOfUser){
		if(olService.getAllRegisteredEmails().contains(emailOfUser)) {
			olService.sendMailForRestPassword(emailOfUser);
			return true;
		}
		return false;
	}
	
	
	//http://localhost:9090/resetpassword
	@PostMapping(value="resetpassword",produces="text/plain")
	public String resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
		System.out.println(resetPasswordDto.getEmail()+" "+resetPasswordDto.getPassword()+" "+resetPasswordDto.getOtp());
		return olService.resetPassword(resetPasswordDto.getEmail(),resetPasswordDto.getPassword(),resetPasswordDto.getOtp());
	}
	
}
