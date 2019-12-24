/**
 * Sample Skeleton for 'ShowSubsWindow.fxml' Controller Class
 */

package AppStart;

import java.util.ArrayList;
import java.util.List;

import Methods.Librarian;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ShowSubscribersController extends AbstractController {
	
	private Subscriber sub;

	@FXML
	private TableView<Subscriber> subscribersTable;

	@FXML
	private TableColumn<Subscriber, String> IDitem;

	@FXML
	private TableColumn<Subscriber, String> GivenNameitem;

	@FXML
	private TableColumn<Subscriber, String> Usernameitem;

	@FXML
	private TableColumn<Subscriber, String> Statusitem;

	@FXML
	private TableColumn<Subscriber, String> Graduationitem;

	@FXML // fx:id="HelpHyperlink"
	private Hyperlink HelpHyperlink; // Value injected by FXMLLoader

	@FXML // fx:id="FilterBtn"
	private Button FilterBtn; // Value injected by FXMLLoader

	@FXML // fx:id="SubscriberIdTxt"
	private TextField SubscriberIdTxt; // Value injected by FXMLLoader

	@FXML // fx:id="ShowAllToggle"
	private RadioButton ShowAllToggle; // Value injected by FXMLLoader

	@FXML // fx:id="ShowByToggleGroup"
	private ToggleGroup ShowByToggleGroup; // Value injected by FXMLLoader

	@FXML // fx:id="ShowActiveToggle"
	private RadioButton ShowActiveToggle; // Value injected by FXMLLoader

	@FXML // fx:id="ShowSuspendedToggle"
	private RadioButton ShowSuspendedToggle; // Value injected by FXMLLoader

	@FXML // fx:id="ShowLockedToggle"
	private RadioButton ShowLockedToggle; // Value injected by FXMLLoader

	@FXML // fx:id="UserInfoTxt"
	private Label UserInfoTxt; // Value injected by FXMLLoader

	@FXML // fx:id="EditCardBtn"
	private Button EditCardBtn; // Value injected by FXMLLoader

	@FXML // fx:id="ShowBtn"
	private Button ShowBtn; // Value injected by FXMLLoader

	private String librarianName;

	public Subscriber getSub() {
		return sub;
	}

	public void setSub(Subscriber sub) {
		this.sub = sub;
	}


	@FXML
    void EditCardBtnHandle(ActionEvent event) {
		setSub(subscribersTable.getSelectionModel().getSelectedItem());
		
    	Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/Subscriber.fxml"));
		try {
			Pane root = loader.load();

			ReaderCardController readerCardController = loader.getController();
			readerCardController.setClient(client);
			readerCardController.setLibrarianName(librarianName);
			readerCardController.setSubscriber(subscribersTable.getSelectionModel().getSelectedItem());
			readerCardController.initTables();
			client.AddClientUI(readerCardController);
			readerCardController.setUserMode(isSubscriber, isLibrarian, isManager);

			Platform.setImplicitExit(false);
			Scene scene = new Scene(root);			
			primaryStage.setScene(scene);
			primaryStage.initModality(Modality.APPLICATION_MODAL);
			primaryStage.showAndWait();
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace()+"\n"+e.getMessage()+"\n"+e.getLocalizedMessage());
		}
    }

    @FXML
    void FilterBtnHandle(ActionEvent event) {
    	Request message = new Request(RequestType.GetAllSubscribers,"");
		Response res = getClient().handleMessageFromClientUI(message);
	
		ArrayList<Subscriber> all = (ArrayList<Subscriber>)res.getData();
		ArrayList<Subscriber> active=new ArrayList<Subscriber>() ;
		ArrayList<Subscriber> suspened=new ArrayList<Subscriber>() ;
		ArrayList<Subscriber> locked=new ArrayList<Subscriber>() ;
		
		for (int i = 0; i < all.size(); i++) {
			if (all.get(i).getCardStatus().equals(Subscriber.ReaderCardStatus.Active))
				active.add(all.get(i));
			if (all.get(i).getCardStatus().equals(Subscriber.ReaderCardStatus.Suspended))
				suspened.add(all.get(i));
			if (all.get(i).getCardStatus().equals(Subscriber.ReaderCardStatus.Locked))
				locked.add(all.get(i));
		}
		
		if(!res.isSucceeded()) { //failed
			this.DisplayMessage(AlertType.ERROR, "Search Failed", res.getMessage());
			return;
		}

		// success
		if (all.size() == 0) { // no user found
			this.DisplayMessage(AlertType.INFORMATION, "No Results Found", res.getMessage());
			return;
		}

		IDitem.setCellValueFactory(new PropertyValueFactory<Subscriber, String>("iD"));
		GivenNameitem.setCellValueFactory(new PropertyValueFactory<Subscriber, String>("firstName"));
		Usernameitem.setCellValueFactory(new PropertyValueFactory<Subscriber, String>("userName"));
		Statusitem.setCellValueFactory(new PropertyValueFactory<Subscriber, String>("cardStatus"));
		Graduationitem.setCellValueFactory(new PropertyValueFactory<Subscriber, String>("graduated"));
		ObservableList<Subscriber> data_table = FXCollections.observableArrayList();

		if (ShowByToggleGroup.getSelectedToggle().equals(ShowAllToggle)) {
			data_table.addAll(all);
			subscribersTable.setItems(data_table);
		}

		if (ShowByToggleGroup.getSelectedToggle().equals(ShowActiveToggle)) {
			data_table.addAll(active);
			subscribersTable.setItems(data_table);
		}

		if (ShowByToggleGroup.getSelectedToggle().equals(ShowSuspendedToggle)) {
			data_table.addAll(suspened);
			subscribersTable.setItems(data_table);
		}

		if (ShowByToggleGroup.getSelectedToggle().equals(ShowLockedToggle)) {
			data_table.addAll(locked);
			subscribersTable.setItems(data_table);
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
    void HelpHyperlinkHandle(ActionEvent event) {
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
	void ShowBtnHandle(ActionEvent event) {

		if (SubscriberIdTxt.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Missing fields");
			alert.setHeaderText("WARNING");
			alert.setContentText(" Please insert full Data ");
			alert.showAndWait();

		} else {
			Request message = new Request(RequestType.GetSubscriber, SubscriberIdTxt.getText());
			Response res = getClient().handleMessageFromClientUI(message);
			Subscriber sub = (Subscriber) res.getData();
			EditCardBtn.setVisible(isManager);

			if (!res.isSucceeded()) { // failed
				this.DisplayMessage(AlertType.ERROR, "Search Failed", res.getMessage());
				return;
			}

			IDitem.setCellValueFactory(new PropertyValueFactory<Subscriber, String>("iD"));
			GivenNameitem.setCellValueFactory(new PropertyValueFactory<Subscriber, String>("firstName"));
			Usernameitem.setCellValueFactory(new PropertyValueFactory<Subscriber, String>("userName"));
			Statusitem.setCellValueFactory(new PropertyValueFactory<Subscriber, String>("cardStatus"));
			Graduationitem.setCellValueFactory(new PropertyValueFactory<Subscriber, String>("graduated"));
			ObservableList<Subscriber> data_table = FXCollections.observableArrayList();

			data_table.add(sub);
			// edittableCols();
			subscribersTable.setItems(data_table);
		}
	}

	
	@FXML
	void initialize() {
		this.subscribersTable.getSelectionModel().selectedItemProperty()
				.addListener((obs, oldSelection, newSelection) -> {
					if (newSelection != null) {
						UserInfoTxt.setText(subscribersTable.getSelectionModel().getSelectedItem().getID() + ": "
								+ subscribersTable.getSelectionModel().getSelectedItem().getFirstName() + " "
								+ subscribersTable.getSelectionModel().getSelectedItem().getLastName());
						EditCardBtn.setVisible(true);
					}
					else {
						UserInfoTxt.setVisible(false);
						EditCardBtn.setVisible(false);
				}});
	}
	
	@Override
	public void DoClose(String message) {
	}

	public void setLibrarian(Librarian librarain) {
		this.librarianName = librarain.getFirstName()+" "+librarain.getLastName();
		
	}
}
