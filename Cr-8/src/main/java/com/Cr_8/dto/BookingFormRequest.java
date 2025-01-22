/**
 * The BookingFormRequest class represents a form used to create a booking request.
 * It contains fields for user information and booking details, along with validation annotations.
 *
 * This class will be used to validate and transfer data during the booking process.
 * 
 */
package com.Cr_8.dto;
import java.time.LocalDate;
import java.util.List;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class BookingFormRequest {

    /**
     * This field cannot be empty.
     */
    @NotBlank(message = "NAME CAN'T BE EMPTY")
    private String name;

    /**
     * This field cannot be empty.
     */
    @NotBlank(message = "LASTNAME CAN'T BE EMPTY")
    private String surname;

    /**
     * This field cannot be empty.
     */
    //@Pattern(regexp = "^(\\+39)?\\s?3[1-9]\\d{7,9}$", message = "NOT VALID NUMBER")
    @NotBlank(message = "PHONE NUMBER CAN'T BE EMPTY")
    private String phone;

    /**
     * This field cannot be empty and must be a valid email format.
     */
    @NotBlank(message = "Email CAN'T BE EMPTY")
    @Email(message = "NOT VALID EMAIL")
    private String email;

    /**
     * The number of days for the booking.
     */
    private int numberOfDays;

    /**
     * Additional details provided by the person making the booking (optional).
     */
    //@NotBlank(message ="TEXT CAN'T BE EMPTY")
    private String additionalDetails;

    /**
     * The start date of the booking.
     */
    private LocalDate dataFrom;

    /**
     * The end date of the booking.
     */
    private LocalDate dataTo;

    /**
     * The date the booking was created.
     */
    private LocalDate CreatedAt;

    /**
     * The number of participants for the booking.
     * This must be at least 1.
     */
    @Min(1)
    private int participantNumber;

    /**
     * The type of booking,half day or full day,is optional because if the days of the visit is greater than 1 then it's set automatically to full day.
     */
    //@NotBlank(message = "BOOK TYPE CAN'T BE EMPTY")
    private String bookType;

    /**
     * The type of visitor making the booking.
     * This field cannot be empty.
     */
    @NotBlank(message = "VISTOR TYPE CAN'T BE EMPTY")
    private String visitorType;

    /**
     * A list of labs related to the booking (optional).
     */
    private List<String> labs;

    // Getters and Setters for each field
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