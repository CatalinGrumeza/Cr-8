package com.Cr_8.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
/**
 * The RegisterRequestDTO class represents a data transfer object (DTO) for handling user registration requests.
 * It includes fields for the user's email, name, and role, along with validation annotations to ensure data consistency.
 */
public class RegisterRequestDTO {

    /**
     * This field is required and must be in a valid email format.
     */
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    /**
     * This field is not required and can be left blank.
     */
    private String name;

    /**
     * This field is not required and can be left blank.
     */
    private String role;
    
    //password not required
    
    //getters and setters for each field
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}