package com.Cr_8.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cr_8.entities.Admin;

/**
 * Repository interface for managing Admin entities.
 */
public interface AdminRepo extends JpaRepository<Admin, Long> {

    /**
     * Finds an Admin by its email.
     *
     * @param email the email of the admin to find.
     * @return an Optional containing the admin if found, or empty if not found.
     */
    Optional<Admin> findByEmail(String email);

    /**
     * Finds an Admin by its temporary code.
     *
     * @param code the unique code of the admin to find.
     * @return an Optional containing the admin if found, or empty if not found.
     */
    Optional<Admin> findByCode(String code);

    /**
     * Checks whether an Admin exists with the given ID.
     *
     * @param id the ID to check for existence.
     * @return true if an admin with the given ID exists, false otherwise.
     */
    boolean existsById(Long id);
    
}

