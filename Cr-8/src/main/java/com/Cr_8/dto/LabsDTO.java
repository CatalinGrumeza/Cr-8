package com.Cr_8.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.Cr_8.entities.Target;

import jakarta.mail.Multipart;

public class LabsDTO {

	private String name;
	private String description;
	private String scope;
	private String duration;
	private List<String> targetDescription;
	private MultipartFile img;
	
	
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
	public void setDescription(String descrizione) {
		this.description = descrizione;
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
