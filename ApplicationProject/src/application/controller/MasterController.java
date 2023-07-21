package application.controller;

import application.Main;
import application.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * MasterController is a class containing several helper methods used by other controller classes
 * @author Benjamin Samuel (MistakesWereMaed)
 */
public class MasterController {
	
	protected final String LOGIN_VIEW = "view/LoginPage.fxml";
	protected final String HOME_VIEW = "view/HomePage.fxml";
	protected final String HOME_TITLE = "Vexing Manager";
	
	/**
	 * Switches the current scene to the new scene
	 * @param filePath (String) - file path to the new fxml file
	 * @param title of the new page (String)
	 */
	protected static void switchScene(String filePath, String title) {
		
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(filePath));
			HBox layout = (HBox) loader.load();
			Scene scene = new Scene(layout);
			Main.stage.setScene(scene);
			Main.stage.setTitle(title);
			Main.stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Sets the currentUser to the given User data and switches to the HomePage fxml file if able, otherwise shows and error message
	 * @param username (String) - username of the User
	 * @param password (String) - password of the User
	 * @param errorTextLabel (Label) - error label to display the error message
	 */
	protected void login(String username, String password, Label errorTextLabel) {
    	
    	if(User.login(username, password)) {
    		
    		switchScene(HOME_VIEW, HOME_TITLE);
    		
    	} else {
    		
    		errorTextLabel.setText("Invalid username or password");
    		errorTextLabel.setOpacity(1);
    		
    	}
    	
    }
	
	/**
	 * Logs the current user out and switches to the LoginPage fxml file
	 */
	protected void logout() {
		
		User.logout();
    	switchScene(LOGIN_VIEW, HOME_TITLE);
		
	}
	
	/**
	 * Switches to the HomePage fxml file
	 */
	protected void switchToHome() {
    	switchScene(HOME_VIEW, HOME_TITLE);
	}
	
	/**
	 * Change the text and opactity of the given label
	 * @param errorLabel (Label) - label to be changed
	 * @param opacity (int) - opacity of the label
	 * @param errorMessage (String) - message to be shown
	 */
	protected void changeErrorMessage(Label errorLabel, int opacity, String errorMessage) {
    	
		errorLabel.setText(errorMessage);
		errorLabel.setOpacity(opacity);
    	
    }
	
	/**
	 * Checks if any of the given fields are blank
	 * @param field1 (TextField) - 1st field to be checked
	 * @param field2 (TextField) - 2nd field to be checked
	 * @param field3 (TextField) - 3rd field to be checked
	 * @param field4 (TextField) - 4th field to be checked
	 * @return whether any fields are empty (boolean)
	 */
	protected boolean blankFields(TextField field1, TextField field2, TextField field3, TextField field4) {
		return field1.getText() == "" && field2.getText() == "" && field3.getText() == "" && field4.getText() == "";
	}
	
	/**
	 * Clears the given TextFields
	 * @param field1 (TextField) - 1st field to be cleared
	 * @param field2 (TextField) - 2nd field to be cleared
	 * @param field3 (TextField) - 3rd field to be cleared
	 * @param field4 (TextField) - 4th field to be cleared
	 */
	protected void clearFields(TextField field1, TextField field2, TextField field3, TextField field4) {
    	
		field1.clear();
		field2.clear();
		field3.clear();
		field4.clear();
    	
    }
	
	/**
	 * Clears the given TextFields
	 * @param field1 (TextField) - 1st field to be cleared
	 * @param field2 (TextField) - 2nd field to be cleared
	 */
	protected void clearFields(TextField field1, TextField field2) {
    	
		field1.clear();
		field2.clear();
    	
    }
	
	/**
	 * Blank superclass method that all children will inherit and implement
	 */
	public void initialize() {}
	
	
}