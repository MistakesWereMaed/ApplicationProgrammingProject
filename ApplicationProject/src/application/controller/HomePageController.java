package application.controller;

import application.model.PasswordData;
import application.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;


/**
 * HomePageController is the class controlling the HomePage fxml file
 * @author Benjamin Samuel (MistakesWereMaed)
 * @author Amos Munteanu (amosm2002)
 */
public class HomePageController extends MasterController{

    @FXML
    private Button addPasswordButton;

    @FXML
    private Button changePasswordButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Label errorTextLabel;

    @FXML
    private Button getPasswordButton;

    @FXML
    private ListView<PasswordData> listView;

    @FXML
    private Button logoutButton;

    @FXML
    private Button removePasswordButton;

    @FXML
    private Label welcomeLabel;

    
    private String ADD_VIEW = "view/AddPassword.fxml";
    private String CHANGE_VIEW = "view/ChangePassword.fxml";
    private String SETTINGS_VIEW = "view/SettingsPage.fxml";
    private String HANGMAN_VIEW = "view/HangmanPage.fxml";
    
    private final ObservableList<PasswordData> passwords = FXCollections.observableArrayList();
    
    private PasswordData selectedData;
    
    
    /**
     * Sets up the Scene's labels and text fields
     */
    public void initialize() {
    	
    	welcomeLabel.setText("Welcome, " + User.currentUser.getName() + "!");
    	changeErrorMessage(errorTextLabel, 0, "");
    	
    	listView.setItems(passwords);
    	listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    	passwords.addAll(User.currentUser.getPasswords());
    	
    }
    
    /**
     * Switches to the AddPassword fxml file
     * @param event (ActionEvent) - the button being clicked
     */
    @FXML
    void onAddPasswordButtonClicked(ActionEvent event) {
    	
    	switchScene(ADD_VIEW, HOME_TITLE);
    	
    }
    
    /**
     * Switches to the ChangePassword fxml file if able, otherwise shows an error message
     * @param event (ActionEvent) - the button being clicked
     */
    @FXML
    void onChangePasswordButtonClicked(ActionEvent event) {
    	
    	selectedData = listView.getSelectionModel().getSelectedItem();
    	
    	if(selectedData != null) {
    		
    		User.currentUser.setSelectedPassword(selectedData);
    		switchScene(CHANGE_VIEW, HOME_TITLE);
    		
    	} else {
    		changeErrorMessage(errorTextLabel, 1, "Click on an entry");
    	}
    	
    }
    
    /**
     * Deletes the currentUser's data and data files
     * @param event (ActionEvent) - the button being clicked
     */
    @FXML
    void onSettingsButtonClicked(ActionEvent event) {
    	switchScene(SETTINGS_VIEW, HOME_TITLE);
    }
    
    /**
     * Switches to the Hangman fxml file if able, otherwise shows an error message
     * @param event (ActionEvent) - the button being clicked
     */
    @FXML
    void onGetPasswordButtonClicked(ActionEvent event) {
    	
    	selectedData = listView.getSelectionModel().getSelectedItem();
    	
    	if(selectedData != null) {
    		
    		User.currentUser.setSelectedPassword(selectedData);
    		switchScene(HANGMAN_VIEW, HOME_TITLE);
    		
    	} else {
    		changeErrorMessage(errorTextLabel, 1, "Click on an entry to get its password");
    	}
    	
    }
    
    /**
     * Returns the User to the home page
     * @param event (ActionEvent) - the button being clicked
     */
    @FXML
    void onLogoutButtonClicked(ActionEvent event) {
    	logout();
    }
    
    /**
     * Removes the selected PasswordData if able, otherwise shows an error message
     * @param event (ActionEvent) - the button being clicked
     */
    @FXML
    void onRemovePasswordButtonClicked(ActionEvent event) {
    	
    	changeErrorMessage(errorTextLabel, 0, "");
    	
    	selectedData = listView.getSelectionModel().getSelectedItem();
    	
    	if(selectedData == null || !User.currentUser.removePassword(selectedData)) {
    		changeErrorMessage(errorTextLabel, 1, "Failed to remove password");
    	} else {
    		
        	refreshListView();
    		
    	}
    	
    }
    
    
    /**
     * Refreshes the list view to show current changes
     */
    private void refreshListView() {
    	
    	passwords.clear();
    	passwords.addAll(User.currentUser.getPasswords());
    	
    }

}
