package com.maveric.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maveric.model.LoginRequest;
import com.maveric.model.LoginResponse;
import com.maveric.service.JwtUserDetailsService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	
    @Autowired
	private JwtUserDetailsService  jwtUserDetailsService;
    
	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;
    
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest)
			throws Exception {

		jwtUserDetailsService.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		
		final String token =  jwtUserDetailsService.generateToken(userDetails);

		return ResponseEntity.ok(new LoginResponse(token));
	}

	
}
