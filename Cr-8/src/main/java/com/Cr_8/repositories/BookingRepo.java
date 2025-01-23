package com.Cr_8.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cr_8.entities.BookingRequest;

/**
 * Repository interface for managing BookingRequest entities.
 */
public interface BookingRepo extends JpaRepository<BookingRequest, Long>{

}
