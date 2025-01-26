/**
 * The InfoFormRequest class is a Data Transfer Object (DTO) used to capture input 
 * from the user in a contact or information form. It contains fields for the user's
 * personal information and a message, along with validation annotations to ensure 
 * that the data is valid before processing.
 * 
 */
package com.Cr_8.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class InfoFormRequest {

    /**
     *This field cannot be empty.
     */
    @NotBlank(message = "NAME CAN'T BE EMPTY")
    private String name;

    /**
     *This field cannot be empty.
     */
    @NotBlank(message = "LASTNAME CAN'T BE EMPTY")
    private String surname;

    /**
     *This field cannot be empty 
     * and must match the specified pattern for valid numbers.
     */
    // @Pattern(regexp = "^(\\+39)?\\s?3[1-9]\\d{7,9}$", message = "NOT VALID NUMBER")
    @NotBlank(message = "PHONE NUMBER CAN'T BE EMPTY")
    private String phone;

    /**
     *This field cannot be empty 
     * and must be a valid email format.
     */
    @NotBlank(message = "Email CAN'T BE EMPTY")
    @Email(message = "NOT VALID EMAIL")
    private String email;

    /**
     *This field cannot be empty.
     */
    @NotBlank(message ="TEXT CAN'T BE EMPTY")
    private String text;

    // Getter and Setter methods for each field

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}