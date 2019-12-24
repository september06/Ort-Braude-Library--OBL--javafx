package Methods;
import java.io.Serializable;
import java.sql.Timestamp;

public class Losing extends Activity implements Serializable{
	private String SubscriberID;	
	private String CopyISBN;
	private int CopySerialCode;
	
	public Losing(String subscriberID, Timestamp issueDate,  String copyISBN, int CopySerialCode, String activityName) {
		super(activityName, issueDate);
		this.SubscriberID = subscriberID;
		this.CopyISBN = copyISBN;
		this.CopySerialCode = CopySerialCode;
	}

	public String getSubscriberID() {
		return SubscriberID;
	}

	public void setSubscriberID(String subscriberID) {
		SubscriberID = subscriberID;
	}

	public String getCopyISBN() {
		return CopyISBN;
	}

	public void setCopyISBN(String copyISBN) {
		CopyISBN = copyISBN;
	}

	public int getCopySerialCode() {
		return CopySerialCode;
	}

	public void setCopySerialCode(int copySerialCode) {
		CopySerialCode = copySerialCode;
	}

	@Override
	public String getNote() {
		return "For losing the book #"+CopyISBN+" Copy "+CopySerialCode;
	}
}
