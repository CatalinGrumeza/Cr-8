package com.Cr_8.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cr_8.entities.Reference;

public interface ReferenceRepo extends JpaRepository<Reference,Long> {

	/**
     * Finds a reference by its id.
     *
     * @param id the id of the reference to be found
     * @return an Optional containing the Reference object if found, or an empty Optional if not found
     */
	Optional<Reference> findById(Long id);
	Optional<Reference> findByEmail(String email);
	Optional<Reference> createReference(String email, String surname, String name, String phone);
	
}
