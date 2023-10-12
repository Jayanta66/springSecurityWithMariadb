package com.example.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
//@Qualifier
@Configuration
@EnableWebSecurity
public class MySecurityConfig {
	
	@Autowired
	UserDetailsService userDetailsService; 
	
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		}
	@Bean
	SecurityContextRepository securityContextRepository() {
		return new DelegatingSecurityContextRepository(new RequestAttributeSecurityContextRepository()
				,new HttpSessionSecurityContextRepository());
	}
	
	@Bean
	AuthenticationManager authManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		
		return new ProviderManager(provider);
		}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
		
			.requestMatchers("/","/login","/registration","/index","/registration","/","/log","/regis")
			.permitAll()
			.requestMatchers(HttpMethod.GET,"/","/log","/regis")
			.hasAnyRole("user","admin")
			.requestMatchers(HttpMethod.POST,"/login","/registration","/index","/registration")
			.hasAnyRole("user","admin")	
			.and().logout().logoutSuccessUrl("/")
			
			.and().csrf().disable();
		
	//	http.addFilterBefore(new MySecurityFilter(), BasicAuthenticationFilter.class);
		
		http.securityContext(securityContext->securityContext.requireExplicitSave(true));
		
		return http.build();
		}

}
