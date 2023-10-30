package com.sunbase.assignment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sunbase.assignment.dao.UserRepository;
import com.sunbase.assignment.model.User;


@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				
	User userByUserName = userRepo.findByUsername(username);
		if(userByUserName==null) {
			throw new UsernameNotFoundException("Could not found user !!");
		}
		
		CustomUserDetailsimpl customUserDetails=new CustomUserDetailsimpl(userByUserName);
		return customUserDetails;
	}
	
	public User save(User user) {
		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setRole(user.getRole());
		newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepo.save(newUser);
	}

}
