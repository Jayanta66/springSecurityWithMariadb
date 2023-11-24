package com.example.springsecurity.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.springsecurity.bean.User;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.service.SecurityService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class MainController {

	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private UserRepository userRepository;
		
	@Autowired
	private PasswordEncoder encoder;
	
	@GetMapping({"/login"})
	public String home() {
		return "login";
		}
	
//	@GetMapping({"/logout"})
//	public String logoutt() {
//		return "/registration";
//		}
//	
//	@PostMapping({"/logout"})
//	public String logout() {
//		return "/registration";
//		}
	
	@PostMapping("/login")
	public String longinresponse(String username, String password,HttpServletRequest request, HttpServletResponse response) {
		boolean loginResponse = securityService.longin(username, password,request, response);

		if(loginResponse) {
			return "home";
		}
		return "login";
		
	}
	
	@GetMapping("/index")
	public String index() {
		return "index";
		}
	
	@GetMapping("/registration")
	public String registration() {
		return "registration";
		}
	
	@PostMapping("/registration")
	public String register(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);
		return "login";
		}
	
//    @GetMapping("/index")
//    public String add(Model model) {
//    	List<User> listuser = securityService.save(user);
//        model.addAttribute("user", new User());
//        return "index";
//    }
}
