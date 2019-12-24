package Methods;

import java.io.Serializable;

public class Subscriber extends User implements Serializable{/*the subscriber is user too*/
	public enum ReaderCardStatus {
		Active, Suspended, Locked
	}
	
	private boolean graduated;
	private ReaderCardStatus cardStatus;
	
	/*Constructor of subscriber*/
	public Subscriber(String iD, String firstName, String lastName, String userName, String password, String phone, String email, boolean onLineStatus,
			boolean graduated, ReaderCardStatus cardStatus) {
		super(iD, firstName, lastName, userName, password, phone, email, onLineStatus);
		this.graduated = graduated;
		this.cardStatus = cardStatus;
	}
	
	public Subscriber(User data, boolean graduated, ReaderCardStatus cardStatus) {
		super(data.getID(), data.getFirstName(), data.getLastName(), data.getUserName(), data.getPassword(), 
				data.getPhone(), data.getEmail(), data.isOnline());
		this.graduated = graduated;
		this.cardStatus = cardStatus;
	}

	/*getters and setters of Subscriber attributes*/
	public boolean isGraduated() {
		return graduated;
	}

	public void setGraduated(boolean graduated) {
		this.graduated = graduated;
	}

	public ReaderCardStatus getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(ReaderCardStatus cardStatus) {
		this.cardStatus = cardStatus;
	}

	@Override/*toString of subscriber*/
	public String toString() {
		return super.toString()+"\nGraduated: " + this.graduated + ", Reader Card Status: " + cardStatus;
	}
}