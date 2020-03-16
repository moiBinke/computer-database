package com.excilys.computerDatabase.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name="user")
public class UserCbd  {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;	
	    @Column(name="username")
	 	private String username;
	    @Column(name="password")
	    private String password;
	    @Column(name="role")
	    private String role;
	    
	    public UserCbd()  {
	         
	    }
	 
	    public UserCbd(String userName, String password,String role) {
	        this.username = userName;
	        this.password = password;
	        this.role=role;
	    }
	 
	   
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getUsername() {
	        return username;
	    }
	 
	    public void setUsername(String userName) {
	        this.username = userName;
	    }
	 
	    public String getPassword() {
	        return password;
	    }
	 
	    public void setPassword(String password) {
	        this.password = password;
	    }
	    
	    public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}


}
