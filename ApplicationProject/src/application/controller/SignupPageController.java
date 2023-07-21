package application.controller;

import application.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


/**
 * LoginPageController is the class controlling the SignupPage fxml file
 * @author Benjamin Samuel (MistakesWereMaed)
 * @author Amos Munteanu (amosm2002)
 */
public class SignupPageController extends MasterController {

    @FXML
    private PasswordField confirmTextField;

    @FXML
    private Label errorTextLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button signupButton;

    @FXML
    private TextField usernameTextField;
    
    /**
     * Inserts the new User into the Database and switches to the HomePage fxml file if able, otherwise shows an error message
     * @param event (ActionEvent) - the button being clicked
     */
    @FXML
    void onSignupButtonClicked(ActionEvent event){

    	changeErrorMessage(errorTextLabel, 0, "");
    	
    	String name = nameTextField.getText();
    	String username = usernameTextField.getText();
    	String password = passwordTextField.getText();
    	
    	if(blankFields(nameTextField, usernameTextField, passwordTextField, confirmTextField)) {
    		changeErrorMessage(errorTextLabel, 1, "Please fill out all fields");
    	} else if(!passwordTextField.getText().equals(confirmTextField.getText())) {
    		changeErrorMessage(errorTextLabel, 1, "Passwords do not match");
    	} else if(User.addUser(name, username, password)) {
    		login(username, password, errorTextLabel);
    	}
    	
    }
    /**
     * Sets up the Scene's labels and text fields
     */
    public void initialize() {
    	
    	errorTextLabel.setOpacity(0);
    	clearFields(nameTextField, usernameTextField, passwordTextField, confirmTextField);
    	
    }
}
