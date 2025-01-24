package com.Cr_8.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


/**
 * Entity class representing the time fraction that a booking can have
 */
@Entity
public class DayFraction {

    // Primary key for the DayFraction entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Name of the day fraction
    private String name;
    
    // Getter and setter methods
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    // Method to check if the day fraction is morning
    public boolean isMorning() {
        if(this.id == 1 || this.id == 3)
            return true;
        return false;
    }

    // Method to check if the day fraction is a full day
    public boolean isFullDay() {
        if(this.id == 2 || this.id == 3)
            return true;
        return false;
    }
}
