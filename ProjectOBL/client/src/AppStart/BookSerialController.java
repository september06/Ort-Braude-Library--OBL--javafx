/**
 * Sample Skeleton for 'BookSerial.fxml' Controller Class
 */

package AppStart;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import common.ChatIF;
import javafx.fxml.FXML;

public class BookSerialController implements ChatIF {

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

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }

	@Override
	public void DoClose(String message) {
		// TODO Auto-generated method stub
		
	}
}
