package com.example.cafe.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cafe.dtos.SignupRequest;
import com.example.cafe.dtos.Userdto;
import com.example.cafe.entities.User;
import com.example.cafe.enums.UserRole;
import com.example.cafe.repositery.UserRepository;
import com.example.cafe.services.UserService;

import jakarta.annotation.PostConstruct;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	
	 @PostConstruct
	    public void createAdminAccount(){
	        User adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
	        if (adminAccount==null){
	            User user = new User();
	            user.setName("admin");
	            user.setEmail("admin@test.com");
	            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
	            user.setUserRole(UserRole.ADMIN);
	            userRepository.save(user);
	        }
	 }
	
	@Override
	public Userdto createUser(SignupRequest signupRequest) {
		User users=new User();
		users.setName(signupRequest.getName());
		users.setEmail(signupRequest.getEmail());
		users.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
		users.setUserRole(UserRole.CUSTOMER);
		User createdUser=userRepository.save(users);
		
		Userdto createdUserdto=new Userdto();
		createdUserdto.setName(createdUser.getName());
		createdUserdto.setEmail(createdUser.getEmail());
		createdUserdto.setUserRole(createdUser.getUserRole());;
		
		return createdUserdto;
	}

	
}
