package com.Cr_8.dto;
import java.time.LocalDate;
/**
 * BookedDateDTO class holds the necessary information to book a booking request.
 */
public class BookedDateDTO {//DTO to send as an object to complete a booking request

    /**
     * The start date of the booking.
     */
    private LocalDate date;

    /**
     * The end date of the booking (for multi-day bookings).
     */
    private LocalDate toDate;

    /**
     * A flag indicating if the booking is for the morning.
     */
    private boolean morning;

    /**
     * A flag indicating if the booking is for the full day.
     */
    private boolean fullDay;

    /**
     * The identifier of the booking request this booked date belongs to.
     */
    private Long idBookingRequest;

    // Getters and Setters for each field
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
    public LocalDate getToDate() {
        return toDate;
    }
    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
    public boolean isFullDay() {
        return fullDay;
    }
    public void setFullDay(boolean fullDay) {
        this.fullDay = fullDay;
    }
}