package com.lti.repository;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

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

}
