package Protocols;

import Methods.User;

public class LogInResponse extends Response{
	boolean isLibrarian, isSubscriber, isManager;

	public LogInResponse(boolean succeeded, String ID, String message, boolean isLibrarian, boolean isManager, boolean isSubscriber) {
		super(RequestType.LogIn, succeeded, ID, message);
		this.isLibrarian=isLibrarian;
		this.isSubscriber=isSubscriber;
		this.isManager = isManager;
	}
	
	public LogInResponse(boolean succeeded, String message) {
		super(RequestType.LogIn, succeeded, null, message);
		this.isLibrarian=false;
		this.isManager=false;
		this.isSubscriber=false;
	}

	public boolean isLibrarian() {
		return isLibrarian;
	}

	public void setLibrarian(boolean isLibrarian) {
		this.isLibrarian = isLibrarian;
	}

	public boolean isSubscriber() {
		return isSubscriber;
	}

	public void setSubscriber(boolean isSubscriber) {
		this.isSubscriber = isSubscriber;
	}
	public boolean isManager() {
		return isManager;
	}
	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}
}
