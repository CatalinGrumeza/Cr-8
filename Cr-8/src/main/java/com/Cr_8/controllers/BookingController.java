package com.Cr_8.controllers;

import java.util.ArrayList;
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

import com.Cr_8.dto.BookedDTO;
import com.Cr_8.dto.BookedDateDTO;
import com.Cr_8.dto.BookingFormRequest;
import com.Cr_8.dto.LabsDTO;
import com.Cr_8.entities.BookedDate;
import com.Cr_8.entities.BookingRequest;
import com.Cr_8.entities.Labs;
import com.Cr_8.servicies.BookedDatesService;
import com.Cr_8.servicies.BookingService;
import com.Cr_8.servicies.LabsService;
import com.Cr_8.servicies.MailService;
import com.Cr_8.servicies.StatusService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
@RequestMapping("/api")
@RestController
public class BookingController {
	@Autowired
	private LabsService labsService ;
	@Autowired
	private BookingService bookService;
	@Autowired
	private MailService mailService;
	@Autowired
	private BookedDatesService bookedDatesService;
	
	@Autowired
	private StatusService statusService;
	
	//shows all the book requests
	 @Operation(
		        summary = "GET Api for booking requests",
		        description = "This endpoint provides a list of booked requests."
		    )
	 @Tag(name = "Dashboard Endpoint")
	 @GetMapping("/all-bookings")
	public ResponseEntity<List<BookingRequest>> getAll() {
		List<BookingRequest> bookList = bookService.getAllbookRequest();
		return ResponseEntity.ok(bookList);
	}
	
	//creates book request,checks if user exists and creates it if it doesn't and then sends email to user and admin
	 @Operation(
		        summary = "POST Api for creating a booking request",
		        description = "This endpoint provides a POST for creating bookings."
		        		+ "  \"bookType\": \"morning,evening or full day\""
		    )
	 @Tag(name = "Public Endpoint")
	 @PostMapping("/pub/create-booking")
	public ResponseEntity<?> postBookRequest(@Valid @RequestBody BookingFormRequest bookRequest, BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity<>(errore(result).toString(), HttpStatus.BAD_REQUEST);
		} else {
		    bookService.createBooking(bookRequest.getDataFrom(),bookRequest.getDataTo(),bookRequest.getAdditionalDetails(),bookRequest.getParticipantNumber(),bookRequest.getBookType(),bookRequest.getVistorType(),bookRequest.getName(),bookRequest.getSurname(),bookRequest.getPhone(),bookRequest.getLabs(),bookRequest.getEmail(),bookRequest.getNumberOfDays());
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
	@Operation(
	        summary = "GET Api for displaying all the booked dates",
	        description = "This endpoint provides a list of booked dates."
	    )
	@Tag(name = "Dashboard Endpoint")
	@GetMapping("/all-booked-dates")
	public ResponseEntity<List<BookedDTO>> getAllBookedDates(){
		List<BookedDate> list=bookedDatesService.getAllBookedDates();
		List<BookedDTO> returnList=new ArrayList<BookedDTO>();
		for (BookedDate bookedDate : list) {
			BookedDTO bookDto = new BookedDTO();
			bookDto.setIdBookedDate(bookedDate.getId());
			bookDto.setIdBookingRequest(bookedDate.getBookingRequest().getId());
			bookDto.setEvening(bookedDate.getDayFractions().isEvening());
			bookDto.setMorning(bookedDate.getDayFractions().isMorning());
			bookDto.setIdReference(bookedDate.getBookingRequest().getReference().getId());
			returnList.add(bookDto);
			}
		return ResponseEntity.ok(returnList);
	}
	@Operation(
			summary = "POST Api for creating a booked dates",
			description = "This endpoint provides a POST for creating booked dates."
			)
	@Tag(name = "Dashboard Endpoint")
	@PostMapping("/book-date")
	public ResponseEntity<?> bookDate(@RequestBody BookedDateDTO bookedDate) {
		bookedDatesService.createBookedDates(bookedDate);
		bookService.updateBookRequest(bookedDate.getIdBookingRequest(), "completed");
		mailService.sendEmailConfirmBooked(bookService.getBookingRequestByid(bookedDate.getIdBookingRequest()).get(), bookedDate.getDate());
		return ResponseEntity.ok("Book date created successfully!");
	}
	@Operation(
	        summary = "POST Api for updating a booking status request",
	        description = "The possible options are Pending    In Progress    Completed     Cancelled"
	    )
	@Tag(name = "Dashboard Endpoint")
	@PostMapping("/update-booking-status")
	public ResponseEntity<String> statusChange(@RequestParam Long bookingId,String status){
		if (bookService.getBookingRequestByid(bookingId).isPresent()) {
			if (statusService.findByName(status) != null) {
				bookService.updateBookRequest(bookingId, status);
				return ResponseEntity.ok("Status changed successfully!");
			}
			return new ResponseEntity<>("Invalid status", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Invalid bookingId", HttpStatus.BAD_REQUEST);
	}
	@Tag(name = "Public Endpoint")
	@GetMapping("/pub/labs")
	public ResponseEntity<?> getAllLabs(){
		List<Labs> allLabs = labsService.getAllLabs();
		return new ResponseEntity<>(allLabs, HttpStatus.OK);
	}
	@Tag(name = "Dashboard Endpoint")
	@PostMapping("/add-new-labs")
	public ResponseEntity<?> addNewLabs(@RequestBody LabsDTO labsDTO){
		String newlabs =labsService.addNewLabs(labsDTO.getName(), labsDTO.getDescription(),labsDTO.getScope(),labsDTO.getTargetDescription());
		return new ResponseEntity<>(newlabs, HttpStatus.OK);
	}
	
}
