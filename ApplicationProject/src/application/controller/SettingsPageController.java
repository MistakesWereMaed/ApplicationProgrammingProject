package application.controller;

import application.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SettingsPageController extends MasterController{

    @FXML
    private Button confirmChangesButton;

    @FXML
    private TextField confirmTextField;

    @FXML
    private Button deleteAccountButton;

    @FXML
    private Label errorTextLabel;
    
    @FXML
    private Label confirmationTextLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private Button menuButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField passwordTextField;

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
    	
    	nameTextField.setText(User.currentUser.getName());
    	usernameTextField.setText(User.currentUser.getUsername());
    	passwordTextField.setText(User.currentUser.getPassword());
    	confirmTextField.clear();
    	
    }
    /**
     * Sets the User's data to the new data provided
     * @param ActionEvent event the button being clicked
     */
    @FXML
    void onConfirmChangesButtonClicked(ActionEvent event) {
    	
    	if(blankFields(nameTextField, usernameTextField, passwordTextField, confirmTextField)) {
    		changeErrorMessage(errorTextLabel, 1, "Please fill out all fields");
    	} else if(!passwordTextField.getText().equals(confirmTextField.getText())) {
    		
    		changeErrorMessage(errorTextLabel, 1, "Passwords do not match");
    		
    	} else {
    		
    		User.currentUser.setName(nameTextField.getText());
        	User.currentUser.setUsername(usernameTextField.getText());
        	User.currentUser.setPassword(passwordTextField.getText());
        	//User.saveUsers();
        	initialize();
        	changeErrorMessage(confirmationTextLabel, 1, "Account changed successfully");
        	
    	}
    	
    }
    /**
     * Deletes the currentUser's data and data files
     * @param ActionEvent event the button being clicked
     */
    @FXML
    void onDeleteAccountButtonClicked(ActionEvent event) {
    	
    	errorTextLabel.setOpacity(0);
    	
    	if(User.removeUser(User.currentUser.getUserID())) {
    		
    		User.currentUser = null;
        	switchScene("view/LoginPage.fxml", "Vexing Manager");
    		
    	} else {
    		changeErrorMessage(errorTextLabel, 1, "Failed to delete user");
    	}
    }
    /**
     * Returns the User to the login page
     * @param ActionEvent event the button being clicked
     */
    @FXML
    void onLogoutButtonPressed(ActionEvent event) {

    	logout();
    	
    }
    /**
     * Returns the User to the home page
     * @param ActionEvent event the button being clicked
     */
    @FXML
    void onMenuButtonPressed(ActionEvent event) {

    	switchToHome();
    	
    }

}
