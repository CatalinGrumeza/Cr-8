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
import com.Cr_8.entities.BookedDate;
import com.Cr_8.entities.BookingRequest;
import com.Cr_8.servicies.BookedDatesService;
import com.Cr_8.servicies.BookingService;
import com.Cr_8.servicies.MailService;
import com.Cr_8.servicies.StatusService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RequestMapping("/api")
@RestController
public class BookingController {

    // Injecting service dependencies through Spring's dependency injection
    @Autowired
    private BookingService bookService;
    @Autowired
    private MailService mailService;
    @Autowired
    private BookedDatesService bookedDatesService;
    @Autowired
    private StatusService statusService;
    
    /**
     * Retrieves all booking requests.
     * @return ResponseEntity containing a list of all BookingRequest objects
     */
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
    
    /**
     * Creates a new booking request. If the user does not exist, this method 
     * creates the user and sends confirmation emails to both the user and the admin.
     * @param bookRequest the request body containing booking details
     * @param result captures any validation errors
     * @return ResponseEntity indicating the result of the operation
     */
    @Operation(
        summary = "POST Api for creating a booking request",
        description = "This endpoint provides a POST for creating bookings."
                 + "  \"bookType\": \"morning, evening or full day\""
    )
    @Tag(name = "Public Endpoint")
    @PostMapping("/pub/create-booking")
    public ResponseEntity<?> postBookRequest(@Valid @RequestBody BookingFormRequest bookRequest, BindingResult result) {
        // Check if there are validation errors in the request body
        if(result.hasErrors()) {
            return new ResponseEntity<>(error(result).toString(), HttpStatus.BAD_REQUEST);
        } else {
            // Create the booking request and send confirmation emails
            bookService.createBooking(bookRequest);
            mailService.sendEmailBooking(bookRequest, "Prenotazione");
            mailService.sendEmailToAdmin(bookRequest.getEmail(), bookRequest.getAdditionalDetails(),
                "Richiesta di Prenotazione", bookRequest.getName(), bookRequest.getSurname(), "Prenotazione", bookRequest.getPhone());
            return ResponseEntity.ok("Book request created successfully!");
        }
    }
    
    /**
     * Constructs a string  that contains validation error messages from binding results.
     * @param result the BindingResult containing errors
     * @return a StringBuilder containing error messages
     */
    public StringBuilder error(BindingResult result) {
        StringBuilder errors = new StringBuilder();
        result.getFieldErrors().forEach(error -> 
            errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n")
        );
        return errors;
    }

    /**
     * Retrieves all booked dates.
     * @return ResponseEntity containing a list of BookedDTO objects
     */
    @Operation(
        summary = "GET Api for displaying all the booked dates",
        description = "This endpoint provides a list of booked dates."
    )
    @Tag(name = "Dashboard Endpoint")
    @GetMapping("/all-booked-dates")
    public ResponseEntity<List<BookedDTO>> getAllBookedDates() {
        List<BookedDate> list = bookedDatesService.getAllBookedDates();
        List<BookedDTO> returnList = new ArrayList<>();
        
        // Transform BookedDate entities into BookedDTO objects for the response
        for (BookedDate bookedDate : list) {
            BookedDTO bookDto = new BookedDTO();
            bookDto.setIdBookedDate(bookedDate.getId());
            bookDto.setIdBookingRequest(bookedDate.getBookingRequest().getId());
            bookDto.setAllDay(bookedDate.getDayFractions().isFullDay());
            bookDto.setMorning(bookedDate.getDayFractions().isMorning());
            bookDto.setIdReference(bookedDate.getBookingRequest().getReference().getId());
            bookDto.setDate(bookedDate.getDate());
            bookDto.setToDate(bookedDate.getToDate());
            bookDto.setReferenceName(bookedDate.getBookingRequest().getReference().getFirstName() + " " + 
                                       bookedDate.getBookingRequest().getReference().getLastName());
            returnList.add(bookDto);
        }
        return ResponseEntity.ok(returnList);
    }

    /**
     * Creates a new booked date and updates the corresponding booking request status to "completed".
     * Sends a confirmation email to the user.
     * @param bookedDate the request body containing booked date details
     * @return ResponseEntity indicating the result of the operation
     */
    @Operation(
        summary = "POST Api for creating a booked dates",
        description = "This endpoint provides a POST for creating booked dates."
    )
    @Tag(name = "Dashboard Endpoint")
    @PostMapping("/book-date")
    public ResponseEntity<?> bookDate(@RequestBody BookedDateDTO bookedDate) {
        bookedDatesService.createBookedDates(bookedDate);
        bookService.updateBookRequest(bookedDate.getIdBookingRequest(), "completed");
        mailService.sendEmailConfirmBooked(bookService.getBookingRequestByid(bookedDate.getIdBookingRequest()).get());
        return ResponseEntity.ok("Book date created successfully!");
    }

    /**
     * Updates the status of a booking request based on the provided booking ID and status.
     * @param bookingId the ID of the booking request to update
     * @param status the new status for the booking request
     * @return ResponseEntity indicating the result of the operation
     */
    @Operation(
        summary = "POST Api for updating a booking status request",
        description = "The possible options are Pending, In Progress, Completed, Cancelled."
    )
    @Tag(name = "Dashboard Endpoint")
    @PostMapping("/update-booking-status")
    public ResponseEntity<String> statusChange(@RequestParam Long bookingId, String status) {
        // Check if the booking request exists
        if (bookService.getBookingRequestByid(bookingId).isPresent()) {
            // Validate the new status
            if (statusService.findByName(status) != null) {
                bookService.updateBookRequest(bookingId, status);
                return ResponseEntity.ok("Status changed successfully!");
            }
            return new ResponseEntity<>("Invalid status", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Invalid bookingId", HttpStatus.BAD_REQUEST);
    }
}