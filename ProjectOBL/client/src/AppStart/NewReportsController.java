/**
 * Sample Skeleton for 'new reports.fxml' Controller Class
 */

package AppStart;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import Methods.Report;
import Protocols.Request;
import Protocols.RequestType;
import Protocols.Response;
import client.ChatClient;
import common.ChatIF;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.Alert.AlertType;

public class NewReportsController extends AbstractController {
	
	@FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;
	
    @FXML
    private SplitMenuButton splitMenu;
    @FXML // fx:id="ActivityReportBtn"
    private MenuItem ActivityReportBtn; // Value injected by FXMLLoader

    @FXML // fx:id="BorrowingReportBtn"
    private MenuItem BorrowingReportBtn; // Value injected by FXMLLoader

    @FXML // fx:id="DelayingReportBtn"
    private MenuItem DelayingReportBtn; // Value injected by FXMLLoader

    private void DisplayMessage(AlertType type, String title, String msg) {
		Alert alert = new Alert(type);
		alert.setTitle(type.toString());
		alert.setHeaderText(title);
		alert.setContentText(msg);
		alert.showAndWait();
		return;
	}
    
    private void issueReport(RequestType reqType) {
    	if(dateFrom.getValue()==null) {
			DisplayMessage(AlertType.ERROR, "Date Not Chosen", "You must choose a From-date.");
			return;
		}
		if(dateTo.getValue()==null) {
			DisplayMessage(AlertType.ERROR, "Date Not Chosen", "You must choose a To-date.");
			return;
		}
    	
		if(dateFrom.getValue().compareTo(dateTo.getValue())>0) {
			DisplayMessage(AlertType.ERROR, "Wrong Dates Chosen", "From-Date must come before To-Date");
			return;
		}
		
    	LocalDate localDate = dateFrom.getValue();
		java.util.Date tempDate = java.sql.Date.valueOf(localDate);
		java.sql.Date fromDate = new java.sql.Date(tempDate.getTime());
	
		localDate = dateTo.getValue();
		tempDate = java.sql.Date.valueOf(localDate);
		java.sql.Date toDate = new java.sql.Date(tempDate.getTime());
		
		ArrayList<Date> dates = new ArrayList<Date>();
		dates.add(fromDate);
		dates.add(toDate);
		Request req = new Request(reqType, dates);
    	Response res = getClient().handleMessageFromClientUI(req);
    	if(!res.isSucceeded()) {
			DisplayMessage(AlertType.ERROR, "Failed to report", res.getMessage());
			return;
    	}
    	
    	Report rep = (Report)res.getData();
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(res.getRequestType().toString());
		alert.setHeaderText(rep.getReportText());
		alert.setContentText("");
		alert.showAndWait();
		return;
    }
    
    @FXML
    void ActivityReportBtnHandle(ActionEvent event) {
		issueReport(RequestType.IssueActivity);
    }

    @FXML
    void BorrowingReportBtnHandle(ActionEvent event) {
    	issueReport(RequestType.IssueBorrowing);
    }

    @FXML
    void DelayingReportBtnHandle(ActionEvent event) {
    	issueReport(RequestType.IssueDelayings);
    }

	@Override
	public void DoClose(String message) {
	}
}