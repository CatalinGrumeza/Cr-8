package com.Cr_8.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class InfoFormRequest {


	@NotBlank(message = "NAME CAN'T BE EMPTY")
    private String name;
	@NotBlank(message = "LASTNAME CAN'T BE EMPTY")
    private String surname;
	@Pattern(regexp = "^(\\+39)?\\s?3[1-9]\\d{7,9}$", message = "NOT VALID NUMBER")
	@NotBlank(message = "PHONE NUMBER CAN'T BE EMPTY")
    private String phone;
	@NotBlank(message = "Email CAN'T BE EMPTY")
	@Email(message = "NOT VALID EMAIL")
    private String email;
	@NotBlank(message ="TEXT CAN'T BE EMPTY")
    private String text;

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

