package com.Cr_8.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cr_8.entities.Status;


public interface StatusRepo extends JpaRepository<Status,Integer> {

	/**
     * Finds a status by its id.
     *
     * @param id the id of the status to be found
     * @return an Optional containing the Status object if found, or an empty Optional if not found
     */
	Optional<Status> findById(Integer id);
	
}
