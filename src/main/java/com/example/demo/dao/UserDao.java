package com.example.demo.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.demo.models.User;

@RepositoryRestResource
public interface UserDao extends CrudRepository<User, Integer>{

	public Optional<User> findByPhoneNb(String phoneNb);
	
}
