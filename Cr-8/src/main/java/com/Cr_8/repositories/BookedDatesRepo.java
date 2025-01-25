package com.Cr_8.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cr_8.entities.BookedDate;

/**
 * Repository interface for managing BookedDate entities.
 */
public interface BookedDatesRepo extends JpaRepository<BookedDate, Long> {

    /**
     * Retrieves a list of BookedDate entities where the date is not null.
     *
     * @return a list of BookedDate entities with non-null dates.
     */
    List<BookedDate> findByDateIsNotNull();
}
