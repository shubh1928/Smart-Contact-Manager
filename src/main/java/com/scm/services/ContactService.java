package com.scm.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.scm.entities.Contact;
import com.scm.entities.User;

public interface ContactService {
	
	//Save contact
	Contact save(Contact contact);
	
	//Update contact
	Contact update(Contact contact);
	
	//Get contact
	List<Contact> getAll();
	
	//Get contact by id 
	Contact getById(String id);
	
	//Delete contact
	void delete(String id);
	
	//Search contact by name, email and phonenumber
	Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user);
	Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user);
	Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order, User user);
	
	//get contact by userid 
	List<Contact> getbyUserId(String userId);
	
	//To retrive the specific user's contacts
	Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction);

}
