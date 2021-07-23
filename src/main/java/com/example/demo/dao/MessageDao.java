package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.demo.models.UserContactMessage;

@RepositoryRestResource
public interface MessageDao extends CrudRepository<UserContactMessage, Integer> {

}
