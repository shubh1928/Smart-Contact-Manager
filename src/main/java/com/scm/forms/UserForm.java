package com.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {

	@NotBlank(message = "Username is required")
	@Size(min = 2, message = "Min 2 Characters are required")
	private String name;
	
	@Email(message = "Invalid Email Address")
	@NotBlank(message = "Email is required")
	private String email;
	
	@NotBlank(message = "Passwrod is required")
	@Size(min = 6, message = "Min 6 Characters are required")
	private String password;
	
	@NotBlank(message = "Phone Number is required")
	@Size(min = 8, max = 12, message = "Invalid Phone Number")
	private String phoneNumber;
	
	@NotBlank(message = "About is required")
	private String about;	
	
}
