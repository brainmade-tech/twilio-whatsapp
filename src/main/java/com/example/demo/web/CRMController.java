package com.example.demo.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Contact;
import com.example.demo.models.User;
import com.example.demo.models.UserContactMessage;
import com.example.demo.service.CRMService;

//@RepositoryRestController
@RestController
public class CRMController {
	
	@Autowired
	private CRMService service;
	
	@PostMapping("/send-message")  
	public ResponseEntity<?> sendMessage(@RequestBody UserContactMessage msg) {
		String status = service.sendMessage(msg);
		return ResponseEntity.ok(status);
	}
	
	@PostMapping(value = "/whatsapp-message", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> processReceivedMessage(@RequestParam Map<String, String> params) {
		
		String from = params.get("From").split("whatsapp:")[1];
		String to = params.get("To").split("whatsapp:")[1];
		String body = params.get("Body");
		
		UserContactMessage message = new UserContactMessage();
		message.setContact(new Contact(from));
		message.setUser(new User(to));
		message.setText(body);
		
		service.processIncomingMessage(message);
		
		return ResponseEntity.ok(null);
	}
	
}
