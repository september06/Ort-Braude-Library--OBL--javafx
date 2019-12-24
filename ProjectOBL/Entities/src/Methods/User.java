package Methods;

import java.io.Serializable;

public class User implements Serializable {
	private String userName, Password, ID, FirstName, LastName, Phone, Email;
	private boolean online;

	/*User Constructor*/
	public User(String iD, String firstName, String lastName, String userName, String password, String phone, String email, boolean onLineStatus) {
		this.userName = userName;
		Password = password;
		ID = iD;
		FirstName = firstName;
		LastName = lastName;
		Phone = phone;
		Email = email;
		online = onLineStatus;
	}
	
	/*Setters & Getters of attributes*/
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String name) {
		FirstName = name;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}
	
	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean isOnline) {
		this.online = isOnline;
	}
	@Override/*toString of User*/
	public String toString() {
		return "UserName: " + userName + "\nPassword: " + Password + "\nID: " + ID + "\nFirst Name: " + FirstName + "\nLast Name: "
				+ LastName + "\nPhone: " + Phone + "\nEmail: " + Email + "\nOnline Status: " + this.online;
	}
}