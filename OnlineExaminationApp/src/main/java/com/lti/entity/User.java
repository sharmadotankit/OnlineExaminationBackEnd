package com.lti.entity;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Ol_User")
public class User {
	@Id
	@SequenceGenerator(name="ol_user_seq",initialValue=5000,allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "ol_user_seq")
	int userId;
	String userName;
	
	@Column(unique = true)
	String userEmail;
	String userMobile;
	String userCity;
	String userState;
	String userQualification;
	LocalDate dateOfBirth;
	LocalDate dateOfCompletion;
	String password;

	
	@OneToMany(mappedBy = "user",cascade=CascadeType.ALL)
	List<Enrollment> enrollments;
	
	//	variable and method related to Otp
//	private static final long OTP_VALID_DURATION = 5 * 60 * 1000;
	
	 String oneTimePassword;

	 Long otpRequestedTime;
	
//	public boolean isOTPRequired() {
//        if (this.getOneTimePassword() == null) {
//            return false;
//        }
//         
//        long currentTimeInMillis = System.currentTimeMillis();
//        long otpRequestedTimeInMillis = this.otpRequestedTime.getTime();
//         
//        if (otpRequestedTimeInMillis + OTP_VALID_DURATION < currentTimeInMillis) {
//            // OTP expires
//            return false;
//        }
//         
//        return true;
//    }
	 
	 
	
	
	 
	 
	public String getOneTimePassword() {
		return oneTimePassword;
	}

	public Long getOtpRequestedTime() {
		return otpRequestedTime;
	}

	public void setOtpRequestedTime(Long otpRequestedTime) {
		this.otpRequestedTime = otpRequestedTime;
	}

	public void setOneTimePassword(String oneTimePassword) {
		this.oneTimePassword = oneTimePassword;
	}

	

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@JsonIgnore
	public List<Enrollment> getEnrollments() {
		return enrollments;
	}
	public void setEnrollments(List<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public String getUserCity() {
		return userCity;
	}
	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}
	public String getUserState() {
		return userState;
	}
	public void setUserState(String userState) {
		this.userState = userState;
	}
	public String getUserQualification() {
		return userQualification;
	}
	public void setUserQualification(String userQualification) {
		this.userQualification = userQualification;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public LocalDate getDateOfCompletion() {
		return dateOfCompletion;
	}
	public void setDateOfCompletion(LocalDate dateOfCompletion) {
		this.dateOfCompletion = dateOfCompletion;
	}
	
	
	

}
