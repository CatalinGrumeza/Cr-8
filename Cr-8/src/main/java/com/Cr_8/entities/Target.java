package com.Cr_8.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/**
 * Entity class representing a target for the laboratories
 */
@Entity
@Table(name = "target")
public class Target {

    // Primary key for the Target entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Description of the target
    private String description;

    // Many-to-many relationship with the Labs entity, ignored in JSON serialization
    @ManyToMany(mappedBy = "targets")
    @JsonIgnore
    private List<Labs> labsSet;

    // Getter and setter methods
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<Labs> getLabsSet() {
        return labsSet;
    }
    public void setLabsSet(List<Labs> labsSet) {
        this.labsSet = labsSet;
    }
}
