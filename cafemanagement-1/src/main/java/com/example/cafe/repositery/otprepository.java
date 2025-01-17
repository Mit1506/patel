package com.example.cafe.repositery;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cafe.entities.OtpEntity;

public interface otprepository extends  JpaRepository<OtpEntity, Long> {

	Optional<OtpEntity> findByEmailAndOtp(String email, String otp);

	
	Optional<OtpEntity> findByEmail(String email);


	Optional<OtpEntity> findByOtp(String otp);


	

	

	

}
