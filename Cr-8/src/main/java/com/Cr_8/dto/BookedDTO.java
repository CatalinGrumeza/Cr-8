/**
 * The BookedDTO class serves as a Data Transfer Object (DTO) used to represent
 * the details of booked dates to be sent back as a response to an API. 
 * It encapsulates information related to a booking request, including 
 * the booked dates, time frames, and reference details. This class is 
 * useful for integrating with calendar functionalities where booking 
 * information is displayed or manipulated.
 * 
 */
package com.Cr_8.dto;

import java.time.LocalDate;

public class BookedDTO {
    
    /**
     * The identifier for the booking request associated with this booked date.
     */
    private Long idBookingRequest;

    /**
     * The identifier for the booked date.
     */
    private Long idBookedDate;

    /**
     * An optional reference identifier for additional context.
     */
    private Long idReference;

    /**
     * The start date of the booking.
     */
    private LocalDate date;

    /**
     * The end date of the booking, applicable for range bookings.
     */
    private LocalDate toDate;

    /**
     * A flag indicating if the booking is for the morning.
     */
    private boolean morning;

    /**
     * A flag indicating if the booking covers the entire day.
     */
    private boolean allDay;

    /**
     * A human-readable name or description associated with the booking reference.
     */
    private String referenceName;

    /**
     * Default constructor.
     */
    public BookedDTO() {}
    
    //getters and setters for each field

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

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
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
    }
}