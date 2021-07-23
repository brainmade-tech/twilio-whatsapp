package com.example.demo.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Contact {

	@Id @GeneratedValue
	private int id;
	private String firstname;
	private String lastname;
	private String email;
	private String phoneNb;
	private String address;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy="contact", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<UserContactMessage> messages;
	
	public Contact(String phoneNb) {
		this.phoneNb = phoneNb;
	}
	
	public Contact(String firstname, String lastname, String email, String phoneNb, String address, User user) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.phoneNb = phoneNb;
		this.address = address;
		this.user = user;
	}
	
}
