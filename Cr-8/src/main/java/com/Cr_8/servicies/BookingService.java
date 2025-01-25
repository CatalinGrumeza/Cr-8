package com.Cr_8.servicies;
import com.Cr_8.dto.BookingFormRequest;
import com.Cr_8.entities.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Cr_8.repositories.BookedDatesRepo;
import com.Cr_8.repositories.BookingRepo;
import com.Cr_8.repositories.LabsRepo;
import com.Cr_8.repositories.ReferenceRepo;
import com.Cr_8.repositories.StatusRepo;

import jakarta.transaction.Transactional;
@Service
public class BookingService {


    @Autowired
    private BookingRepo bookrepo; // Repository for managing BookingRequest entities

    @Autowired
    private StatusRepo statusrepo; // Repository for managing Status entities

    @Autowired
    private ReferenceRepo referenceRepo; // Repository for managing Reference entities

    @Autowired
    private BookedDatesRepo bookedDatesRepo; // Repository for managing BookedDate entities

    @Autowired
    private MailService mailService; // Service for sending emails

    @Autowired
    private LabsRepo labRepo; // Repository for managing Labs entities

    /**
     * Retrieves a BookingRequest by its ID.
     * 
     * @param id The ID of the booking request.
     * @return An Optional containing the BookingRequest, if found.
     */
    public Optional<BookingRequest> getBookingRequestByid(Long id) {
        return bookrepo.findById(id);
    }

    /**
     * Creates a new booking request with associated details.
     * 
     * @param dataFrom       Start date of the booking.
     * @param dataTo         End date of the booking.
     * @param additional     Additional details about the booking.
     * @param partiNumber    Number of participants.
     * @param bookType       Type of booking.
     * @param visitorType    Type of visitor.
     * @param name           First name of the person making the booking.
     * @param surname        Last name of the person making the booking.
     * @param phone          Phone number of the person making the booking.
     * @param labsName       List of lab names involved in the booking.
     * @param email          Email address of the person making the booking.
     * @param numberOfDays   Number of days for the booking.
     */
    @Transactional
    public void createBooking(BookingFormRequest booking) {

        BookingRequest book = new BookingRequest();
        book.setCreatedAt(LocalDate.now());
        book.setDataFrom(booking.getDataFrom());
        book.setDataTo(booking.getDataTo());
        book.setAdditionalDetails(booking.getAdditionalDetails());
        book.setParticipantNumber(booking.getParticipantNumber());
        book.setBookType(booking.getBookType());
        book.setVistorType(booking.getVisitorType());
        book.setStatus(statusrepo.findById(1)); // Set initial status (default to ID 1)
        book.setNumberOfDays(booking.getNumberOfDays());

        // Handle reference creation or update based on email
        Optional<Reference> ref = referenceRepo.findByEmail(booking.getEmail().toLowerCase());
        Reference user;
        if (ref.isPresent()) {
            user = ref.get();
            user.setPhoneNumber(booking.getPhone()); // Update phone number if reference already exists
        } else {
            user = new Reference();
            user.setEmail(booking.getEmail().toLowerCase());
            user.setFirstName(booking.getName());
            user.setLastName(booking.getSurname());
            user.setPhoneNumber(booking.getPhone());
            user = referenceRepo.save(user); // Save new reference
        }
        book.setReference(user);

        // Create and associate a BookedDate with the booking request
        BookedDate bookedDate = new BookedDate();
        bookedDate.setBookingRequest(book);
        book.setBookedDate(bookedDate);

        // Associate labs with the booking request
        for (String lab : booking.getLabs()) {
            Labs Lab1 = labRepo.findByName(lab).get();
            book.addLab(Lab1);
        }

        // Save the booking request
        bookrepo.save(book);
    }

    /**
     * Retrieves all booking requests.
     * 
     * @return A list of all booking requests.
     */
    public List<BookingRequest> getAllbookRequest() {
        return bookrepo.findAll();
    }

    /**
     * Deletes a booking request by its ID.
     * 
     * @param id The ID of the booking request to delete.
     */
    public void deleteById(Long id) {
        BookingRequest bookid = bookrepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("book not found with id: " + id));
        bookrepo.delete(bookid);
    }

    /**
     * Updates the status of a booking request and performs related actions.
     * 
     * @param bookId     The ID of the booking request.
     * @param statusName The new status name to assign.
     */
    public void updateBookRequest(Long bookId, String statusName) {
        BookingRequest booking = bookrepo.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("book not found with id: " + bookId));

        // Handle cancellation logic
        if (statusName.equalsIgnoreCase("cancelled")) {
            booking.getBookedDate().setDate(null); // Clear the booking date
            booking.getBookedDate().setDayFractions(null); // Clear day fractions
            mailService.sendEmailCancelledBooked(booking); // Notify the user about cancellation
        }

        // Update the status
        booking.setStatus(statusrepo.findByName(statusName).get());
        bookrepo.save(booking);
    }

    /**
     * Retrieves a Status entity by its ID.
     * 
     * @param statusId The ID of the status.
     * @return The Status entity.
     */
    public Status status(int statusId) {
        return statusrepo.findById(statusId);
    }

}
