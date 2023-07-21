package application.controller;

import application.model.PasswordData;
import application.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * AddPasswordController is the class controlling the AddPassword fxml file
 * @author Benjamin Samuel (MistakesWereMaed)
 * @author Amos Munteanu (amosm2002)
 */
public class AddPasswordController extends MasterController {

    @FXML
    private Button addPasswordButton;

    @FXML
    private TextField applicationTextField;

    @FXML
    private PasswordField confirmTextField;

    @FXML
    private Label errorTextLabel;
    
    @FXML
    private Label confirmationTextLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private Button menuButton;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Label welcomeTF;

    /**
     * Sets up the Scene's labels and text fields
     */
    public void initialize() {
    	
    	changeErrorMessage(confirmationTextLabel, 0, "");
    	changeErrorMessage(errorTextLabel, 0, "");
    	clearFields(applicationTextField, usernameTextField, passwordTextField, confirmTextField);
    	
    }
    
    /**
     * Changes a new PasswordData to the currentUser's data if able, otherwise shows an error message
     * @param event (ActionEvent) - the button being clicked
     */
    @FXML
    void onAddPasswordButtonClicked(ActionEvent event) {

    	changeErrorMessage(errorTextLabel, 0, "");
    	changeErrorMessage(confirmationTextLabel, 0, "");

    	PasswordData data = new PasswordData(
    			User.currentUser.getID(), applicationTextField.getText(), usernameTextField.getText(), passwordTextField.getText()
    		);

    	if(blankFields(applicationTextField, usernameTextField, passwordTextField, confirmTextField)) {
    		changeErrorMessage(errorTextLabel, 1, "Please fill out all fields");
    	} else if(!passwordTextField.getText().equals(confirmTextField.getText())) {
    		changeErrorMessage(errorTextLabel, 1, "Passwords do not match");
    		return;
    	} else if(User.currentUser.addPassword(data)) {

    		clearFields(applicationTextField, usernameTextField, passwordTextField, confirmTextField);
    		changeErrorMessage(confirmationTextLabel, 1, "Password added successfully");
    		
    	} else {
    		changeErrorMessage(errorTextLabel, 1, "Username already exists");
    	}
    	
    }
    
    /**
     * Returns the User to the login page
     * @param event (ActionEvent) - the button being clicked
     */
    @FXML
    void onLogoutButtonPressed(ActionEvent event) {
    	logout();
    }
    
    /**
     * Returns the User to the home page
     * @param event (ActionEvent) - the button being clicked
     */
    @FXML
    void onMenuButtonPressed(ActionEvent event) {
    	switchToHome();
    }

}
