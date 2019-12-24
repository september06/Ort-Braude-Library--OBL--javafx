/**
 * Sample Skeleton for 'Inventory.fxml' Controller Class
 */

package AppStart;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import FileHandling.FileTransmitor;
import Methods.Book;
import Methods.Order;
import Methods.Book.Demand;
import Methods.Subscriber.ReaderCardStatus;
import Methods.Subscriber;
import Protocols.Request;
import Protocols.RequestType;
import Protocols.Response;
import client.ChatClient;
import common.ChatIF;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



public class InventoryController extends AbstractController{

	private String search;
	
	@FXML
	private Label pathLabel;
	
	@FXML
	private Hyperlink isbnLink;
	
	@FXML
	private Hyperlink descLink;
	
	@FXML
	private Hyperlink contentsLink;

	@FXML
	private TableView<Book> resultsTable;

	@FXML
	private TableColumn<Book, String> iSBNView;

    @FXML
    private TableColumn<Book, String> bookTitView;

    @FXML
    private TableColumn<Book, String> authorView;

    @FXML
    private TableColumn<Book, Integer> editionView;

    @FXML
    private TableColumn<Book, String> categoryView;

    @FXML
    private TableColumn<Book, String> shelfView;

    @FXML
    private TableColumn<Book, String> availabiltyView;

    @FXML
    private TableColumn<Book, String> discribtionView;


    @FXML
    private Button newBookBtn;
    
    @FXML
    private Button orderBtn;

    @FXML
    private Button advancedBtn;

    @FXML
    private Tab addNewBookTab;

    @FXML
    private TextField BookNameTxt;

    @FXML
    private MenuButton demandBtn;    
    
    @FXML
    private TextField AuthorTxt;

    @FXML
    private DatePicker PrintDateTxt;

    @FXML
    private DatePicker PurchaseDateTxt;
    
    @FXML
    private TextField CategoryTxt;

    @FXML
    private TextField EditionTxt;

    @FXML
    private TextField ShelfTxt;

    @FXML
    private TextField CatalogueNumberTxt;

    @FXML
    private TextField NumberOfCopiesTxt;

    @FXML
    private TextArea DescribtionTxt;

    @FXML
    private Hyperlink GeneralStringHyperlink;

    @FXML
    private Hyperlink NaturalNumberHyperlink;

    @FXML
    private Hyperlink ShelfFormatHyperlink;

    @FXML
    private TextField isbnCode;

    @FXML
    private TextField departmentTxt;

    @FXML
    private Button saveBookBtn;
    
    @FXML
    private Label selectedBookLabel;
    
    @FXML
    private Label avalabel;
    
    @FXML
    private Button SelectFileBtn;

	private Subscriber subscriber;

	private boolean addNewBook;

	private boolean newFile;


	@FXML
	void CatalougeFormatHyperlinkHandle(ActionEvent event) {

	}
	@FXML
	void DateHyperlinkHandle(ActionEvent event) {

	}


	@FXML
	void GeneralStringHyperlinkHandle(ActionEvent event) {

	}

	@FXML
	void NaturalNumberHyperlinkHandle(ActionEvent event) {

	}

	@FXML
	void PdfFileHyperlinkHandle(ActionEvent event) {

	}
	
	@FXML
	void SelectFileBtnHandle(ActionEvent event) {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files",".pdf");
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(filter);
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		int returnValue = jfc.showOpenDialog(null);
		
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			this.pathLabel.setText(selectedFile.getAbsolutePath());
		}
	}

	@FXML
	void SelectImageBtnHandle(ActionEvent event) {

	}

	@FXML
	void ShelfFormatHyperlinkHandle(ActionEvent event) {

	}
	
	@FXML
	void advancedBtnHandle(ActionEvent event) {
		Book book = this. resultsTable.getSelectionModel().getSelectedItem();
		if(book!=null) {
			this.setBook(book);
			this.DisplayMessage(AlertType.INFORMATION, "Go To Book Editor", "Please open the book editor tab.");
		}
		else {
			DisplayMessage(AlertType.WARNING, "Choose a book", "Please choose a book from the list first.");
		}
	}

	@FXML
	void demandBtnHandle(ActionEvent event) {

	}
	
	@FXML
	void isbnLinkHandle(ActionEvent event) {

	}
	
	@FXML
    void LowItemHandle(ActionEvent event) {
		this.demandBtn.setText(Demand.Low.toString());
    }
	
	@FXML
	void RegItemHandle(ActionEvent event) {
		this.demandBtn.setText(Demand.Regular.toString());
	}

	@FXML
	void highItemHandle(ActionEvent event) {
		this.demandBtn.setText(Demand.High.toString());
	}
	
	@FXML
	void newBookBtnHandle(ActionEvent event) {
		this.setBook(null);
		this.DisplayMessage(AlertType.INFORMATION, "Go To Book Editor", "Please open the book editor tab.");
	}
	
	@FXML
	void descLinkHandle(ActionEvent event) {
		this.DisplayMessage(AlertType.INFORMATION, getSelected().getTitle(), getSelected().getDescription());
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
	
	private Book getSelected() {
		return  resultsTable.getSelectionModel().getSelectedItem();
	}
	
	@FXML
	void contentsLinkHandle(ActionEvent event) {
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
	
	@FXML
	void saveBookBtnHandle(ActionEvent event) {		
		String invalid="";
		if(!(this.isbnCode.getText().trim().matches("[0-9]+") && this.isbnCode.getText().trim().length()==13)) {
			invalid+=" ISBN Code";
		}
		
		if(this.BookNameTxt.getText().compareTo("")==0)
			invalid+=" Book Name";
		
		if(!(this.AuthorTxt.getText().trim().replace(',', ' ').replace(" ", "").matches("[a-zA-Z]+"))) {
			invalid+=" Authors";
		}
		
		if(!(this.EditionTxt.getText().trim().matches("[0-9]+"))) {
			invalid+=" Edition Number";
		}
		else if (Integer.parseInt(this.EditionTxt.getText().trim())<=0) {
			invalid+=" Edition Number";
		}
		
		if(PrintDateTxt.getValue()==null) {
			invalid+=" Print Date";
		}
		
		if(PurchaseDateTxt.getValue()==null) {
			invalid+= " Purchase Date";
		}
		
		if(this.ShelfTxt.getText().compareTo("")==0)
			invalid+=" Book Shelf";
		
		if(this.DescribtionTxt.getText().compareTo("")==0)
			invalid+=" Description";
		
		
		if(this.CatalogueNumberTxt.getText().compareTo("")==0)
			invalid+=" Catalogue Number";
		
		if(!(this.departmentTxt.getText().trim().replace(',', ' ').replace(" ", "").matches("[a-zA-Z]+")))
			invalid+=" Department Name";
		
		if(!(this.CategoryTxt.getText().trim().replace(',', ' ').replace(" ", "").matches("[a-zA-Z]+"))) {
			invalid+=" Categories";
		}
		
		if(!(this.NumberOfCopiesTxt.getText().trim().matches("[0-9]+"))) {
			invalid+=" Number of copies";
		}
		else if (Integer.parseInt(this.NumberOfCopiesTxt.getText().trim())<=0) {
			invalid+=" Number of copies";
		}
		
		if(this.pathLabel.getText().compareTo("")==0) {
			this.DisplayMessage(AlertType.WARNING, "Missing Contents Table", "Please Choose a pdf file for it.");
			return;
		}
		
		if(invalid.compareTo("")!=0) {
			DisplayMessage(AlertType.WARNING, "Ivalid input", 
					"You have typed invalid data in the following fileds:\n"+
			invalid+"\nFor information about each field click the correspoonding link");
			return;
		}
		
		LocalDate localDate = PrintDateTxt.getValue();
		java.util.Date tempDate = java.sql.Date.valueOf(localDate);
		java.sql.Date printDate = new java.sql.Date(tempDate.getTime());
	
		localDate = PurchaseDateTxt.getValue();
		tempDate = java.sql.Date.valueOf(localDate);
		java.sql.Date purchaseDate = new java.sql.Date(tempDate.getTime());
		
		/*
		 * public Book(String isbn, String tilte, String authors, 
		 * int edition, Date publicationDate, Date purchaseDate, 
		 * String category, String catalogue, String Department, 
		 * int totalCopiesNumber, int availableCopiesNumber, 
		 * String shelf, Methods.Book.Demand demand, String description, boolean deleted)
		 */
		Book book = new Book(this.isbnCode.getText().trim(), this.BookNameTxt.getText().trim(), this.AuthorTxt.getText().trim(),
				Integer.parseInt(this.EditionTxt.getText().trim()), printDate, purchaseDate, 
				this.CategoryTxt.getText().trim(), CatalogueNumberTxt.getText().trim(), departmentTxt.getText().trim(),
				Integer.parseInt(this.NumberOfCopiesTxt.getText().trim()), Integer.parseInt(this.NumberOfCopiesTxt.getText().trim()), 
				this.ShelfTxt.getText().trim(), Demand.valueOf(this.demandBtn.getText()),
				this.DescribtionTxt.getText().trim(), false);
		
		RequestType reqtype;
		if(this.addNewBook) {
			reqtype = RequestType.InsertNewBook;
		}
		else reqtype = RequestType.UpdateBook;
		
		Request req = new Request(reqtype, book);
    	Response res = getClient().handleMessageFromClientUI(req);
		if (!res.isSucceeded()) {
			this.DisplayMessage(AlertType.ERROR, "Failed in Inserting New Book:", res.getMessage());
			return;
		}
		else if(reqtype == RequestType.UpdateBook && this.pathLabel.getText().compareTo("Exists in DataBase")==0) {
			this.DisplayMessage(AlertType.INFORMATION, "Book :", res.getMessage());
			this.RenewTable();
			return;
		}
		
    	String fileName = book.getISBN()+".pdf";
		FileTransmitor msg = new FileTransmitor(fileName, book.getISBN());
		String LocalfilePath =  this.pathLabel.getText();
		//System.out.println(LocalfilePath);
		try {
			File newFile = new File(LocalfilePath);
			byte[] mybytearray = new byte[(int) newFile.length()];
			FileInputStream fis = new FileInputStream(newFile);
			BufferedInputStream bis = new BufferedInputStream(fis);

			msg.initArray(mybytearray.length);
			msg.setSize(mybytearray.length);

			bis.read(msg.getMybytearray(), 0, mybytearray.length);
			
			req = new Request(RequestType.SetBookPDF, msg);
	    	res = getClient().handleMessageFromClientUI(req);
			if(!res.isSucceeded()) {
				this.DisplayMessage(AlertType.ERROR, "Failed to uplaod file:", res.getMessage());
				req = new Request(RequestType.DeleteBook, book);
		    	res = getClient().handleMessageFromClientUI(req);
				return;
			}
						
			this.DisplayMessage(AlertType.INFORMATION, "File Uploded:", res.getMessage());
			this.RenewTable();
		} catch (Exception e) {
			this.DisplayMessage(AlertType.ERROR, "Failed to add book:", e.getMessage());
		}
	}
	
	private void RenewTable() {
		Request req = new Request(RequestType.GetAllBooks, "");
    	Response res = getClient().handleMessageFromClientUI(req);
    	if(!res.isSucceeded()) {
			this.DisplayMessage(AlertType.ERROR, "Failed to display books:", res.getMessage());
    	}
    	this.addNewBookTab.setDisable(true);
    	this.initTableAndCol1((ArrayList<Book>)res.getData());
	}
	private void DisplayMessage(AlertType warning, String header, String messgae) {
		Alert alert = new Alert(warning);
		alert.setTitle("");
		alert.setHeaderText(header);
		alert.setContentText(messgae);
		alert.showAndWait();
	}
	
	/*
	@FXML
	void SubmitBtnHandle(ActionEvent event) {

		if ((BookNameTxt.getText().trim().isEmpty()) || (AuthorTxt.getText().trim().isEmpty())
				|| (DescribtionTxt.getText().trim().isEmpty()) || (NumberOfCopiesTxt.getText().trim().isEmpty())
				|| (ShelfTxt.getText().trim().isEmpty()) || (CategoryTxt.getText().trim().isEmpty())
				|| (CatalogueNumberTxt.getText().trim().isEmpty()) || (EditionTxt.getText().trim().isEmpty()))
		// ||format( PurchaseDateTxt.getValue())|| PrintDateTxt.getValue().toString() )
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Missing fields");
			alert.setHeaderText("WARNING");
			alert.setContentText(" Please insert full Data ");
			alert.showAndWait();
			return;
		} else {
			
			LocalDate localDate = PrintDateTxt.getValue();
			java.util.Date date = java.sql.Date.valueOf(localDate);
		java.sql.Date pdate = new java.sql.Date(date.getTime());
		
		 localDate = PurchaseDateTxt.getValue();
		 date = java.sql.Date.valueOf(localDate);
	java.sql.Date qdate = new java.sql.Date(date.getTime());
			//Done;
			
		 /*File contentsTable, boolean deleted)/
			Book book=new Book(isbnCode.getText(),BookNameTxt.getText(),AuthorTxt.getText(),Integer.parseInt(EditionTxt.getText()),pdate,qdate,CategoryTxt.getText(), 
					CatalogueNumberTxt.getText(),departmentTxt.getText(),Integer.parseInt(NumberOfCopiesTxt.getText()),Integer.parseInt(NumberOfCopiesTxt.getText()),
					ShelfTxt.getText(),Demand.Regular,DescribtionTxt.getText(),false);
		
		}	
	}*/

	
	@FXML
	void initialize() {
	}

	public void initTableAndCol1(ArrayList<Book> books) {
		iSBNView.setCellValueFactory(new PropertyValueFactory<Book,String>("ISBN"));
		bookTitView.setCellValueFactory(new PropertyValueFactory<Book,String>("title"));
		authorView.setCellValueFactory(new PropertyValueFactory<Book,String>("authors"));
		categoryView.setCellValueFactory(new PropertyValueFactory<Book,String>("category"));
		shelfView.setCellValueFactory(new PropertyValueFactory<Book,String>("Shelf"));
		availabiltyView.setCellValueFactory(new PropertyValueFactory<Book,String>("Demand"));
		discribtionView.setCellValueFactory(new PropertyValueFactory<Book,String>("Description"));
		editionView.setCellValueFactory(new PropertyValueFactory<Book,Integer>("edition"));

		ObservableList<Book> data_table = FXCollections.observableArrayList();
		data_table.addAll(books);

		resultsTable.setItems(data_table);
		//
		resultsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	this.selectedBookLabel.setText(resultsTable.getSelectionModel().getSelectedItem().getTitle());
		    	this.selectedBookLabel.setVisible(true);
		    	this.contentsLink.setVisible(true);
		    	this.contentsLink.setVisited(false);
		    	this.descLink.setVisible(true);
		    	this.descLink.setVisited(false);
		    	this.advancedBtn.setVisible(isLibrarian);
		    	this.orderBtn.setVisible(isSubscriber);
	        	avalabel.setVisible(true);
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
		    	this.selectedBookLabel.setVisible(false);
		    	this.contentsLink.setVisible(false);
		    	this.descLink.setVisible(false);
		    	this.advancedBtn.setVisible(false);
		    	this.orderBtn.setVisible(false);
		    	this.avalabel.setVisible(false);
		    }
		});
	}


	@Override
	public void DoClose(String message) {
	}

	public Tab getAddNewBookTab() {
		return addNewBookTab;
	}

	public void setAddNewBookTab(boolean isSubscriber) {
		this.addNewBookTab.setDisable(isSubscriber);
	
	}
	
	@Override
	public void setUserMode(boolean isSubscriber, boolean isLibrarian, boolean isManager) {
		super.setUserMode(isSubscriber, isLibrarian, isManager);
		this.newBookBtn.setVisible(isLibrarian);
		this.advancedBtn.setVisible(isLibrarian);
	}
	
	public void setSubscriber(Subscriber subscriber) {
		this.subscriber=subscriber;
	}
	
	public void setBook(Book book) {
		if(book != null) {
			this.addNewBook = false;
			this.pathLabel.setText("Exists in DataBase");
	    	
	    	this.isbnCode.setText(book.getISBN());
	    	this.isbnCode.setDisable(true);
	    	this.BookNameTxt.setText(book.getTitle());
	    	this.AuthorTxt.setText(book.getAuthors());
	    	this.EditionTxt.setText(book.getEdition()+"");
	    	//
			java.util.Date tempDate = new java.util.Date(book.getPublicationDate().getTime());
			LocalDate localDate = tempDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			this.PrintDateTxt.setValue(localDate);
			//
			tempDate = new java.util.Date(book.getPurchaseDate().getTime());
			localDate = tempDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			this.PurchaseDateTxt.setValue(localDate);
	    	//
	    	this.CatalogueNumberTxt.setText(book.getCatalogue());
	    	this.departmentTxt.setText(book.getDepartment());
	    	this.CategoryTxt.setText(book.getCategory());
	    	this.ShelfTxt.setText(book.getShelf());
	    	this.NumberOfCopiesTxt.setText(book.getTotalCopiesNumber()+"");
	    	this.NumberOfCopiesTxt.setDisable(true);
	    	this.demandBtn.setText(book.getDemand().toString());
	    	this.DescribtionTxt.setText(book.getDescription());
		}
		else {
			this.addNewBook = true;
			this.pathLabel.setText("");
			
	    	this.isbnCode.setText("");
	    	this.isbnCode.setDisable(false);
	    	this.BookNameTxt.setText("");
	    	this.AuthorTxt.setText("");
	    	this.EditionTxt.setText("");
	    	//
			//this.PrintDateTxt.setValue(localDate);
			//
			//this.PurchaseDateTxt.setValue(localDate);
	    	//
	    	this.CatalogueNumberTxt.setText("");
	    	this.departmentTxt.setText("");
	    	this.CategoryTxt.setText("");
	    	this.ShelfTxt.setText("");
	    	this.NumberOfCopiesTxt.setText("");
	    	this.NumberOfCopiesTxt.setDisable(false);
	    	this.demandBtn.setText(Demand.Regular.toString());
	    	this.DescribtionTxt.setText("");
		}
		
		this.addNewBookTab.setDisable(false);
	}
	
}
