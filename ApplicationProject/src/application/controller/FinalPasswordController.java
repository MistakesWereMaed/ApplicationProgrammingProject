package application.controller;

import application.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


/**
 * FinalPasswordController is the class controlling the FinalPasswordView fxml file
 * @author Benjamin Samuel (MistakesWereMaed)
 * @author Amos Munteanu (amosm2002)
 */
public class FinalPasswordController extends MasterController{

    @FXML
    private Button logoutButton;

    @FXML
    private Button menuButton;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label welcomeTF;

    @FXML
    private Label welcomeTF1;

    /**
     * Sets up the Scene's labels and text fields
     */
    public void initialize() {
    	passwordLabel.setText(User.currentUser.getSelectedPassword().getPassword());
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
