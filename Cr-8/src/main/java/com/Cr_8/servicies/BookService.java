package com.Cr_8.servicies;
import com.Cr_8.entities.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.Cr_8.repositories.BookRepo;
import com.Cr_8.repositories.ReferenceRepo;
import com.Cr_8.repositories.StatusRepo;

import jakarta.transaction.Transactional;

@Service
public class BookService {

	private final BookRepo bookrepo;
	private final StatusRepo statusrepo;
	
	@Autowired
	private ReferenceRepo referenceRepo;
	
	public BookService(BookRepo bookrepo, StatusRepo statusrepo) {
		this.bookrepo = bookrepo;
		this.statusrepo = statusrepo;

	}
	
	@Transactional//rollback in case of fail when saving
	public void createBook(LocalDate dataFrom
			,LocalDate dataTo
			,String additional
			,int partiNumber
			,String bookType
			,String visitorType
			,String name
			,String surname
			,String phone
			,String email) {
		
		BookRequest book = new BookRequest();
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
			book.setReference(user);
		}
		
		book.setStatus(status(1));
		bookrepo.save(book);
	}
	
	public List<BookRequest> getAllbookRequest() {
		return bookrepo.findAll();
	}
	public void deleteById(int id) {
		BookRequest bookid = bookrepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("book not found with id:"+id));
		bookrepo.delete(bookid);
	}
	public void updateBookRequest(int bookId, int statusid) {
		BookRequest booking = bookrepo.findById(bookId)
				.orElseThrow(() -> new IllegalArgumentException("book not found with id: "+bookId));
		
		booking.setStatus(status(statusid));
		bookrepo.save(booking);
	}
	public Status status(int statusId) {
		return statusrepo.findById(statusId);
	}
	
	
}
