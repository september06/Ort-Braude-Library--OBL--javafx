package application;
	
import java.io.IOException;

import echoServer.EchoServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Server.fxml"));
			Parent root=(Parent) loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("OBL Server");
			Image anotherIcon = new Image("/image/oblLogo.jpg");
			primaryStage.getIcons().add(anotherIcon);
			//primaryStage.setResizable(false) ;
			primaryStage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
	            if (newValue)
	                primaryStage.setMaximized(false);
	        });
			
			primaryStage.setOnCloseRequest((WindowEvent event1) -> {
				try {
					System.out.println("Closing...");
					if(EchoServer.getServer() != null)
						EchoServer.getServer().close();
					System.exit(0);
				} catch (IOException ex) {
					System.out.println("Server Controller: failed to close server.\n" + ex.getMessage());
				}
		    });
			
			primaryStage.show();

		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
