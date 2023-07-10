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
	/**
	 * Switches the current scene to the new scene
	 * @param String filePath to the new fxml file
	 * @param String title of the new page
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
	 * @param String username of the User
	 * @param String password of the User
	 * @param Label errorTextLabel to display the error message
	 */
	protected void login(String username, String password, Label errorTextLabel) {
    	
    	if(User.login(username, password)) {
    		
    		switchScene("view/HomePage.fxml", "Vexing Manager");
    		
    	} else {
    		
    		errorTextLabel.setText("Invalid username or password");
    		errorTextLabel.setOpacity(1);
    		
    	}
    	
    }
	/**
	 * Switches to the LoginPage fxml file
	 */
	protected void logout() {
		
		User.currentUser = null;
    	switchScene("view/LoginPage.fxml", "Vexing Manager");
		
	}
	/**
	 * Switches to the HomePage fxml file
	 */
	protected void switchToHome() {
		
    	switchScene("view/HomePage.fxml", "Vexing Manager");
		
	}
	/**
	 * Change the text and opactity of the given label
	 * @param Label errorLabel to be changes
	 * @param int opacity of the label
	 * @param String errorMessage to be shown
	 */
	protected void changeErrorMessage(Label errorLabel, int opacity, String errorMessage) {
    	
		errorLabel.setText(errorMessage);
		errorLabel.setOpacity(opacity);
    	
    }
	/**
	 * Checks if any of the given fields are blank
	 * @param TextField field1 to be checked
	 * @param TextField field2 to be checked
	 * @param TextField field3 to be checked
	 * @param TextField field4 to be checked
	 * @return whether any fields of blank (boolean)
	 */
	protected boolean blankFields(TextField field1, TextField field2, TextField field3, TextField field4) {
		
		return field1.getText() == "" && field2.getText() == "" && field3.getText() == "" && field4.getText() == "";
		
	}
	/**
	 * Clears the given TextFields
	 * @param TextField field1 to be cleared
	 * @param TextField field2 to be cleared
	 * @param TextField field3 to be cleared
	 * @param TextField field4 to be cleared
	 */
	protected void clearFields(TextField field1, TextField field2, TextField field3, TextField field4) {
    	
		field1.clear();
		field2.clear();
		field3.clear();
		field4.clear();
    	
    }
	/**
	 * Clears the given TextFields
	 * @param TextField field1 to be cleared
	 * @param TextField field2 to be cleared
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