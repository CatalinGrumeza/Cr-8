package com.Cr_8.servicies;

// Importing required Spring and custom components
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Cr_8.entities.Admin; // Entity representing an admin user in the application
import com.Cr_8.repositories.AdminRepo; // Repository interface for Admin entity

// Marks this class as a Spring service component
@Service
public class AdminDetailsService implements UserDetailsService {

    // Automatically injects an instance of AdminRepo
    @Autowired
    private AdminRepo adminRepo;

    /**
     * Loads the admin's details by their email (used as the username).
     * 
     * @param email The email of the admin.
     * @return UserDetails object containing admin's credentials and role information.
     * @throws UsernameNotFoundException if no admin is found with the given email.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Finds the admin by their email or throws an exception if not found
        Admin admin = adminRepo.findByEmail(email).orElseThrow(() ->
            new UsernameNotFoundException("Admin non trovato")); // "Admin not found"

        // Builds a UserDetails object using the admin's credentials and roles
        return User.builder()
            .username(admin.getEmail()) // Sets the username to the admin's email
            .password(admin.getPassword()) // Sets the password (should be encoded)
            .roles(admin.getRole().getName()) // Sets the roles (retrieved from the admin's role entity)
            .build(); // Constructs the UserDetails object
    }
}

