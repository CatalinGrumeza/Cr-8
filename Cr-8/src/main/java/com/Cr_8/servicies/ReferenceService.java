package com.Cr_8.servicies;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Cr_8.entities.Reference;
import com.Cr_8.repositories.ReferenceRepo;
@Service
public class ReferenceService {
	@Autowired
	private ReferenceRepo referenceRepo; // Repository for interacting with Reference data
	
	/**
	 * Creates a new Reference and saves it in the database.
	 * 
	 * @param email    Email of the reference
	 * @param name     First name of the reference
	 * @param lastName Last name of the reference
	 * @param phone    Phone number of the reference
	 * @return The created Reference object
	 */
	public Reference createReference(String email, String name, String lastName, String phone) {
		Reference user = new Reference();
		user.setEmail(email);
		user.setFirstName(name);
		user.setLastName(lastName);
		user.setPhoneNumber(phone);
		referenceRepo.save(user); // Save the reference object
		return user;
	}

	/**
	 * Finds a Reference by email.
	 * 
	 * @param email Email of the reference
	 * @return The Reference object if found; null otherwise
	 */
	public Reference findByEmail(String email) {
		Optional<Reference> reference = referenceRepo.findByEmail(email);
		return reference.orElse(null); // Return the reference or null
	}

	/**
	 * Finds a Reference by ID.
	 * 
	 * @param id ID of the reference
	 * @return The Reference object if found; null otherwise
	 */
	public Reference findById(Long id) {
		Optional<Reference> reference = referenceRepo.findById(id);
		return reference.orElse(null); // Return the reference or null
	}

	/**
	 * Retrieves all Reference objects from the database.
	 * 
	 * @return A list of all Reference objects
	 */
	public List<Reference> findAll() {
		return referenceRepo.findAll(); // Retrieve all references
	}

	/**
	 * Deletes a Reference by ID if it exists.
	 * 
	 * @param id ID of the reference to be deleted
	 * @return true if the reference was deleted; false otherwise
	 */
	public boolean deleteReference(Long id) {
		Optional<Reference> reference = referenceRepo.findById(id);
		if (reference.isPresent()) {
			referenceRepo.delete(reference.get()); // Delete the reference
			return true;
		}
		return false; // Return false if the reference was not found
	}
}
