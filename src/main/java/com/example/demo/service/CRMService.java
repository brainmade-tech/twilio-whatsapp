package com.example.demo.service;

import com.example.demo.models.UserContactMessage;

public interface CRMService {
	
	public String sendMessage(UserContactMessage message);
	public void processIncomingMessage(UserContactMessage message);

}
