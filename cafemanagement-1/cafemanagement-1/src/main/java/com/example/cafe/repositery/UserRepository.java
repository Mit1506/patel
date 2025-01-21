package com.example.cafe.repositery;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cafe.entities.User;
import com.example.cafe.enums.UserRole;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findFirstByEmail(String email);

	User findByUserRole(UserRole admin);

	User findByEmail(String email);

	User findByOtp(String code);

	boolean existsByEmail(String email);	
	 
}
