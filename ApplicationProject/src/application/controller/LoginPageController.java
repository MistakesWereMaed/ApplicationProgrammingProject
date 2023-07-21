package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


/**
 * LoginPageController is the class controlling the LoginPage fxml file
 * @author Benjamin Samuel (MistakesWereMaed)
 * @author Amos Munteanu (amosm2002)
 */
public class LoginPageController extends MasterController{

    @FXML
    private Label errorTextLabel;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button signupButton;

    @FXML
    private TextField usernameTextField;

    private String SIGNUP_VIEW = "view/SignupPage.fxml";
    
    /**
     * Sets up the Scene's labels and text fields
     */
    public void initialize() {
    	
    	errorTextLabel.setOpacity(0);
    	usernameTextField.clear();
    	passwordTextField.clear();
    	
    }
    
    /**
     * Switches to the HomePage fxml file if able, otherwise shows an error message
     * @param event (ActionEvent) - the button being clicked
     */
    @FXML
    void onLoginButtonClicked(ActionEvent event) {
    	
    	errorTextLabel.setOpacity(0);
    	
    	String username = usernameTextField.getText();
    	String password = passwordTextField.getText();
    	
    	if(username != "" && password != "") {
    		login(username, password, errorTextLabel);
    	} else {
    		errorTextLabel.setText("Please fill out all given fields");
    		errorTextLabel.setOpacity(1);
    	}
    	
    }
    
    /**
     * Switches to the SignupPage fxml file
     * @param event (ActionEvent) - the button being clicked
     */
    @FXML
    void onSignupButtonClicked(ActionEvent event) {
    	switchScene(SIGNUP_VIEW, HOME_TITLE);
    }

}
