/**
 * Sample Skeleton for 'Workers.fxml' Controller Class
 */

package AppStart;
import java.util.List;
import Methods.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.ArrayList;



import Protocols.Request;
import Protocols.RequestType;
import Protocols.Response;


import javafx.scene.control.Alert;

import javafx.scene.control.Hyperlink;

import javafx.scene.control.RadioButton;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import javafx.scene.control.Alert.AlertType;

public class WorkersController extends AbstractController{




	@FXML
	private TableView<Librarian> WorkersTable;

	@FXML
	private TableColumn<Librarian,String> IDitem;

	@FXML
	private TableColumn<Librarian,String> GivenNameitem;

	@FXML
	private TableColumn<Librarian,String> Subnameitem;

	@FXML
	private TableColumn<Librarian,String> Departmentitem;
    @FXML
    private TableColumn<Librarian,String> Roleitem;
	@FXML
	private TextField WorkerIDTxt;
	@FXML // fx:id="FilterBtn"
	private Button FilterBtn; // Value injected by FXMLLoader

	@FXML // fx:id="ShowBtn"
	private Button ShowBtn; // Value injected by FXMLLoader

	@FXML // fx:id="DepartmentAllToggle"
	private RadioButton DepartmentAllToggle; // Value injected by FXMLLoader

	@FXML // fx:id="DepartmentToggleGroup"
	private ToggleGroup DepartmentToggleGroup; // Value injected by FXMLLoader

	@FXML // fx:id="ManagementToggle"
	private RadioButton ManagementToggle; // Value injected by FXMLLoader


	@FXML // fx:id="WorkerTxt"
	private Label WorkerTxt; // Value injected by FXMLLoader

	@FXML // fx:id="HelpHyperlink"
	private Hyperlink HelpHyperlink; // Value injected by FXMLLoader

	@FXML
	void ActiveToggleHandle(ActionEvent event) {

	}

	@FXML
	void DepartmentAllToggleHandle(ActionEvent event) {

	}

	@FXML
	void EmploymentAllToggleHandle(ActionEvent event) {

	}

	@FXML
	void FilterBtnHandle(ActionEvent event) {


		//	if(DepartmentToggleGroup.getSelectedToggle().equals(ManagementToggle)) {
		//	search="Management";
		//	}	

		Request message = new Request(RequestType.GetAllLibrarians,"");
		Response res = getClient().handleMessageFromClientUI(message);
		
		
		ArrayList<Librarian> librarians = (ArrayList<Librarian>)res.getData();
		
		
		
		ArrayList<Librarian> managers=new ArrayList<Librarian>() ;
		for(int i = 0 ;i < librarians.size() ;i++)
		{
		if(librarians.get(i).getRole().equals(Librarian.Role.Manager))
			managers.add(librarians.get(i));
		}
		

		if(!res.isSucceeded()) { //failed
			this.DisplayMessage(AlertType.ERROR, "Search Failed", res.getMessage());
			return;
		}

		//success

		if(librarians.size()==0) { // no books found
			this.DisplayMessage(AlertType.INFORMATION, "No Results Found", res.getMessage());
			return;
		}

	
			IDitem.setCellValueFactory(new PropertyValueFactory<Librarian,String>("iD"));
			GivenNameitem.setCellValueFactory(new PropertyValueFactory<Librarian,String>("firstName"));
			Subnameitem.setCellValueFactory(new PropertyValueFactory<Librarian,String>("userName"));
			Departmentitem.setCellValueFactory(new PropertyValueFactory<Librarian,String>("phone"));
			Roleitem.setCellValueFactory(new PropertyValueFactory<Librarian,String>("Role"));
			ObservableList<Librarian> data_table = FXCollections.observableArrayList();

			
			if(DepartmentToggleGroup.getSelectedToggle().equals(DepartmentAllToggle)) {
			for(int i = 0 ;i < librarians.size() ;i++)
			{
				data_table.add(librarians.get(i));
			}


			//edittableCols();

			WorkersTable.setItems(data_table);	


			
			
			}
			
			if(DepartmentToggleGroup.getSelectedToggle().equals(ManagementToggle)) {
				
				for(int i = 0 ;i < managers.size() ;i++)
				{
					data_table.add(managers.get(i));
				}


				//edittableCols();

				WorkersTable.setItems(data_table);	

			}
			

		}
	
		private void edittableCols() {

			WorkersTable.setEditable(true);

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
		void InactiveToggleHandle(ActionEvent event) {

		}

		@FXML
		void LockedToggleHandle(ActionEvent event) {

		}

		@FXML
		void ManagementToggleHandle(ActionEvent event) {

		}

		@FXML
		void ShowBtnHandle(ActionEvent event) {
			if (WorkerIDTxt.getText().trim().isEmpty())
			{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Missing fields");
				alert.setHeaderText("WARNING");
				alert.setContentText(" Please insert full Data ");
				alert.showAndWait();

			} else {
				Request message = new Request(RequestType.GetLibrarian,WorkerIDTxt.getText());
				Response res = getClient().handleMessageFromClientUI(message);
				Librarian lib=(Librarian)res.getData();
				if(!res.isSucceeded()) { //failed
					this.DisplayMessage(AlertType.ERROR, "Search Failed", res.getMessage());
					return;
				}
				IDitem.setCellValueFactory(new PropertyValueFactory<Librarian,String>("iD"));
				GivenNameitem.setCellValueFactory(new PropertyValueFactory<Librarian,String>("firstName"));
				Subnameitem.setCellValueFactory(new PropertyValueFactory<Librarian,String>("userName"));
				Departmentitem.setCellValueFactory(new PropertyValueFactory<Librarian,String>("phone"));
				Roleitem.setCellValueFactory(new PropertyValueFactory<Librarian,String>("Role"));
				ObservableList<Librarian> data_table = FXCollections.observableArrayList();

					data_table.add(lib);

				//edittableCols();

				WorkersTable.setItems(data_table);	


				 
			}
		}
		@FXML
		void SuspendedToggleHandle(ActionEvent event) {

		}

		@Override
		public void DoClose(String message) {
			// TODO Auto-generated method stub
		}
	}
