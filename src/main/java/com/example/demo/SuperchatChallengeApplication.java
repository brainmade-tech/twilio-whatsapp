package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.dao.ContactDao;
import com.example.demo.dao.UserDao;
import com.example.demo.models.Contact;
import com.example.demo.models.User;

@SpringBootApplication
public class SuperchatChallengeApplication implements ApplicationRunner {

	@Autowired private UserDao userDao;
	@Autowired private ContactDao contactDao;
	
	public static void main(String[] args) {
		SpringApplication.run(SuperchatChallengeApplication.class, args);
	}

	public void run(ApplicationArguments args) throws Exception {
		// replace "phone_number" by your TWILIO WHATSAPP number
		User admin = userDao.save(new User("admin", "admin@superchat.de", "phone_number"));
		// replace "phone_number" by your customers' numbers
		contactDao.save(new Contact("yassine","chaoui","yassinechaoui95@gmail.com","PHONE_NUMBER","Morocco", admin));
		contactDao.save(new Contact("smith","will","smith@gmail.com","PHONE_NUMBER","Germany", admin));
		
	}
}
