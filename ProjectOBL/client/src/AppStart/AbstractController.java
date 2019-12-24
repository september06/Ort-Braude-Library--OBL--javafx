package AppStart;

import client.ChatClient;
import common.ChatIF;

public abstract class AbstractController implements ChatIF {
	
	ChatClient client;
	boolean isSubscriber, isLibrarian , isManager;
	
	public ChatClient getClient() {
		return client;
	}

	public void setClient(ChatClient client) {
		this.client = client;
	}
	
	public void setUserMode(boolean isSubscriber, boolean isLibrarian, boolean isManager) {
		this.isSubscriber=isSubscriber;
		this.isLibrarian=isLibrarian;
		this.isManager=isManager;
	}
}
