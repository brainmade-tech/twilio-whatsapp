package com.example.demo.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreUpdate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class User {
	
	@Id
	@GeneratedValue
	private int id;
	private String username;
	private String email;
	private String phoneNb;
	
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Contact> contacts;
	
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<UserContactMessage> messages;
	
	public User(String phoneNb) {
		this.phoneNb = phoneNb;
	}
	
	public User(String username, String email) {
		this.username = username;
		this.email = email;
	}
	
	public User(String username, String email, String phoneNb) {
		this(username,email);
		this.phoneNb = phoneNb;
	}
	
	//@PrePersist
	@PreUpdate
	public void populateContacts() {
		if (contacts != null && !contacts.isEmpty())
			contacts.forEach(contact -> contact.setUser(this));
	}
	
}
