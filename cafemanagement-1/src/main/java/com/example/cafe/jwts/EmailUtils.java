package com.example.cafe.jwts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;




@Component
public class EmailUtils {

	 @Autowired
	    private JavaMailSender mailSender;

	    public void sendMail(String to, String subject, String body) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(to);
	        message.setSubject(subject);
	        message.setText(body);
	        mailSender.send(message);
	    }

	   
	    public void sendVerificationEmail(String recipientEmail, String otp) {
	        String subject = "Verify your email address";
	        String content = "Your OTP is: " + otp + " (valid for 5 minutes)";

	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(recipientEmail);
	        message.setSubject(subject);
	        message.setText(content);

	        mailSender.send(message);
	    }
	    
	   

	    
}