package com.Cr_8.servicies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Cr_8.entities.Labs;
import com.Cr_8.entities.Target;
import com.Cr_8.exceptions.ResourceNotFoundException;
import com.Cr_8.repositories.LabsRepo;
@Service
public class LabsService  {

	@Autowired
	private LabsRepo  labsRepo;
	@Autowired
	private TargetService targetService;
	
	
	public List<Labs> getAllLabs(){
		
		return labsRepo.findAll(); 
	}
	
	public String addNewLabs(String name , String description,String scope,List<String> target,String duration, String img) {
		Optional<Labs> existlab = labsRepo.findByName(name);
		 if(existlab.isPresent()) {
			 return "labs  found with name: "+name;
		 }
		
		Labs newLabs =  new Labs();
		
		newLabs.setName(name);
		newLabs.setDescription(description);
		newLabs.setScope(scope);
		newLabs.setDuration(duration);
		newLabs.setImg(img);
		List<Target> targetList=new ArrayList<Target>();
		for (String target2 : target) {
			if(targetService.findTargetByDescription(target2).isEmpty()) {
				Target newTarget=targetService.createTarget(target2);
				targetList.add(newTarget);
			}else {
				targetList.add(targetService.findTargetByDescription(target2).get());
			}
		}
		newLabs.setTargets(targetList);
		labsRepo.save(newLabs);
		return "added labs !";
	}
	public String RemoveExistLabs(String name)  {
		
		 Optional<Labs> existlab = labsRepo.findByName(name);
		 if(existlab.isEmpty()) {
			 throw new ResourceNotFoundException("not found labs with name : "+name);
		 }
		 return "labs deleted";
	}

	public Optional<Labs> getByName(String string) {
		// TODO Auto-generated method stub
		return labsRepo.findByName(string);
	}
}
