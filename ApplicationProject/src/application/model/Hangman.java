package application.model;

/**
 * Hangman is a class containing all instance variables and methods for the Hangman data object
 * @author Benjamin Samuel (MistakesWereMaed)
 */
public class Hangman {
	
	private String word;
	private int guessesLeft;
	
	private String word_redacted;
	private String tempWord;
	/**
	 * The Hangman that is constructed with a word and amount of guesses
	 * @param word
	 * @param guessesLeft
	 */
	public Hangman(String word, int guessesLeft) {
		
		this.word = word;
		this.guessesLeft = guessesLeft;
		
	}
	/**
	 * Initializes the Hangman object's word_redacted and tempWord variabled based on the word it was constructed with
	 */
	public void initialize() {
		
		redactWord();
		tempWord = word;
		
	}
	/**
	 * Redacts the word the Hangman was constructed with
	 */
	public void redactWord() {
		
		word_redacted = word.replaceAll("[a-zA-Z]", "_");
		
	}
	/**
	 * Checks if a given guess String contains letters in the Hangman word
	 * @param String guess to be checked
	 */
	public void checkGuess(String guess) {
		
		char[] guessArr = guess.toCharArray();
		char[] wordRedactedArr = word_redacted.toCharArray();
		char[] tempWordArr;
		
		int guessCharIndex;
		
		for(int i = 0; i < guess.length(); i++) {
			
			for(int j = 0; j < tempWord.length(); j++) {
				
				guessCharIndex = tempWord.indexOf(guessArr[i]);
				tempWordArr = tempWord.toCharArray();
				
				if(guessCharIndex != -1) {
					wordRedactedArr[guessCharIndex] = guessArr[i];
					tempWordArr[guessCharIndex] = '*';
				} else if(word.contains(Character.toString(guessArr[i]))){
					break;
				} else {
					guessesLeft--;
					break;
				}
				tempWord = new String(tempWordArr);
				
			}

		}
		word_redacted = new String(wordRedactedArr);
		
	}
	/**
	 * Returns the word of the Hangman
	 * @return word of the Hangman (String)
	 */
	public String getWord() {
		return word;
	}
	/**
	 * Sets the word of the Hangman
	 * @param String word of the Hangman
	 */
	public void setWord(String word) {
		this.word = word;
	}
	/**
	 * Returns the redacted word of the Hangman
	 * @return redacted word of the Hangman (String)
	 */
	public String getWord_Redacted() {
		return word_redacted;
	}
	/**
	 * Sets the redacted word of the Hangman
	 * @param String redacted word of the Hangman
	 */
	public void setWord_Redacted(String word_redacted) {
		this.word_redacted = word_redacted;
	}
	/**
	 * Returns the guesses left of the Hangman
	 * @return guesses left of the Hangman (int)
	 */
	public int getGuessesLeft() {
		return guessesLeft;
	}
	/**
	 * Sets the guesses left of the Hangman
	 * @param int guesses left of the Hangman
	 */
	public void setGuessesLeft(int guessesLeft) {
		this.guessesLeft = guessesLeft;
	}
	
}
