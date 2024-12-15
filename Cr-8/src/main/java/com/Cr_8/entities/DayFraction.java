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

    private boolean morning;
    private boolean evening;
    
    public DayFraction() {
        this.morning = false;
        this.evening = false;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isMorning() {
		return morning;
	}

	public void setMorning(boolean morning) {
		this.morning = morning;
	}

	public boolean isEvening() {
		return evening;
	}

	public void setEvening(boolean evening) {
		this.evening = evening;
	}

    
    
    
}
