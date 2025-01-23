package com.Cr_8.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Cr_8.dto.LabsDTO;
import com.Cr_8.entities.Labs;
import com.Cr_8.servicies.LabsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
public class LabsController {

    // Injecting service dependencies through Spring's dependency injection
    @Autowired
    private LabsService labsService;
    /**
     * Retrieves all labs.
     * @return ResponseEntity containing a list of all Labs objects
     */
    @Operation(
        summary = "GET Method for display all labs"
    )
    @Tag(name = "Public Endpoint")
    @GetMapping("/pub/labs")
    public ResponseEntity<?> getAllLabs() {
        // Fetch all labs from the services layer
        List<Labs> allLabs = labsService.getAllLabs();
        return new ResponseEntity<>(allLabs, HttpStatus.OK); // Return the list of labs
    }

    /**
     * Adds a new lab to the database.
     * @param labsDTO the request body contain lab's information
     * @return ResponseEntity containing a success message
     */
    @Operation(
        summary = "POST Method for adding new labs "
    )
    @Tag(name = "Dashboard Endpoint")
    @PostMapping("/add-new-labs")
    public ResponseEntity<?> addNewLab(@RequestBody LabsDTO labsDTO) {
        // Add the new lab to the services layer
    	String newLabs = labsService.addNewLabs(labsDTO);
        return new ResponseEntity<>(newLabs, HttpStatus.OK); // Return the success message
    }

    /**
     * Deletes an existing lab from the database.
     * @param name the name of the lab to be deleted
     * @return ResponseEntity containing a success message
     */
    @Operation(
        summary = "DELETE Method for delete exist labs"
    )
    @Tag(name = "Dashboard Endpoint")
    @DeleteMapping("/delete-labs")
    public ResponseEntity<?> deleteLab(String name) {
        // Remove the existing lab from the services layer
        String labExist = labsService.RemoveExistLabs(name);
        return new ResponseEntity<>(labExist, HttpStatus.OK); // Return the success message
    }
}