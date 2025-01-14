package com.Cr_8.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class BookingFormRequest {

	@NotBlank(message = "NAME CAN'T BE EMPTY")
    private String name;
	@NotBlank(message = "LASTNAME CAN'T BE EMPTY")
    private String surname;
	//@Pattern(regexp = "^(\\+39)?\\s?3[1-9]\\d{7,9}$", message = "NOT VALID NUMBER")
	@NotBlank(message = "PHONE NUMBER CAN'T BE EMPTY")
    private String phone;
	@NotBlank(message = "Email CAN'T BE EMPTY")
	@Email(message = "NOT VALID EMAIL")
    private String email;
	private int numberOfDays;
	//@NotBlank(message ="TEXT CAN'T BE EMPTY")
	private String additionalDetails;  
	private LocalDate dataFrom;
	private LocalDate dataTo;
	private LocalDate CreatedAt;
	@Min(1)
	private int participantNumber;
	//@NotBlank(message = "BOOK TYPE CAN'T BE EMPTY")
	private String bookType; 
	@NotBlank(message = "VISTOR  TYPE CAN'T BE EMPTY")
	private String visitorType;
	private List<String> labs;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getDataFrom() {
		return dataFrom;
	}
	public void setDataFrom(LocalDate dataFrom) {
		this.dataFrom = dataFrom;
	}
	public LocalDate getDataTo() {
		return dataTo;
	}
	public void setDataTo(LocalDate dataTo) {
		this.dataTo = dataTo;
	}
	public LocalDate getCreatedAt() {
		return CreatedAt;
	}
	public void setCreatedAt(LocalDate createdAt) {
		CreatedAt = createdAt;
	}
	public String getAdditionalDetails() {
		return additionalDetails;
	}
	public void setAdditionalDetails(String additionalDetails) {
		this.additionalDetails = additionalDetails;
	}
	public int getParticipantNumber() {
		return participantNumber;
	}
	public void setParticipantNumber(int participantNumber) {
		this.participantNumber = participantNumber;
	}
	public String getBookType() {
		return bookType;
	}
	public void setBookType(String bookType) {
		this.bookType = bookType;
	}
	public List<String> getLabs() {
		return labs;
	}
	public void setLabs(List<String> labs) {
		this.labs = labs;
	}
	public int getNumberOfDays() {
		return numberOfDays;
	}
	public void setNumberOfDays(int numberOfDays) {
		this.numberOfDays = numberOfDays;
	}
	public String getVisitorType() {
		return visitorType;
	}
	public void setVisitorType(String visitorType) {
		this.visitorType = visitorType;
	}
	
	
	
	 
	
}
