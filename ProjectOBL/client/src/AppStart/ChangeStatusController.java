/**
 * Sample Skeleton for 'Change Permission.fxml' Controller Class
 */

package AppStart;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import common.ChatIF;


public class ChangeStatusController implements ChatIF {

	ChatClient client;

	public ChatClient getClient() {
		return client;
	}

	public void setClient(ChatClient client) {
		this.client = client;
	}

	    @FXML // ResourceBundle that was given to the FXMLLoader
	    private ResourceBundle resources;

	    @FXML // URL location of the FXML file that was given to the FXMLLoader
	    private URL location;

	    @FXML // fx:id="ActiveToggle"
	    private RadioButton ActiveToggle; // Value injected by FXMLLoader

	    @FXML // fx:id="ToggleStatus"
	    private ToggleGroup ToggleStatus; // Value injected by FXMLLoader

	    @FXML // fx:id="SuspendedToggle"
	    private RadioButton SuspendedToggle; // Value injected by FXMLLoader

	    @FXML // fx:id="LockedToogle"
	    private RadioButton LockedToogle; // Value injected by FXMLLoader

	    @FXML // fx:id="ApplyBtn"
	    private Button ApplyBtn; // Value injected by FXMLLoader

	    @FXML
	    void ActiveToggleHandle(ActionEvent event) {

	    }

	    @FXML
	    void ApplyBtnHandle(ActionEvent event) {

	    }

	    @FXML
	    void LockedToogleHandle(ActionEvent event) {

	    }

	    @FXML
	    void SuspendedToggleHandle(ActionEvent event) {

	    }

	    @FXML // This method is called by the FXMLLoader when initialization is complete
	    void initialize() {
	        assert ActiveToggle != null : "fx:id=\"ActiveToggle\" was not injected: check your FXML file 'Change Permission.fxml'.";
	        assert ToggleStatus != null : "fx:id=\"ToggleStatus\" was not injected: check your FXML file 'Change Permission.fxml'.";
	        assert SuspendedToggle != null : "fx:id=\"SuspendedToggle\" was not injected: check your FXML file 'Change Permission.fxml'.";
	        assert LockedToogle != null : "fx:id=\"LockedToogle\" was not injected: check your FXML file 'Change Permission.fxml'.";
	        assert ApplyBtn != null : "fx:id=\"ApplyBtn\" was not injected: check your FXML file 'Change Permission.fxml'.";

	    }

		@Override
		public void DoClose(String message) {
			// TODO Auto-generated method stub
			
		}
}
