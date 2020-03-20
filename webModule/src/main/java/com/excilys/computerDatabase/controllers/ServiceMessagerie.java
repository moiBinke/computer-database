package com.excilys.computerDatabase.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceMessagerie {
	
	@CrossOrigin("*")
	@PostMapping("/envoieMail")
	public void postController(
	  @RequestBody CourierElectronique courier) {
		courier.envoyer();
	}
}
