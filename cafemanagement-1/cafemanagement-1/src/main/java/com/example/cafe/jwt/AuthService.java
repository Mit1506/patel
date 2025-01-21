package com.example.cafe.jwt;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService {
	
	 UserDetailsService userDetailsService();

}
