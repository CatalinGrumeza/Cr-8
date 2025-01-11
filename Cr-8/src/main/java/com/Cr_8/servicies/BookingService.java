package com.Cr_8.servicies;
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
	private BookedDatesRepo bookedDatesRepo;
	@Autowired
	private MailService mailService;
	@Autowired
	private LabsRepo labRepo;
	
	public Optional <BookingRequest> getBookingRequestByid(Long id) {
		return bookrepo.findById(id);
	}
	@Transactional
	public void createBooking(LocalDate dataFrom,
	                         LocalDate dataTo,
	                         String additional,
	                         int partiNumber,
	                         String bookType,
	                         String visitorType,
	                         String name,
	                         String surname,
	                         String phone,
	                         List<String> labsName,
	                         String email,
	                         int numberOfDays) {
		

	    BookingRequest book = new BookingRequest();
	    book.setCreatedAt(LocalDate.now());
	    book.setDataFrom(dataFrom);
	    book.setDataTo(dataTo);
	    book.setAdditionalDetails(additional);
	    book.setParticipantNumber(partiNumber);
	    book.setBookType(bookType);
	    book.setVistorType(visitorType);
	    book.setStatus(statusrepo.findById(1));
	    book.setNumberOfDays(numberOfDays);

	    Optional<Reference> ref = referenceRepo.findByEmail(email);
	    Optional<Reference> refPhone = referenceRepo.findByPhoneNumber(phone);
	    Reference user;
	    if(ref.isPresent()&&refPhone.isPresent()&&ref.get().equals(refPhone.get())) {
	    		user = ref.get();
	    } else {
	        user = new Reference();
	        user.setEmail(email);
	        user.setFirstName(name);
	        user.setLastName(surname);
	        user.setPhoneNumber(phone);
	        user = referenceRepo.save(user);
	    }
	    book.setReference(user);

	    BookedDate bookedDate = new BookedDate();
	    bookedDate.setBookingRequest(book);
	    book.setBookedDate(bookedDate);
	   
	    for (String string : labsName) {
			Labs existlabs =labRepo.findByName(string).get();
			book.setLabsSet(existlabs);
			
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
			mailService.sendEmailCancelledBooked(booking);
		}
		booking.setStatus(statusrepo.findByName(statusName).get());
		bookrepo.save(booking);
	}
	public Status status(int statusId) {
		return statusrepo.findById(statusId);
	}
	
	
}
