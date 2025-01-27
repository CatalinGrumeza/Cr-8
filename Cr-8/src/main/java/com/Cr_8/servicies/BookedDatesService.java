package com.Cr_8.servicies;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Cr_8.dto.BookedDateDTO;
import com.Cr_8.entities.BookedDate;
import com.Cr_8.entities.BookingRequest;
import com.Cr_8.entities.DayFraction;
import com.Cr_8.entities.Reference;
import com.Cr_8.exceptions.ResourceNotFoundException;
import com.Cr_8.repositories.BookedDatesRepo;
import com.Cr_8.repositories.DayFractionRepo;

@Service
public class BookedDatesService {

    @Autowired
    private BookedDatesRepo bookedDatesRepo; // Repository for managing BookedDate entities

    @Autowired
    private DayFractionRepo dayFractionRepo; // Repository for managing DayFraction entities

    @Autowired
    private BookingService bookingService; // Service for handling booking-related logic

    /**
     * Retrieves all booked dates that have a non-null date value.
     * 
     * @return A list of booked dates.
     */
    public List<BookedDate> getAllBookedDates() {
        return bookedDatesRepo.findByDateIsNotNull();
    }

    /**
     * Creates or updates booked dates based on the given data transfer object (DTO).
     * 
     * @param bookedDayDTO The data transfer object containing the booked date details.
     */
    public void createBookedDates(BookedDateDTO bookedDayDTO) {
        // Retrieve the BookingRequest by its ID
        Optional<BookingRequest> bookingRequest = bookingService.getBookingRequestByid(bookedDayDTO.getIdBookingRequest());

        // Throw an exception if the booking request is not found
        if (bookingRequest == null) {
            throw new ResourceNotFoundException("Booking request with id " + bookedDayDTO.getIdBookingRequest() + " not found.");
        }

        // Retrieve the BookedDate associated with the booking request
        BookedDate booked = bookingRequest.get().getBookedDate();

        // Set the date and optional "to date" from the DTO
        booked.setDate(bookedDayDTO.getDate());
        booked.setToDate(bookedDayDTO.getToDate());

        // Assign day fractions based on the DTO flags
        if (bookedDayDTO.isMorning()) {
            booked.setDayFractions(dayFractionRepo.findById((long) 1).get()); // Morning slot
        }
        if (bookedDayDTO.isFullDay()) {
            booked.setDayFractions(dayFractionRepo.findById((long) 2).get()); // Full-day slot
        }
        if(bookingService.getBookingRequestByid(bookedDayDTO.getIdBookingRequest()).get().getNumberOfDays()>1) {
        	booked.setDayFractions(dayFractionRepo.findById((long) 2).get()); // Full-day slot
        }

        // Save the updated booked date into the repository
        bookedDatesRepo.save(booked);
    }
}
