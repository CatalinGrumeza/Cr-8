package com.Cr_8.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "booking")
public class BookRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate dataFrom;
	private LocalDate dataTo;
	private LocalDate CreatedAt;
	private String additionalDetails;
	private int participantNumber;
	private String bookType;
	private String vistorType;
	@OneToOne
	@JoinColumn(name = "status_id")
	private Status status;
	@ManyToOne
	@JoinColumn(name ="reference_id", nullable = false)
	private Reference reference;
}
