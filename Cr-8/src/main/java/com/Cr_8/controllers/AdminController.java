package com.Cr_8.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Cr_8.entities.Admin;
import com.Cr_8.servicies.AdminService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@GetMapping("/getAllAdmin")
	@Tag(name = "Dashboard Endpoint")
	public ResponseEntity<List<Admin>> getAll() {
		List<Admin> adminList = adminService.getAllAdmin();
		return ResponseEntity.ok(adminList);
	}
	
}
