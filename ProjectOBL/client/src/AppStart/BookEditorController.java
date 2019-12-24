
/**
 * Sample Skeleton for 'Change Permission.fxml' Controller Class
 */

package AppStart;


import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import common.ChatIF;


public class BookEditorController implements ChatIF {

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

	    @FXML // fx:id="BookNameTxt"
	    private TextField BookNameTxt; // Value injected by FXMLLoader

	    @FXML // fx:id="AuthorTxt"
	    private TextField AuthorTxt; // Value injected by FXMLLoader

	    @FXML // fx:id="PrintDateTxt"
	    private DatePicker PrintDateTxt1; // Value injected by FXMLLoader
	    @FXML // fx:id="PrintDateTxt"
	    private DatePicker PrintDateTxt2; 
	    @FXML // fx:id="CategoryTxt"
	    private TextField CategoryTxt; // Value injected by FXMLLoader

	    @FXML // fx:id="EditionTxt"
	    private TextField EditionTxt; // Value injected by FXMLLoader

	    @FXML
	    private Hyperlink helpHyperlink;

	    @FXML // fx:id="ShelfTxt"
	    private TextField ShelfTxt; // Value injected by FXMLLoader

	    @FXML // fx:id="CatalogueNumberTxt"
	    private TextField CatalogueNumberTxt; // Value injected by FXMLLoader

	    @FXML // fx:id="NumberOfCopiesTxt"
	    private TextField NumberOfCopiesTxt; // Value injected by FXMLLoader

	    @FXML // fx:id="SelectFileBtn"
	    private Button SelectFileBtn; // Value injected by FXMLLoader

	    @FXML // fx:id="DescribtionTxt"
	    private TextArea DescribtionTxt; // Value injected by FXMLLoader

	    @FXML // fx:id="GeneralStringHyperlink"
	    private Hyperlink GeneralStringHyperlink; // Value injected by FXMLLoader

	    @FXML // fx:id="NaturalNumberHyperlink"
	    private Hyperlink NaturalNumberHyperlink; // Value injected by FXMLLoader

	    @FXML // fx:id="DateHyperlink"
	    private Hyperlink DateHyperlink; // Value injected by FXMLLoader

	    @FXML // fx:id="CatalougeFormatHyperlink"
	    private Hyperlink CatalougeFormatHyperlink; // Value injected by FXMLLoader

	    @FXML // fx:id="ShelfFormatHyperlink"
	    private Hyperlink ShelfFormatHyperlink; // Value injected by FXMLLoader

	    @FXML // fx:id="PdfFileHyperlink"
	    private Hyperlink PdfFileHyperlink; // Value injected by FXMLLoader

	    @FXML // fx:id="BookImageView"
	    private ImageView BookImageView; // Value injected by FXMLLoader

	    @FXML // fx:id="SelectImageBtn"
	    private Button SelectImageBtn; // Value injected by FXMLLoader

	    @FXML // fx:id="EditBtn"
	    private Button EditBtn; // Value injected by FXMLLoader

	    @FXML // fx:id="SerialsTxt"
	    private TextField SerialsTxt; // Value injected by FXMLLoader

	    @FXML // fx:id="GenerateNewSerialsBtn"
	    private Button GenerateNewSerialsBtn; // Value injected by FXMLLoader

	    @FXML // fx:id="NewSerialsHyperlink"
	    private Hyperlink NewSerialsHyperlink; // Value injected by FXMLLoader

	    @FXML // fx:id="MarkAsDeletedBtn"
	    private Button MarkAsDeletedBtn; // Value injected by FXMLLoader

	    @FXML
	    void helpHyperlinkHandler(ActionEvent event) {
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
	    void CatalougeFormatHyperlinkHandle(ActionEvent event) {

	    }

	    @FXML
	    void DateHyperlinkHandle(ActionEvent event) {

	    }

	    @FXML
	    void EditBtnHandle(ActionEvent event) {

	    }

	    @FXML
	    void GeneralStringHyperlinkHandle(ActionEvent event) {

	    }

	    @FXML
	    void GenerateNewSerialsBtnHandle(ActionEvent event) {

	    }

	    @FXML
	    void MarkAsDeletedBtnHandle(ActionEvent event) {

	    }

	    @FXML
	    void NaturalNumberHyperlinkHandle(ActionEvent event) {

	    }

	    @FXML
	    void NewSerialsHyperlinkHandle(ActionEvent event) {

	    }

	    @FXML
	    void PdfFileHyperlinkHandle(ActionEvent event) {

	    }

	    @FXML
	    void SelectFileBtnHandle(ActionEvent event) {

	    }

	    @FXML
	    void SelectImageBtnHandle(ActionEvent event) {

	    }

	    @FXML
	    void ShelfFormatHyperlinkHandle(ActionEvent event) {

	    }

	    @FXML // This method is called by the FXMLLoader when initialization is complete
	    void initialize() {
	        assert BookNameTxt != null : "fx:id=\"BookNameTxt\" was not injected: check your FXML file 'BookEditor.fxml'.";
	        assert AuthorTxt != null : "fx:id=\"AuthorTxt\" was not injected: check your FXML file 'BookEditor.fxml'.";
	        assert PrintDateTxt1 != null : "fx:id=\"PrintDateTxt\" was not injected: check your FXML file 'BookEditor.fxml'.";
	        assert PrintDateTxt2 != null : "fx:id=\"PrintDateTxt\" was not injected: check your FXML file 'BookEditor.fxml'.";
	        assert CategoryTxt != null : "fx:id=\"CategoryTxt\" was not injected: check your FXML file 'BookEditor.fxml'.";
	        assert EditionTxt != null : "fx:id=\"EditionTxt\" was not injected: check your FXML file 'BookEditor.fxml'.";
	        assert ShelfTxt != null : "fx:id=\"ShelfTxt\" was not injected: check your FXML file 'BookEditor.fxml'.";
	        assert CatalogueNumberTxt != null : "fx:id=\"CatalogueNumberTxt\" was not injected: check your FXML file 'BookEditor.fxml'.";
	        assert NumberOfCopiesTxt != null : "fx:id=\"NumberOfCopiesTxt\" was not injected: check your FXML file 'BookEditor.fxml'.";
	        assert SelectFileBtn != null : "fx:id=\"SelectFileBtn\" was not injected: check your FXML file 'BookEditor.fxml'.";
	        assert DescribtionTxt != null : "fx:id=\"DescribtionTxt\" was not injected: check your FXML file 'BookEditor.fxml'.";
	        assert GeneralStringHyperlink != null : "fx:id=\"GeneralStringHyperlink\" was not injected: check your FXML file 'BookEditor.fxml'.";
	        assert NaturalNumberHyperlink != null : "fx:id=\"NaturalNumberHyperlink\" was not injected: check your FXML file 'BookEditor.fxml'.";
	        assert DateHyperlink != null : "fx:id=\"DateHyperlink\" was not injected: check your FXML file 'BookEditor.fxml'.";
	        assert CatalougeFormatHyperlink != null : "fx:id=\"CatalougeFormatHyperlink\" was not injected: check your FXML file 'BookEditor.fxml'.";
	        assert ShelfFormatHyperlink != null : "fx:id=\"ShelfFormatHyperlink\" was not injected: check your FXML file 'BookEditor.fxml'.";
	        assert PdfFileHyperlink != null : "fx:id=\"PdfFileHyperlink\" was not injected: check your FXML file 'BookEditor.fxml'.";
	        assert BookImageView != null : "fx:id=\"BookImageView\" was not injected: check your FXML file 'BookEditor.fxml'.";
	        assert SelectImageBtn != null : "fx:id=\"SelectImageBtn\" was not injected: check your FXML file 'BookEditor.fxml'.";
	        assert EditBtn != null : "fx:id=\"EditBtn\" was not injected: check your FXML file 'BookEditor.fxml'.";
	        assert SerialsTxt != null : "fx:id=\"SerialsTxt\" was not injected: check your FXML file 'BookEditor.fxml'.";
	        assert GenerateNewSerialsBtn != null : "fx:id=\"GenerateNewSerialsBtn\" was not injected: check your FXML file 'BookEditor.fxml'.";
	        assert NewSerialsHyperlink != null : "fx:id=\"NewSerialsHyperlink\" was not injected: check your FXML file 'BookEditor.fxml'.";
	        assert MarkAsDeletedBtn != null : "fx:id=\"MarkAsDeletedBtn\" was not injected: check your FXML file 'BookEditor.fxml'.";

	    }

	@Override
	public void DoClose(String message) {
	}
}
