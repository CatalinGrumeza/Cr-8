package com.Cr_8.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

/**
 * Entity class representing the booking request  in the database.
 */
@Entity
@Table(name = "booking")
public class BookingRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate dataFrom;
	private LocalDate dataTo;
	private LocalDate CreatedAt;
	private String additionalDetails; 
	private int participantNumber;
	private String bookType; //half-day,day,hour
	private String vistorType;  // school-organization- ...etc
	@ManyToOne
	@JoinColumn(name = "status_id")
	private Status status; // status of the book
	@ManyToOne
	@JoinColumn(name ="reference_id", nullable = false)
	@JsonIgnoreProperties("infos")
	private Reference reference; //reference of the group
	@OneToOne(mappedBy = "bookingRequest", cascade = CascadeType.ALL)
	@JsonIgnore
	private BookedDate bookedDate;
	@ManyToMany
	@JoinTable(
			name = "labsRequest",
			joinColumns =@JoinColumn(name= "booking_id"),
			inverseJoinColumns = @JoinColumn(name = "labs_id")
			)
	private List<Labs> labsSet =new ArrayList();
	
	public List<Labs> getLabsSet() {
		return labsSet;
	}
	public void setLabsSet(Labs existlabs) {
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
	
	
	
}
