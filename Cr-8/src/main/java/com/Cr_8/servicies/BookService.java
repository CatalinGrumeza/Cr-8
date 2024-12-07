package com.Cr_8.servicies;
import com.Cr_8.entities.*;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;


import com.Cr_8.repositories.BookRepo;

import com.Cr_8.repositories.StatusRepo;

@Service
public class BookService {

	private final BookRepo bookrepo;
	private final StatusRepo statusrepo;
	
	public BookService(BookRepo bookrepo, StatusRepo statusrepo) {
		this.bookrepo = bookrepo;
		this.statusrepo = statusrepo;

	}
	public void createBook(LocalDate datafrom
			,LocalDate datato
			,LocalDate CreatedAt
			,String additional
			,int partiNumber
			,String booktype
			,String vitortype
			,Reference ref) {
		
		BookRequest book = new BookRequest();
		book.setCreatedAt(LocalDate.now());
		book.setDataFrom(datafrom);
		book.setDataTo(datato);
		book.setBookType(booktype);
		book.setParticipantNumber(partiNumber);
		book.setReference(ref);
		book.setStatus(status(1));
		book.setAdditionalDetails(additional);
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
