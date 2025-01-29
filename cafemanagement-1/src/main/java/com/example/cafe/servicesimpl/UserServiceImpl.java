package com.example.cafe.servicesimpl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cafe.entities.User;
import com.example.cafe.enums.UserRole;
import com.example.cafe.jwts.EmailUtils;
import com.example.cafe.jwts.OtpUtil;
import com.example.cafe.repositery.UserRepository;
import com.example.cafe.services.UserService;

import jakarta.annotation.PostConstruct;

@Service
public  class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    EmailUtils utils;

    @Autowired
    OtpUtil otpUtil;

    @PostConstruct
    public void createAdminAccount() {
        User adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
        if (adminAccount == null) {
            User user = new User();
            user.setName("admin");
            user.setEmail("admin@test.com");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            user.setUserRole(UserRole.ADMIN);
            user.setOtpVerified(true);
            userRepository.save(user);
        }
    }

    @Override
    public User createUser(User user) throws Exception {
    	
    	 if (userRepository.existsByEmail(user.getEmail())) {
    	        throw new Exception("User  with this email already exists.");
    	    }
        String otp = generateOtp();
        utils.sendVerificationEmail(user.getEmail(), otp);

        User users = new User();
        users.setOtp(otp);
        users.setName(user.getName());
        users.setEmail(user.getEmail());
        users.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        users.setUserRole(UserRole.CUSTOMER);

        User createdUser = userRepository.save(users);

        User createdUserDto = new User();
        createdUserDto.setName(createdUser.getName());
        createdUserDto.setEmail(createdUser.getEmail());
        createdUserDto.setUserRole(createdUser.getUserRole());

        return createdUserDto;
    }

    public String generateOtp() {
        Random random = new Random();
        int randomNumber = random.nextInt(999999);
        String output = Integer.toString(randomNumber);

        while (output.length() < 6) {
            output = "0" + output;
        }
        return output;
    }

   

	

	

	
}
