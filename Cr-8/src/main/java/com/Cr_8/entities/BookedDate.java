package com.Cr_8.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="booked")
public class BookedDate {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private LocalDate date;
	    private LocalDate toDate;
	    
	    @OneToOne
	    @JoinColumn(name="booking_request")
	    @JsonIgnore
	    private BookingRequest bookingRequest;
	    
	    @ManyToOne
	    @JoinColumn(name = "dayFraction_id")
	    private DayFraction dayFractions;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public LocalDate getDate() {
			return date;
		}

		public void setDate(LocalDate date) {
			this.date = date;
		}

		public DayFraction getDayFractions() {
			return dayFractions;
		}

		public void setDayFractions(DayFraction dayFractions) {
			this.dayFractions = dayFractions;
		}

		public BookingRequest getBookingRequest() {
			return bookingRequest;
		}

		public void setBookingRequest(BookingRequest bookingRequest) {
			this.bookingRequest = bookingRequest;
		}

		public LocalDate getToDate() {
			return toDate;
		}

		public void setToDate(LocalDate toDate) {
			this.toDate = toDate;
		}
		

		
		
	    
	    
	    

}
