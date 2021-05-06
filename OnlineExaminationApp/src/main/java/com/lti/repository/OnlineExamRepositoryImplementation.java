package com.lti.repository;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import com.lti.entity.Course;
import com.lti.entity.User;

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

}
