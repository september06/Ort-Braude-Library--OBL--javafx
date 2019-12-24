/**
 * Sample Skeleton for 'Profile Editor.fxml' Controller Class
 */

package AppStart;

import java.util.ArrayList;
import java.util.List;

import Methods.*;
import Protocols.Request;
import Protocols.RequestType;
import Protocols.Response;
import client.ChatClient;
import common.ChatIF;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ProfileEditorController extends AbstractController {
	private Librarian librarian;
	
	private Subscriber subscriber;
	private List<String> list = new ArrayList();

	
	
	
    @FXML // fx:id="ProfilePic"
    private ImageView ProfilePic; // Value injected by FXMLLoader

    @FXML // fx:id="SaveBtn"
    private Button SaveBtn; // Value injected by FXMLLoader

    @FXML // fx:id="GivenNameTxt"
    private TextField GivenNameTxt; // Value injected by FXMLLoader

    @FXML // fx:id="FamilyNameTxt"
    private TextField FamilyNameTxt; // Value injected by FXMLLoader

    @FXML // fx:id="IDTxt"
    private TextField IDTxt; // Value injected by FXMLLoader

    @FXML // fx:id="UsernameTxt"
    private TextField UsernameTxt; // Value injected by FXMLLoader

    @FXML // fx:id="TelephoneTxt"
    private TextField TelephoneTxt; // Value injected by FXMLLoader

    @FXML // fx:id="EmailTxt"
    private TextField EmailTxt; // Value injected by FXMLLoader

    @FXML // fx:id="ChangePhotoBtn"
    private Button ChangePhotoBtn; // Value injected by FXMLLoader

    @FXML // fx:id="PasswordTxt"
    private PasswordField PasswordTxt; // Value injected by FXMLLoader

    @FXML // fx:id="ConfirmPasswordTxt"
    private PasswordField ConfirmPasswordTxt; // Value injected by FXMLLoader

    @FXML // fx:id="HelpHyperlink"
    private Hyperlink HelpHyperlink; // Value injected by FXMLLoader

    @FXML
    void ChangePhotoBtnHandle(ActionEvent event) {

    }

    @FXML
    void HelpHyperlinkHandle(ActionEvent event) {
    	Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guies/help.fxml"));
		try {
			Pane root = loader.load();

			helpController helpcontroller = loader.getController();
			helpcontroller.setClient(client);
			client.AddClientUI(helpcontroller);

			Platform.setImplicitExit(false);

			Scene scene = new Scene(root);			
			primaryStage.setScene(scene);		
			primaryStage.show();
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace()+"\n"+e.getMessage()+"\n"+e.getLocalizedMessage());

		}
    }

    @FXML
	void SaveBtnHandle(ActionEvent event) {

		if ((GivenNameTxt.getText().trim().isEmpty()) || (PasswordTxt.getText().trim().isEmpty())
				|| (ConfirmPasswordTxt.getText().trim().isEmpty()) || (TelephoneTxt.getText().trim().isEmpty())
				|| (EmailTxt.getText().trim().isEmpty()) || (FamilyNameTxt.getText().trim().isEmpty())) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Missing fields");
			alert.setHeaderText("WARNING");
			alert.setContentText(" Please insert full Data ");
			alert.showAndWait();

		} else {
			if (!(PasswordTxt.getText().equals(ConfirmPasswordTxt.getText()))) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle(" Unmatched Password !!!!");
				alert.setHeaderText("WARNING");
				alert.setContentText(" Please insert full Data ");
				alert.showAndWait();
				return;
			} else {
				Subscriber subsub = new Subscriber(IDTxt.getText(), GivenNameTxt.getText(), FamilyNameTxt.getText(),
						UsernameTxt.getText(), PasswordTxt.getText(), TelephoneTxt.getText(), EmailTxt.getText(),
						subscriber.isOnline(), subscriber.isGraduated(), subscriber.getCardStatus());
				Request message = new Request(RequestType.UpdateSubscriber, subsub);
				Response res = getClient().handleMessageFromClientUI(message);
				if (!res.isSucceeded()) { // failed
					this.DisplayMessage(AlertType.ERROR, "update Failed", res.getMessage());
					return;
				} else
					this.DisplayMessage(AlertType.INFORMATION, "update success", res.getMessage());

			}
		}
	}
	private void DisplayMessage(AlertType type, String title, String msg) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(type.toString());
		alert.setContentText(msg);
		alert.showAndWait();
		return;
	}

	@Override
	public void DoClose(String message) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	void initialize() {
		Image img= new Image("/image/3.png");
		ProfilePic.setImage(img);
	}
	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
		 getUsernameTxt().setText(this.getSubscriber().getUserName());
		 getGivenNameTxt().setText(this.getSubscriber().getFirstName());
		 getFamilyNameTxt().setText(this.getSubscriber().getLastName());
		 getIDTxt().setText(this.getSubscriber().getID());
		 getTelephoneTxt().setText(this.getSubscriber().getPhone());
		 getEmailTxt().setText(this.getSubscriber().getEmail());
		 getPasswordTxt().setText(this.getSubscriber().getPassword());
		 getConfirmPasswordTxt().setText(this.getSubscriber().getPassword());
	}

	public void setLibrarian(Librarian librarian) {
		this.librarian = librarian;
	
		 getUsernameTxt().setText(this.getLibrarian().getUserName());
		 getGivenNameTxt().setText(this.getLibrarian().getFirstName());
		 getFamilyNameTxt().setText(this.getLibrarian().getLastName());
		 getIDTxt().setText(this.getLibrarian().getID());
		 getTelephoneTxt().setText(this.getLibrarian().getPhone());
		 getEmailTxt().setText(this.getLibrarian().getEmail());
		 getPasswordTxt().setText(this.getLibrarian().getPassword());
		 getConfirmPasswordTxt().setText(this.getLibrarian().getPassword());
	}

	public TextField getGivenNameTxt() {
		return GivenNameTxt;
	}

	public void setGivenNameTxt(TextField givenNameTxt) {
		GivenNameTxt = givenNameTxt;
	}

	public TextField getFamilyNameTxt() {
		return FamilyNameTxt;
	}

	public void setFamilyNameTxt(TextField familyNameTxt) {
		FamilyNameTxt = familyNameTxt;
	}

	public TextField getIDTxt() {
		return IDTxt;
	}

	public void setIDTxt(TextField iDTxt) {
		IDTxt = iDTxt;
	}

	public TextField getUsernameTxt() {
		return UsernameTxt;
	}

	public void setUsernameTxt(TextField usernameTxt) {
		UsernameTxt = usernameTxt;
	}

	public TextField getTelephoneTxt() {
		return TelephoneTxt;
	}

	public void setTelephoneTxt(TextField telephoneTxt) {
		TelephoneTxt = telephoneTxt;
	}

	public TextField getEmailTxt() {
		return EmailTxt;
	}

	public void setEmailTxt(TextField emailTxt) {
		EmailTxt = emailTxt;
	}

	public PasswordField getPasswordTxt() {
		return PasswordTxt;
	}

	public void setPasswordTxt(PasswordField passwordTxt) {
		PasswordTxt = passwordTxt;
	}

	public PasswordField getConfirmPasswordTxt() {
		return ConfirmPasswordTxt;
	}

	public void setConfirmPasswordTxt(PasswordField confirmPasswordTxt) {
		ConfirmPasswordTxt = confirmPasswordTxt;
	}

	public Librarian getLibrarian() {
		return librarian;
	}

	public Subscriber getSubscriber() {
		return subscriber;
	}
	
}
