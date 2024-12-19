package com.Cr_8.servicies;
import com.Cr_8.entities.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.Cr_8.repositories.BookingRepo;
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
	
	public BookingRequest getBookingRequestByid(Long id) {
		return bookrepo.findById(id).get();
	}
	
	@Transactional//rollback in case of fail when saving
	public void createBooking(LocalDate dataFrom
			,LocalDate dataTo
			,String additional
			,int partiNumber
			,String bookType//the time of the day for the booking
			,String visitorType
			,String name
			,String surname
			,String phone
			,String email) {
		
		BookingRequest book = new BookingRequest();
		book.setCreatedAt(LocalDate.now());
		book.setDataFrom(dataFrom);
		book.setDataTo(dataTo);
		book.setAdditionalDetails(additional);
		book.setParticipantNumber(partiNumber);
		book.setBookType(bookType);
		book.setVistorType(visitorType);
		Optional<Reference> ref =referenceRepo.findByEmail(email);
		if(ref.isPresent()) {
			
			book.setReference(ref.get());
		} else {
			Reference user=new Reference();
			user.setEmail(email);
			user.setFirstName(name);
			user.setLastName(surname);
			user.setPhoneNumber(phone);
			referenceRepo.save(user);
			book.setReference(user);
		}
		
		book.setStatus(status(1));
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
	public void updateBookRequest(Long bookId, int statusid) {
		BookingRequest booking = bookrepo.findById(bookId)
				.orElseThrow(() -> new IllegalArgumentException("book not found with id: "+bookId));
		
		booking.setStatus(status(statusid));
		bookrepo.save(booking);
	}
	public Status status(int statusId) {
		return statusrepo.findById(statusId);
	}
	
	
}
