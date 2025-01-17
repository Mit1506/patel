package com.example.cafe.Controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.cafe.dtos.AuthenticationRequest;
import com.example.cafe.dtos.AuthenticationResponse;
import com.example.cafe.dtos.ForgotPasswordRequest;
import com.example.cafe.dtos.ResetPasswordRequest;
import com.example.cafe.dtos.VerifyOtpRequest;
import com.example.cafe.entities.OtpEntity;
import com.example.cafe.entities.User;
import com.example.cafe.jwt.UserDetailsServiceImpl;
import com.example.cafe.jwts.EmailUtils;
import com.example.cafe.jwts.JwtUtils;
import com.example.cafe.repositery.UserRepository;
import com.example.cafe.repositery.otprepository;
import com.example.cafe.services.UserService;
import com.example.cafe.servicesimpl.UserServiceImpl;

import jakarta.servlet.http.HttpServletResponse;






@RestController

@RequestMapping("/api")
@CrossOrigin("*")

public class UserController {

	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	 AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	otprepository otprepository;
	
	@Autowired
     JavaMailSender mailSender;
	
	@Autowired
	EmailUtils emailUtils;
	
	@Autowired
	UserServiceImpl impl;
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> signupUser(@RequestBody User user) throws Exception {
		
	User createdUserdto=userService.createUser(user);
	
	if(createdUserdto==null) {
		return new ResponseEntity<>("User not created.",HttpStatus.BAD_REQUEST);
	}
		return new ResponseEntity<>(createdUserdto,HttpStatus.CREATED);
		
	}
	@PostMapping("/verify")
	public ResponseEntity<String> verifyEmail(@RequestBody Map<String, String> request) {
	    String code = request.get("code");  // Extract OTP code from the request body

	    
	    // Fetch the OTP entity from the database by OTP code 
	    User user = userRepository.findByOtp(code);  // Assuming you have a method to find OTP by code
	    
	    // Check if the provided OTP matches the stored OTP
	    if (!user.getOtp().equals(code)) {
	        return ResponseEntity.badRequest().body("Incorrect OTP code!");
	    }

	    // If everything is correct
	    return ResponseEntity.ok("OTP verified successfully!");
	}

	
	
	
	
	 @PostMapping("/login")
	    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws IOException {
	        try{
	            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));
	        } catch (BadCredentialsException e){
	            throw new BadCredentialsException("Incorrect Username or Password");

	        }catch( DisabledException disabledException){
	            response.sendError(HttpServletResponse.SC_NOT_FOUND,"User not active");
	            return null;
	        }
	        final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(authenticationRequest.getEmail());
	        final String jwt = jwtUtils.genreteToken(userDetails.getUsername());
	        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
	        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
	        if(optionalUser.isPresent()){
	            authenticationResponse.setJwt(jwt);
	            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
	            authenticationResponse.setUserId(optionalUser.get().getId());
	        }
	        return authenticationResponse;
	    }
	 
	 @PostMapping("/forgot-password")
	 public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
	     // Find the user by email
	     User user = userRepository.findFirstByEmail(request.getEmail())
	         .orElseThrow(() -> new RuntimeException("User  not found"));

	     // Generate a new 6-digit OTP
	     String otp = String.format("%06d", new Random().nextInt(999999));

	     // Check if an OTP already exists for this user
	     Optional<OtpEntity> existingOtpEntity = otprepository.findByEmail(user.getEmail());

	     if (existingOtpEntity.isPresent()) {
	         // Update the existing OTP and expiration time
	         OtpEntity otpEntity = existingOtpEntity.get();
	         otpEntity.setOtp(otp);
	         otpEntity.setExpirationTime(LocalDateTime.now().plusMinutes(5));
	         otprepository.save(otpEntity);
	     } else {
	         // Create a new OTP entry
	         OtpEntity otpEntity = new OtpEntity(user.getEmail(), otp, LocalDateTime.now().plusMinutes(5));
	         otprepository.save(otpEntity);
	     }

	     // Send OTP via email
	     SimpleMailMessage message = new SimpleMailMessage();
	     message.setTo(user.getEmail());
	     message.setSubject("Password Reset OTP");
	     message.setText("Your OTP is: " + otp + " (valid for 5 minutes)");
	     mailSender.send(message);

	     return ResponseEntity.ok("OTP sent to your email");
	 }
	 
	 @PostMapping("/verify-otp")
	 public ResponseEntity<String> verifyOtp(@RequestBody VerifyOtpRequest request) {
	     // Fetch OTP entity from the database using OTP code
	     OtpEntity otpEntity = otprepository.findByOtp(request.getOtp())
	             .orElseThrow(() -> new RuntimeException("Invalid OTP"));

	     // Check if the OTP has expired
	     if (otpEntity.getExpirationTime().isBefore(LocalDateTime.now())) {
	         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP expired");
	     }

	     return ResponseEntity.ok("OTP verified successfully");
	 }

	 
	 @PostMapping("/reset-password")
	 public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
	     User user = userRepository.findFirstByEmail(request.getEmail())
	         .orElseThrow(() -> new RuntimeException("User not found"));

	     // Update password (encrypt using BCryptPasswordEncoder)
	     user.setPassword(new BCryptPasswordEncoder().encode(request.getNewPassword()));
	     userRepository.save(user);

	     return ResponseEntity.ok("Password reset successfully");
	 }

}
