package com.Cr_8.dto;

import java.time.LocalDate;

public class BookedDTO {
	private Long idBookingRequest;
	private Long idBookedDate;
	private Long idReference;
	private LocalDate date;
    private LocalDate toDate;
	private boolean morning;
    private boolean allDay;
    private String referenceName;
    
    public BookedDTO() {}

	public Long getIdBookingRequest() {
		return idBookingRequest;
	}

	public void setIdBookingRequest(Long idBookingRequest) {
		this.idBookingRequest = idBookingRequest;
	}

	public Long getIdBookedDate() {
		return idBookedDate;
	}

	public void setIdBookedDate(Long idBookedDate) {
		this.idBookedDate = idBookedDate;
	}

	public Long getIdReference() {
		return idReference;
	}

	public void setIdReference(Long idReference) {
		this.idReference = idReference;
	}

	public boolean isMorning() {
		return morning;
	}

	public void setMorning(boolean morning) {
		this.morning = morning;
	}

	public boolean isAllDay() {
		return allDay;
	}

	public void setAllDay(boolean evening) {
		this.allDay = evening;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public String getReferenceName() {
		return referenceName;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	};
	
	
    

}
