package com.Cr_8.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Cr_8.entities.Info;
import com.Cr_8.servicies.InfoService;
import com.Cr_8.servicies.MailService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;


@RequestMapping("/api")
@RestController
public class InfoController {
	
	@Autowired
	private InfoService infoService;
	@Autowired
	private MailService mailService;
	
	//shows all the infos
	@GetMapping("/all-info")
	@Operation(
	        summary = "GET Api for displaying all the info requests",
	        description = "This endpoint provides a list of booked dates."
	    )
	public ResponseEntity<List<Info>> getAll() {
		List<Info> infoList=infoService.getAllInfo();
		return ResponseEntity.ok(infoList);
	}
	//creates info,checks if user exists and creates it if it doesn't and then sends email to user and admin
	@PostMapping("/create-info")
	@Operation(
	        summary = "POST Api for creating an info request",
	        description = "This endpoint provides a POST method for creating info requests"
	    )
	public ResponseEntity<?> postMethodName(@Valid @RequestBody InfoFormRequest infoRequest, BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity<>(errore(result).toString(), HttpStatus.BAD_REQUEST);
		}else {
	    infoService.createInfo(infoRequest.getName(), infoRequest.getSurname(), infoRequest.getPhone(), infoRequest.getEmail(), infoRequest.getText());
	    mailService.sendEmail(infoRequest.getEmail(), infoRequest.getText(), "Richiesta informazioni", infoRequest.getName(), infoRequest.getSurname(), "informazioni");
	    mailService.sendEmailToAdmin(infoRequest.getEmail(), infoRequest.getText(), "Richiesta informazioni", infoRequest.getName(), infoRequest.getSurname(),"informazioni",infoRequest.getPhone());
	    return ResponseEntity.ok("Info created successfully!");
		}
	}
	public StringBuilder errore(BindingResult result) {
		
		StringBuilder errors = new StringBuilder();
        result.getFieldErrors().forEach(error -> 
            errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n")
        );
		return errors;
		
	}

	
}