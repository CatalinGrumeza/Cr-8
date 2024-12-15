package dto;

import com.Cr_8.entities.BookedDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class BookedDateDTO {
	@JsonIgnoreProperties("reference")
	BookedDate bookedDate;
	String email;
	
	
	public BookedDate getBookedDate() {
		return bookedDate;
	}
	public void setBookedDate(BookedDate bookedDate) {
		this.bookedDate = bookedDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
