package application.controller;

import java.io.File;

import application.model.Hangman;
import application.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * HangmanController is the class controlling the HangmanPage fxml file
 * @author Benjamin Samuel (MistakesWereMaed)
 * @author Amos Munteanu (amosm2002)
 */
public class HangmanPageController extends MasterController{

    @FXML
    private Button checkGuessButton;

    @FXML
    private Label errorTextLabel;

    @FXML
    private TextField guessTextField;

    @FXML
    private TextArea guessesArea;

    @FXML
    private ImageView hangmanImageView;

    @FXML
    private Label letterGuessed;

    @FXML
    private Button logoutButton;

    @FXML
    private Button menuButton;

    @FXML
    private Button resetButton;

    @FXML
    private Label resultLabel;

    @FXML
    private Label textForWord;

    @FXML
    private Label welcomeTF;
    
    
    private String FINAL_VIEW = "view/FinalPassword.fxml";
    
    private int HANGMAN_GUESSES = 7;
    
    private enum GameState {IN_PROGESS, WON, LOST};
    private GameState gameState;
    
    private Hangman hangman;
    
    
    /**
     * Sets up the Scene's labels and text fields
     */
    public void initialize() {
    	
    	hangman = new Hangman(User.currentUser.getSelectedPassword().getPassword(), HANGMAN_GUESSES);
    	hangman.initialize();
    	gameState = GameState.IN_PROGESS;
    	
    	textForWord.setText(hangman.getWord_Redacted());
    	
    	hangmanImageView.setImage(new Image(new File("./src/application/images/hangman1.png").toURI().toString()));
    	guessTextField.clear();
    	guessesArea.clear();
    	changeErrorMessage(errorTextLabel, 0, "");
    	changeErrorMessage(resultLabel, 0, "");
    	
    }
    /**
     * Updates the state of the current Hangman game
     */
    public void update() {
    	
    	textForWord.setText(hangman.getWord_Redacted());
    	hangmanImageView.setImage(new Image(new File(
    			"./src/application/images/hangman" + ((HANGMAN_GUESSES + 1) - hangman.getGuessesLeft())+ ".png"
    			).toURI().toString()));
    	
    	gameState = checkGameState();
    	
    	switch(gameState) {
    	
    		case WON:
    			win();
    			break;
    		case LOST:
    			lose();
    			break;
    		case IN_PROGESS:
    			break;
    	
    	}
    	
    }  
    
 
    /**
     * Checks if the current guess is in the Hangman word
     * @param event (ActionEvent) - the button being clicked
     */
    @FXML
    void onCheckButtonClicked(ActionEvent event) {

    	errorTextLabel.setOpacity(0);
    	String guess = guessTextField.getText();
    	
    	if(guess != "" && !guessesArea.getText().contains(guess)) {
    		
    		hangman.checkGuess(guess);
    		update();
    		guessesArea.appendText(guess + " ");
    		
    	} else {
    		
    		errorTextLabel.setText("Please enter a valid guess");
    		errorTextLabel.setOpacity(1);
    		
    	}
    	guessTextField.clear();
    	
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
    
    /**
     * Resets the current Hangman game
     * @param event (ActionEvent) - the button being clicked
     */
    @FXML
    void onResetButtonPressed(ActionEvent event) {
    	initialize();
    }

    
    /**
     * Checks the current state of the Hangman game
     * @return state of the Hangman game (GameState)
     */
    private GameState checkGameState() {
    	
    	if(hangman.getGuessesLeft() == 0) {
    		return GameState.LOST;
    	} else if(hangman.getWord_Redacted().toString().equals(hangman.getWord())) {
    		return GameState.WON;
    	} else {
    		return GameState.IN_PROGESS;
    	}
    }
    /**
     * Switches views to the FinalPassword fxml file
     */
    private void win() {
    	switchScene(FINAL_VIEW, HOME_TITLE);
    }
    /**
     * Shows a defeat message
     */
    private void lose() {
    	changeErrorMessage(resultLabel, 1, "You lost, better luck next time!");
    }
    
}
