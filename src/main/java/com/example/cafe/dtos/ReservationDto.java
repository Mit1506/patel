package com.example.cafe.dtos;


import java.time.LocalDateTime;

import com.example.cafe.enums.ReservationStatus;


public class ReservationDto {

	private Long id;
	
	private String tableType;
	
	private String description;

	private LocalDateTime dateTime;

	private ReservationStatus reservationstatus;
	
	private Long customerId;
	
	private String customerName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
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

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public String toString() {
		return "ReservationDto [id=" + id + ", tableType=" + tableType + ", description=" + description + ", dateTime="
				+ dateTime + ", reservationstatus=" + reservationstatus + ", customerId=" + customerId
				+ ", customerName=" + customerName +"]";
	}

	
	

}