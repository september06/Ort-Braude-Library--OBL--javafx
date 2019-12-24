package Methods;
import java.io.Serializable;
import java.sql.Timestamp;

public class Extension extends Activity implements Serializable {
	
	private String SubscriberID;
	private Timestamp BorrowingDate; //issue date of the meant borrowing
	private boolean manual;

	public Extension(String subscriberID, Timestamp borrowingDate, Timestamp issueDate, String activityName, boolean manual) {
		super(activityName, issueDate);
		SubscriberID = subscriberID;
		BorrowingDate = borrowingDate;
		this.manual = manual;
	}

	public String getSubscriberID() {
		return SubscriberID;
	}

	public void setSubscriberID(String subscriberID) {
		SubscriberID = subscriberID;
	}

	public Timestamp getBorrowingDate() {
		return BorrowingDate;
	}

	public void setBorrowingDate(Timestamp borrowingDate) {
		BorrowingDate = borrowingDate;
	}
	
	public boolean isManual() {
		return manual;
	}

	public void setManual(boolean manual) {
		this.manual = manual;
	}

	@Override
	public String getNote() {
		return "Extension for the borrowing on date "+BorrowingDate;
	}
	
}
