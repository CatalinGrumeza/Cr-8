package com.Cr_8.servicies;

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
		if(referenceRepo.findByEmail(email).isPresent())
			return referenceRepo.findByEmail(email).get();
		return null;
	}
}
