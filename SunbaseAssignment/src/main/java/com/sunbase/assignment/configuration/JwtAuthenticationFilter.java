package com.sunbase.assignment.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sunbase.assignment.helper.JwtUtilHelper;
import com.sunbase.assignment.service.impl.CustomUserDetailsServiceImpl;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	@Lazy
	private CustomUserDetailsServiceImpl customUserDetailsService;
	
	@Autowired
	private JwtUtilHelper jwtUtilHelper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String requestTokenHeader = request.getHeader("Authorization");
		String username=null;
		String jwtToken=null;
		
		if (requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ") ) {
			jwtToken=requestTokenHeader.substring(7);
			
			try {
				username = jwtUtilHelper.extractUsername(jwtToken);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
			//security
			
			if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			
			
			}else {
				System.out.println("Token is not validated");
			}
			
		}
		filterChain.doFilter(request, response);
		
	}
}
