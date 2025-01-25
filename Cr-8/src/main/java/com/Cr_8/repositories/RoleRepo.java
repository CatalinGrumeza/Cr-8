package com.Cr_8.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cr_8.entities.Role;

/**
 * Repository interface for managing Role entities.
 */
public interface RoleRepo extends JpaRepository<Role,Integer> {
	
	/**
     * Finds a Role by its name.
     *
     * @param name the name of the role to find.
     * @return an Optional containing the role if found, or empty if not found.
     */
	Optional<Role> findByName(String name);

}
