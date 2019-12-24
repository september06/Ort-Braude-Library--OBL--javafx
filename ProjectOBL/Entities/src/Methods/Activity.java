package Methods;
import java.io.Serializable;
import java.sql.Timestamp;

public abstract class Activity implements Serializable {

	String ActivityName;
    Timestamp IssueDate;
      
	public Activity(String activityName, Timestamp issueDate) {
		ActivityName = activityName;
		IssueDate = issueDate;
	}

	public String getActivityName() {
		return ActivityName;
	}

	public void setActivityName(String activityName) {
		ActivityName = activityName;
	}

	public Timestamp getIssueDate() {
		return IssueDate;
	}

	public void setIssueDate(Timestamp issueDate) {
		IssueDate = issueDate;
	}
	
    @Override
	public String toString() {
		return "ActivityName:" + ActivityName + ", IssueDate=" + IssueDate;
	}
	
	public abstract String getNote();
      
}
