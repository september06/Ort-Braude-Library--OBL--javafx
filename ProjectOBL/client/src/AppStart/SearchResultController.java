/**
 * Sample Skeleton for 'SearchResults.fxml' Controller Class
 */

package AppStart;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import FileHandling.FileTransmitor;
import Methods.Book;
import Methods.Order;
import Methods.Subscriber;
import Methods.Subscriber.ReaderCardStatus;
import Protocols.Request;
import Protocols.RequestType;
import Protocols.Response;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class SearchResultController extends AbstractController{

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
	private TableView <Book> resultsTable;

	@FXML
	private TableColumn<Book,String> bookCoverView;

	@FXML
	private TableColumn<Book,String> bookTitleView;

	@FXML
	private TableColumn<Book,String> authorView;

	@FXML
	private TableColumn<Book,String> categoryView;

	@FXML
	private TableColumn<Book,String> shelfView;

	@FXML
	private TableColumn<Book,String> availabiltyView;

	@FXML
	private TableColumn<Book,String> discribtionView;

    @FXML // fx:id="iSBNLable"
    private Label iSBNLable; // Value injected by FXMLLoader

    @FXML // fx:id="orderBtn"
    private Button orderBtn; // Value injected by FXMLLoader

    @FXML // fx:id="advancedBtn"
    private Button advancedBtn; // Value injected by FXMLLoader

    @FXML
    private Hyperlink descLink; // Value injected by FXMLLoader
    
    @FXML // fx:id="tabletOfContent"
    private Hyperlink tabletOfContent; // Value injected by FXMLLoader

    @FXML
    private Label avalabel;

	private Subscriber subscriber;

	@FXML
    void advancedBtnHandle(ActionEvent event) {

    }

    @FXML
    void orderBtnHandle(ActionEvent event) {
    	if(this.subscriber.getCardStatus() != ReaderCardStatus.Active) {
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("");
			alert.setHeaderText("Reader Card is not active.");
			alert.setContentText("Inactive subscribers cannot orders");
			alert.showAndWait();
			return;
    	}
    	
    	Request req = new Request(RequestType.GetOrdersBySubscriber, subscriber.getID());
    	Response res = getClient().handleMessageFromClientUI(req);
    	if (!res.isSucceeded()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("");
			alert.setHeaderText("Couldn't get data.");
			alert.setContentText(res.getMessage());
			alert.showAndWait();
			return;
    	}
    	
    	ArrayList<Order> orders = (ArrayList<Order>)res.getData();
    	for(Order ord: orders) {
    		if(ord.getISBN().compareTo(this.getSelected().getISBN())==0) {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("");
    			alert.setHeaderText("Already ordered.");
    			alert.setContentText("You can't order the same book again.");
    			alert.showAndWait();
    			return;
    		}
    	}
    	
    	req = new Request(RequestType.CanOrder, this.getSelected().getISBN());
    	res = getClient().handleMessageFromClientUI(req);
    	if (!res.isSucceeded()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("");
			alert.setHeaderText("Can't Complete Order.");
			alert.setContentText(res.getMessage());
			alert.showAndWait();
			return;
    	}
    	
    	java.util.Date currentDate= new java.util.Date();
		long time = currentDate.getTime();
		Timestamp now = new Timestamp(time);
		
    	/*
    	 * public Order(String subscriberID, Timestamp issueDate, String ISBN, 
    	 * Date saveDate, String activityName, boolean realised)
    	 */
    	Order ord = new Order(subscriber.getID(), now, this.getSelected().getISBN(), null, "Order", false);
    	req = new Request(RequestType.InsertNewOrder, ord);
    	res = getClient().handleMessageFromClientUI(req);
    	
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("");
		alert.setHeaderText("Order");
		alert.setContentText(res.getMessage());
		alert.showAndWait();
    }
    
    @FXML
    void descLinktHandle(ActionEvent event) {
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Book Descriprion");
		alert.setHeaderText(resultsTable.getSelectionModel().getSelectedItem().getTitle());
		alert.setContentText(resultsTable.getSelectionModel().getSelectedItem().getDescription());
		alert.showAndWait();
    }
    
    
    @FXML
    void tabletOfContentHandle(ActionEvent event) {
    	String isbn = resultsTable.getSelectionModel().getSelectedItem().getISBN();
		Request req = new Request(RequestType.GetBookPDF, isbn);
    	Response res = getClient().handleMessageFromClientUI(req);
    	if (!res.isSucceeded()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Failed to get table of contents.");
			alert.setHeaderText("");
			alert.setContentText(res.getMessage());
			alert.showAndWait();
			return;
    	}
    	else {
    		FileTransmitor file = (FileTransmitor)res.getData();
    		
    		String fileName = isbn+".pdf";
    		FileTransmitor msg = new FileTransmitor(fileName, isbn);
    		String LocalfilePath = System.getProperty("user.dir")+"/Books/Contents/"+fileName;
    		
    		try {
    			int fileSize = file.getSize();
    			byte[] mybytearray = new byte[fileSize];

    			File newFIle = new File(LocalfilePath);
    			FileOutputStream fos = new FileOutputStream(newFIle);
    			BufferedOutputStream bos = new BufferedOutputStream(fos);
    			bos.write(file.getMybytearray(), 0, file.getSize());
    			bos.close();
    			fos.close();
    			
    			java.awt.Desktop.getDesktop().open(newFIle);
    			
    		} catch (Exception e) {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Failed to get table of contents.");
    			alert.setHeaderText("");
    			alert.setContentText(e.getMessage());
    			alert.showAndWait();
    			return;
    		}
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert resultsTable != null : "fx:id=\"resultsTable\" was not injected: check your FXML file 'SearchResults.fxml'.";
        assert bookCoverView != null : "fx:id=\"bookCoverView\" was not injected: check your FXML file 'SearchResults.fxml'.";
        assert bookTitleView != null : "fx:id=\"bookTitleView\" was not injected: check your FXML file 'SearchResults.fxml'.";
        assert authorView != null : "fx:id=\"authorView\" was not injected: check your FXML file 'SearchResults.fxml'.";
        assert categoryView != null : "fx:id=\"categoryView\" was not injected: check your FXML file 'SearchResults.fxml'.";
        assert shelfView != null : "fx:id=\"shelfView\" was not injected: check your FXML file 'SearchResults.fxml'.";
        assert availabiltyView != null : "fx:id=\"availabiltyView\" was not injected: check your FXML file 'SearchResults.fxml'.";
        assert discribtionView != null : "fx:id=\"discribtionView\" was not injected: check your FXML file 'SearchResults.fxml'.";
        assert iSBNLable != null : "fx:id=\"iSBNLable\" was not injected: check your FXML file 'SearchResults.fxml'.";
        assert orderBtn != null : "fx:id=\"orderBtn\" was not injected: check your FXML file 'SearchResults.fxml'.";
        assert advancedBtn != null : "fx:id=\"advancedBtn\" was not injected: check your FXML file 'SearchResults.fxml'.";
        assert tabletOfContent != null : "fx:id=\"tabletOfContent\" was not injected: check your FXML file 'SearchResults.fxml'.";

    }

	@Override
	public void DoClose(String message) {
	}

	public void initTableAndCol(List<Book> books) {
		bookCoverView.setCellValueFactory(new PropertyValueFactory<Book,String>("ISBN"));
		bookTitleView.setCellValueFactory(new PropertyValueFactory<Book,String>("title"));
		authorView.setCellValueFactory(new PropertyValueFactory<Book,String>("authors"));
		categoryView.setCellValueFactory(new PropertyValueFactory<Book,String>("category"));
		shelfView.setCellValueFactory(new PropertyValueFactory<Book,String>("Shelf"));
		availabiltyView.setCellValueFactory(new PropertyValueFactory<Book,String>("Demand"));
		discribtionView.setCellValueFactory(new PropertyValueFactory<Book,String>("Description"));


		ObservableList<Book> data_table = FXCollections.observableArrayList();

		for(int i = 0 ;i < books.size() ;i++)
		{
			data_table.add(books.get(i));
		}


		//edittableCols();
		resultsTable.setItems(data_table);
		
		resultsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	this.iSBNLable.setText(resultsTable.getSelectionModel().getSelectedItem().getTitle());
		    	this.iSBNLable.setVisible(true);
		    	this.tabletOfContent.setVisible(true);
		    	this.tabletOfContent.setVisited(false);
		    	this.descLink.setVisible(true);
		    	this.descLink.setVisited(false);
		    	this.advancedBtn.setVisible(this.isLibrarian);
		    	this.orderBtn.setVisible(isSubscriber);
		    	this.avalabel.setVisible(true);
		        if(newSelection.getAvailableCopiesNumber()>0)
		        	avalabel.setText("Currently Available");
		        else {
		        	Request req = new Request(RequestType.GetBookAvailability, newSelection.getISBN());
		        	Response res = getClient().handleMessageFromClientUI(req);
		        	if (!res.isSucceeded()) {
		    			Alert alert = new Alert(AlertType.ERROR);
		    			alert.setTitle("");
		    			alert.setHeaderText("");
		    			alert.setContentText(res.getMessage());
		    			alert.showAndWait();
		    			return;
		        	}
		        	else {
		        		avalabel.setText(res.getMessage());
		        	}
		        }
		    }
		    else {
		    	this.iSBNLable.setVisible(false);
		    	this.tabletOfContent.setVisible(false);
		    	this.descLink.setVisible(false);
		    	this.advancedBtn.setVisible(false);
		    	this.orderBtn.setVisible(false);
		    	this.avalabel.setVisible(false);
		    }
		});
	}
	
	private Book getSelected() {
		return  resultsTable.getSelectionModel().getSelectedItem();
	}
	
	public void setSubsciber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}
}
