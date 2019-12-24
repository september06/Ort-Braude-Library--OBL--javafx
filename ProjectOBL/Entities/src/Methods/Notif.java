package Methods;
import java.sql.Timestamp;

import java.io.Serializable;

public class Notif implements Serializable {
	
	private String subscriberID;
	private Timestamp issueDate;
	private String message;
	
	public Notif(String subscriberID, Timestamp issueDate, String message) {
		this.subscriberID = subscriberID;
		this.issueDate = issueDate;
		this.message = message;
	}
	
	public String getSubscriberID() {
		return subscriberID;
	}
	public void setSubscriberID(String subscriberID) {
		this.subscriberID = subscriberID;
	}
	public Timestamp getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Timestamp issueDate) {
		this.issueDate = issueDate;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
