package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.TwilioConfig;
import com.example.demo.dao.ContactDao;
import com.example.demo.dao.MessageDao;
import com.example.demo.dao.UserDao;
import com.example.demo.models.Contact;
import com.example.demo.models.MessageState;
import com.example.demo.models.User;
import com.example.demo.models.UserContactMessage;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class WhatsappCRMService implements CRMService {

	@Autowired private MessageDao messageDao;
	@Autowired private ContactDao contactDao;
	@Autowired private UserDao userDao;
	@Autowired private TwilioConfig config;
	private Logger logger = LoggerFactory.getLogger(WhatsappCRMService.class);

	public String sendMessage(UserContactMessage message) {
		Contact contact = contactDao.findById(message.getContact().getId()).get();
		message.setContact(contact);
		
		replacePlaceHolder(message);
		String status = send(message).getStatus().toString();
		
		message.setState(MessageState.SENT);
		messageDao.save(message);
		
		logger.info("Sent message status : " + status);
		logger.info("Channel[Whatsapp] - From[" + config.getPhoneNb() + "] - To[" + contact.getPhoneNb() + "] - Text[" + message.getText() +"]");
		return status;
	}

	public void processIncomingMessage(UserContactMessage message) {
		String userPhoneNb = message.getUser().getPhoneNb();
		String contactPhoneNb = message.getContact().getPhoneNb();

		Contact contact = contactDao.findByPhoneNb(contactPhoneNb).get();
		User user = userDao.findByPhoneNb(userPhoneNb).get();

		message.setUser(user);
		message.setContact(contact);
		message.setState(MessageState.RECEIVED);

		messageDao.save(message);
		logger.info("Incoming Message...");
		logger.info("Channel[Whatsapp] - From[" + contactPhoneNb + "] - To[" + userPhoneNb + "] - Text[" + message.getText() +"]");
	}

	private void replacePlaceHolder(UserContactMessage message) {
		String body = message.getText();
		
		if (body.contains(Placeholder.CONTACT_PLACEHOLDER))
			body = body.replace(Placeholder.CONTACT_PLACEHOLDER, message.getContact().getFirstname());
		
		if (body.contains(Placeholder.BITCOIN_PLACEHOLDER))
			body = body.replace(Placeholder.BITCOIN_PLACEHOLDER, Placeholder.BITCOIN_VALUE);
		
		if (!body.equals(message.getText()))
			message.setText(body);
	}
	

	private Message send(UserContactMessage message) {
		PhoneNumber from = new PhoneNumber(Placeholder.WHATSAPP + config.getPhoneNb());
		PhoneNumber to = new PhoneNumber(Placeholder.WHATSAPP + message.getContact().getPhoneNb());
		return Message.creator(to,from,message.getText()).create();// sending message to TWILIO
	}
	
	
	private class Placeholder {
		private static final String WHATSAPP = "whatsapp:";
		private static final String CONTACT_PLACEHOLDER = "{contact-name}";
		private static final String BITCOIN_PLACEHOLDER = "{bitcoin-value}";
		private static final String BITCOIN_VALUE = "$32334.90";
	}
}
