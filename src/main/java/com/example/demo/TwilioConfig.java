package com.example.demo;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.twilio.Twilio;

import lombok.Getter;

@Component
public class TwilioConfig {
	
	private Logger logger = LoggerFactory.getLogger(TwilioConfig.class);
	
	@Value("${account.sid}")
	private String accountSID;
	
	@Value("${auth.token}")
	private String authToken;
	
	@Value("${phone.number}")
	@Getter
	private String phoneNb;
	
	@PostConstruct
	public void authenticate() {
		Twilio.init(accountSID, authToken);
		logger.info("Twilio API authentication has been done successfully...");
	}
	
}
