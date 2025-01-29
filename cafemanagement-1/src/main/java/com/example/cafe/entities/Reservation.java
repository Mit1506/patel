package com.example.cafe.entities;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Reservation {

	private Long id;
	
	private String tabletype;
	
	private String description;
	
	
	private Date dateTime;
	
	
	private Reservation reservationstatus;
	
	@ManyToOne(fetch=FetchType.LAZY,optional = false)
	@JoinColumn(name="user_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private User user;
	
}
