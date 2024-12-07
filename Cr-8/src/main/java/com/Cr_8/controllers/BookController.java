package com.Cr_8.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Cr_8.entities.BookRequest;
import com.Cr_8.servicies.BookService;
import com.Cr_8.servicies.MailService;

import jakarta.validation.Valid;

@RestController
public class BookController {
	
	@Autowired
	private BookService bookService;
	@Autowired
	private MailService mailService;
	
	//shows all the book requests
	@GetMapping("/api/book-index")
	public ResponseEntity<List<BookRequest>> getAll() {
		List<BookRequest> bookList = bookService.getAllbookRequest();
		return ResponseEntity.ok(bookList);
	}
	
	//creates book request,checks if user exists and creates it if it doesn't and then sends email to user and admin
	@PostMapping("/api/createBookRequest")
	public ResponseEntity<?> postBookRequest(@Valid @RequestBody InfoFormRequest infoRequest, BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity<>(errore(result).toString(), HttpStatus.BAD_REQUEST);
		} else {
		    bookService.createInfo(infoRequest.getName(), infoRequest.getSurname(), infoRequest.getPhone(), infoRequest.getEmail(), infoRequest.getText());
		    mailService.sendEmail(infoRequest.getEmail(), infoRequest.getText(), "Richiesta informazioni", infoRequest.getName(), infoRequest.getSurname(), "informazioni");
		    mailService.sendEmailToAdmin(infoRequest.getEmail(), infoRequest.getText(), "Richiesta informazioni", infoRequest.getName(), infoRequest.getSurname());
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
