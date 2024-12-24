package com.Cr_8.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cr_8.entities.Role;

public interface RoleRepo extends JpaRepository<Role,Integer> {
	
	Optional<Role> findByName(String name);

}
