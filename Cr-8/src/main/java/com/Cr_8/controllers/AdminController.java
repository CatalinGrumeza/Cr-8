package com.Cr_8.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Cr_8.entities.Reference;
import com.Cr_8.servicies.ReferenceService;

@RestController
@RequestMapping("/api")
public class AdminController {

	@Autowired
	private ReferenceService referenceService;
	
	@GetMapping("/getReferences")
	public ResponseEntity<List<Reference>> getAll() {
		List<Reference> referenceList = referenceService.findAll();
		return ResponseEntity.ok(referenceList);
	}
	
}
