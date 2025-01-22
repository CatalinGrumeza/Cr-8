package com.Cr_8.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Cr_8.entities.Reference;
import com.Cr_8.servicies.ReferenceService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/api")
@RestController
public class ReferenceController {
    
    // Injecting the ReferenceService dependency to access reference data
    @Autowired
    private ReferenceService referenceService;

    /**
     * Retrieves all references from the database.
     * @return ResponseEntity containing a list of all Reference objects
     */
    @Tag(name = "Dashboard Endpoint")
    @GetMapping("/all-references")
    public ResponseEntity<List<Reference>> getAll() {
        // Fetching the list of all references using the service layer
        List<Reference> referenceList = referenceService.findAll();
        // Returning the list of references wrapped in a ResponseEntity with status code 200 OK
        return ResponseEntity.ok(referenceList);
    }
}