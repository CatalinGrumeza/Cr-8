package com.Cr_8.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cr_8.entities.BookedDate;


public interface BookedDatesRepo extends JpaRepository<BookedDate, Long> {
	public List<BookedDate> findByDateIsNotNull();
}
