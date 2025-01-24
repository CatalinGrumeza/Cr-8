package com.Cr_8.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class DayFraction {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	private String name;
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isMorning() {
		if(this.id==1||this.id==3)
			return true;
		return false;
	}

	public boolean isFullDay() {
		if(this.id==2||this.id==3)
			return true;
		return false;
	}


    
    
    
}
