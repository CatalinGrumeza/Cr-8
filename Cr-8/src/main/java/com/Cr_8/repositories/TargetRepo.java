package com.Cr_8.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Cr_8.entities.Target;

public interface TargetRepo extends JpaRepository<Target, Integer>{
	
	public Optional<Target> findTargetById(int id);
	public Optional<Target> findTargetByDescription(String description);

}
