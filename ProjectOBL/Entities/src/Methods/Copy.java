package Methods;

import java.io.Serializable;

public class Copy implements Serializable {
	public enum Availability {
		Available, Borrowed, Lost;
	}
	
	private String ISBN;
	int serialCode;
	private Availability availability;
	
	public Copy(String ISBN, int serialCode, Availability availability) {
		this.ISBN = ISBN;
		this.serialCode = serialCode;
		this.availability = availability;
	}
	
	public String getISBN() {
		return ISBN;
	}

	/*public void setISBN(String iSBN) {
		ISBN = iSBN;
	}*/

	public int getSerialCode() {
		return serialCode;
	}

	/*public void setSerialCode(int serialCode) {
		this.serialCode = serialCode;
	}*/

	public Availability getAvailability() {
		return availability;
	}

	public void setAvailability(Availability availability) {
		this.availability = availability;
	}
	public void setLost() {
		availability = Availability.Lost;
	}

	public boolean isBorrowed() {
		return availability == Availability.Borrowed;
	}
	
	public boolean isAvailable() {
		return availability == Availability.Available;
	}
}
