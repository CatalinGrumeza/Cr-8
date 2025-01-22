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
	private BookingRepo bookrepo;
	@Autowired
	private StatusRepo statusrepo;
	@Autowired
	private ReferenceRepo referenceRepo;
	@Autowired
	private MailService mailService;
	@Autowired
	private LabsRepo labRepo;
	
	public Optional <BookingRequest> getBookingRequestByid(Long id) {
		return bookrepo.findById(id);
	}
	@Transactional
	public void createBooking(BookingFormRequest booking) {
		

	    BookingRequest book = new BookingRequest();
	    book.setCreatedAt(LocalDate.now());
	    book.setDataFrom(booking.getDataFrom());
	    book.setDataTo(booking.getDataTo());
	    book.setAdditionalDetails(booking.getAdditionalDetails());
	    book.setParticipantNumber(booking.getParticipantNumber());
	    book.setBookType(booking.getBookType());
	    book.setVistorType(book.getVistorType());
	    book.setStatus(statusrepo.findById(1));
	    book.setNumberOfDays(booking.getNumberOfDays());

	    Optional<Reference> ref = referenceRepo.findByEmail(booking.getEmail().toLowerCase());
	    Reference user;
	    if(ref.isPresent()) {
	    		user = ref.get();
	    		user.setPhoneNumber(booking.getPhone());
	    } else {
	        user = new Reference();
	        user.setEmail(booking.getEmail().toLowerCase());
	        user.setFirstName(booking.getName());
	        user.setLastName(booking.getSurname());
	        user.setPhoneNumber(booking.getPhone());
	    }
	    user = referenceRepo.save(user);
	    book.setReference(user);

	    BookedDate bookedDate = new BookedDate();
	    bookedDate.setBookingRequest(book);
	    book.setBookedDate(bookedDate);
	   
	    for (String lab : booking.getLabs()) {
	    	if(labRepo.findByName(lab).isPresent()) {
	    		Labs lab1=labRepo.findByName(lab).get();
	    		book.addLab(lab1);
	    	}
	    	
			
		}
	    bookrepo.save(book);
	}
	
	public List<BookingRequest> getAllbookRequest() {
		return bookrepo.findAll();
	}
	public void deleteById(Long id) {
		BookingRequest bookid = bookrepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("book not found with id:"+id));
		bookrepo.delete(bookid);
	}
	public void updateBookRequest(Long bookId, String statusName) {
		BookingRequest booking = bookrepo.findById(bookId)
				.orElseThrow(() -> new IllegalArgumentException("book not found with id: "+bookId));
		if(statusName.equalsIgnoreCase("cancelled")) {
			booking.getBookedDate().setDate(null);
			booking.getBookedDate().setDayFractions(null);
			booking.getBookedDate().setToDate(null);
			mailService.sendEmailCancelledBooked(booking);
		}
		booking.setStatus(statusrepo.findByName(statusName).get());
		bookrepo.save(booking);
	}
	public Status status(int statusId) {
		return statusrepo.findById(statusId);
	}
	
	
}
