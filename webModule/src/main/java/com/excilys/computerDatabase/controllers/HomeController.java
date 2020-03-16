package com.excilys.computerDatabase.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.excilys.computerDatabase.dto.ComputerDTO;
import com.excilys.computerDatabase.dto.UserCbdDTO;
import com.excilys.computerDatabase.model.UserCbd;
import com.excilys.computerDatabase.services.ComputerDatabaseAuthService;

@RestController
public class HomeController {

	private ComputerDatabaseAuthService serviceUser;

	
	public HomeController(ComputerDatabaseAuthService serviceUser) {
		this.serviceUser = serviceUser;
	}

	@CrossOrigin("*")
	@GetMapping(value= {"","/accueil","/home"})
	public String home() {
		return "accueil";
	}
	
	
	@GetMapping(value = "/registerPage")
    public String register(Model model ) {
	   return "register";
    }
	
	
	
	@CrossOrigin("*")
	@PostMapping(value = "/login")
    public  ResponseEntity<UserCbd> loginPage(@RequestBody UserCbd user ) throws IOException {
		user=serviceUser.getUser(user);
		return new ResponseEntity<UserCbd>(user,HttpStatus.OK);
    }
//	@PostMapping("/ggg")
//	public ResponseEntity<Object> ggg(@RequestBody Object o ) {
//		RestTemplate restTemplate = new RestTemplate();
//		 
//		HttpEntity<Object> request = new HttpEntity<>(o);
//		Object o2= restTemplate.patchForObject("http://10.0.1.154:8080/webModule/editComputer", request, Object.class);
//		System.out.println(o2);
//		return new ResponseEntity<Object>(o2, HttpStatus.OK); 
//	}
	
	@CrossOrigin("*")
	@GetMapping(value="/logout")
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/logoutSuccessful";
	}
	
	@CrossOrigin("*")
	@GetMapping(value = "/logoutSuccessful")
	public String logoutSuccessfulPage(Model model) {
	   model.addAttribute("title", "Logout");
	   return "logoutSuccessfulPage";
    }
	
	@CrossOrigin("*")
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
