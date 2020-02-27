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
public class ComputerDatabaseAuthService implements UserDetailsService  {

	private UserCbdDAO userDAO;
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ComputerDatabaseAuthService(UserCbdDAO userDAO,PasswordEncoder passwordEncoder) {
		this.passwordEncoder=passwordEncoder;
		this.userDAO=userDAO;
	}
	public UserCbd registerNewUserAccountUser(UserCbdDTO userDto) {
	   
	    UserCbd user = new UserCbd();
	    user.setUsername(userDto.getUsername());
	    user.setRole(userDto.getRole());
	     
	    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
	    return userDAO.save(user);
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserCbd userCbd=userDAO.findUser(username);
		if(userCbd==null) {
			throw new UsernameNotFoundException("not found username: "+username );
		}
		List<GrantedAuthority> grantList= new ArrayList<GrantedAuthority>();
		
		if(userCbd.getRole()!=null) {
			GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + userCbd.getRole());
			grantList.add(authority);
		}
		UserDetails userDetails = new User(userCbd.getUsername(),userCbd.getPassword(),grantList);
		return userDetails;
	}
}
