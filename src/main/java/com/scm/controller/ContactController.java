package com.scm.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.forms.ContactSearchForm;
import com.scm.helper.AppConstants;
import com.scm.helper.Helper;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.repositories.ContactRepo;
import com.scm.services.ContactService;
import com.scm.services.EmailService;
import com.scm.services.ImageService;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

	@Autowired
    private final ContactRepo contactRepo;
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private  ImageService imageService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;

    ContactController(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }
	
	@GetMapping("/add")
	public String addContactView(Model model)
	{
		ContactForm contactForm = new ContactForm();
	
		model.addAttribute("contactForm", contactForm);
		
		return "user/add_contact";
	}
	
	
	@PostMapping("/add")
	public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult bindingResult, Authentication authentication, HttpSession session)
	{
		
		if (bindingResult.hasErrors()) 
		{
			session.setAttribute("message", Message.builder()
												   .content("Please correct the following errors!")
												   .type(MessageType.red)
												   .build());
			
			return "user/add_contact";
		}
		
		String username = Helper.getEmailOfLoggedInUser(authentication);
		
		User user = userService.getUserByEmail(username);

		Contact contact = new Contact();
		
		contact.setName(contactForm.getName());
		contact.setEmail(contactForm.getEmail());
		contact.setDescription(contactForm.getDescription());
		contact.setAddress(contactForm.getAddress());
		contact.setPhoneNumber(contactForm.getPhoneNumber());
		contact.setWebsiteLink(contactForm.getWebsiteLink());
		contact.setLinkedinLink(contactForm.getLinkedinLink());
		contact.setFavorite(contactForm.isFavorite());
		contact.setUser(user);
		
		if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
			
			String filename = UUID.randomUUID().toString();
			//code to upload the image -->
			String fileURL = imageService.uploadImage(contactForm.getContactImage(), filename);
			
			contact.setPicture(fileURL);
			contact.setCloudinaryImagePublicId(filename);
			
		}
		
		contactService.save(contact);
		
		session.setAttribute("message", Message.builder()
											   .content("You have successfully added a new contact :)")
											   .type(MessageType.green)
											   .build());
		
		return "redirect:/user/contacts/add";
	}
	
	//View Contacts
	@GetMapping
	public String viewContacts(@RequestParam(value = "page", defaultValue = "0") int page,
							   @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE+"") int size,
							   @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
							   @RequestParam(value = "direction", defaultValue = "asc") String direction,
							   @RequestParam(defaultValue = "contacts") String type,
							   Model model, Authentication authentication)
	{
		
		String username = Helper.getEmailOfLoggedInUser(authentication);
		
		User user = userService.getUserByEmail(username);
		
		Page<Contact> pageContact = contactService.getByUser(user, page, size, sortBy, direction);
		
		model.addAttribute("pageContact", pageContact);
		
		model.addAttribute("pageSize",  AppConstants.PAGE_SIZE);
		
		model.addAttribute("contactSearchForm", new ContactSearchForm());
		
		
		if(type.equals("contacts"))
		{
			return "user/contacts";

		}
		else if (type.equals("direct-message")) {

			return "user/direct_message";
		}
		
	return null;
	}
	
	//User wise contact Search and Sort
	@GetMapping("/search")
	public String searchHandler(@ModelAttribute ContactSearchForm contactSearchForm,
								@RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
								@RequestParam(value = "page", defaultValue = "0") int page,
								@RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
								@RequestParam(value = "direction", defaultValue = "asc") String direction,
								@RequestParam(value = "type", defaultValue = "contacts") String type,
								Model model,
								Authentication authentication)
	{
		
		var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));
		
		Page<Contact> pageContact = null;
		
		if (contactSearchForm.getField().equalsIgnoreCase("name")) {
			
			pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction, user);
			
		}
		else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
			
			pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction, user);
			
		} 
		else if (contactSearchForm.getField().equalsIgnoreCase("phone")){
			
			pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy, direction, user);

		}
		
		model.addAttribute("contactSearchForm", contactSearchForm);
		
		model.addAttribute("pageContact", pageContact);
		
		model.addAttribute("pageSize",  AppConstants.PAGE_SIZE);
		
		model.addAttribute("type", type);
		
		return "user/search";
	}
	
	//To delete the contact of specific user
	@GetMapping("/delete/{contactId}")
	public String deleteContact(@PathVariable String contactId, HttpSession httpSession) 
	{
		
		contactService.delete(contactId);
		
		httpSession.setAttribute("message", Message.builder()
													.content("Contact is Deleted Successfully!!")
													.type(MessageType.green)
													.build());
		
		return "redirect:/user/contacts";
	}
	
	
	//Update contact form view
	@GetMapping("/view/{contactId}")
	public String updateContactFormView(@PathVariable("contactId") String contactId,
										Model model)
	{
		
		var contact = contactService.getById(contactId);
		
		ContactForm contactForm = new ContactForm();
		
		contactForm.setName(contact.getName());
		contactForm.setEmail(contact.getEmail());
		contactForm.setPhoneNumber(contact.getPhoneNumber());
		contactForm.setPhoneNumber(contact.getPhoneNumber());
		contactForm.setAddress(contact.getAddress());
		contactForm.setDescription(contact.getDescription());
		contactForm.setPicture(contact.getPicture());
		contactForm.setFavorite(contact.isFavorite());
		contactForm.setWebsiteLink(contact.getWebsiteLink());
		contactForm.setLinkedinLink(contact.getLinkedinLink());
		
		model.addAttribute("contactForm", contactForm);
		model.addAttribute("contactId", contactId);
		
		return "user/update_contact_view";
		
	}
	
	//Update Handler
	@PostMapping("/update/{contactId}")
	public String updateContact(@PathVariable String contactId,
								@Valid @ModelAttribute ContactForm contactForm,
								BindingResult bindingResult,
								Model model)
	{
		
		if (bindingResult.hasErrors()) {
			
			return "user/update_contact_view";
			
		}
		
		var con = contactService.getById(contactId);
		
		con.setId(contactId);
		con.setName(contactForm.getName());
		con.setEmail(contactForm.getEmail());
		con.setPhoneNumber(contactForm.getPhoneNumber());
		con.setAddress(contactForm.getAddress());
		con.setDescription(contactForm.getDescription());
		con.setFavorite(contactForm.isFavorite());
		con.setWebsiteLink(contactForm.getWebsiteLink());
		con.setLinkedinLink(contactForm.getLinkedinLink());
		
		if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
			
			String fileName = UUID.randomUUID().toString();
			String imageUrl = imageService.uploadImage(contactForm.getContactImage(), fileName);
			
			con.setCloudinaryImagePublicId(fileName);
			con.setPicture(imageUrl);
			
			contactForm.setPicture(imageUrl);
			
			con.setPicture(contactForm.getPicture());
			
		}
		
		var updateCon = contactService.update(con);
		
		model.addAttribute("message", Message.builder().content("Contact Updated :)").type(MessageType.green).build());
		
		return "redirect:/user/contacts/view/" + contactId;
		
	}
	
	@PostMapping("/feedback")
	public String submitFeedback(@ModelAttribute ContactForm contactForm, HttpSession session)
	{
		
		try 
		{
			
			String subject = "New Feedback from " + contactForm.getName();
			String body = "Name: " + contactForm.getName() + "\n"
					    + "Email: " + contactForm.getEmail() + "\n"
					    + "Feedback: " + contactForm.getDescription();
			
			emailService.sendEmail("shubhamgopale16@gmail.com", subject, body);
			
			session.setAttribute("message", Message.builder()
					   							   .type(MessageType.green)
					   							   .content("Your feedback has been sent successfully!")
					   							   .build());
						
		} catch (Exception e) {
		
			session.setAttribute("message", Message.builder()
												   .type(MessageType.red)
												   .content("Failed to send feedback. Please try again later.")
												   .build());
			
		}
		
		System.out.println("Message in session: " + session.getAttribute("message"));

		
		return "redirect:/user/feedback";
		
	}
    
    
    
	
}
