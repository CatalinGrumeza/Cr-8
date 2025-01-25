package com.Cr_8.controllers;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.Cr_8.dto.LabsDTO;
import com.Cr_8.entities.Labs;
import com.Cr_8.servicies.LabsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

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

//    /**
//     * Adds a new lab to the database.
//     * @param labsDTO the request body contain lab's information
//     * @return ResponseEntity containing a success message
//     */
//    @Operation(
//        summary = "POST Method for adding new labs "
//    )
//    @Tag(name = "Dashboard Endpoint")
//    @PostMapping("/add-new-labs")
//    public ResponseEntity<?> addNewLab(@RequestBody LabsDTO labsDTO) {
//        // Add the new lab to the services layer
//    	String newLabs = labsService.addNewLabs(labsDTO);
//        return new ResponseEntity<>(newLabs, HttpStatus.OK); // Return the success message
//    }

}
