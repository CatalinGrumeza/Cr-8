package com.Cr_8.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cr_8.entities.BookingRequest;

public interface BookingRepo extends JpaRepository<BookingRequest, Long>{

	Optional<BookingRequest> findById(Long id);
}
