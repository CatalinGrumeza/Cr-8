package com.Cr_8.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entity class representing an information request
 */
@Entity
@Table(name = "info")
public class Info {

    // Primary key for the Info entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Text content of the info
    private String text;
    
    // Timestamp when the info was created
    private LocalDateTime createdAt;

    // Many-to-one relationship with reference who submitted the request, managed in JSON serialization
    @ManyToOne
    @JoinColumn(name = "reference_id", nullable = false)
    @JsonManagedReference
    private Reference reference;
    
    // Many-to-one relationship with Status entity
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    // Getter and setter methods
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public Reference getReference() {
        return reference;
    }
    public void setReference(Reference reference) {
        this.reference = reference;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
}
