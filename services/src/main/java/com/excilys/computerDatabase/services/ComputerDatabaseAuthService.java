package com.excilys.computerDatabase.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.excilys.computerDatabase.daos.UserCbdDAO;
import com.excilys.computerDatabase.dto.UserCbdDTO;
import com.excilys.computerDatabase.model.UserCbd;

@Service
public class ComputerDatabaseAuthService  {

	@Autowired
	private UserCbdDAO userDAO;

	public UserCbd getUser(UserCbd user) {

		return userDAO.getUser(user);
	}

	

	
	
	
}