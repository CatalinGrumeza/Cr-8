package com.Cr_8.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Cr_8.dto.InfoFormRequest;
import com.Cr_8.entities.Info;
import com.Cr_8.servicies.InfoService;
import com.Cr_8.servicies.MailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RequestMapping("/api")
@RestController
public class InfoController {

    // Injecting service dependencies through Spring's dependency injection
    @Autowired
    private InfoService infoService;
    @Autowired
    private MailService mailService;

    /**
     * Retrieves all information requests.
     * @return ResponseEntity containing a list of all Info objects
     */
    @Operation(
        summary = "GET Api for displaying all the info requests",
        description = "This endpoint provides a list of booked dates."
    )
    @Tag(name = "Dashboard Endpoint")
    @GetMapping("/all-info")
    public ResponseEntity<List<Info>> getAll() {
        List<Info> infoList = infoService.getAllInfo(); // Fetch all info requests
        return ResponseEntity.ok(infoList); // Return the list of requests
    }

    /**
     * Creates a new info request. Performs input validation and sends confirmation emails
     * to both the user and the admin.
     * @param infoRequest the request body containing information details
     * @param result captures any validation errors
     * @return ResponseEntity indicating the result of the operation
     */
    @Operation(
        summary = "POST Api for creating an info request",
        description = "This endpoint provides a POST method for creating info requests."
    )
    @Tag(name = "Public Endpoint")
    @PostMapping("/pub/create-info")
    public ResponseEntity<?> postMethodName(@Valid @RequestBody InfoFormRequest infoRequest, BindingResult result) {
        // Check for validation errors in the request body
        if(result.hasErrors()) {
            return new ResponseEntity<>(error(result).toString(), HttpStatus.BAD_REQUEST);
        } else {
            // Create the info request and send confirmation emails
            infoService.createInfo(infoRequest.getName(), infoRequest.getSurname(), infoRequest.getPhone(), infoRequest.getEmail(), infoRequest.getText());
            mailService.sendEmail(infoRequest.getEmail(), infoRequest.getText(), "Richiesta informazioni",
                                  infoRequest.getName(), infoRequest.getSurname(), "informazioni");
            mailService.sendEmailToAdmin(infoRequest.getEmail(), infoRequest.getText(), "Richiesta informazioni",
                                          infoRequest.getName(), infoRequest.getSurname(), "informazioni", infoRequest.getPhone());
            return ResponseEntity.ok("Info created successfully!");
        }
    }

    /**
     * Constructs a string containing validation error messages from binding results.
     * @param result the BindingResult containing errors
     * @return a StringBuilder containing error messages
     */
    public StringBuilder error(BindingResult result) {
        StringBuilder errors = new StringBuilder();
        // Collect each field error and append it to the StringBuilder
        result.getFieldErrors().forEach(error -> 
            errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n")
        );
        return errors;
    }

    /**
     * Updates the status of an info request based on the provided info ID and status.
     * Possible statuses are "Pending" and "Completed".
     * @param infoId the ID of the info request to update
     * @param status the new status for the info request
     * @return ResponseEntity indicating the result of the operation
     */
    @Operation(
        summary = "POST Api for updating an info status request",
        description = "The possible options are Pending, Completed."
    )
    @Tag(name = "Dashboard Endpoint")
    @PostMapping("/update-info-status")
    public ResponseEntity<String> statusInfoChange(@RequestParam Long infoId, @RequestParam String status) {
        try {
            // Update the info request status
            infoService.updateInfoStatus(status, infoId);
            return ResponseEntity.ok("Status changed successfully!");
        } catch (Exception e) {
            // Return an error response if status update fails
            return new ResponseEntity<>("Invalid Status or ID", HttpStatus.BAD_REQUEST);
        }
    }
}