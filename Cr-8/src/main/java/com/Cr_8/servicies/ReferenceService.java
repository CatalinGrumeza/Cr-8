package com.Cr_8.servicies;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Cr_8.entities.Reference;
import com.Cr_8.repositories.ReferenceRepo;

@Service
public class ReferenceService {
	@Autowired
	private ReferenceRepo referenceRepo;
	
	public Reference createReference(String email,String name,String lastName,String phone) {
		Reference user=new Reference();
		user.setEmail(email);
		user.setFirstName(name);
		user.setLastName(lastName);
		user.setPhoneNumber(phone);
		referenceRepo.save(user);
		return user;
	}
	
	public Reference findByEmail(String email) {
		Optional<Reference> reference = referenceRepo.findByEmail(email);
		if(reference.isPresent())
			return reference.get();
		return null;
	}
	
	public Reference findById(Long id) {
		Optional<Reference> reference = referenceRepo.findById(id);
		if (reference.isPresent())
			return reference.get();
		return null;
	}
	
	public boolean deleteReference (Long id) {
		if (referenceRepo.findById(id).isPresent()) {
			referenceRepo.delete(referenceRepo.findById(id).get());
			return true;
		}
		return false;
	}
	
}
