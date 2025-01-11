package com.Cr_8.servicies;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Cr_8.entities.Target;
import com.Cr_8.exceptions.ResourceNotFoundException;
import com.Cr_8.repositories.TargetRepo;

@Service
public class TargetService {
	@Autowired
	private TargetRepo targetRepo;
	
	public List<Target> getAllTarget(){
		return targetRepo.findAll();
	}
	public Optional<Target> findTargetById(int id) {
		if(targetRepo.findTargetById(id).isPresent())
			return targetRepo.findTargetById(id);
		throw new ResourceNotFoundException("Target non trovato");
	}
	public Optional<Target> findTargetByDescription(String description) {
		return targetRepo.findTargetByDescription(description);
	}
	public Target createTarget(String description) {
		Target target=new Target();
		target.setDescription(description);
		targetRepo.save(target);
		return target;
	}
	
}
