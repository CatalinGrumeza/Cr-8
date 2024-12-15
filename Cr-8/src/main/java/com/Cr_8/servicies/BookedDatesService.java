package com.Cr_8.servicies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Cr_8.entities.BookedDate;
import com.Cr_8.entities.DayFraction;
import com.Cr_8.entities.Reference;
import com.Cr_8.exceptions.ResourceNotFoundException;
import com.Cr_8.repositories.BookedDatesRepo;
import com.Cr_8.repositories.DayFractionRepo;

import dto.BookedDateDTO;


@Service
public class BookedDatesService {
	@Autowired
	private BookedDatesRepo bookedDatesRepo;
	@Autowired
	private DayFractionRepo dayFractionRepo;
	@Autowired
	private ReferenceService referenceService;
	
	
	public List<BookedDate> getAllBookedDates(){
		return bookedDatesRepo.findAll();
	}
	
	public void createBookedDates(BookedDateDTO bookedDayDTO) {
		
		
		BookedDate booked=new BookedDate();
		BookedDate bookedDay=bookedDayDTO.getBookedDate();
		Reference reference=referenceService.findByEmail(bookedDayDTO.getEmail());
		if (reference == null) {
	        throw new ResourceNotFoundException("Reference with email " + bookedDayDTO.getEmail() + " not found.");
	    }
		booked.setReference(reference);
		booked.setDate(bookedDay.getDate());
		DayFraction dayFraction=new DayFraction();
		dayFraction.setEvening(bookedDay.getDayFractions().isEvening());
		dayFraction.setMorning(bookedDay.getDayFractions().isMorning());
		booked.setDayFractions(dayFraction);
		dayFractionRepo.save(dayFraction);
		bookedDatesRepo.save(booked);
		
	}
}
