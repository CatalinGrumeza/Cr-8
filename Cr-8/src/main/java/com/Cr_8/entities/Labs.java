package com.Cr_8.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/**
 * Entity class representing a laboratory
 */
@Entity
@Table(name="labs")
public class Labs {

    // Primary key for the Labs entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    // Name of the lab
    private String name;
    
    // Description of the lab
    //private String description;
    
    // Scope of the lab (recipient, aimed for)
    //private String scope;
    
    // Duration of the lab
    //private String duration;
  
    //Image of the lab
    //private String img;
  
    // Many-to-many relationship with the corresponding BookingRequest entity, ignored in JSON serialization
    @ManyToMany(mappedBy = "labsSet")
    @JsonIgnore
    private List<BookingRequest> bookingRequest;
    
    
    // Many-to-many relationship with the target of the lab (elementary school, secondary school...)
//    @ManyToMany
//    @JoinTable(
//        name = "labsTarget",
//        joinColumns = @JoinColumn(name= "labs_id"),
//        inverseJoinColumns = @JoinColumn(name = "target_id")
//    )
//    private List<Target> targets;
    
    // Getter and setter methods
//    public List<Target> getTargets() {
//        return targets;
//    }
//    public void setTargets(List<Target> targets) {
//        this.targets = targets;
//    }
    public int getId() {
    	return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
//    public String getDescription() {
//        return description;
//    }
//    public void setDescription(String description) {
//        this.description = description;
//    }
//    public String getScope() {
//        return scope;
//    }
//    public void setScope(String scope) {
//        this.scope = scope;
//    }
//    public String getDuration() {
//        return duration;
//    }
//    public void setDuration(String duration) {
//        this.duration = duration;
//    }
//  	public String getImg() {
//		return img;
//	}
//
//	public void setImg(String img) {
//		this.img = img;
//	}
    

}
