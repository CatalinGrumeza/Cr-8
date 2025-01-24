package com.Cr_8.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entity class representing a reference that submitted a request
 */
@Entity
@Table(name="reference")
public class Reference {
    
    // Primary key for the Reference entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // First name of the reference
    private String firstName;

    // Last name of the reference
    private String lastName;

    // Phone number of the reference
    private String phoneNumber;

    // Email of the reference
    private String email;

    // One-to-many relationship with the Info entity, prevents infinite recursion during serialization
    @OneToMany(mappedBy = "reference", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Info> infos;
    
    // Getter and setter methods
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public List<Info> getInfos() {
        return infos;
    }
    public void setInfos(List<Info> infos) {
        this.infos = infos;
    }

    // Override equals method for comparing references
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // Same reference
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; // Obj null or different class
        }
        Reference other = (Reference) obj;

        // Check equality based on email and phone number
        return (this.email != null && this.email.equals(other.email)) &&
               (this.phoneNumber != null && this.phoneNumber.equals(other.phoneNumber));
    }
}
