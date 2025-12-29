package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.forms.UserForm;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.helper.SessionHelper;
import com.scm.services.EmailService;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {

	@Autowired
	private UserService userService;
	
    @Autowired
    private EmailService emailService;

	//Default page
	@GetMapping("/")
	public String index()
	{
		return "redirect:/home";
	}
	
	//Home page
	@GetMapping("/home")
	public String home()
	{
		return "home";
	}
	
	//About page
	@GetMapping("/about")
	public String about()
	{
		return "about";
	}
	
	//Services page
	@GetMapping("/services")
	public String services()
	{
		return "services";
	}
	
	//Login page
	@GetMapping("/login")
	public String login()
	{
		return "login";
	}
	
	//Contact page
	@GetMapping("/contact")
	public String contact()
	{
		return "contact";
	}
	
	//Register page
	@GetMapping("/register")
	public String register(Model model)
	{
		UserForm userForm = new UserForm();
		
		model.addAttribute("userForm", userForm);
		
		return "register";
	}
	
	//Processing register
	@PostMapping("/do-register")
	public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult, HttpSession session)
	{
		
		if (rBindingResult.hasErrors()) {
			return "register";
		}
		
		User user = new User();
		
		user.setName(userForm.getName());
		user.setEmail(userForm.getEmail());
		user.setPassword(userForm.getPassword());
		user.setPhoneNumber(userForm.getPhoneNumber());
		user.setAbout(userForm.getAbout());
		user.setEnabled(false);
		user.setProfilePic("https://static.vecteezy.com/system/resources/thumbnails/009/734/564/small_2x/default-avatar-profile-icon-of-social-media-user-vector.jpg");
		
		User savedUser = userService.saveUser(user);
		
		Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();
		
		session.setAttribute("message", message);
		
		return "redirect:/register";
	}
	
	@PostMapping("/contact/send")
	public String sendContactEmail(@ModelAttribute ContactForm contactForm, HttpSession session)
	{
		
		try 
		{
			
			String subject = "New Contact Message from " + contactForm.getName();
			String body = "Name: " + contactForm.getName() + "\n"
					    + "Email: " + contactForm.getEmail() + "\n"
					    + "Message: " + contactForm.getDescription();
			
			emailService.sendEmail("shubhamgopale16@gmail.com", subject, body);
			
			session.setAttribute("message", Message.builder()
					   							   .type(MessageType.green)
					   							   .content("Your message has been sent successfully!")
					   							   .build());
						
		} catch (Exception e) {
		
			session.setAttribute("message", Message.builder()
												   .type(MessageType.red)
												   .content("Failed to send message. Please try again later.")
												   .build());
			
		}
		
		return "redirect:/home";
		
	}


}
