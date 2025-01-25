package com.Cr_8.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity class representing a role for admins
 */
@Entity
@Table(name = "role")
public class Role {

    // Primary key for the Role entity
    @Id
    private Integer id;

    // Name of the role
    private String name;
    
    // Getter and setter methods
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
}

