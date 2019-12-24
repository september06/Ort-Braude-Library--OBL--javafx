package Methods;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;

public class Report implements Serializable {
	private String reportText;
	private Timestamp issueDate;
	
	public String getReportText() {
		return reportText;
	}
	public void setReportText(String reportText) {
		this.reportText = reportText;
	}
	public Report(String reportText, Timestamp issueDate) {
		this.reportText = reportText;
		this.issueDate = issueDate;
	}
	public Timestamp getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Timestamp issueDate) {
		this.issueDate = issueDate;
	}
}
