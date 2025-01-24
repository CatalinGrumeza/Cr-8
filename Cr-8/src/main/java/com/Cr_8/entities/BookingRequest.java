package com.Cr_8.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

/**
 * Entity class representing the booking request in the database.
 */
@Entity
@Table(name = "booking")
public class BookingRequest {
	
	// Primary key for the BookingRequest entity
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Start date of the booking
    private LocalDate dataFrom;
    
    // End date of the booking
    private LocalDate dataTo;
    
    // Date when the booking was created
    private LocalDate CreatedAt;
    
    // Additional details about the booking
    private String additionalDetails; 
    
    // Number of participants in the booking
    private int participantNumber;
    
    // Type of booking (half-day, day, hour)
	private String bookType;

	// Number of days for the booking
    private int numberOfDays;
    
    // Type of visitor (e.g., school, organization)
    private String vistorType;  
    
    // Many-to-one relationship with the status of the request 
	@ManyToOne
	@JoinColumn(name = "status_id")
	private Status status;
	
	// Many-to-one relationship with the reference who submitted the request, ignored in JSON serialization
	@ManyToOne
	@JoinColumn(name ="reference_id", nullable = false)
	@JsonIgnoreProperties("infos")
	private Reference reference;
	
	// One-to-one relationship with BookedDate entity, with cascading all operations
	@OneToOne(mappedBy = "bookingRequest", cascade = CascadeType.ALL)
	private BookedDate bookedDate;
	
	// Many-to-many relationship with Labs entity
	@ManyToMany
	@JoinTable(
			name = "labsRequest",
			joinColumns =@JoinColumn(name= "booking_id"),
			inverseJoinColumns = @JoinColumn(name = "labs_id")
			)
	private List<Labs> labsSet = new ArrayList();
	
	// Getter and setter methods
	public List<Labs> getLabsSet() {
		return labsSet;
	}
	public void addLab(Labs existlabs) {
		this.labsSet.add(existlabs);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getVistorType() {
		return vistorType;
	}
	public void setVistorType(String vistorType) {
		this.vistorType = vistorType;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Reference getReference() {
		return reference;
	}
	public void setReference(Reference user) {
		this.reference = user;
	}
	public BookedDate getBookedDate() {
		return bookedDate;
	}
	public void setBookedDate(BookedDate bookedDate) {
		this.bookedDate = bookedDate;
	}
	public int getNumberOfDays() {
		return numberOfDays;
	}
	public void setNumberOfDays(int numberOfDays) {
		this.numberOfDays = numberOfDays;
	}
}
