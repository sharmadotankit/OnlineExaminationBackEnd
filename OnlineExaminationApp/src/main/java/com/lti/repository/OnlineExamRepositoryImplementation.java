package com.lti.repository;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import com.lti.entity.Course;
import com.lti.entity.Enrollment;
import com.lti.entity.User;
import com.lti.entity.Exam;
import com.lti.entity.Question;
import com.lti.entity.Report;
import com.lti.entity.Status;

@Repository
public class OnlineExamRepositoryImplementation implements OnlineExamRepository {
	@PersistenceContext
	EntityManager em;

	@Transactional
	public User registerOrUpdateUser(User user) {

		User u = null;
		try {

			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();

			byte[] b = md.digest(user.getPassword().getBytes());

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, b);

			// Convert message digest into hex value
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			user.setPassword(hashtext);
			u = em.merge(user);
		}

		// For specifying wrong message digest algorithms
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		return u;
	}

	@Override
	public List<String> getAllRegisteredEmails() {
		String jpql = "select u.userEmail from User u";
		TypedQuery<String> query = em.createQuery(jpql, String.class);
		List<String> registeredEmails = query.getResultList();
		return registeredEmails;
	}

	@Override
	public User loginUser(String email,String password) {
		String jpql = "select u from User u where u.userEmail=:ue";
		TypedQuery<User> query = em.createQuery(jpql,User.class);
		query.setParameter("ue", email)	;
		User loggedInUser = null;
		try{
			loggedInUser= query.getSingleResult();
		}
		catch(Exception e){
			
		}
		try{
			MessageDigest md=MessageDigest.getInstance("MD5");
			md.reset();
			md.update(password.getBytes());
			byte[] digest = md.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			String hashtext = bigInt.toString(16);
			while (hashtext.length() < 32) {
		         hashtext = "0" + hashtext;
		    }
			if(loggedInUser!=null && loggedInUser.getPassword().equals(hashtext))
		        	return loggedInUser;
		}
		catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
		return null;
	}

	
	@Override
	public List<Course> getAllCourses() {
		String jpql = "select c from Course c";
		TypedQuery<Course> query = em.createQuery(jpql,Course.class);
		List<Course> courses = query.getResultList();
		return courses;
	}

	@Transactional
	public Enrollment enrollUserToACourse(int userId, int courseId) {
		User user = em.find(User.class,userId);
		Course course = em.find(Course.class, courseId);
		Enrollment enrollment = new Enrollment();
		enrollment.setCourse(course);
		enrollment.setUser(user);
		enrollment.setLevelOfUser(0);
		Enrollment enrollmentPersisted = em.merge(enrollment);
		return enrollmentPersisted;
	}

	@Override
	public List<Enrollment> getEnrollmentByUserId(int userId) {
		User user=em.find(User.class, userId);
		return user.getEnrollments();
	}

	@Override
	public int getExamIdByLevelAndCourseId(int level, int courseId) {
		String jpql = "select e.examId from Exam e where e.level=:lvl and e.course.courseId=:cid";
		TypedQuery<Integer> query = em.createQuery(jpql,Integer.class);
		query.setParameter("lvl", level);
		query.setParameter("cid", courseId);
		int examId = query.getSingleResult();
		return examId;
	}

	@Override
	public Enrollment getEnrollmentByUserIdAndCourseId(int userId, int courseId) {
		String jpql = "select e from Enrollment e where e.user.userId=:uid and e.course.courseId=:cid";
		TypedQuery<Enrollment> query = em.createQuery(jpql,Enrollment.class);
		query.setParameter("uid", userId);
		query.setParameter("cid", courseId);
		Enrollment enrollment = (Enrollment) query.getSingleResult();
		return enrollment;
	}

	@Override
	public int getExamIdByCourseIdAndLevel(int courseId, int level) {
		String jpql = "select e.examId from Exam e where e.course.courseId=:cid and e.level=:lvl";
		TypedQuery<Integer> query = em.createQuery(jpql,Integer.class);
		query.setParameter("cid", courseId);
		query.setParameter("lvl", level);
		int examId = query.getSingleResult();
		return examId;
	}

	@Override
	public List<Question> getQuestionsForExamId(int examId) {
		String jpql = "select q from Question q where q.exam.examId=:eid";
		TypedQuery<Question> query = em.createQuery(jpql,Question.class);
		query.setParameter("eid", examId);
		List<Question> questions = query.getResultList();
		return questions;
	}

	@Transactional
	public Report generateReport(Report report) {
		Report persistedReport=null;
		if(report.getStatus()==Status.PASSED) {
			updateLevelOfUser(report.getEnrollment());
			persistedReport = em.merge(report);
		}
		else {
			persistedReport = em.merge(report);
		}
		
		return persistedReport;
	}

	@Transactional
	public Enrollment updateLevelOfUser(Enrollment enrollment) {
		enrollment.setLevelOfUser(enrollment.getLevelOfUser()+1);
		Enrollment persistedEnrollment = em.merge(enrollment);
		return persistedEnrollment;
	}

	
	@Override
	public int getCourseIdByCourseName(String courseName) {
		String jpql = "select courseId from Course c where courseName=:cnm";
		TypedQuery<Integer> query = em.createQuery(jpql,Integer.class);
		query.setParameter("cnm", courseName);
		Integer courseId = query.getSingleResult();
		return courseId;
	}
	
	
	@Override
	public List<Report> viewAllReports() {
		String jpql = "select r from Report r";
		TypedQuery<Report> query = em.createQuery(jpql, Report.class);
		List<Report> reports = query.getResultList();
		return reports;
	}

	@Override
	public List<Question> getQuestionByCourseNameAndLevel(String courseName, int level) {
		int courseId = getCourseIdByCourseName(courseName);
		String jpql= "select q from Question q where q.exam.course.courseId=:cid and q.exam.level=:lvl";
		TypedQuery<Question> query = em.createQuery(jpql,Question.class);
		query.setParameter("cid", courseId);
		query.setParameter("lvl", level);
		List<Question> questions = query.getResultList();
		return questions;
	}

	@Transactional
	public boolean removeQuestionByQuestionId(int questionId) {
		String jpql="delete from Question q where q.questionId=:qid";
		Query query = em.createQuery(jpql);
		query.setParameter("qid", questionId);
		if(query.executeUpdate()==1) {
			return true;
		}
		else {
			return false;
		}
	}

	@Transactional
	public Question addQuestion(Question question, String courseName, int level) {
		int courseId = getCourseIdByCourseName(courseName);
		int examId = getExamIdByCourseIdAndLevel(courseId, level);
		Exam exam = em.find(Exam.class, examId);
		question.setExam(exam);
		
		Question persistedQuestion = em.merge(question);
		return persistedQuestion;
	}

	@Override
	public User getUserByEmail(String emailOfUser) {
		String jpql = "select u from User u where u.userEmail=:eou";
		TypedQuery<User> query = em.createQuery(jpql,User.class);
		query.setParameter("eou", emailOfUser);
		User user = query.getSingleResult();
		return user;
	}

	@Transactional
	public String resetPassword(String email,String password,String otp) {
		User user = getUserByEmail(email);
		if(user.getOneTimePassword().equals(otp)) {
			if(System.currentTimeMillis()>user.getOtpRequestedTime()+(10*60*1000)) {
				return "OTP expired";
			}
			else {
				User persistedUser = null;
				try {
					MessageDigest md = MessageDigest.getInstance("MD5");
					md.reset();
					byte[] b = md.digest(password.getBytes());
					
					// Convert byte array into signum representation
					BigInteger no = new BigInteger(1, b);
					
					// Convert message digest into hex value
					String hashtext = no.toString(16);
					while (hashtext.length() < 32) {
						hashtext = "0" + hashtext;
					}
					user.setPassword(hashtext);
					user.setOneTimePassword("0");
					persistedUser = em.merge(user);
					
				}

				// For specifying wrong message digest algorithms
				catch (Exception e) {
					throw new RuntimeException(e);
				}
				if(persistedUser==null) {
					return "Failed to reset password";
				}
				else {
					return "Password reset successful";
				}
			}
		}
		else {
			return "Otp is wrong";
		}
		
		
	}

	@Transactional
	public String generateOtp(int userId) {
		System.out.println("In repository : "+userId);
		User user = em.find(User.class, userId);
		String characters = "0123456789abcdefghijklmnopqrstuvwxyz+#@$";
		String otp  = "";
		for(int i=0;i<8;i++){
			int index = (int) Math.floor(Math.random()*40);
			otp+=characters.charAt(index);
		}
		
		long requestedTime = System.currentTimeMillis();
		System.out.println(requestedTime);
		System.out.println(otp);
	
		user.setOneTimePassword(otp);
		user.setOtpRequestedTime(requestedTime);
		registerOrUpdateUser(user);
		
		return null;
	}


}
