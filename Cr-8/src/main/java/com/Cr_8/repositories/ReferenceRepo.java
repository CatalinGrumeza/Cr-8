package com.Cr_8.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cr_8.entities.Reference;

/**
 * Repository interface for managing Reference entities.
 */
public interface ReferenceRepo extends JpaRepository<Reference, Long> {

    /**
     * Finds a Reference by its ID.
     *
     * @param id the ID of the reference to find.
     * @return an Optional containing the reference if found, or empty otherwise.
     */
    Optional<Reference> findById(Long id);

    /**
     * Finds a Reference by its email.
     *
     * @param email the email of the reference to find.
     * @return an Optional containing the reference if found, or empty otherwise.
     */
    Optional<Reference> findByEmail(String email);

    /**
     * Finds a Reference by its phone number.
     *
     * @param phone the phone number of the reference to find.
     * @return an Optional containing the reference if found, or empty Optional otherwise.
     */
    Optional<Reference> findByPhoneNumber(String phone);

    /**
     * Retrieves all Reference entities.
     *
     * @return a list of all references.
     */
    List<Reference> findAll();
}

