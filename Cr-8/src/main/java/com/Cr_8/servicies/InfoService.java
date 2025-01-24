package com.Cr_8.servicies;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Cr_8.entities.Info;
import com.Cr_8.entities.Reference;
import com.Cr_8.entities.Status;
import com.Cr_8.exceptions.ResourceNotFoundException;
import com.Cr_8.repositories.InfoRepo;
import com.Cr_8.repositories.ReferenceRepo;

import jakarta.transaction.Transactional;
@Service
public class InfoService {

    @Autowired
    private InfoRepo infoRepo; // Repository for managing Info entities

    @Autowired
    private ReferenceService referenceService; // Service for managing Reference entities

    @Autowired
    private StatusService statusService; // Service for managing Status entities

    /**
     * Retrieves all Info records from the database.
     * 
     * @return A list of all Info entities.
     */
    public List<Info> getAllInfo() {
        return infoRepo.findAll();
    }

    /**
     * Creates a new Info record with the provided details. Rolls back the transaction in case of failure.
     * 
     * @param name    First name of the user.
     * @param surname Last name of the user.
     * @param phone   Phone number of the user.
     * @param email   Email address of the user.
     * @param Text    Text content of the Info.
     */
    @Transactional // Ensures atomicity: rolls back if an exception occurs
    public void createInfo(String name, String surname, String phone, String email, String Text) {

        // Create a new Info object and set its properties
        Info info = new Info();
        info.setText(Text); // Set the text content
        info.setCreatedAt(LocalDateTime.now()); // Set the creation timestamp
        info.setStatus(statusService.findByName("Pending")); // Set the initial status to "Pending"

        // Check if the Reference already exists for the provided email
        if (referenceService.findByEmail(email) != null) {
            Reference user = referenceService.findByEmail(email);
            info.setReference(user); // Link the existing Reference
        } else {
            // Create a new Reference if it does not exist
            Reference user = referenceService.createReference(email, surname, name, phone);
            info.setReference(user); // Link the new Reference
        }

        // Save the Info object to the database
        infoRepo.save(info);
    }

    /**
     * Updates the status of an existing Info record by its ID.
     * 
     * @param status The new status to set ("Completed" or "Pending").
     * @param infoId The ID of the Info record to update.
     */
    public void updateInfoStatus(String status, Long infoId) {
        // Check if the Info record exists in the database
        if (infoRepo.findById(infoId).isPresent()) {
            Info info = infoRepo.findById(infoId).get();

            // Update the status if it is valid and not null
            if ((status.equals("Completed") || status.equals("Pending")) && status != null) {
                info.setStatus(statusService.findByName(status)); // Set the new status
            }

            // Save the updated Info object
            infoRepo.save(info);
        } else {
            // Throw an exception if the Info record is not found
            throw new ResourceNotFoundException("Info non trovata!");
        }
    }
}
