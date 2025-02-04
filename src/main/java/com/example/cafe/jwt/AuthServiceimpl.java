package com.example.cafe.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.cafe.repositery.UserRepository;

@Service

public class AuthServiceimpl implements AuthService {
	
	@Autowired
	UserRepository userRepository;
    @Override
    public UserDetailsService userDetailsService() {
       return new UserDetailsService() {
           @Override
           public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
               return userRepository.findFirstByEmail(username)
                       .orElseThrow(() -> new UsernameNotFoundException("User not Found"));
           }
       };
    }
}