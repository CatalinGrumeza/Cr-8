package com.Cr_8.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cr_8.entities.Labs;

/**
 * Repository interface for managing Labs entities.
 */
public interface LabsRepo extends JpaRepository<Labs, Integer> {

	/**
     * Finds a Lab by its name.
     *
     * @param name the name of the lab to find.
     * @return an Optional containing the lab if found, or empty if not found.
     */
	Optional<Labs> findByName(String name);
	
}
