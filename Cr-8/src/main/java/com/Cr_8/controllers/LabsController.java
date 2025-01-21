package com.Cr_8.controllers;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Cr_8.dto.LabsDTO;
import com.Cr_8.entities.Labs;
import com.Cr_8.servicies.LabsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api")
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
	@PostMapping(value = "/add-new-labs", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> addNewLab(@ModelAttribute LabsDTO labsDTO) {
		try {
			String fileName = null;
	        if (labsDTO.getImg() != null && !labsDTO.getImg().isEmpty()) {
	            MultipartFile img = labsDTO.getImg();
	            fileName = labsDTO.getName() + ".jpg";
	            
	            File file = new File(fileName);
	            try (FileOutputStream fos = new FileOutputStream(file)) {
	                fos.write(img.getBytes());
	            }
	        }
            
			String newlabs = labsService.addNewLabs(
	                labsDTO.getName(),
	                labsDTO.getDescription(),
	                labsDTO.getScope(),
	                labsDTO.getTargetDescription(),
	                labsDTO.getDuration(),
	                fileName
					);
	        return new ResponseEntity<>(newlabs, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>("Failed to upload the lab: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
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
