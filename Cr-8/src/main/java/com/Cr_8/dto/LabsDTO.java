/**
 * The LabsDTO class is a Data Transfer Object (DTO) used for capturing 
 * the details required to create a lab. It includes fields for the lab's 
 * name, description, scope, duration, and targeted descriptions. 
 */
package com.Cr_8.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;


public class LabsDTO {

    /**
     * The name of the lab. This field is essential to identify the lab.
     */
    private String name;

    /**
     * A brief description of the lab, detailing its purpose or functionalities.
     */
    private String description;

    /**
     * The scope of the lab, outlining the areas or topics that the lab covers.
     */
    private String scope;

    /**
     * The duration of the lab, specifying how long the lab is designed to run.
     */
    private String duration;

    /**
     * A list of descriptions of the targets associated with the lab. 
     * Each entry in the list provides additional detail about the targets.
     */
    private List<String> targetDescription;
  
    private MultipartFile img;

    // Getter and Setter for each field

    public List<String> getTargetDescription() {
        return targetDescription;
    }

    public void setTargetDescription(List<String> targetDescription) {
        this.targetDescription = targetDescription;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
  public MultipartFile getImg() {
		return img;
	}
	
	public void setImg(MultipartFile img) {
		this.img = img;
	}
}

