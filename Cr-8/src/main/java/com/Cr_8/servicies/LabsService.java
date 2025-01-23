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
    public String addNewLabs(String name, String description, String scope, List<String> target, String duration) {
        // Check if a Lab with the given name already exists
        Optional<Labs> existlab = labsRepo.findByName(name);
        if (existlab.isPresent()) {
            return "Labs found with name: " + name;
        }

        // Create a new Lab entity
        Labs newLabs = new Labs();
        newLabs.setName(name);
        newLabs.setDescription(description);
        newLabs.setScope(scope);
        newLabs.setDuration(duration);

        // Process targets, creating new ones if they don't exist
        List<Target> targetList = new ArrayList<>();
        for (String targetDescription : target) {
            Optional<Target> existingTarget = targetService.findTargetByDescription(targetDescription);
            if (existingTarget.isEmpty()) {
                // Create a new Target if it doesn't exist
                Target newTarget = targetService.createTarget(targetDescription);
                targetList.add(newTarget);
            } else {
                // Add the existing Target to the list
                targetList.add(existingTarget.get());
            }
        }
        newLabs.setTargets(targetList); // Set the list of targets

        // Save the new Lab entity to the database
        labsRepo.save(newLabs);
        return "Added Lab!";
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
