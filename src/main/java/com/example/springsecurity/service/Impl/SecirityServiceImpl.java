package com.example.springsecurity.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import com.example.springsecurity.bean.User;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.service.SecurityService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class SecirityServiceImpl implements SecurityService {

	@Autowired
	UserDetailsService  userDetailsService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	SecurityContextRepository securityContextRepo;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public boolean longin(String username, String password,HttpServletRequest request, HttpServletResponse response) {
	UserDetails userDetails =	userDetailsService.loadUserByUsername(username);
	UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

		authenticationManager.authenticate(token);
		boolean result = token.isAuthenticated();
		if(result) {
			SecurityContext context =SecurityContextHolder.getContext();
			context.setAuthentication(token);
			securityContextRepo.saveContext(context, request, response);
		}
		
		return result;
	}

	@Override
	public User save(User user) {
		userRepository.save(user);
		return user;
	}

}
