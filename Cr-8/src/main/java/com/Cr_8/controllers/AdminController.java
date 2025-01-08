package com.Cr_8.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Cr_8.entities.Admin;
import com.Cr_8.servicies.AdminService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@GetMapping("/get-all-admin")
	@Tag(name = "Dashboard Endpoint")
	public ResponseEntity<List<Admin>> getAll() {
		List<Admin> adminList = adminService.getAllAdmin();
		return ResponseEntity.ok(adminList);
	}
	
	@DeleteMapping("/delete-admin")
	@Tag(name = "Dashboard Endpoint")
	public ResponseEntity<String> deleteAdmin(@RequestParam Long id) {
		try {
			adminService.deleteAdmin(adminService.getAdminById(id));			
		}
		catch (Exception e) {
			return ResponseEntity.ok(e.getMessage());
		}
		return ResponseEntity.ok("Admin eliminato con successo");
	}
	
}
