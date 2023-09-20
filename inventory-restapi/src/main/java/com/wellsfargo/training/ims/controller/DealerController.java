package com.wellsfargo.training.ims.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wellsfargo.training.ims.exception.ResourceNotFoundException;
import com.wellsfargo.training.ims.model.Address;
import com.wellsfargo.training.ims.model.Dealer;
import com.wellsfargo.training.ims.model.DealerAndAddressProjection;
import com.wellsfargo.training.ims.service.DealerService;

/* Spring MVC provides @CrossOrigin annotation that marks the annotated method or type as permitting cross-origin requests.
The CORS (Cross-Origin Resource Sharing) allows a webpage to request additional resources into the browser from other domains
such as API data using AJAX, font files, style sheets etc. */


// Controller for Registration and Login process of Dealer
@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping(value="/api")
public class DealerController {
	
	@Autowired //DI
	private DealerService dservice;

	/* ResponseEntity represents an HTTP response, including headers, body, and status. */
	
	@PostMapping("/register")
	public ResponseEntity<String> createDealer(@Validated @RequestBody Dealer dealer) {
		try {
			Address address = dealer.getAddress();
			
			// Establish the bidirectional 1-1 Mapping
			address.setDealer(dealer);;
			dealer.setAddress(address);
			
			Dealer registerDealer = dservice.registerDealer(dealer); // invoke service layer method
			if (registerDealer != null) {
				return ResponseEntity.ok("Registration Successful");
			}
			else {
				return ResponseEntity.badRequest().body("Registration Failed");
			}
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An Error Ocurred: "+e.getMessage());
		}
	}
	
	@PostMapping("/login")
	public Boolean loginDealer(@Validated @RequestBody Dealer d)
			throws ResourceNotFoundException {
		
		Boolean isLoggedIn = false;
		
		String email = d.getEmail();
		String password = d.getPassword();
		
		Dealer dealer = dservice.loginDealer(email).orElseThrow(() ->
			new ResourceNotFoundException("Dealer Not Found For This Email"));
		
		if(email.equals(dealer.getEmail()) && password.equals(dealer.getPassword())) {
			isLoggedIn = true;	
		}
		return isLoggedIn;		
	}
	
	@GetMapping("/dealers")
	public ResponseEntity<List<DealerAndAddressProjection>> getDealerInfo() {
		try {
			List<DealerAndAddressProjection> selectedFields = dservice.getDealerInfo();
			return ResponseEntity.ok(selectedFields);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}
	}
}
