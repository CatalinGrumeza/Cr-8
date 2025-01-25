package com.Cr_8.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cr_8.entities.Info;

/**
 * Repository interface for managing Info entities.
 */
public interface InfoRepo extends JpaRepository<Info, Long> {

}
