package dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.Cr_8.entities.BookedDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class BookedDateDTO {
	@JsonIgnoreProperties({"infos","bookingRequest"})
	private BookedDate bookedDate;
	private LocalDate date;
	private boolean morning;
	private boolean evening;
	
	private Long idBookingRequest;
	
	
	public BookedDate getBookedDate() {
		return bookedDate;
	}
	public void setBookedDate(BookedDate bookedDate) {
		this.bookedDate = bookedDate;
	}
	public Long getIdBookingRequest() {
		return idBookingRequest;
	}
	public void setIdBookingRequest(Long idBookingRequest) {
		this.idBookingRequest = idBookingRequest;
	}
	
	
	

}
