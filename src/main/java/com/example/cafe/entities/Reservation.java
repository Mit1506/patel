package com.example.cafe.entities;



import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.cafe.dtos.ReservationDto;
import com.example.cafe.enums.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String tabletype;
	
	private String description;

	 @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime dateTime;

	private ReservationStatus reservationstatus;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private User user;


	

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", tableType=" + tabletype + ", description=" + description + ", dateTime="
				+ dateTime + ", reservationstatus=" + reservationstatus + ", user=" + user + "]";
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTableType() {
		return tabletype;
	}

	public void setTableType(String tableType) {
		this.tabletype = tableType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	

	public ReservationStatus getReservationstatus() {
		return reservationstatus;
	}

	public void setReservationstatus(ReservationStatus reservationstatus) {
		this.reservationstatus = reservationstatus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ReservationDto getReservationDto() {
		ReservationDto reservationDto = new ReservationDto();
		reservationDto.setId(id);
		reservationDto.setTableType(tabletype);
		reservationDto.setReservationstatus(reservationstatus);
		reservationDto.setDescription(description);
		reservationDto.setDateTime(dateTime);
		reservationDto.setCustomerId(user.getId());
		reservationDto.setCustomerName(user.getName());
		return reservationDto;
	}
}