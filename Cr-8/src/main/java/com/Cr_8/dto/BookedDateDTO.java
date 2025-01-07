package com.Cr_8.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.Cr_8.entities.BookedDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class BookedDateDTO {
	private LocalDate date;
	private boolean morning;
	private boolean evening;
	private Long idBookingRequest;
	public Long getIdBookingRequest() {
		return idBookingRequest;
	}
	public void setIdBookingRequest(Long idBookingRequest) {
		this.idBookingRequest = idBookingRequest;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public boolean isMorning() {
		return morning;
	}
	public void setMorning(boolean morning) {
		this.morning = morning;
	}
	public boolean isEvening() {
		return evening;
	}
	public void setEvening(boolean evening) {
		this.evening = evening;
	}
	
	
	

}
