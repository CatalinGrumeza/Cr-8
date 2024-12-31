package com.Cr_8.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cr_8.entities.Labs;

public interface LabsRepo extends JpaRepository<Labs, Integer> {

	Optional<Labs> findByName(String name);
	
}
