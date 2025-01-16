package com.example.cafe.services;

import com.example.cafe.dtos.SignupRequest;
import com.example.cafe.dtos.Userdto;


public interface UserService  {

	

Userdto createUser(SignupRequest signupRequest);


}
