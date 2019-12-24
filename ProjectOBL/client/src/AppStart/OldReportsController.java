/**
 * Sample Skeleton for 'Reports.fxml' Controller Class
 */

package AppStart;

import client.ChatClient;
import common.ChatIF;

public class OldReportsController implements ChatIF {
	
	ChatClient client;

	public  ChatClient getClient() {
		return client;
	}

	public  void setClient(ChatClient client) {
		this.client = client;
	}

	@Override
	public void DoClose(String message) {
		// TODO Auto-generated method stub
		
	}
}
