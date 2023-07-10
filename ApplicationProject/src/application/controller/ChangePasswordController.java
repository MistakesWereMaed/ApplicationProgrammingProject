package application.controller;

import application.model.PasswordData;
import application.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


/**
 * ChangePasswordController is the class controlling the ChangePassword fxml file
 * @author Benjamin Samuel (MistakesWereMaed)
 * @author Amos Munteanu (amosm2002)
 */
public class ChangePasswordController extends MasterController{

    @FXML
    private TextField applicationTextField;

    @FXML
    private Button changePasswordButton;

    @FXML
    private TextField confirmTextField;

    @FXML
    private Label errorTextLabel;
    
    @FXML
    private Label confirmationTextLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private Button menuButton;

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
    	
    	changeErrorMessage(errorTextLabel, 0, "");
    	changeErrorMessage(confirmationTextLabel, 0, "");
    	clearFields(passwordTextField, confirmTextField);
    	
    	applicationTextField.setDisable(true);
    	usernameTextField.setDisable(true);
    	
    	applicationTextField.setText(User.currentUser.getSelectedPassword().getApplication());
    	usernameTextField.setText(User.currentUser.getSelectedPassword().getUsername());
    	
    }
    
    /**
     * Changes the currentUser's password if able, otherwise shows an error message
     * @param ActionEvent event the button being clicked
     */
    @FXML
    void onChangePasswordButtonPressed(ActionEvent event) {

    	changeErrorMessage(errorTextLabel, 0, "");
    	
    	PasswordData data = new PasswordData(
    			applicationTextField.getText(), usernameTextField.getText(), passwordTextField.getText()
    		);
    	
    	if(blankFields(applicationTextField, usernameTextField, passwordTextField, confirmTextField)) {
    		changeErrorMessage(errorTextLabel, 1, "Please fill out all fields");
    	} else if(!passwordTextField.getText().equals(confirmTextField.getText())) {
    		changeErrorMessage(errorTextLabel, 1, "Passwords do not match");
    	} else if(User.currentUser.changePassword(User.currentUser.getSelectedPasswordIndex(), data.getPassword())) {
    		
    		changeErrorMessage(confirmationTextLabel, 1, "Password changed successfully");
    		User.saveUsers();
    		
    	} else {
    		changeErrorMessage(errorTextLabel, 1, "Failed to change password");
    	}
    	
    }
    /**
     * Returns the User to the login page
     * @param ActionEvent event the button being clicked
     */
    @FXML
    void onLogoutButtonClicked(ActionEvent event) {
    	
    	logout();
    	
    }
    /**
     * Returns the User to the home page
     * @param ActionEvent event the button being clicked
     */
    @FXML
    void onMenuButtonClicked(ActionEvent event) {

    	switchToHome();
    	
    }

}
