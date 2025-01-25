package com.Cr_8.servicies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Cr_8.dto.LabsDTO;
import com.Cr_8.entities.Labs;
import com.Cr_8.entities.Target;
import com.Cr_8.exceptions.ResourceNotFoundException;
import com.Cr_8.repositories.LabsRepo;
@Service
public class LabsService {
    @Autowired
    private LabsRepo labsRepo; // Repository for managing Labs entities


    @Autowired
    private TargetService targetService; // Service for managing Target entities

    /**
     * Retrieves all Labs records from the database.
     * 
     * @return A list of all Labs entities.
     */
    public List<Labs> getAllLabs() {
        return labsRepo.findAll();
    }

    /**
     * Adds a new Lab to the database. If a Lab with the given name already exists, returns an error message.
     * 
     * @param name        Name of the Lab.
     * @param description Description of the Lab.
     * @param scope       Scope of the Lab.
     * @param target      List of target descriptions.
     * @param duration    Duration of the Lab.
     * @return A success or error message.
     */

    public String addNewLabs(LabsDTO labsDTO) {
		Optional<Labs> existlab = labsRepo.findByName(labsDTO.getName());
		 if(existlab.isPresent()) {
			 return "labs  found with name: "+labsDTO.getName();
		 }
		
		Labs newLabs =  new Labs();

		newLabs.setName(labsDTO.getName());
		newLabs.setDescription(labsDTO.getDescription());
		newLabs.setScope(labsDTO.getScope());
		newLabs.setDuration(labsDTO.getDuration());
		List<Target> targetList=new ArrayList<Target>();
		for (String target2 : labsDTO.getTargetDescription()) {

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

    /**
     * Removes an existing Lab by name. If the Lab is not found, throws a ResourceNotFoundException.
     * 
     * @param name Name of the Lab to be removed.
     * @return A success message if the Lab is deleted.
     */
    public String RemoveExistLabs(String name) {
        // Check if the Lab with the given name exists
        Optional<Labs> existlab = labsRepo.findByName(name);
        if (existlab.isEmpty()) {
            throw new ResourceNotFoundException("Not found Labs with name: " + name);
        }
        labsRepo.delete(existlab.get()); // Delete the Lab
        return "Labs deleted";
    }

    /**
     * Retrieves a Lab by its name.
     * 
     * @param name The name of the Lab to retrieve.
     * @return An Optional containing the Lab entity if found, or empty if not.
     */
    public Optional<Labs> getByName(String name) {
        return labsRepo.findByName(name);
    }
}
