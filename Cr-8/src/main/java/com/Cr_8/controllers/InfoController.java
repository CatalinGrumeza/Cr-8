package com.Cr_8.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Cr_8.entities.Info;
import com.Cr_8.entities.InfoRequest;
import com.Cr_8.servicies.InfoService;
import com.Cr_8.servicies.MailService;



@RestController
public class InfoController {
	
	@Autowired
	private InfoService infoService;
	@Autowired
	private MailService mailService;
	
	//shows all the infos
	@GetMapping("/api/index")
	public ResponseEntity<List<Info>> getAll() {
		List<Info> infoList=infoService.getAllInfo();
		return ResponseEntity.ok(infoList);
	}
	//creates info,checks if user exists and creates it if it doesn't and then sends email to user and admin
	@PostMapping("/api/createInfo")
	public ResponseEntity<String> postMethodName(@RequestBody InfoRequest infoRequest) {
	    infoService.createInfo(infoRequest.getName(), infoRequest.getSurname(), infoRequest.getPhone(), infoRequest.getEmail(), infoRequest.getText());
	    mailService.sendEmail(infoRequest.getEmail(), infoRequest.getText(), "Richiesta informazioni", infoRequest.getName(), infoRequest.getSurname(), "informazioni");
	    mailService.sendEmailToAdmin(infoRequest.getEmail(), infoRequest.getText(), "Richiesta informazioni", infoRequest.getName(), infoRequest.getSurname());
	    return ResponseEntity.ok("Info created successfully!");
	}

	
}