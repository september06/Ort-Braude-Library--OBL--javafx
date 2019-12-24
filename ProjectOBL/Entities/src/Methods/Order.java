package Methods;
import java.sql.Timestamp;
import java.io.Serializable;
import java.sql.Date;

public class Order extends Activity implements Serializable{

	private String SubscriberID;
	private String ISBN;
	private Date SaveDate;
	private boolean realised;

	public Order(String subscriberID, Timestamp issueDate, String ISBN, Date saveDate, String activityName, boolean realised) {
		super(activityName, issueDate);
		SubscriberID = subscriberID;
		this.ISBN = ISBN;
		SaveDate = saveDate;
		this.realised = realised;
	}

	public String getSubscriberID() {
		return SubscriberID;
	}

	public void setSubscriberID(String subscriberID) {
		SubscriberID = subscriberID;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	public Date getSaveDate() {
		return SaveDate;
	}

	public void setSaveDate(Date saveDate) {
		SaveDate = saveDate;
	}
	
	public boolean isRealised() {
		return realised;
	}

	public void setRealised(boolean realised) {
		this.realised = realised;
	}
	
	@Override
	public String toString() {
		return super.toString() + " SubscriberID=" + SubscriberID + ", ISBN=" + ISBN + ", SaveDate=" + SaveDate + ", realised="
				+ realised;
	}

	@Override
	public String getNote() {
		return toString();
	}
}
