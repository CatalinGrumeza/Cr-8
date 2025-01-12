package com.Cr_8.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.Cr_8.dto.LabsDTO;
import com.Cr_8.entities.Labs;
import com.Cr_8.servicies.LabsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api ")
public class LabsController {

	@Autowired
	private LabsService labsService;
	
	@Operation(
			summary = "GET Method for display all labs"
			)
	@Tag(name = "Public Endpoint")
	@GetMapping("/pub/labs")
	public ResponseEntity<?> getAllLabs(){
		List<Labs> allLabs = labsService.getAllLabs();
		return new ResponseEntity<>(allLabs, HttpStatus.OK);
	}
	@Operation(
			summary = "POST Method for adding new labs "
			)
	@Tag(name = "Dashboard Endpoint")
	@PostMapping("/add-new-labs")
	public ResponseEntity<?> addNewLab(@RequestBody LabsDTO labsDTO){
		String newlabs =labsService.addNewLabs(labsDTO.getName(), labsDTO.getDescription(),labsDTO.getScope(),labsDTO.getTargetDescription());
		return new ResponseEntity<>(newlabs, HttpStatus.OK);
	}
	@Operation(
			summary = "DELETE Method for delete exist labs"
			)
	@Tag(name = "Dashboard Endpoint")
	@DeleteMapping("/delete-labs")
	public ResponseEntity<?>  deleteExistslabs (String name){
		
		String labsExist = labsService.RemoveExistLabs(name);
		
		return new ResponseEntity<>(labsExist, HttpStatus.OK);
	}
}
