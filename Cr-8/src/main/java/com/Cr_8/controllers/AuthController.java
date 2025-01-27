package com.Cr_8.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Cr_8.dto.RegisterRequestDTO;
import com.Cr_8.entities.Admin;
import com.Cr_8.servicies.AdminService;
import com.Cr_8.servicies.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * The AuthController class provides RESTful endpoints for handling user 
 * registration, password resetting, and verification code generation. It 
 * integrates with the AdminService and RoleService to perform business 
 * logic operations.
 */
@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;
    
    /**
     * Swagger/OpenAPI operation annotation for API documentation. This 
     * endpoint is for user registration and includes detailed API 
     * documentation and validation rules.
     */
    @Operation(
            summary = "Registration api",
            description = "This endpoint is the api for the registration,you need a name,an email, the password and the role (Admin/Super admin).Password must be at least 8 characters long, include an uppercase letter, a lowercase letter, a number, and a special character.REGEX:  ^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*[@$!%*?&])[A-Za-z\\\\d@$!%*?&]{8,}$"
        )
    @Tag(name = "Dashboard Endpoint")
    @PostMapping("/super/register")
    public ResponseEntity<String> register(@Valid @ModelAttribute RegisterRequestDTO request,BindingResult result) {
        // Check if the request data contains any validation errors.
        if(result.hasErrors()) {
            // Return a bad request response with the validation errors.
            return new ResponseEntity<>(result.getFieldErrors().toString(), HttpStatus.BAD_REQUEST);
        }
        
        // Prepare a new Admin object using the request details.
        Admin admin=new Admin();
        admin.setEmail(request.getEmail());
        admin.setName(request.getName());
        
        /**
         * Password is hardcoded, which is insecure,the password
         * should be changed as soon as possible by the user
         */
        admin.setPassword("Cascinacaccia2025!");
        
        /**
         * Set the role for the new admin based on the request details. 
         * This assumes that the RoleService provides a method to retrieve 
         * roles by name.
         */
        admin.setRole(roleService.getByName(request.getRole()));
        
        /**
         * Save the new Admin to the database using the AdminService. 
         * This method should perform any necessary validation and 
         * business logic operations.
         */
        adminService.register(admin);
        
        /**
         * Return a successful response message indicating that the 
         * registration was successful.
         */
        return ResponseEntity.ok("Admin creato con successo");
    }
    /**
     * Swagger/OpenAPI operation annotation for API documentation. This 
     * endpoint is for generating a verification code for password reset.
     */
    @Operation(
            summary = "Get Api for Send Code verification",
            description = "This endpoint provides a Get for creating Code verification."
        )
    @Tag(name = "Public Endpoint")
    @PostMapping("/pub/forget-password")
    public ResponseEntity<String> restPassword(@RequestParam String email){
        
        // Integrate with the AdminService to send a verification code to 
        // the provided email address.
        return new ResponseEntity<String>(adminService.reset(email),HttpStatus.OK);
    }
    /**
     * Swagger/OpenAPI operation annotation for API documentation. This 
     * endpoint is for updating a user's password using a verification code.
     */
    @Operation(
            summary = "Put Api for set new password  with Code verification",
            description = "This endpoint provides a Put for setting new password "
        )
    @Tag(name = "Public Endpoint")
    @PostMapping("/pub/newpassword")
    public ResponseEntity<String> setNewPassword(@RequestParam String code , @RequestParam String password){
        String Code = code.toUpperCase();
        // Integrate with the AdminService to update the user's password 
        // using the provided verification code and new password.
        return new ResponseEntity<String>(adminService.setnewPassword(Code, password), HttpStatus.OK);
        
    }
    @Operation(
            summary = "Get Api for  Code verification",
            description = "This endpoint provides a string with  code verification "
        )
    @Tag(name = "Public Endpoint")
    @GetMapping("/pub/verification-code")
    public ResponseEntity<String> checkCode(@RequestParam String code){
    	String res=adminService.exitsByCode(code);
    	if(res.equals("Codice errato!")) {
    		return new ResponseEntity<String>(adminService.exitsByCode(code), HttpStatus.BAD_REQUEST);
    	}
    	return new ResponseEntity<String>(adminService.exitsByCode(code), HttpStatus.OK);
    }
}
