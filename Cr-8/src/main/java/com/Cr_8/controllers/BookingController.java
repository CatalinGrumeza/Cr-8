package com.Cr_8.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Cr_8.entities.BookedDate;
import com.Cr_8.entities.BookingRequest;
import com.Cr_8.servicies.BookedDatesService;
import com.Cr_8.servicies.BookingService;
import com.Cr_8.servicies.MailService;

import dto.BookedDateDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
@RequestMapping("/api")
@RestController
public class BookingController {
	
	@Autowired
	private BookingService bookService;
	@Autowired
	private MailService mailService;
	@Autowired
	private BookedDatesService bookedDatesService;
	
	//shows all the book requests
	@GetMapping("/all-bookings")
	 @Operation(
		        summary = "GET Api for booking requests",
		        description = "This endpoint provides a list of booked requests."
		    )
	public ResponseEntity<List<BookingRequest>> getAll() {
		List<BookingRequest> bookList = bookService.getAllbookRequest();
		return ResponseEntity.ok(bookList);
	}
	
	//creates book request,checks if user exists and creates it if it doesn't and then sends email to user and admin
	@PostMapping("/create-booking")
	 @Operation(
		        summary = "POST Api for creating a booking request",
		        description = "This endpoint provides a POST for creating bookings."
		        		+ "  \"bookType\": \"mattina-pomeriggio\""
		    )
	public ResponseEntity<?> postBookRequest(@Valid @RequestBody BookingFormRequest bookRequest, BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity<>(errore(result).toString(), HttpStatus.BAD_REQUEST);
		} else {
		    bookService.createBooking(bookRequest.getDataFrom(),bookRequest.getDataTo(),bookRequest.getAdditionalDetails(),bookRequest.getParticipantNumber(),bookRequest.getBookType(),bookRequest.getVistorType(),bookRequest.getName(),bookRequest.getSurname(),bookRequest.getPhone(),bookRequest.getEmail());
		    mailService.sendEmail(bookRequest.getEmail(), bookRequest.getAdditionalDetails(), "Richiesta di Prenotazione", bookRequest.getName(), bookRequest.getSurname(), "Prenotazione");
		    mailService.sendEmailToAdmin(bookRequest.getEmail(), bookRequest.getAdditionalDetails(), "Richiesta di Prenotazione", bookRequest.getName(), bookRequest.getSurname(),"Prenotazione",bookRequest.getPhone());
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
	@GetMapping("/all-booked-dates")
	@Operation(
	        summary = "GET Api for displaying all the booked dates",
	        description = "This endpoint provides a list of booked dates."
	    )
	public ResponseEntity<List<BookedDate>> getAllBookedDates(){
		return ResponseEntity.ok(bookedDatesService.getAllBookedDates());
	}
	@PostMapping("/book-date")
	@Operation(
			summary = "POST Api for creating a booked dates",
			description = "This endpoint provides a POST for creating booked dates."
			)
	public ResponseEntity<?> bookDate(@RequestBody BookedDateDTO bookedDate) {
		bookedDatesService.createBookedDates(bookedDate);
		return ResponseEntity.ok("Book date created successfully!");
	}
	
	
}
