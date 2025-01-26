package com.Cr_8.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Cr_8.entities.Admin;
import com.Cr_8.servicies.AdminService;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * The AdminController class is a Spring REST controller that manages 
 * the admin-related API endpoints. It provides functionality to 
 * retrieve all admins and delete an admin by their ID. 
 * The controller communicates with the AdminService to perform business logic.
 */
@RestController
@RequestMapping("/api")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * Retrieves a list of all administrators.
     *
     * @return a ResponseEntity containing a list of all Admins.
     */
    @GetMapping("/super/get-all-admin")
    @Tag(name = "Dashboard Endpoint")
    public ResponseEntity<List<Admin>> getAll() {
        List<Admin> adminList = adminService.getAllAdmin();
        return ResponseEntity.ok(adminList);
    }

    /**
     * Deletes an administrator identified by their ID.
     *
     * This method attempts to delete an admin and returns a success 
     * message if successful. If any exceptions occur, it returns 
     * the exception message.
     * This method can be called only by super admins
     * @param id the ID of the admin to be deleted.
     * @return a ResponseEntity containing a success or error message.
     */
    @DeleteMapping("/super/delete-admin")
    @Tag(name = "Dashboard Endpoint")
    public ResponseEntity<String> deleteAdmin(@RequestParam Long id) {
        try {
            // Find and delete the admin by ID
            adminService.deleteAdmin(adminService.getAdminById(id));
        } catch (Exception e) {
            // Return error message if deletion fails
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        // Return success message if deletion is successful
        return ResponseEntity.ok("Admin eliminato con successo");
    }
}