package com.Cr_8.servicies;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Cr_8.entities.Labs;
import com.Cr_8.repositories.LabsRepo;
@Service
public class LabsService  {

	@Autowired
	private LabsRepo  labsRepo;
	
	
	public List<Labs> getAllLabs(){
		
		return labsRepo.findAll(); 
	}
	
	public String addNewLabs(String name , String descrizione) {
		Optional<Labs> existlab = labsRepo.findByName(name);
		 if(existlab.isPresent()) {
			 return "labs  found with name: "+name;
		 }
		
		Labs newLabs =  new Labs();
		
		newLabs.setName(name);
		newLabs.setDescrizione(descrizione);
		labsRepo.save(newLabs);
		return "added labs !";
	}
	public String RemoveExistLabs(String name)  {
		
		 Optional<Labs> existlab = labsRepo.findByName(name);
		 if(existlab.isEmpty()) {
			 return "labs not found with name: "+name;
		 }
		 return "labs deleted";
	}

	public Optional<Labs> getByName(String string) {
		// TODO Auto-generated method stub
		return labsRepo.findByName(string);
	}
}
