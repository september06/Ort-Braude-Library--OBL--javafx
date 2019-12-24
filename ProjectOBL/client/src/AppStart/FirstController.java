package AppStart;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;


import com.sun.security.ntlm.Client;

import Methods.*;
import client.ChatClient;
import common.ChatIF;
import javafx.application.Platform;
//import com.sun.javafx.scene.control.skin.DatePickerSkin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import Protocols.Request;
import Protocols.RequestType;
import Protocols.Response;
import application.ConnectController;
import Protocols.LogInResponse;
public class FirstController implements ChatIF {

   private String search ;
   private boolean isSubscriber, isLibrarian , isManager;

	ChatClient client;
	private List<String> list = new ArrayList();
	ConnectController conn = new ConnectController();
	
	

	public ChatClient getClient() {
		return client;
	}

	public void setClient(ChatClient client) {
		this.client = client;
	}

	@FXML
	private GridPane grido;

	public GridPane getGrido() {
		return grido;
	}

	public void setGrido(GridPane grido) {
		this.grido = grido;
	}

	@FXML
	private AnchorPane ancor;

	@FXML
	private TextField SearchTxt;

	@FXML
	private TextField UsernameTxt;

	@FXML
	private PasswordField PasswordTxt;

	@FXML
	private Button LogInBtn;

	@FXML
	private Button SearchBookBtn;

	@FXML
	private MenuItem BookNameBtn;

	@FXML
	private MenuItem AuthorBtn;

	@FXML
	private MenuItem CategoryBtn;

	@FXML
	private MenuItem DepatmentBtn;

	@FXML
	private MenuButton SearchByBtn;

	@FXML
	private BorderPane dRoot;
	@FXML
	private Pane bordermid;

	@FXML
	private Button btnExit;

    @FXML
    private MenuItem LowItem;

    @FXML
    private MenuItem RegItem;

    @FXML
    private MenuItem highItem;
    
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Button getBtnExit() {
		return btnExit;
	}

	public void setBtnExit(Button btnExit) {
		this.btnExit = btnExit;
	}

	public BorderPane getdRoot() {
		return dRoot;
	}

	@FXML
	void btnExitOnAction(ActionEvent event) {

		getBtnExit().setOnMouseClicked(e -> System.exit(0));

	}

	public void setdRoot() {

		// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		// DatePickerSkin datePickerSkin = new DateCellSkin(new
		// DatePicker(LocalDate.now()));
		// javafx.scene.Node popupContent = datePickerSkin.getPopupContent();

		// droot.setCenter(popupContent);
		// this.dRoot.setCenter(popupContent);

	}

	@FXML
	void SearchByHandle(ActionEvent event) {

	}

	@FXML
	void AuthorBtnHandle(ActionEvent event) {
		SearchByBtn.setText("Author");

	}

	@FXML
	void BookNameBtnHandle(ActionEvent event) {
		SearchByBtn.setText("Book Name");

	}

	@FXML
	void CategoryBtnHandle(ActionEvent event) {
		SearchByBtn.setText("Category");

	}

	@FXML
	void DepatmentBtnHandle(ActionEvent event) {
		SearchByBtn.setText("Department");

		//book.setDepartment(SearchTxt.getText());
	}

	@FXML
	void LogInBtnHandle(ActionEvent event) {
		try {
			if ((UsernameTxt.getText().trim().isEmpty()) || (PasswordTxt.getText().trim().isEmpty())) {

				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Missing fields");
				alert.setHeaderText("WARNING");
				alert.setContentText("Please fill in both username(or ID) and password.");
				alert.showAndWait();
				return;
			} else {
				list.clear();
				list.add(UsernameTxt.getText());
				list.add(PasswordTxt.getText());
				//user.setUserName(UsernameTxt.getText());
			}
		} catch (Exception ex) {
			System.out.println("Exception in Log In button first window.\n" + ex.getMessage());
		}

		
		Request message = new Request(RequestType.LogIn, list);
		LogInResponse res = (LogInResponse)getClient().handleMessageFromClientUI(message);
		//setSearch(res.getData().toString());
		this.isSubscriber=res.isSubscriber();
		this.isLibrarian=res.isLibrarian();
		this.isManager= res.isManager();
		
		if(!res.isSucceeded()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Log in failed.");
			alert.setHeaderText("");
			alert.setContentText(res.getMessage());
			alert.showAndWait();
			return;
		}
		else {
			try {
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/SecondWin.fxml"));
				
				AnchorPane root = loader.load();
				
				SecondWin secondWin = loader.getController();

				secondWin.setClient(client);
				client.AddClientUI(secondWin);
				secondWin.assignButtons(res.isSubscriber(), res.isLibrarian(), res.isManager(), list.get(0));
				Platform.setImplicitExit(false);

				Scene scene = new Scene(root);
				primaryStage.setScene(scene);

				((Node)event.getSource()).getScene().getWindow().hide();

				//primaryStage.setTitle("OBL Library : " + user.getUserName());
				Image anotherIcon = new Image("/image/oblLogo.jpg");
				primaryStage.getIcons().add(anotherIcon);
				primaryStage.setOnCloseRequest((WindowEvent event1) -> {
					secondWin.closeSecond();
					primaryStage.hide();
		    });
				
				primaryStage.show();
				
			} catch (Exception e) {
				System.out.println("LogIn Exception: " + e.getMessage() 
				+ "\n" + e.getLocalizedMessage());

				//Do Log Out in case of fail! = create new log out req and send it.
				Request reqOut = new Request(RequestType.LogOut, list.get(0));
				Response resOut = getClient().handleMessageFromClientUI(reqOut);
				
				if(resOut.isSucceeded()) {
					DisplayMessage(AlertType.WARNING, "System Failure", "Could not complete Log Out.");
				}
				else {
					DisplayMessage(AlertType.WARNING, "System Failure", resOut.getMessage());
				}
				return;
			}
		}
	}


	private void DisplayMessage(AlertType type, String title, String msg) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(type.toString());
		alert.setContentText(msg);
		alert.showAndWait();
		return;
	}

	@FXML
	void SearchBookBtnHandle(ActionEvent event) {
		if (SearchTxt.getText().trim().isEmpty()) {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Missing fields");
			alert.setHeaderText("WARNING");
			alert.setContentText(" Please insert full Data ");
			alert.showAndWait();
			return;
		}
		
		search = SearchByBtn.getText();
		String toSearchby = this.SearchTxt.getText();
		RequestType rqType;
		switch (search) {
		case "Book Name":
			rqType = RequestType.SearchByTitle;
			break;
		case "Author":
			rqType = RequestType.SearchByAuthors;
			toSearchby = new String(toSearchby.replace(" ", ""));
			break;

		case "Category":
			rqType = RequestType.SearchByCategory;
			toSearchby = new String(toSearchby.replace(" ", ""));
			break;

		case "Department":
			rqType = RequestType.SearchByDepartment;
			toSearchby = new String(toSearchby.replace(" ", ""));
			break;

		default:
			rqType = RequestType.SearchByDescription;
			toSearchby = new String(toSearchby.replace(" ", ""));
			break;
		}
		
		Request message = new Request(rqType, toSearchby);
		Response res = getClient().handleMessageFromClientUI(message);
		
		if(!res.isSucceeded()) { //failed
			this.DisplayMessage(AlertType.ERROR, "Search Failed", res.getMessage());
			return;
		}
		
		//success
		List<Book> books = (List<Book>)res.getData();
		if(books.size()==0) { // no books found
			this.DisplayMessage(AlertType.INFORMATION, "No Results Found", res.getMessage());
			return;
		}
		
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/SearchResults.fxml"));
		try {
			Pane root = loader.load();

			SearchResultController searchResultController = loader.getController();
			searchResultController.setUserMode(false, false, false);

			searchResultController.setClient(client);
			client.AddClientUI(searchResultController);
			searchResultController.initTableAndCol(books);
			Platform.setImplicitExit(false);

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			System.out.println(e.getStackTrace() + "\n" + e.getMessage() + "\n" + e.getLocalizedMessage());
		}
	}
	
	@Override
	public void DoClose(String message) {

	}
}
