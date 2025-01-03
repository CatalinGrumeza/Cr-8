package com.Cr_8.servicies;

import java.util.List;

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
		    return bookedDatesRepo.findByDateIsNotNull();
	}
	
	public void createBookedDates(BookedDateDTO bookedDayDTO) {
		
		
		BookingRequest bookingRequest= bookingService.getBookingRequestByid(bookedDayDTO.getIdBookingRequest());
		if (bookingRequest == null) {
	        throw new ResourceNotFoundException("Booking request with id " + bookedDayDTO.getIdBookingRequest() + " not found.");
	    }
		BookedDate booked=bookingRequest.getBookedDate();
		booked.setDate(bookedDayDTO.getDate());
		if(bookedDayDTO.isMorning())
			booked.setDayFractions(dayFractionRepo.findById((long) 1).get());
		if(bookedDayDTO.isEvening())
			booked.setDayFractions(dayFractionRepo.findById((long) 2).get());
		if(bookedDayDTO.isMorning()&& bookedDayDTO.isEvening())
			booked.setDayFractions(dayFractionRepo.findById((long) 3).get());
		bookedDatesRepo.save(booked);
		
	}
}
