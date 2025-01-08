package com.example.cafe.Controllers;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cafe.dtos.AuthenticationRequest;
import com.example.cafe.dtos.AuthenticationResponse;
import com.example.cafe.dtos.SignupRequest;
import com.example.cafe.dtos.Userdto;
import com.example.cafe.entities.User;
import com.example.cafe.jwt.UserDetailsServiceImpl;
import com.example.cafe.jwts.JwtUtils;
import com.example.cafe.repositery.UserRepository;
import com.example.cafe.services.UserService;

import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api")
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
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {
		
	Userdto createdUserdto=	userService.createUser(signupRequest);
	
	if(createdUserdto==null) {
		return new ResponseEntity<>("User not created.",HttpStatus.BAD_REQUEST);
	}
		return new ResponseEntity<>(createdUserdto,HttpStatus.CREATED);
		
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
}
