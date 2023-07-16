package application;
	
import application.model.DatabaseManager;
import application.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;


public class Main extends Application {
	public static Stage stage;
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//User.initialize();
			
			stage = primaryStage;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/LoginPage.fxml"));
			HBox layout = (HBox) loader.load();
			Scene scene = new Scene(layout);
			stage.setScene(scene);
			stage.setTitle("Vexing Manager");
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		//TODO: Remove this after testing is complete
		//DatabaseManager.test();
		launch(args);
		
	}
}
