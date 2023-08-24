package com.wellsfargo.training.pms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wellsfargo.training.pms.exception.ResourceNotFoundException;
import com.wellsfargo.training.pms.model.Address;
import com.wellsfargo.training.pms.model.Dealer;
import com.wellsfargo.training.pms.service.DealerService;

// Controller for Registration and Login process of Dealer
@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping(value="/api")
public class DealerController {
	
	@Autowired
	private DealerService dservice;
	
	   /* ResponseEntity represents an HTTP response, including headers, body, and status. */
	
	 	@PostMapping("/register")
		public ResponseEntity<String> createDealer(@Validated @RequestBody Dealer dealer) {
			
			Address address = dealer.getAddress();
			
			 // Establish the bi-directional relationship
	        address.setDealer(dealer);
	        dealer.setAddress(address);
					
			Dealer registeredDealer = dservice.registerDealer(dealer);
		        if (registeredDealer!= null) {
		            return ResponseEntity.ok("Registration successful");
		        } else {
		            return ResponseEntity.badRequest().body("Registration failed");
		        }
		}
	 
	//Open Postman and make POST request - http://localhost:8085/pms/api/login
			@PostMapping("/login")
			public Boolean loginDealer(@Validated @RequestBody Dealer dealer) throws ResourceNotFoundException
			{
				Boolean a=false;
				String email=dealer.getEmail();
				String password=dealer.getPassword();
			
				Dealer d = dservice.loginDealer(email).orElseThrow(() ->
				new ResourceNotFoundException("Dealer not found for this id :: "));
				
				if(email.equals(d.getEmail()) && password.equals(d.getPassword()))
				{
					a=true;

				}
				return a;
			}
}


