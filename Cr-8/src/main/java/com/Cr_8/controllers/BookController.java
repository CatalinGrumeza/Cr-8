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
	public ResponseEntity<?> postBookRequest(@Valid @RequestBody BookFormRequest bookRequest, BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity<>(errore(result).toString(), HttpStatus.BAD_REQUEST);
		} else {
		    bookService.createBook(bookRequest.getDataFrom(),bookRequest.getDataTo(),bookRequest.getAdditionalDetails(),bookRequest.getParticipantNumber(),bookRequest.getBookType(),bookRequest.getVistorType(),bookRequest.getName(),bookRequest.getSurname(),bookRequest.getPhone(),bookRequest.getEmail());
		    mailService.sendEmail(bookRequest.getEmail(), /*body*/ "", "Richiesta informazioni", bookRequest.getName(), bookRequest.getSurname(), "informazioni");
		    mailService.sendEmailToAdmin(bookRequest.getEmail(), /*body*/ "", "Richiesta informazioni", bookRequest.getName(), bookRequest.getSurname());
		    return ResponseEntity.ok("Book request created successfully!");
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
