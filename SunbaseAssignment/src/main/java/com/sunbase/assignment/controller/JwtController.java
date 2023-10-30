package com.sunbase.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunbase.assignment.helper.JwtUtilHelper;
import com.sunbase.assignment.model.JwtRequest;
import com.sunbase.assignment.model.JwtResponse;
import com.sunbase.assignment.model.User;
import com.sunbase.assignment.service.impl.CustomUserDetailsServiceImpl;

@RestController
@RequestMapping("/sunbase/portal/api/")
public class JwtController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailsServiceImpl customUserDetailsService;
	
	@Autowired
	private JwtUtilHelper jwtUtilHelper;

	@PostMapping("/assignment_auth")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		System.out.println("jwtRequest "+authenticationRequest);
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtUtilHelper.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> saveUser(@RequestBody User user) throws Exception {
		return ResponseEntity.ok(customUserDetailsService.save(user));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
