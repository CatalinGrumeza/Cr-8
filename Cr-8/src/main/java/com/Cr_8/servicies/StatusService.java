package com.Cr_8.servicies;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Cr_8.entities.Status;
import com.Cr_8.repositories.StatusRepo;

@Service
public class StatusService {
	@Autowired
	private StatusRepo statusRepo;
	
	public Status findById(Integer id) {
		Optional<Status> status = statusRepo.findById(id);
		if(status.isPresent())
			return status.get();
		return null;
	}
	
}