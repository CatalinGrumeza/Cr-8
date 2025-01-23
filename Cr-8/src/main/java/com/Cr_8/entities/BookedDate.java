package com.Cr_8.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Entity class representing a booked date or set of dates.
 */
@Entity
@Table(name="booked")
public class BookedDate {

    // Primary key for the BookedDate entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Date of the booking
    private LocalDate date;
    
    // End date of the booking
    private LocalDate toDate;
    
    // One-to-one relationship with the corresponding BookingRequest entity, ignored in JSON serialization
    @OneToOne
    @JoinColumn(name="booking_request")
    @JsonIgnore
    private BookingRequest bookingRequest;
    
    // Many-to-one relationship with DayFraction entity
    @ManyToOne
    @JoinColumn(name = "dayFraction_id")
    private DayFraction dayFractions;

    // Getter and setter methods
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
