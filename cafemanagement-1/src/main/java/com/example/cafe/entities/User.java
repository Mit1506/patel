package com.example.cafe.entities;


import org.springframework.stereotype.Component;


import com.example.cafe.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Component
@Entity
@Table(name="Users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	
	
	@Column(name="name")
	private String name;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="userRole")
	private UserRole userRole;
	
	@Column(name="otp")
	    private String otp;
	
	

public User() {
		
	}
	
	public String getOtp() {
			return otp;
		}

		public void setOtp(String otp) {
			this.otp = otp;
		}

	

		

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", userRole="
				+ userRole + "]";
	}

	public User(Long id, String name, String email, String password, UserRole userRole) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.userRole = userRole;
	}

	
	
}
