package com.Cr_8.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cr_8.entities.Admin;

public interface AdminRepo extends JpaRepository<Admin, Long>{
	Optional<Admin>  findByEmail(String email);
	Optional<Admin> findByCode(String code);
	public boolean existsById(Long Id);

}
