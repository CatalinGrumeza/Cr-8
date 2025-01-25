package com.Cr_8.repositories;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cr_8.entities.Status;

/**
 * Repository interface for managing Status entities.
 */
public interface StatusRepo extends JpaRepository<Status,Integer> {

	/**
     * Finds a status by its id.
     *
     * @param id the ID of the status to find
     * @return a Status object with the desired ID
     */
	Status findById(int id);
	
	/**
     * Finds a status by its name.
     *
     * @param name the name of the status to find
     * @return an Optional containing a Status if found, or empty if not found
     */
	Optional<Status> findByName(String name);
	
}
