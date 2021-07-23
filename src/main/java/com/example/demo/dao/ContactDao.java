package com.example.demo.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.demo.models.Contact;

@RepositoryRestResource
public interface ContactDao extends CrudRepository<Contact, Integer> {
	
	public Optional<Contact> findByPhoneNb(String phoneNb);

}
