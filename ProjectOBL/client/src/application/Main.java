package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root=FXMLLoader.load(getClass().getResource("/Guies/Connect.fxml"));
			//BorderPane root = new BorderPane();
			Scene scene = new Scene(root);
		//	scene.getStylesheets().add(getClass().getResource("Connect.fxml").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("OBL Client");
			Image anotherIcon = new Image("/image/oblLogo.jpg");
			primaryStage.getIcons().add(anotherIcon);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
