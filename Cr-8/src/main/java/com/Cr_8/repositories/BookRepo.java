package com.Cr_8.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cr_8.entities.BookRequest;

public interface BookRepo extends JpaRepository<BookRequest, Integer>{

}
