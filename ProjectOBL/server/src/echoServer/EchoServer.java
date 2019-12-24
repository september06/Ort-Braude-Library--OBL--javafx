package echoServer;
// This file contains material supporting section 3.7 of the textbook:

// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.sql.DriverManager;
import java.sql.SQLException;

import ocsf.server.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Protocols.Request;
import Protocols.Response;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer {
	private static EchoServer server;

	// Class variables *************************************************
	// Constructors ****************************************************

		/**
		 * Constructs an instance of the echo server.
		 *
		 * @param port The port number to connect on.
		 */
		public EchoServer(int port) {
			super(port);
		}

	private String serverStatus;
	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;

	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 */
	public void handleMessageFromClient(Object rsq, ConnectionToClient client) {
		System.out.println("Recieved from " + client + ": " + rsq);
		Request req = (Request) rsq;
		try {
			Response res = DataBaseManagemer.getDBManager().HandleRequest(req);
			client.sendToClient(res);
			System.out.println("Send to " + client + ": " + res);
		} catch (Exception ex) {
			System.out.println("Failed to send to client " + client + "\n" + ex.getMessage());
		}
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}
	
	@Override
	protected void serverClosed() {
		DataBaseManagemer.getDBManager().LogOutAll();
	}

	// Class methods ***************************************************

	/**
	 * This method is responsible for the creation of the server instance (there is
	 * no UI in this phase).
	 *
	 * @param args[0] The port number to listen on. Defaults to 5555 if no argument
	 *        is entered.
	 */
	public void connectTOServer() {
		Alert alert;
		int port = 0; // Port to listen on

		try {
			port = getPort(); // Get port from user line

			

		} catch (Throwable t) {
			alert = new Alert(AlertType.WARNING, "Worng Port input we will set default at port 5555", ButtonType.OK);
			alert.setTitle("Warning");
			alert.show();
			port = DEFAULT_PORT; // Set port to 5555
		}

		EchoServer sv = new EchoServer(port);
		try {
			sv.listen(); // Start listening for connections
			String msg = "Server listening for connections on port " + getPort();
			
			alert = new Alert(AlertType.INFORMATION, msg, ButtonType.OK);
			alert.setHeaderText("Running the server");
			alert.setTitle("Information");
			alert.show();
			setServerStatus("Server is running");
		} catch (Exception ex) {
			alert = new Alert(AlertType.ERROR, "ERROR occured while running the server", ButtonType.OK);
			alert.setTitle("Warning");
			alert.show();
			System.out.println("ERROR - Could not listen for clients!");

		}
	}

	public String getServerStatus() {
		return serverStatus;
	}

	public void setServerStatus(String serverStatus) {
		this.serverStatus = serverStatus;
	}

	public static void setServer(EchoServer ser) {
		EchoServer.server = ser;
	}
	
	public static EchoServer getServer() {
		return EchoServer.server;
	}

}
//End of EchoServer class
