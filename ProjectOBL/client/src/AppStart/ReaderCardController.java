/**
 * Sample Skeleton for 'Subscriber.fxml' Controller Class
 */

package AppStart;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import Methods.Activity;
import Methods.Book;
import Methods.Borrow;
import Methods.Extension;
import Methods.Librarian;
import Methods.Notif;
import Methods.Order;
import Methods.Subscriber;
import Methods.Subscriber.ReaderCardStatus;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ReaderCardController extends AbstractController {
	private Librarian librarian;

	private Subscriber subscriber;
	@FXML
	private ImageView ProfilePic;
	@FXML // fx:id="EditProfileBtn"
	private Button EditProfileBtn; // Value injected by FXMLLoader

	@FXML // fx:id="CardStatusHyperLink"
	private Hyperlink CardStatusHyperLink; // Value injected by FXMLLoader
	@FXML
	private Label userNameLable;

	public Hyperlink getCardStatusHyperLink() {
		return CardStatusHyperLink;
	}
	
	@FXML
	private Text borrowLabel;
	
	@FXML
	private Text cancelLabel;
	
	@FXML
	private Label statusLab;

	@FXML
	private Label subNameLabel;
	////////////////////////////////////////////////
	@FXML
	private TableView<Activity> myHistoryTable;

	@FXML
	private TableColumn<Activity,String> activityNameView;

	@FXML
	private TableColumn<Activity, Timestamp> issueDateView;

	///////////////////////////////////////////////
	@FXML
	private TableView<Borrow> borrowingTable;

	@FXML
	private TableColumn<Borrow, Integer> bIssueDateView;

	@FXML
	private TableColumn<Borrow,String> bookNameView;

	@FXML
	private TableColumn<Borrow, Date> dueDateView;

	@FXML
	private TableColumn<Borrow, Date> returndView;
	//////////////////////////////////////////

	@FXML
	private TableView<Order> orderTable;

	@FXML
	private TableColumn<Order, String> orderDateView;

	@FXML
	private TableColumn<Order, String> orderedBookView;

	@FXML
	private TableColumn<Order,String> orderStatusView;
	////////////////////////////////////////////////////////////
	@FXML
	private TableView<Notif> reminderTable;

	@FXML
	private TableColumn<Notif, Timestamp> notissueDate;

	@FXML
	private TableColumn<Notif, String> notMessage;

	@FXML
	private Button extendBtn;

	@FXML
	private Button cancelBtn;

	private String librarianName;
	
	@FXML
	void cancelBtnHandle(ActionEvent event) {
		Order order = this.orderTable.getSelectionModel().getSelectedItem();
		Request req = new Request(RequestType.DeleteOrder, order);
		Response res = getClient().handleMessageFromClientUI(req);
		if(!res.isSucceeded()) {
			DisplayMessage(AlertType.ERROR, "Failed to cancel order.", res.getMessage());
			return;
		}
		DisplayMessage(AlertType.INFORMATION, "Order Cancelled Susccessfully", res.getMessage());
		initTableAndColOrder();
	}
	
	@FXML
	void extendBtnHandle(ActionEvent event) {
		Borrow borrow = this.getSelectedBorrow();		
		String extName = "Borrow" + ((this.isLibrarian)?" By "+this.librarianName : "");
		java.util.Date currentDate= new java.util.Date();
		long time = currentDate.getTime();
		Timestamp now = new Timestamp(time);
		
		/*
		 * public Extension(String subscriberID, Timestamp borrowingDate, 
		 * Timestamp issueDate, String activityName, boolean manual)
		 */
		Extension ext = new Extension(subscriber.getID(), borrow.getIssueDate(), now, extName, this.isLibrarian);

		Request req = new Request(RequestType.Extend, ext);
		Response res = getClient().handleMessageFromClientUI(req);
		if(!res.isSucceeded()) {
			DisplayMessage(AlertType.ERROR, "Failed to exten borrowing.", res.getMessage());
			return;
		}
		DisplayMessage(AlertType.INFORMATION, "Borrowing was extended Susccessfully", res.getMessage());
		initTableAndColBorrowing();
	}
	
	private Borrow getSelectedBorrow() {
		return this.borrowingTable.getSelectionModel().getSelectedItem();
	}
	
	@FXML
	void CardStatusHyperLinkHandle(ActionEvent event) {

		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/Change Permission.fxml"));
		try {
			Pane root = loader.load();

			ChangeStatusController changeStatusController = loader.getController();
			changeStatusController.setClient(client);
			client.AddClientUI(changeStatusController);

			Platform.setImplicitExit(false);

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			System.out.println(e.getStackTrace() + "\n" + e.getMessage() + "\n" + e.getLocalizedMessage());
		}
	}

	@FXML
	void EditProfileBtnHandle(ActionEvent event) {
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/Profile Editor.fxml"));
		try {
			Pane root = loader.load();
			if (isSubscriber) {

			}
			ProfileEditorController profileEditorController = loader.getController();

			if(isSubscriber)
			{
				profileEditorController.setSubscriber(subscriber);
			}
			else
			{
				profileEditorController.setLibrarian(librarian);
			}
			profileEditorController.setClient(client);
			//client.AddClientUI(profileEditorController);
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

	@FXML
	void initialize() {
		Image img = new Image("/image/3.png");
		ProfilePic.setImage(img);
		
		this.borrowingTable.getSelectionModel().selectedItemProperty()
		.addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) 
			{
				if(subscriber.getCardStatus()!=ReaderCardStatus.Active) {
					this.extendBtn.setVisible(false);
					this.borrowLabel.setVisible(false);
					return;
				}
				if(!isLastWeekofBorrow(getSelectedBorrow())) {
					this.extendBtn.setVisible(false);
					this.borrowLabel.setVisible(true);
					this.borrowLabel.setText("Can be extended duting the last week only.");
					return;
				}
				if(getSelectedBorrow().getReturnDate()!=null) {
					this.extendBtn.setVisible(false);
					this.borrowLabel.setVisible(true);
					this.borrowLabel.setText("Already Returned.");
					return;
				}
				if(!getSelectedBorrow().isExtendable()) {
					this.extendBtn.setVisible(false);
					this.borrowLabel.setVisible(true);
					this.borrowLabel.setText("This Borrow is inextendable.");
					return;
				}
				this.extendBtn.setVisible(true);
				this.borrowLabel.setVisible(true);
				this.borrowLabel.setText("Please select a book and press \"Extend\" Button to complete extention.");
			}
			else {
				this.extendBtn.setVisible(false);
				this.borrowLabel.setVisible(false);
		}});
		
		this.orderTable.getSelectionModel().selectedItemProperty()
		.addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				this.cancelBtn.setVisible(this.isSubscriber);
				this.cancelLabel.setVisible(this.isSubscriber);
			}
			else {
				this.cancelBtn.setVisible(false);
				this.cancelLabel.setVisible(false);
		}});
	}

	private boolean isLastWeekofBorrow(Borrow borrow) {
		long dueTime = borrow.getDueDate().getTime();
		java.util.Date date= new java.util.Date();
		long today = date.getTime();
		
		if(dueTime < today)
			return true;
		
		long diffDays = (dueTime-today) / (1000 * 60 * 60 * 24);
		if(diffDays > 1 && diffDays <=7)
			return true;
		
		return false;
	}

	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
		userNameLable.setText(this.getSubscriber().getUserName());
		subNameLabel.setText(this.getSubscriber().getFirstName()+" "+getSubscriber().getLastName());
		statusLab.setText(this.getSubscriber().getCardStatus().toString());
	}

	public Subscriber getSubscriber() {
		return subscriber;
	}

	public Label getUserNameLable() {
		return userNameLable;
	}

	public void setUserNameLable(String userName) {
		this.userNameLable.setText(userName);
	}

	public Label getStatusLab() {
		return statusLab;
	}

	public void setStatusLab(String status) {
		this.statusLab.setText(status);
	}

	public Label getSubNameLabel() {
		return subNameLabel;
	}

	public void setSubNameLabel(String subName) {
		this.subNameLabel.setText(subName);
	}

	public Button getEditProfileBtn() {
		return EditProfileBtn;
	}

	public void setEditProfileBtn(Button editProfileBtn) {
		EditProfileBtn = editProfileBtn;
	}

	public Librarian getLibrarian() {
		return librarian;
	}

	public void setLibrarian(Librarian librarian) {
		this.librarian = librarian;
		userNameLable.setText(this.getLibrarian().getUserName());
		subNameLabel.setText(this.getLibrarian().getFirstName());
		statusLab.setText("Active");
	}
	public void initTableAndColNotify() {
		Request message = new Request(RequestType.GetNotifsBySubscriber,getSubscriber().getID());
		Response res = getClient().handleMessageFromClientUI(message);


		if (!res.isSucceeded()) { // failed
			this.DisplayMessage(AlertType.ERROR, "Search Failed", res.getMessage());
			return;
		}
		// success
		ArrayList<Notif> notif = (ArrayList<Notif>) res.getData();
		if (notif.size() == 0) { // no books found
			//this.DisplayMessage(AlertType.INFORMATION, "No Results Found", res.getMessage());
			return;
		}

		notissueDate.setCellValueFactory(new PropertyValueFactory<Notif, Timestamp>("issueDate"));
		notMessage.setCellValueFactory(new PropertyValueFactory<Notif, String>("message"));


		ObservableList<Notif> data_table = FXCollections.observableArrayList();
		data_table.addAll(notif);

		reminderTable.setItems(data_table);	

	}
	public void initTableAndColBorrowing() {

		Request	message = new Request(RequestType.GetBorrowsBySubscriber,getSubscriber().getID());

		Response res = getClient().handleMessageFromClientUI(message);

		if (!res.isSucceeded()) { // failed
			this.DisplayMessage(AlertType.ERROR, "Search Failed", res.getMessage());
			return;
		}
		// success
		ArrayList<Borrow> borrow = (ArrayList<Borrow>) res.getData();
		if (borrow.size() == 0) { // no books found
			orderTable.getItems().clear();
			///this.DisplayMessage(AlertType.INFORMATION, "No Results Found", res.getMessage());
			return;
		}
		bIssueDateView.setCellValueFactory(new PropertyValueFactory<Borrow, Integer>("IssueDate"));
		bookNameView.setCellValueFactory(new PropertyValueFactory<Borrow,String>("copyISBN"));
		dueDateView.setCellValueFactory(new PropertyValueFactory<Borrow, Date>("DueDate"));
		returndView.setCellValueFactory(new PropertyValueFactory<Borrow, Date>("ReturnDate"));


		ObservableList<Borrow> data_table = FXCollections.observableArrayList();
		data_table.addAll(borrow);
		borrowingTable.setItems(data_table);	

	}
	public void initTableAndColOrder() {
		Request	message = new Request(RequestType.GetOrdersBySubscriber,getSubscriber().getID());
		Response res = getClient().handleMessageFromClientUI(message);

		if (!res.isSucceeded()) { // failed
			this.DisplayMessage(AlertType.ERROR, "Search Failed", res.getMessage());
			return;
		}
		// success
		ArrayList<Order> orders = (ArrayList<Order>) res.getData();
		if (orders.size() == 0) { // no books found
			orderTable.getItems().clear();
			//this.DisplayMessage(AlertType.INFORMATION, "No Results Found", res.getMessage());
			return;
		}
		//Order
		
		orderDateView.setCellValueFactory(new PropertyValueFactory<Order, String>("SaveDate"));
		orderedBookView.setCellValueFactory(new PropertyValueFactory<Order, String>("ISBN"));
		orderStatusView.setCellValueFactory(new PropertyValueFactory<Order,String>("realised"));


		ObservableList<Order> data_table = FXCollections.observableArrayList();
		data_table.addAll(orders);
		orderTable.setItems(data_table);	

	}
	private void DisplayMessage(AlertType type, String title, String msg) {
		Alert alert = new Alert(type);
		alert.setTitle(type.toString());
		alert.setHeaderText(title);
		alert.setContentText(msg);
		alert.showAndWait();
		return;
	}
	public void initTableAndColActivity() {
		Request	message = new Request(RequestType.GetActivitiesBySubscriber,getSubscriber().getID());
		Response res = getClient().handleMessageFromClientUI(message);


		if (!res.isSucceeded()) { // failed
			this.DisplayMessage(AlertType.ERROR, "Search Failed", res.getMessage());
			return;
		}
		// success
		ArrayList<Activity> activity = (ArrayList<Activity>) res.getData();
		if (activity.size() == 0) { // no books found
			//this.DisplayMessage(AlertType.INFORMATION, "No Results Found", res.getMessage());
			return;
		}

		activityNameView.setCellValueFactory(new PropertyValueFactory<Activity,String>("subscriberID"));
		issueDateView.setCellValueFactory(new PropertyValueFactory<Activity, Timestamp>("issueDate"));


		ObservableList<Activity> data_table = FXCollections.observableArrayList();
		data_table.addAll(activity);

		myHistoryTable.setItems(data_table);	

	}
	public void initTables() {

		this.initTableAndColActivity();
		this.initTableAndColBorrowing();
		this.initTableAndColNotify();
		this.initTableAndColOrder();
	}
	@Override
	public void setUserMode(boolean isSubscriber, boolean isLibrarian, boolean isManager) {
		super.setUserMode(isSubscriber, isLibrarian, isManager);
		this.EditProfileBtn.setVisible(isSubscriber);
		this.CardStatusHyperLink.setDisable(!isManager);
	}
	public void setLibrarianName(String librarianName) {
		this.librarianName=librarianName;
	}
}
