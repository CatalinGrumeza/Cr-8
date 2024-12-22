package com.Cr_8.servicies;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Cr_8.entities.BookedDate;
import com.Cr_8.entities.BookingRequest;
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
	private BookingService bookingService;
	
	
	public List<BookedDate> getAllBookedDates(){
		return bookedDatesRepo.findAll();
	}
	
	public void createBookedDates(BookedDateDTO bookedDayDTO) {
		
		
		BookedDate booked=new BookedDate();
	
		Optional<BookingRequest> bookingRequest= bookingService.getBookingRequestByid(bookedDayDTO.getIdBookingRequest());
		if (bookingRequest.isEmpty()) {
	        throw new ResourceNotFoundException("Booking request with id " + bookedDayDTO.getIdBookingRequest() + " not found.");
	    }
		booked.setDate(bookedDayDTO.getDate());
		DayFraction dayFraction=new DayFraction();
		dayFraction.setEvening(bookedDayDTO.isEvening());
		dayFraction.setMorning(bookedDayDTO.isMorning());
		booked.setDayFractions(dayFraction);
		dayFractionRepo.save(dayFraction);
		bookedDatesRepo.save(booked);
		
	}
}
