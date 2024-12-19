package dto;

public class BookedDTO {
	private Long idBookingRequest;
	private Long idBookedDate;
	private Long idReference;
	private boolean morning;
    private boolean evening;
    
    public BookedDTO() {}

	public Long getIdBookingRequest() {
		return idBookingRequest;
	}

	public void setIdBookingRequest(Long idBookingRequest) {
		this.idBookingRequest = idBookingRequest;
	}

	public Long getIdBookedDate() {
		return idBookedDate;
	}

	public void setIdBookedDate(Long idBookedDate) {
		this.idBookedDate = idBookedDate;
	}

	public Long getIdReference() {
		return idReference;
	}

	public void setIdReference(Long idReference) {
		this.idReference = idReference;
	}

	public boolean isMorning() {
		return morning;
	}

	public void setMorning(boolean morning) {
		this.morning = morning;
	}

	public boolean isEvening() {
		return evening;
	}

	public void setEvening(boolean evening) {
		this.evening = evening;
	};
    

}
