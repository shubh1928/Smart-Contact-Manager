package com.scm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scm.services.EmailService;

@SpringBootTest
class ScmApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
	private EmailService emailService;
	
	@Test
	void sendEmailTest() 
	{
		System.out.println("heyyyyy");
		emailService.sendEmail("shubhamgopale16@gmail.com", 
							   "This is testing email", 
							   "This is testing email bodyyyyy :) ");
	}

}
