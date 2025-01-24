package com.Cr_8.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;

/**
 * Entity class representing an Admin.
 */
@Entity
@Table(name="admin")
public class Admin {

    // Primary key for the Admin entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  
    
    // Name of the admin
    private String name;
    
    // Unique email for the admin, validated to ensure proper formatting
    @Column(unique = true)
    @Email(message = "NOT VALID EMAIL")
    private String email;
    
    // Hashed password for the admin
    private String password;
    
    // Temporary code used in case of forgotten password
    private String code;
    
    // Many-to-one relationship with the Role entity
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
    
    // Getter and setter methods
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
}
