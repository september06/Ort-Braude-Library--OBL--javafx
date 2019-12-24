/**
 * Sample Skeleton for 'Server.fxml' Controller Class
 */

package application;

import java.io.IOException;
import java.sql.Connection;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import echoServer.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ServerController {
	// constructor
	private EchoServer echoServer;
	
	@FXML // fx:id="thPort"
	private TextField thPort; // Value injected by FXMLLoader

	@FXML // fx:id="lablServerStatus"
	private Label lablDBStatus; // Value injected by FXMLLoader
	
	@FXML // fx:id="lablServerStatus"
	private Label lablServerStatus; // Value injected by FXMLLoader

	@FXML // fx:id="tfUserName"
	private TextField tfUserName; // Value injected by FXMLLoader

	@FXML // fx:id="pfPassword"
	private PasswordField pfPassword; // Value injected by FXMLLoader

	@FXML // fx:id="btnConnect"
	private Button btnConnect; // Value injected by FXMLLoader

	@FXML // fx:id="conectLogo"
	private ImageView conectLogo; // Value injected by FXMLLoader

	@FXML // fx:id="resetLogo"
	private Button resetLogo; // Value injected by FXMLLoader

	@FXML // fx:id="exitLogo"
	private ImageView reLogo;
	@FXML // fx:id="btnExit"
	private Button btnExit; // Value injected by FXMLLoader

	@FXML // fx:id="exitLogo"
	private ImageView exitLogo; // Value injected by FXMLLoader
	@FXML // fx:id="btnMySQL"
	private Button btnMySQL; // Value injected by FXMLLoader
	@FXML // fx:id="warningMsg"
	private Label warningMsg; // Value injected by FXMLLoader
	@FXML // fx:id="sQLoff"
	private Label sQLoff; // Value injected by FXMLLoader

	@FXML // fx:id="warning"
	private Label warning; // Value injected by FXMLLoader
	
	@FXML
	private void onClickExit(ActionEvent event){
	}
	
	@FXML
	void initialize() {
		try {
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

		ScheduledFuture scheduledFuture =
		    scheduledExecutorService.schedule(new Callable() {
		        public Object call() {
		        	DataBaseManagemer.getDBManager().NotifyRetunrs();
		        	DataBaseManagemer.getDBManager().DeleteOrders();
		        	return "";
		        }
		    },
		    1,
		    TimeUnit.DAYS);
		}
		catch(Exception ex) {
			
		}
	}
	
	@FXML
	void btnConnectOnAction(ActionEvent event) {
		getBtnConnect().setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				getBtnConnect().setCursor(Cursor.HAND);
				getBtnConnect().setTooltip(new Tooltip("Connect to Server and Database"));
			}
		});
		getBtnConnect().setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
			
			if (getThPort().trim().isEmpty())
			{
				String msg = "Empty Text Field!";
				Alert alret = new Alert(AlertType.WARNING,msg, ButtonType.OK);
				alret.setTitle("WARNING");
				alret.show();				
			}
			else {
				echoServer = new EchoServer(Integer.parseInt(getThPort()));
				EchoServer.setServer(echoServer);
				DataBaseManagemer dataConnect = DataBaseManagemer.getDBManager();
				echoServer.connectTOServer();
				thPort.setDisable(true);
				setLablServerStatus(echoServer.getServerStatus());
				
				if (dataConnect.connection(getTfUserName(),getPfPassword()) == null) {
					btnMySQL.setVisible(true);
					warning.setVisible(true);
					warningMsg.setVisible(true);
					//sQLoff.setVisible(true);
					getBtnConnect().setVisible(false);
				}
				else {
					btnConnect.setDisable(true);
					setLablServerStatus(echoServer.getServerStatus());
					lablDBStatus.setText("DataBase Connected.");
					tfUserName.setDisable(true);
					pfPassword.setDisable(true);
				}
			}
			}
		});
	}

	@FXML
	void btnExitOnAction(ActionEvent event) {
		getBtnExit().setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				getBtnExit().setCursor(Cursor.HAND);
				getBtnExit().setTooltip(new Tooltip("Trun off the Server!"));
			}
		});

		getBtnExit().setOnMouseClicked(e -> {
			try {
				System.out.println("Closing...");
				if(echoServer != null)
					echoServer.close();
				System.exit(0);
			} catch (IOException ex) {
				System.out.println("Server Controller: failed to close server.\n" + ex.getMessage());
			}
		});
	}

	@FXML
	void btnResetOnAction(ActionEvent event) {

		getbtnReset().setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				getbtnReset().setCursor(Cursor.HAND);
				getbtnReset().setTooltip(new Tooltip("Rest the define details"));
			}

		});
		getbtnReset().setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setThPort("5555");
				setTfUserName("obl"); //HEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEre
				setPfPassword("17171919!");
				thPort.setDisable(false);
				tfUserName.setDisable(false);
				pfPassword.setDisable(false);
				getBtnConnect().setVisible(true);
			}
		});
	}

	@FXML
	void btnMySQLOnAction(ActionEvent event) {
		btnMySQL.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				getBtnConnect().setCursor(Cursor.HAND);
				getBtnConnect().setTooltip(new Tooltip("Connect to Database"));
			}

		});
		btnMySQL.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				DataBaseManagemer dataConnect = DataBaseManagemer.getDBManager();
				if (dataConnect.connection(getTfUserName(), getPfPassword()) != null) {
					btnMySQL.setVisible(false);
					warning.setVisible(false);
					warningMsg.setVisible(false);
					sQLoff.setVisible(false);
					tfUserName.setDisable(true);
					pfPassword.setDisable(true);
					setLablServerStatus(echoServer.getServerStatus());
					lablDBStatus.setText("DataBase Connected.");
				} else {
					Alert alert;
					String msg = "Wrong username or password !";
					alert = new Alert(AlertType.ERROR, msg, ButtonType.OK);
					alert.setTitle("Error");
					alert.show();
				}
			}
		});

	}

	public String getThPort() {
		return thPort.getText();
	}

	public void setThPort(String thPort) {
		this.thPort.setText(thPort);
	}

	public String getTfUserName() {
		return tfUserName.getText();
	}

	public void setTfUserName(String tfUserName) {
		this.tfUserName.setText(tfUserName);
	}

	public String getPfPassword() {
		return pfPassword.getText();
	}

	public void setPfPassword(String pfPassword) {
		this.pfPassword.setText(pfPassword);
	}

	public Button getBtnConnect() {
		return btnConnect;
	}

	public void setBtnConnect(Button btnConnect) {
		this.btnConnect = btnConnect;
	}

	public Button getbtnReset() {
		return resetLogo;
	}

	public void setbtnReset(Button btnReset) {
		this.resetLogo = btnReset;
	}

	public Button getBtnExit() {
		return btnExit;
	}

	public void setBtnExit(Button btnExit) {
		this.btnExit = btnExit;
	}

	public String getLablServerStatus() {
		return lablServerStatus.getText();
	}

	public void setLablServerStatus(String lablServerStatus) {
		this.lablServerStatus.setText(lablServerStatus);
	}

}
