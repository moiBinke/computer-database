package com.excilys.computerDatabase.daos;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContexts;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerDatabase.logging.Logging;
import com.excilys.computerDatabase.model.Computer;
import com.excilys.computerDatabase.model.UserCbd;

@Repository
public class UserCbdDAO {

	@PersistenceContext
	EntityManager entityManager;
	
	public UserCbd findUser(String username) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<UserCbd> criteriaQuery = criteriaBuilder.createQuery(UserCbd.class);
		
		Root<UserCbd>root= criteriaQuery.from(UserCbd.class);
		Predicate byUsername=criteriaBuilder.equal(root.get("username"),username);
		criteriaQuery.select(root).where(byUsername);
		TypedQuery<UserCbd> userQuery=entityManager.createQuery(criteriaQuery);
		UserCbd user = userQuery.getSingleResult();
		return user;
	}
	
	public Optional<Computer> addCommputer(Computer computer)  {
    	Long generatedId = null;
    	
    	try {
    		entityManager.persist(computer);
    	}
    	catch(Exception persistanceException) {
    		persistanceException.printStackTrace();
    		Logging.afficherMessageError("Error when Creating Entity");
    	}
    	finally {
    		return computer.getId()==null? Optional.empty() : Optional.of(computer);
    	}

	}

	@Transactional
	public UserCbd save(UserCbd user) {

    	Long generatedId = null;
    	
    	try {
    		entityManager.persist(user);
    	}
    	catch(Exception persistanceException) {
    		persistanceException.printStackTrace();
    		Logging.afficherMessageError("Error when Creating User");
    	}
    	finally {
    		return user.getId()==null?null : user;
    	}

	}
	
	
}
