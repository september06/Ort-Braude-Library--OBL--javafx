/**
 * Sample Skeleton for 'Librarian.fxml' Controller Class
 */

package AppStart;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Methods.Copy;
import Methods.Copy.Availability;
import Methods.Librarian;
import Methods.Subscriber;
import Methods.Subscriber.ReaderCardStatus;
import Protocols.Request;
import Protocols.RequestType;
import Protocols.Response;
import client.ChatClient;
import common.ChatIF;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LibrarianController extends AbstractController{
	
	@FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

	@FXML // fx:id="SubscriberIdTextField"
	private TextField SubscriberIdTextField; // Value injected by FXMLLoader

	@FXML // fx:id="NewSubIdTextField"
	private TextField NewSubIdTextField; // Value injected by FXMLLoader

	@FXML // fx:id="GivenNameTextField"
	private TextField GivenNameTextField; // Value injected by FXMLLoader

	@FXML // fx:id="FamilyNameTextField"
	private TextField FamilyNameTextField; // Value injected by FXMLLoader

	@FXML // fx:id="UserNameTextField"
	private TextField UserNameTextField; // Value injected by FXMLLoader

	@FXML // fx:id="UserPasswordTextField"
	private TextField UserPasswordTextField; // Value injected by FXMLLoader

	@FXML // fx:id="TelephoneTextField"
	private TextField TelephoneTextField; // Value injected by FXMLLoader

	@FXML // fx:id="EmailTextField"
	private TextField EmailTextField; // Value injected by FXMLLoader

	@FXML // fx:id="IdFormatInfo"
	private Hyperlink IdFormatInfo; // Value injected by FXMLLoader

	@FXML // fx:id="FirstNameInfo"
	private Hyperlink FirstNameInfo; // Value injected by FXMLLoader

	@FXML // fx:id="LastNameInfo"
	private Hyperlink LastNameInfo; // Value injected by FXMLLoader

	@FXML // fx:id="UserNameInfo"
	private Hyperlink UserNameInfo; // Value injected by FXMLLoader

	@FXML // fx:id="GenratedCodeInfo"
	private Hyperlink GenratedCodeInfo; // Value injected by FXMLLoader

	@FXML // fx:id="TelephoneFormatInfo"
	private Hyperlink TelephoneFormatInfo; // Value injected by FXMLLoader

	@FXML // fx:id="EmailFormatInfo"
	private Hyperlink EmailFormatInfo; // Value injected by FXMLLoader

	@FXML // fx:id="MoreHelpInfo"
	private Hyperlink MoreHelpInfo; // Value injected by FXMLLoader

	@FXML // fx:id="NewSubSubmitBtn"
	private Button NewSubSubmitBtn; // Value injected by FXMLLoader

	@FXML // fx:id="BookSerialBorrowingTextField"
	private TextField BookSerialBorrowingTextField; // Value injected by FXMLLoader
    @FXML // fx:id="BookISBNBorrowingTextField"
    private TextField BookISBNBorrowingTextField; // Value injected by FXMLLoader
	@FXML // fx:id="BookSerialInfo"
	private Hyperlink BookSerialInfo; // Value injected by FXMLLoader

	@FXML // fx:id="MoreHelpBorrowingInfo"
	private Hyperlink MoreHelpBorrowingInfo; // Value injected by FXMLLoader

	@FXML // fx:id="SubmitBorrowingBtn"
	private Button SubmitBorrowingBtn; // Value injected by FXMLLoader

	@FXML // fx:id="BookSerialReturingTextField"
	private TextField BookSerialReturingTextField; // Value injected by FXMLLoader

	@FXML // fx:id="MoreHelpReturingInfo"
	private Hyperlink MoreHelpReturingInfo; // Value injected by FXMLLoader

	@FXML // fx:id="SubmitReturingBtn"
	private Button SubmitReturingBtn; // Value injected by FXMLLoader

	@FXML // fx:id="ShowAllSubscribersBtn"
	private Button ShowAllSubscribersBtn; // Value injected by FXMLLoader

    @FXML // fx:id="copySerialText"
    private TextField copySerialText; // Value injected by FXMLLoader
    @FXML // fx:id="copyISBNtext"
    private TextField copyISBNtext; // Value injected by FXMLLoader

	private Librarian librarain;
	@FXML
	void ShowAllSubscribersBtnHandle(ActionEvent event) {

		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/ShowSubsWindow.fxml"));
		try {
			Pane root = loader.load();

			ShowSubscribersController showSubscribersController = loader.getController();
			showSubscribersController.setClient(client);
			client.AddClientUI(showSubscribersController);
			showSubscribersController.setLibrarian(this.librarain);
			showSubscribersController.setUserMode(isSubscriber, isLibrarian, isManager);
			Platform.setImplicitExit(false);

			Scene scene = new Scene(root);			
			primaryStage.setScene(scene);		
			primaryStage.show();
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace()+"\n"+e.getMessage()+"\n"+e.getLocalizedMessage());

		}

	}
	void DisplayMessage(AlertType type, String title, String msg) {
		Alert alert = new Alert(type);
		alert.setTitle(type.toString());
		alert.setHeaderText(title);
		alert.setContentText(msg);
		alert.showAndWait();
		return;
	}
	
	@FXML
	void BookSerialInfoHandle(ActionEvent event) {
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/BookSerial.fxml"));
		try {
			Pane root = loader.load();

			BookSerialController bookSerialController = loader.getController();
			bookSerialController.setClient(client);
			client.AddClientUI(bookSerialController);

			Platform.setImplicitExit(false);

			Scene scene = new Scene(root);			
			primaryStage.setScene(scene);		
			primaryStage.show();
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace()+"\n"+e.getMessage()+"\n"+e.getLocalizedMessage());

		}
	}

	@FXML
	void EmailFormatInfoHandle(ActionEvent event) {
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/EmailFormat.fxml"));
		try {
			Pane root = loader.load();

			EmailFormatContoller emailFormatContoller = loader.getController();
			emailFormatContoller.setClient(client);
			client.AddClientUI(emailFormatContoller);

			Platform.setImplicitExit(false);

			Scene scene = new Scene(root);			
			primaryStage.setScene(scene);		
			primaryStage.show();
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace()+"\n"+e.getMessage()+"\n"+e.getLocalizedMessage());

		}
	}

	@FXML
	void FirstNameInfoHandle(ActionEvent event) {
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/NameFormat.fxml"));
		try {
			Pane root = loader.load();

			NameFormatContoller nameFormatContoller = loader.getController();
			nameFormatContoller.setClient(client);
			client.AddClientUI(nameFormatContoller);

			Platform.setImplicitExit(false);

			Scene scene = new Scene(root);			
			primaryStage.setScene(scene);		
			primaryStage.show();
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace()+"\n"+e.getMessage()+"\n"+e.getLocalizedMessage());

		}
	}
	//GeneratedCodeController
	@FXML
	void GenratedCodeInfoHandle(ActionEvent event) {
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/GeneratedCode.fxml"));
		try {
			Pane root = loader.load();

			GeneratedCodeController generatedCodeController = loader.getController();
			generatedCodeController.setClient(client);
			client.AddClientUI(generatedCodeController);

			Platform.setImplicitExit(false);

			Scene scene = new Scene(root);			
			primaryStage.setScene(scene);		
			primaryStage.show();
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace()+"\n"+e.getMessage()+"\n"+e.getLocalizedMessage());
		}

	}

	@FXML
	void IdFormatInfoHandle(ActionEvent event) {
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/IDFormat.fxml"));
		try {
			Pane root = loader.load();

			IDFormatController iDFormatController = loader.getController();
			iDFormatController.setClient(client);
			client.AddClientUI(iDFormatController);

			Platform.setImplicitExit(false);

			Scene scene = new Scene(root);			
			primaryStage.setScene(scene);		
			primaryStage.show();
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace()+"\n"+e.getMessage()+"\n"+e.getLocalizedMessage());
		}

	}

	@FXML
	void LastNameInfoHandle(ActionEvent event) {
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/NameFormat.fxml"));
		try {
			Pane root = loader.load();

			NameFormatContoller nameFormatContoller = loader.getController();
			nameFormatContoller.setClient(client);
			client.AddClientUI(nameFormatContoller);

			Platform.setImplicitExit(false);

			Scene scene = new Scene(root);			
			primaryStage.setScene(scene);		
			primaryStage.show();
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace()+"\n"+e.getMessage()+"\n"+e.getLocalizedMessage());

		}
	}

	@FXML
	void MoreHelpBorrowingInfoHandle(ActionEvent event) {
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/help.fxml"));
		try {
			Pane root = loader.load();

			helpController helpcontroller = loader.getController();
			helpcontroller.setClient(client);
			client.AddClientUI(helpcontroller);

			Platform.setImplicitExit(false);

			Scene scene = new Scene(root);			
			primaryStage.setScene(scene);		
			primaryStage.show();
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace()+"\n"+e.getMessage()+"\n"+e.getLocalizedMessage());

		}
	}

	@FXML
	void MoreHelpInfoHandle(ActionEvent event) {
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/help.fxml"));
		try {
			Pane root = loader.load();

			helpController helpcontroller = loader.getController();
			helpcontroller.setClient(client);
			client.AddClientUI(helpcontroller);

			Platform.setImplicitExit(false);

			Scene scene = new Scene(root);			
			primaryStage.setScene(scene);		
			primaryStage.show();
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace()+"\n"+e.getMessage()+"\n"+e.getLocalizedMessage());

		}

	}

	@FXML
	void MoreHelpReturingInfoHandle(ActionEvent event) {
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/help.fxml"));
		try {
			Pane root = loader.load();

			helpController helpcontroller = loader.getController();
			helpcontroller.setClient(client);
			client.AddClientUI(helpcontroller);

			Platform.setImplicitExit(false);

			Scene scene = new Scene(root);			
			primaryStage.setScene(scene);		
			primaryStage.show();
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace()+"\n"+e.getMessage()+"\n"+e.getLocalizedMessage());

		}
	}

	@FXML
	void NewSubSubmitBtnHamdle(ActionEvent event) {
		String invalid="";
		if(!(this.NewSubIdTextField.getText().trim().matches("[0-9]+") && this.NewSubIdTextField.getText().trim().length()==9))
			invalid+=" Subscriber-ID";
		if(!(this.GivenNameTextField.getText().trim().matches("[a-zA-z]+")))
				invalid+=" Given-Name";
		if(!(this.FamilyNameTextField.getText().trim().matches("[a-zA-z]+")))
			invalid+=" Family-Name";
		if(this.UserNameTextField.getText().trim().compareTo("")==0)
			invalid+=" Username";
		if(this.UserPasswordTextField.getText().trim().compareTo("")==0)
			invalid+=" Password";
		if(!(this.TelephoneTextField.getText().trim().matches("[0-9]+") && this.TelephoneTextField.getText().trim().length()==10))
			invalid+=" Telephone-Number";
		if(this.EmailTextField.getText().trim().compareTo("")==0)
			invalid+=" Email";
		
		if(invalid.compareTo("")!=0) {
			this.DisplayMessage(AlertType.WARNING, "Invalid Input", "Please enter valid input. Explenation about each field is displayed by clicking the links next to each one.\n"
					+ "You have invalid input in:"+invalid);
			return;
		}
		
		//valid input
		/*
		 * public Subscriber(String iD, String firstName, String lastName, String userName, String password, 
		 * String phone, String email, boolean onLineStatus, boolean graduated, ReaderCardStatus cardStatus)
		 */
		Request req = new Request(RequestType.InsertNewSubscriber,
				new Subscriber(NewSubIdTextField.getText().trim(), this.GivenNameTextField.getText().trim(),
				FamilyNameTextField.getText().trim(), UserNameTextField.getText().trim(), UserPasswordTextField.getText().trim(),
				TelephoneTextField.getText().trim(), EmailTextField.getText().trim(), false, false, ReaderCardStatus.Active));
		
		Response res = this.client.handleMessageFromClientUI(req);
		
		if(!res.isSucceeded()) {
			this.DisplayMessage(AlertType.ERROR, "Failed to committ", res.getMessage());
			return;
		}
		this.DisplayMessage(AlertType.INFORMATION, "Success", res.getMessage());
	}

	@FXML
	void SubmitBorrowingBtnHandle(ActionEvent event) {
		if(!(this.SubscriberIdTextField.getText().trim().matches("[0-9]+") && this.SubscriberIdTextField.getText().trim().length()==9)) {
			this.DisplayMessage(AlertType.WARNING, "Invalid Subscriber ID", "Please enter a valid #ID consiting of 9 digits.");
			return;
		}
		
		if(!(this.BookISBNBorrowingTextField.getText().trim().matches("[0-9]+") && this.BookISBNBorrowingTextField.getText().trim().length()==13)) {
			this.DisplayMessage(AlertType.WARNING, "Illegal Copy ISBN", "Please enter a valid #ISBN consiting of 13 digits.");
			return;
		}
		
		if(!(this.BookSerialBorrowingTextField.getText().trim().matches("[0-9]+"))) {
			this.DisplayMessage(AlertType.WARNING, "Illegal Copy Serial Code", "Please enter a valid #Serial Code consiting digits only.");
			return;
		}
		
		//valid Data
		ArrayList<String> list = new ArrayList<String>();
		list.add(SubscriberIdTextField.getText().trim());
		list.add(BookISBNBorrowingTextField.getText().trim());
		list.add(BookSerialBorrowingTextField.getText().trim());
		Request req = new Request(RequestType.Borrow, list);
		Response res = this.client.handleMessageFromClientUI(req);
		
		if(!res.isSucceeded()) {
			this.DisplayMessage(AlertType.ERROR, "Failed to committ", res.getMessage());
			return;
		}
		
		this.DisplayMessage(AlertType.INFORMATION, "Success", res.getMessage());
	}


	@FXML
	void SubmitReturingBtnHandle(ActionEvent event) {
		if(!(this.copyISBNtext.getText().trim().matches("[0-9]+") && this.copyISBNtext.getText().trim().length()==13)) {
			this.DisplayMessage(AlertType.WARNING, "Illegal Copy ISBN", "Please enter a valid #ISBN consiting of 13 digits.");
			return;
		}
		
		if(!(this.copySerialText.getText().trim().matches("[0-9]+"))) {
			this.DisplayMessage(AlertType.WARNING, "Illegal Copy Serial Code", "Please enter a valid #Serial Code consiting digits only.");
			return;
		}
		
		//success	
		Request req = new Request(RequestType.ReturnBorrow, new Copy(this.copyISBNtext.getText().trim(), Integer.parseInt(this.copySerialText.getText().trim()), Availability.Borrowed));
		Response res = this.client.handleMessageFromClientUI(req);
		
		if(!res.isSucceeded()) {
			this.DisplayMessage(AlertType.ERROR, "Failed to committ", res.getMessage());
			return;
		}
		
		this.DisplayMessage(AlertType.INFORMATION, "Success", res.getMessage());		
	}


	@FXML
	void TelephoneFormatInfoHandle(ActionEvent event) {
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/TelFormat.fxml"));
		try {
			Pane root = loader.load();

			TelFormatContoller telFormatContoller = loader.getController();
			telFormatContoller.setClient(client);
			client.AddClientUI(telFormatContoller);

			Platform.setImplicitExit(false);

			Scene scene = new Scene(root);			
			primaryStage.setScene(scene);		
			primaryStage.show();
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace()+"\n"+e.getMessage()+"\n"+e.getLocalizedMessage());

		}
	}

	@FXML
	void UserNameInfoHandle(ActionEvent event) {

	}

	@Override
	public void DoClose(String message) {

	}
	public void setLibrarian(Librarian user) {
		this.librarain = user;
	}
}
