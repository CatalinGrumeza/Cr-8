package com.Cr_8.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Cr_8.entities.Info;
import com.Cr_8.servicies.InfoService;



@RestController
public class InfoController {
	
	@Autowired
	private InfoService infoService;
	@GetMapping("/api/index")
	public ResponseEntity<List<Info>> getAll() {
		List<Info> infoList=infoService.getAllInfo();
		return ResponseEntity.ok(infoList);
	}
	@PostMapping("/api/createInfo")
	public ResponseEntity<String> postMethodName(@RequestBody Info info) {
		infoService.createInfo(info);
		return ResponseEntity.ok("Info created successfully!");
	}
	
}