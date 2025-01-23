package com.Cr_8.servicies;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Cr_8.entities.Admin;
import com.Cr_8.entities.Role;
import com.Cr_8.exceptions.DuplicateResourceException;
import com.Cr_8.exceptions.ResourceNotFoundException;
import com.Cr_8.repositories.AdminRepo;
@Service
public class AdminService {

    @Autowired
    private AdminRepo adminRepo; // Injects the Admin repository for database interactions

    @Autowired
    private PasswordEncoder passwordEncoder; // Injects the password encoder for encrypting passwords

    @Autowired
    private MailService mailService; // Injects a mail service for sending emails

    /**
     * Registers a new admin with an encrypted password.
     * 
     * @param admin The admin entity to register.
     */
    public void register(Admin admin) {
        // Check if an admin with the given email already exists
        Optional<Admin> adm = adminRepo.findByEmail(admin.getEmail());
        if (adm.isEmpty()) {
            // If no duplicate exists, encode the password and save the admin
            String name = admin.getName();
            String email = admin.getEmail();
            String password = passwordEncoder.encode(admin.getPassword());
            Role role = admin.getRole();

            admin.setName(name);
            admin.setPassword(password);
            admin.setEmail(email);
            admin.setRole(role);
            adminRepo.save(admin);
        } else {
            // If duplicate exists, throw a custom exception
            throw new DuplicateResourceException("Exist admin with this email");
        }
    }

    /**
     * Changes the password of the currently authenticated admin.
     * 
     * @param admin      The admin whose password is being changed.
     * @param pass       The new password.
     * @param oldPass    The old password.
     * @param principal  The currently authenticated user's details.
     * @return A success or error message.
     */
    public String passwordChange(Admin admin, String pass, String oldPass, @AuthenticationPrincipal Principal principal) {
        String emailLogged = principal.getName(); // Get the email of the logged-in admin
        String passLogged = adminRepo.findByEmail(emailLogged).get().getPassword(); // Get the current password

        // Check if the old password matches the stored password
        if (!passwordEncoder.matches(oldPass, passLogged)) {
            return "La vecchia password non corrisponde a quella inserita";
        } else {
            // If it matches, update the password
            admin.setPassword(passwordEncoder.encode(pass));
            return "Password cambiata con successo!";
        }
    }

    /**
     * Sends a reset code to the admin's email.
     * 
     * @param email The email of the admin to reset the password for.
     * @return A success message.
     */
    public String reset(String email) {
        // Find the admin by email or throw an exception if not found
        Admin admin = adminRepo.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Email not found"));
        
        // Generate a random reset code
        String code = UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        // Send the reset code via email
        mailService.sendPasswordEmailRest(email, code);

        // Save the reset code to the admin entity
        admin.setCode(code);
        adminRepo.save(admin);
        return "Code has been sent";
    }

    /**
     * Sets a new password for the admin using the reset code.
     * 
     * @param code The reset code.
     * @param pass The new password.
     * @return A success message.
     */
    public String setnewPassword(String code, String pass) {
        // Find the admin by reset code or throw an exception if not found
        Admin admin = adminRepo.findByCode(code).orElseThrow(() -> new IllegalArgumentException("Code is not valid"));

        // Update the admin's password and clear the reset code
        admin.setPassword(passwordEncoder.encode(pass));
        admin.setCode();
        adminRepo.save(admin);
        return "Password saved";
    }

    /**
     * Retrieves all admins from the database.
     * 
     * @return A list of all admins.
     */
    public List<Admin> getAllAdmin() {
        return adminRepo.findAll();
    }

    /**
     * Retrieves an admin by their ID.
     * 
     * @param id The ID of the admin.
     * @return The admin entity.
     * @throws ResourceNotFoundException if no admin is found with the given ID.
     */
    public Admin getAdminById(Long id) {
        if (adminRepo.findById(id).isPresent())
            return adminRepo.findById(id).get();
        else
            throw new ResourceNotFoundException("Admin non trovato");
    }

    /**
     * Deletes an admin from the database.
     * 
     * @param admin The admin entity to delete.
     */
    public void deleteAdmin(Admin admin) {
        adminRepo.delete(admin);
    }
    public String exitsByCode(String code) {
		if(adminRepo.findByCode(code).isPresent()) {
			Optional<Admin> admin = adminRepo.findByCode(code);
			return admin.get().getCode();	
		}else
			return "Codice errato!";
	}
}

