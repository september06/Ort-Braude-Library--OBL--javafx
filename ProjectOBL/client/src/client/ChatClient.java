// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import AppStart.SearchResultController;
import Protocols.Response;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  private ArrayList<ChatIF> clientUIs = new ArrayList<ChatIF>();
  private boolean awaitResponse;
  private Response response;
  

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUIs.add(clientUI);
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
	  System.out.println("Recieved from server " + msg);
	  this.response = (Response) msg;
	  this.awaitResponse = false;
	  //clientUI.display(msg);
  }

	/**
	 * This method handles all data coming from the UI
	 *
	 * @param message The message from the UI.
	 */
	public Response handleMessageFromClientUI(Object message) {
		try {
			awaitResponse = true;
			System.out.println(this + ": send to Server "+message);
			sendToServer(message);
			
			//wait for response from server
			while (awaitResponse) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return this.response;
		} catch (IOException e) {
			System.out.println("Could not send message to server."+e.getMessage());
			this.closeAllUIs();
			quit();
			return null;
		}
	}
  

  private void closeAllUIs() {
		for(ChatIF cUI: this.clientUIs)
			cUI.DoClose("Disconnnected from Server. Terminating.");
	}
  
  public void RemoveCientUI(ChatIF cUI) {
	  this.clientUIs.remove(cUI);
  }

/**
   * This method handles all data coming from the UI            
   *
   * @param message The list-message from the UI.    
   */
 /* public void handleMessageFromClientUI(Request message)
  {
	    try
	    {
	    	sendToServer(message);
	    }
	    catch(IOException e)
	    {
	      clientUI.error
	        ("Could not send message to server.  Terminating client.\n"+e.getMessage());
	      quit();
	    }
	  }*/
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
      this.closeAllUIs();
    }
    catch(IOException e) {}
    System.exit(0);
  }
  
  public void AddClientUI(ChatIF cIF) {
		this.clientUIs.add(cIF);
	}
}
//End of ChatClient class
