package Methods;
import java.sql.Timestamp;
import java.io.Serializable;
import java.sql.Date;;

public class Borrow extends Activity implements Serializable {

	private String SubscriberID;
	private String copyISBN;
	private int copySerialCode;
	private Date DueDate;
	private Date ReturnDate;
	private boolean extendible;

	public Borrow(String subscriberID, Timestamp issueDate, String activityName, String copyISBN, int copySerialCode,
			Date dueDate, Date returnDate, boolean extendible)
	{
		super(activityName, issueDate);
		SubscriberID = subscriberID;
		this.copyISBN = copyISBN;
		this.copySerialCode = copySerialCode;
		DueDate = dueDate;
		ReturnDate = returnDate;
		this.extendible = extendible;
	}

	public String getSubscriberID() {
		return SubscriberID;
	}

	public void setSubscriberID(String subscriberID) {
		SubscriberID = subscriberID;
	}

	public Date getDueDate() {
		return DueDate;
	}

	public void setDueDate(Date dueDate) {
		DueDate = dueDate;
	}

	public Date getReturnDate() {
		return ReturnDate;
	}

	public void setReturnDate(Date returnDate) {
		ReturnDate = returnDate;
	}
	
	public String getCopyISBN() {
		return copyISBN;
	}

	public void setCopyISBN(String copyISBN) {
		this.copyISBN = copyISBN;
	}

	public int getCopySerialCode() {
		return copySerialCode;
	}

	public void setCopySerialCode(int copySerialCode) {
		this.copySerialCode = copySerialCode;
	}
	
	public boolean isExtendable() {
		return extendible;
	}
	
	public void setExtendable(boolean extendible) {
		this.extendible = extendible;
	}
	
	@Override
	public String toString() {
		return super.toString() +" SubscriberID=" + SubscriberID + ", copyISBN=" + copyISBN + ", copySerialCode=" + copySerialCode
				+ ", DueDate=" + DueDate + ", ReturnDate=" + ReturnDate + ", extendible=" + extendible;
	}
	
	@Override
	public String getNote() {
		return toString();
	}
}
