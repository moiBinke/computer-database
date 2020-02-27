package com.excilys.computerDatabase.dto;

public class UserCbdDTO {


		private Long id;	
	 	private String username;
	    private String password;
	    private String role;
	    
	    public UserCbdDTO()  {
	         
	    }
	 
	    public UserCbdDTO(String userName, String password,String role) {
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

		@Override
		public String toString() {
			return "UserCbdDTO [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role
					+ "]";
		}


}
