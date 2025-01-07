package com.Cr_8.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Cr_8.entities.Admin;
import com.Cr_8.servicies.AdminService;
import com.Cr_8.servicies.RoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;


@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private RoleService roleService;
    
    @Operation(
            summary = "Registration api",
            description = "This endpoint the api for the registration,you need a name,an email, the password and the role (Admin/Super admin)."
        )
    @Tag(name = "Dashboard Endpoint")
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDTO request,BindingResult result) {
    	if(result.hasErrors()) {
			return new ResponseEntity<>(result.getFieldErrors().toString(), HttpStatus.BAD_REQUEST);
		}
    	Admin admin=new Admin();
    	admin.setEmail(request.getEmail());
        admin.setName(request.getName());
        admin.setPassword(request.getPassword());
        admin.setRole(roleService.getByName(request.getRole()));
        adminService.register(admin);
        return ResponseEntity.ok("Login successful");
    }
    @Operation(
    		summary = "Get Api for Send Code verification",
			description = "This endpoint provides a Get for creating Code verification."
        )
    @Tag(name = "Public Endpoint")
    @PostMapping("/forget-password")
    public ResponseEntity<String> restPassword(@RequestParam String email){
    	
    	return new ResponseEntity<String>(adminService.reset(email),HttpStatus.OK);
    }
    @Operation(
    		summary = "Put Api for set new password  with Code verification",
			description = "This endpoint provides a Put for setting new password "
        )
    @Tag(name = "Public Endpoint")
    @PostMapping("/newpassword")
    public ResponseEntity<String> setNewPassword(@RequestParam String code , @RequestParam String password){
    	String Code = code.toUpperCase();
    	return new ResponseEntity<String>(adminService.setnewPassword(Code, password), HttpStatus.OK);
    	
    }
}
