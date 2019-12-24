package application;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;




/**
 * Sample Skeleton for 'Connect.fxml' Controller Class
 */

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.sun.javafx.scene.control.skin.DatePickerSkin;

import AppStart.FirstController;
import client.ChatClient;
import common.ChatIF;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.image.Image;
import client.*;
import Guies.*;



public class ConnectController implements ChatIF 
{
	private String host;
	private int port;
	private boolean connected;

	 //Class variables *************************************************
	  
	  /**
	   * The default port to connect on.
	   */
	  final public static int DEFAULT_PORT = 5555;
	  
	  //Instance variables **********************************************
	  
	  /**
	   * The instance of the client that created this ConsoleChat.
	   */
	  ChatClient client;

	  
	@FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tfHost"
    private TextField tfHost; // Value injected by FXMLLoader

    @FXML // fx:id="thPort"
    private TextField thPort; // Value injected by FXMLLoader

    @FXML // fx:id="lablServerStatus"
    private Label lablServerStatus; // Value injected by FXMLLoader

    @FXML // fx:id="btnConnect"
    private Button btnConnect; // Value injected by FXMLLoader

    @FXML
    void btnConnectOnAction(ActionEvent event) {
    
    	if(connected) {
    		Alert alert=new Alert(AlertType.WARNING); 
    		alert.setTitle("Server Connected ");
    		alert.setHeaderText("WARNING");
    		alert.setContentText("You are alredy connected to Serve");
    		alert.showAndWait();
    		
    		return;
    	}

    	this.host=getTfHost().getText();
    	this.port= Integer.parseInt(getThPort().getText());
    	
    	System.out.println("Try to Connect to: " + host +"\n"+"With Port : "+ port);
    	
    	try
    	{
    		client = new ChatClient(this.host, this.port, this);
    		connected = true;
    		Alert alert=new Alert(AlertType.INFORMATION); 
    		alert.setTitle("Connect Succeed");
    		alert.setHeaderText("Connection to Server Established.");
    		alert.setContentText("You have successfully connected to server.");
    		alert.showAndWait();
    		lablServerStatus.setText("Server is running");
    		System.out.println("Connecting Succeeded");
    		//client.handleMessageFromClientUI("ciyikdtkghk,ghk,jh,ljh,.h");

    		((Node)event.getSource()).getScene().getWindow().hide();
    		accept();  //Wait for console data
    	
    	}
    	catch(IOException e)
    	{
    		connected = false;
    		System.out.println("Error: Can't setup connection!\n"+e+"\n"+e.getMessage());
    		Alert alert=new Alert(AlertType.ERROR); 
    		alert.setHeaderText("Connection Failure");
    		alert.setTitle("Connection Failure");
    		alert.setContentText("Connecting To Server Failure: UnKnow Host:" +host);
    		alert.showAndWait();
    		System.out.println("Connection Failure");
    	
    		
    	}

    }
    
    public ChatClient getClient() {
		return client;
	}

	public void setClient(ChatClient client) {
		this.client = client;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public TextField getTfHost() {
		return tfHost;
	}

	public void setTfHost(TextField tfHost) {
		this.tfHost = tfHost;
	}

	public TextField getThPort() {
		return thPort;
	}

	public void setThPort(TextField thPort) {
		this.thPort = thPort;
	}

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert tfHost != null : "fx:id=\"tfHost\" was not injected: check your FXML file 'Connect.fxml'.";
        assert thPort != null : "fx:id=\"thPort\" was not injected: check your FXML file 'Connect.fxml'.";
        assert lablServerStatus != null : "fx:id=\"lablServerStatus\" was not injected: check your FXML file 'Connect.fxml'.";
        assert btnConnect != null : "fx:id=\"btnConnect\" was not injected: check your FXML file 'Connect.fxml'.";

    }
    
    //SERVER & CLIENT
    
    /**
     * This method overrides the method in the ChatIF interface.
     * It handles messages and/or replies from server
     *
     * @param message The string to be displayed.
     */
    public void display(Object message) 
    {
      System.out.println("> " + message);
    }
   
    public void error(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText(message);
		alert.setTitle("Server Error");
		alert.setHeaderText("Server Error");
		alert.showAndWait();
    }
    
    /**
     * This method waits for input from the console.  Once it is 
     * received, it sends it to the client's message handler.
     */
    public void accept() throws IOException
    {
    	Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/FirstWindow.fxml"));
		try {
			Pane root = loader.load();

			DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
			Node popupContent = datePickerSkin.getPopupContent();			
			Label lb = new Label("    ");
			Label lb1 = new Label("    ");
			Label lb3 = new Label("    ");
			Label lb4 = new Label("calendar");
			Label lb5 = new Label("    ");
			lb4.setUnderline(true);
			lb4.setStyle("-fx-font-size:20;\n" + "-fx-text-fill:#ffffff;\n" + "-fx-font-weight: bold;\n"
					+ "-fx-font-family:'Comic Sans MS';\n");
			VBox cln = new VBox(lb4, popupContent, lb5);
			cln.setAlignment(Pos.CENTER);
			HBox cln2 = new HBox(lb1, cln, lb);
			cln.setStyle("-fx-border-color:#000000;\n" + "-fx-border-width:0;\n");
			cln2.setStyle("-fx-border-color:#000000;\n" + "-fx-border-width:3;\n" + "-fx-border-radius: 0 30 30 0; \n"
					+ "-fx-background-color:#ffffff85;\n" + "-fx-background-radius: 0 30 30 0;\n ");

			FirstController firstController = loader.getController();
			firstController.getGrido().add(cln2, 0, 1);

			primaryStage.setTitle("OBL Library");
			Image anotherIcon = new Image("/image/oblLogo.jpg");
			primaryStage.getIcons().add(anotherIcon);

			firstController.setClient(client);
			client.AddClientUI(firstController);

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
}
