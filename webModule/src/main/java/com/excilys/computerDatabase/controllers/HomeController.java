package com.excilys.computerDatabase.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.excilys.computerDatabase.dto.ComputerDTO;
import com.excilys.computerDatabase.dto.UserCbdDTO;
import com.excilys.computerDatabase.model.UserCbd;
import com.excilys.computerDatabase.services.ComputerDatabaseAuthService;

@Controller
public class HomeController {

	private ComputerDatabaseAuthService serviceUser;

	
	public HomeController(ComputerDatabaseAuthService serviceUser) {
		this.serviceUser = serviceUser;
	}

	@GetMapping(value= {"","/accueil","/home"})
	public String home() {
		return "accueil";
	}
	
	
	@GetMapping(value = "/registerPage")
    public String register(Model model ) {
	   return "register";
    }
	
	@PostMapping(value = "/register")
    public String register(@ModelAttribute("newUser") UserCbdDTO newUserDto ) {
	  UserCbd userCreated=serviceUser.registerNewUserAccountUser(newUserDto) ;
	   return "loginPage";
    }
	
	@GetMapping(value = "/login")
    public String loginPage(Model model ) {
	      
	   return "loginPage";
    }
	
	@GetMapping(value="/logout")
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/logoutSuccessful";
	}
	
	@GetMapping(value = "/logoutSuccessful")
	public String logoutSuccessfulPage(Model model) {
	   model.addAttribute("title", "Logout");
	   return "logoutSuccessfulPage";
    }
	
	@GetMapping(value = "/403")
	   public String accessDenied(Model model, Principal principal) {
	        
	       if (principal != null) {
	           model.addAttribute("message", "Hi " + principal.getName()
	                   + "<br> You do not have permission to access this page!");
	       } else {
	           model.addAttribute("msg",
	                   "You do not have permission to access this page!");
	       }
	       return "403";
	   }
}
