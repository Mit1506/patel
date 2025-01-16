package com.example.cafe.jwts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


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
	    
	    @Autowired
	    private JavaMailSender javaMailSender;

	    public void sendOtpEmail(String email, String otp) throws MessagingException {
	      MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
	      mimeMessageHelper.setTo(email);
	      mimeMessageHelper.setSubject("Verify OTP");
	      mimeMessageHelper.setText("""
	          <div>
	            <a href="http://localhost:8080/verify-account?email=%s&otp=%s" target="_blank">click link to verify</a>
	          </div>
	          """.formatted(email, otp), true);

	      javaMailSender.send(mimeMessage);
	    }
}