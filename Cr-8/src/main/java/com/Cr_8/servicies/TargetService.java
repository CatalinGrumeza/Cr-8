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

	/**
	 * Retrieves all Target entities.
	 * 
	 * @return List of all targets
	 */
	public List<Target> getAllTarget() {
		return targetRepo.findAll();
	}

	/**
	 * Retrieves a Target by its ID.
	 * 
	 * @param id ID of the target
	 * @return Target if found
	 * @throws ResourceNotFoundException if no target is found
	 */
	public Optional<Target> findTargetById(int id) {
		if (targetRepo.findTargetById(id).isPresent()) {
			return targetRepo.findTargetById(id);
		}
		throw new ResourceNotFoundException("Target non trovato");
	}

	/**
	 * Retrieves a Target by its description.
	 * 
	 * @param description Description of the target
	 * @return Target if found
	 */
	public Optional<Target> findTargetByDescription(String description) {
		return targetRepo.findTargetByDescription(description);
	}

	/**
	 * Creates a new Target entity.
	 * 
	 * @param description Description of the target
	 * @return Created Target
	 */
	public Target createTarget(String description) {
		Target target = new Target();
		target.setDescription(description);
		targetRepo.save(target);
		return target;
	}
}
