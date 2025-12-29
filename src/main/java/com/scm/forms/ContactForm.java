package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import com.scm.validators.ValidFile;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactForm {
	
	@NotBlank(message = "Name not should be blank!!!")
	private String name;
	
	@Email(message = "Invalid Email Address!!!")
	@NotBlank(message = "Email not should be blank!!!")
	private String email;
	
	@NotBlank(message =  "Phone number not should be blank!!!")
	@Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone Number!!!")
	private String phoneNumber;
	
	@NotBlank(message = "Address is required!!!")
	private String address;

	private String description;
	
	private boolean favorite;
	
	private String websiteLink;
	
	private String linkedinLink;
	
	@ValidFile(message = "Invalid File")
	private MultipartFile contactImage;
	
	private String picture;

}
