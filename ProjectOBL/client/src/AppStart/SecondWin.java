package AppStart;

import java.util.ArrayList;
import java.util.List;

import Methods.Book;
import Methods.Librarian;
import Methods.Subscriber;
import Methods.User;
import Protocols.LogInResponse;
import Protocols.Request;
import Protocols.RequestType;
import Protocols.Response;
import client.ChatClient;
import common.ChatIF;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SecondWin extends AbstractController {

	private List<Stage> GUIChildren = new ArrayList<Stage>();

	private User user;
	private boolean isSubscriber, isLibrarian, isManager;
	private String search;

	private String isbn;
	@FXML
	private MenuButton managerBtn;
	@FXML
	private AnchorPane ancor;

	@FXML
	private Label readerName;

	@FXML
	private Label statusTitle;

	@FXML
	private Label roleLabel;

	@FXML
	private Label statusLabel;

	@FXML
	private Button LogOutBtn;

	@FXML
	private TextField SearchTx;

	@FXML
	private Button SearchBookBtn;

	@FXML
	private MenuButton SearchByBtn;

	@FXML
	private MenuItem BookNameBtn;

	@FXML
	private MenuItem AuthorBtn;

	@FXML
	private MenuItem CategoryBtn;

	@FXML
	private MenuItem DepatmentBtn;

	@FXML
	private Button LibrarianBtn;

	@FXML
	private MenuItem NewReportBtn;

	@FXML
	private MenuItem OldReportBtn;

	@FXML
	private MenuItem ViewWorkrsBtn;

	@FXML
	private Button ReaderCardBtn;

	@FXML
	private Button InventoryBtn;

   
	private Subscriber subscriber;
	private Librarian librarian;
	

	public void assignButtons(boolean isSub, boolean isLib, boolean isMan, String id) {

		this.isSubscriber = isSub;
		this.isLibrarian = isLib;
		this.isManager = isMan;

		LibrarianBtn.setVisible(isLib);
		this.ReaderCardBtn.setVisible(isSub);
		managerBtn.setVisible(isMan);

		statusTitle.setVisible(isSub);
		statusLabel.setVisible(isSub);

		Request req;
		if (isSub)
			req = new Request(RequestType.GetSubscriber, id);
		else
			req = new Request(RequestType.GetLibrarian, id);

		Response res = getClient().handleMessageFromClientUI(req);
		if (!res.isSucceeded()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Log in failed.");
			alert.setHeaderText("");
			alert.setContentText(res.getMessage());
			alert.showAndWait();

			Request reqOut = new Request(RequestType.LogOut, id);
			Response resOut = getClient().handleMessageFromClientUI(reqOut);

			if (resOut.isSucceeded()) {
				DisplayMessage(AlertType.WARNING, "System Failure", "Could not complete Log Out.");
			} else {
				DisplayMessage(AlertType.WARNING, "System Failure", resOut.getMessage());
			}
			return;
		} else {
			user = (User) res.getData();
			readerName.setText(user.getUserName());

			String role = "";
			if (isSub) {
				role = role + "Subscriber ";
				statusLabel.setText(((Subscriber) user).getCardStatus().toString());
				subscriber = (Subscriber) res.getData();
			} else {

				librarian = (Librarian) res.getData();
				if (isLib && !isManager)
					role += "Librarian";
				if (isManager)
					role += "Manager";
			}
			roleLabel.setText(role);
		}
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
	}

	@FXML
	void InventoryBtnHandle(ActionEvent event) {

		Request message = new Request(RequestType.GetAllBooks, null);
		Response res = getClient().handleMessageFromClientUI(message);

		if (!res.isSucceeded()) { // failed
			this.DisplayMessage(AlertType.ERROR, "Search Failed", res.getMessage());
			return;
		}

		// success

		ArrayList<Book> books = (ArrayList<Book>) res.getData();
		if (books.size() == 0) { // no books found
			this.DisplayMessage(AlertType.INFORMATION, "No Results Found", res.getMessage());
			return;
		}

		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/Inventory.fxml"));
		try {
			Pane root = loader.load();

			InventoryController inventoryController = loader.getController();
			inventoryController.setClient(client);
			// client.AddClientUI(inventoryController);
			inventoryController.setUserMode(isSubscriber, isLibrarian, isManager);
			inventoryController.initTableAndCol1(books);
			inventoryController.setSubscriber(this.subscriber);
			Platform.setImplicitExit(false);
			//inventoryController.setAddNewBookTab(isSubscriber);

			this.GUIChildren.add(primaryStage);
			
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			System.out.println(e.getStackTrace() + "\n" + e.getMessage() + "\n" + e.getLocalizedMessage());

		}

	}

	@FXML
	void LibrarianBtnHandle(ActionEvent event) {
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/Librarian.fxml"));
		try {
			Pane root = loader.load();

			LibrarianController librarianController = loader.getController();
			librarianController.setLibrarian((Librarian)user);
			librarianController.setClient(client);
			librarianController.setUserMode(isSubscriber, isLibrarian, isManager);
			client.AddClientUI(librarianController);

			Platform.setImplicitExit(false);
			
			this.GUIChildren.add(primaryStage);
			
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			System.out.println(e.getStackTrace() + "\n" + e.getMessage() + "\n" + e.getLocalizedMessage());

		}
	}

	@FXML
	void LogoutBtnHandle(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logout");
		alert.setHeaderText("CONFIRMATION");
		alert.setContentText("Logout! Are you sure? ");

		if (alert.showAndWait().get() == ButtonType.OK) {
			Request reqOut = new Request(RequestType.LogOut, user.getID());
			Response resOut = getClient().handleMessageFromClientUI(reqOut);

			if (resOut.isSucceeded()) {
				DisplayMessage(AlertType.INFORMATION, "Log Out",
						resOut.getMessage() + " We hope to see you online soon again...");
			} else {
				DisplayMessage(AlertType.WARNING, "System Failure", resOut.getMessage());
			}

			((Node) event.getSource()).getScene().getWindow().hide();
			hideChldren();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/FirstWindow.fxml"));
			try {
				Pane root = loader.load();

				FirstController firstWindowController = loader.getController();
				firstWindowController.setClient(client);
				client.AddClientUI(firstWindowController);

				Platform.setImplicitExit(false);

				Scene scene = new Scene(root);
				// scene.getStylesheets().add(getClass().getResource("/gui/StudentForm.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				System.out.println(e.getStackTrace() + "\n" + e.getMessage() + "\n" + e.getLocalizedMessage());

			}
		}

	}
	
	public void closeSecond() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logout");
		alert.setHeaderText("CONFIRMATION");
		alert.setContentText("Logout! Are you sure? ");

		if (alert.showAndWait().get() == ButtonType.OK) {
			Request reqOut = new Request(RequestType.LogOut, user.getID());
			Response resOut = getClient().handleMessageFromClientUI(reqOut);

			if (resOut.isSucceeded()) {
				DisplayMessage(AlertType.INFORMATION, "Log Out",
						resOut.getMessage() + " We hope to see you online soon again...");
			} else {
				DisplayMessage(AlertType.WARNING, "System Failure", resOut.getMessage());
			}

			//((Node) event.getSource()).getScene().getWindow().hide();
			hideChldren();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/FirstWindow.fxml"));
			try {
				Pane root = loader.load();

				FirstController firstWindowController = loader.getController();
				firstWindowController.setClient(client);
				client.AddClientUI(firstWindowController);

				Platform.setImplicitExit(false);

				Scene scene = new Scene(root);
				// scene.getStylesheets().add(getClass().getResource("/gui/StudentForm.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				System.out.println(e.getStackTrace() + "\n" + e.getMessage() + "\n" + e.getLocalizedMessage());

			}
		}
	}

	private void hideChldren() {
		for(Stage stg: GUIChildren)
			stg.hide();
	}

	private void DisplayMessage(AlertType type, String title, String msg) {
		Alert alert = new Alert(type);
		alert.setTitle(type.toString());
		alert.setHeaderText(title);
		alert.setContentText(msg);
		alert.showAndWait();
		return;
	}

	@FXML
	void NewReportBtnHandle(ActionEvent event) {
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/new reports.fxml"));
		try {
			Pane root = loader.load();

			NewReportsController newReportsController = loader.getController();
			newReportsController.setClient(client);
			client.AddClientUI(newReportsController);

			Platform.setImplicitExit(false);

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			System.out.println(e.getStackTrace() + "\n" + e.getMessage() + "\n" + e.getLocalizedMessage());

		}

	}

	@FXML
	void OldReportBtnHandle(ActionEvent event) {
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/Reports.fxml"));
		try {
			Pane root = loader.load();
			OldReportsController oldReportsController = loader.getController();
			oldReportsController.setClient(client);
			client.AddClientUI(oldReportsController);

			Platform.setImplicitExit(false);

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			System.out.println(e.getStackTrace() + "\n" + e.getMessage() + "\n" + e.getLocalizedMessage());

		}

	}

	@FXML
	void ReaderCardBtnHandle(ActionEvent event) {

		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/Subscriber.fxml"));
		try {
			Pane root = loader.load();

			ReaderCardController readerCardController = loader.getController();

			readerCardController.setUserMode(isSubscriber, isLibrarian, isManager);
			readerCardController.setClient(client);
			client.AddClientUI(readerCardController);
			
			
			readerCardController.setSubscriber(subscriber);
			readerCardController.initTables();

			this.GUIChildren.add(primaryStage);
			
			Platform.setImplicitExit(false);
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);

			primaryStage.show();

			// client.RemoveCientUI(this);
		} catch (Exception e) {
			System.out.println(e.getStackTrace() + "\n" + e.getMessage() + "\n" + e.getLocalizedMessage());

		}

	}

	@FXML
	void SearchBookBtnHandle(ActionEvent event) {
		if (SearchTx.getText().trim().isEmpty()) {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Missing fields");
			alert.setHeaderText("WARNING");
			alert.setContentText(" Please insert full Data ");
			alert.showAndWait();
			return;
		}

		search = SearchByBtn.getText();
		String toSearchby = this.SearchTx.getText();
		RequestType rqType;
		switch (search) {
		case "Book Name":
			rqType = RequestType.SearchByTitle;
			break;
		case "Author":
			rqType = RequestType.SearchByAuthors;
			// toSearchby = new String(toSearchby.replace(" ", ""));
			break;

		case "Category":
			rqType = RequestType.SearchByCategory;
			// toSearchby = new String(toSearchby.replace(" ", ""));
			break;

		case "Department":
			rqType = RequestType.SearchByDepartment;
			// toSearchby = new String(toSearchby.replace(" ", ""));
			break;

		default:
			rqType = RequestType.SearchByDescription;
			// toSearchby = new String(toSearchby.replace(" ", ""));
			break;
		}

		Request message = new Request(rqType, toSearchby);
		Response res = getClient().handleMessageFromClientUI(message);

		if (!res.isSucceeded()) { // failed
			this.DisplayMessage(AlertType.ERROR, "Search Failed", res.getMessage());
			return;
		}

		// success
		List<Book> books = (List<Book>) res.getData();
		if (books.size() == 0) { // no books found
			this.DisplayMessage(AlertType.INFORMATION, "No Results Found", res.getMessage());
			return;
		} else {

			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/SearchResults.fxml"));
			try {
				Pane root = loader.load();

				SearchResultController searchResultController = loader.getController();
				searchResultController.setClient(client);
				client.AddClientUI(searchResultController);
				searchResultController.setUserMode(isSubscriber, isLibrarian, isManager);
				searchResultController.setSubsciber(this.subscriber);
				searchResultController.initTableAndCol(books);
				Platform.setImplicitExit(false);

				this.GUIChildren.add(primaryStage);
				
				Scene scene = new Scene(root);
				// scene.getStylesheets().add(getClass().getResource("/gui/StudentForm.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				System.out.println(e.getStackTrace() + "\n" + e.getMessage() + "\n" + e.getLocalizedMessage());

			}
		}
	}

	@FXML
	void ViewWorkrsBtnHandle(ActionEvent event) {
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/Workers.fxml"));
		try {
			Pane root = loader.load();

			WorkersController workersController = loader.getController();
			workersController.setClient(client);
			client.AddClientUI(workersController);

			Platform.setImplicitExit(false);

			this.GUIChildren.add(primaryStage);
			
			Scene scene = new Scene(root);
			// scene.getStylesheets().add(getClass().getResource("/gui/StudentForm.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			System.out.println(e.getStackTrace() + "\n" + e.getMessage() + "\n" + e.getLocalizedMessage());

		}
	}

	@Override
	public void DoClose(String message) {
	}

	ChatClient client;

	public ChatClient getClient() {
		return client;
	}

	public void setClient(ChatClient client) {
		this.client = client;
	}

	public User getSubscriber() {
		return user;
	}

	public void setSubscriber(User user) {
		this.user = user;
	}
}
