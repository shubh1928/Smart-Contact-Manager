package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//User GET dashboard page
	@GetMapping("/dashboard")
	public String userDashboard()
	{
		return "user/dashboard";
	}
	
	//User POST dashboard page
	@PostMapping("/dashboard")
	public String userPostDashboard()
	{
		return "user/dashboard";
	}
	
	//User GET profile page
	@GetMapping("/profile")
	public String userProfile(Model model, Authentication authentication)
	{
			
		return "user/profile";
			
	}
	
	//User POST profile page
	@PostMapping("/profile")
	public String userPOSTProfile(Model model, Authentication authentication)
	{
		
		return "user/profile";
		
	}
	
	@GetMapping("/feedback")
	public String userFeedback(Model model, HttpSession session) {
		
				
	    
	    return "user/feedback";
	}
	
	//User add contacts page
	
	//User view contacts page
	
	//User edit contacts page
	
	//User delete contacts page

}
