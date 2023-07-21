package application.model;

/**
 * PasswordData is a class containing all instance variables and methods for the PasswordData data object
 * @author Benjamin Samuel (MistakesWereMaed)
 */
public class PasswordData{

	private int userID;
	private int passwordID;
	private String application;
	private String username;
	private String password;
	
	/**
	 * The PasswordData that is constructed with a userID, application name, username, and password
	 * @param userID (int) - user id associated with this data
	 * @param application (String) - application associated with this data
	 * @param username (String) - username associated with this data
	 * @param password (String) - password associated with this data
	 */
	public PasswordData(int userID, String application, String username, String password) {
		
		this.userID = userID;
		this.application = application;
		this.username = username;
		this.password = password;
		
	}
	
	/**
	 * The PasswordData that is constructed with a userID, passwordID, application name, username, and password
	 * @param userID (int) - user id associated with this data
	 * @param passwordID (int) - password id associated with this data
	 * @param application (String) - application associated with this data
	 * @param username (String) - username associated with this data
	 * @param password (String) - password associated with this data
	 */
	public PasswordData(int userID, int passwordID, String application, String username, String password) {
		
		this.userID = userID;
		this.passwordID = passwordID;
		this.application = application;
		this.username = username;
		this.password = password;
		
	}
	
	/**
	 * Formats the PasswordData object (with its password property) into a String
	 * @return the formatted data (String)
	 */
	public String toString_WithPassword() {
		return String.format("%s,%s,%s\n", application, username, password);
	}
	/**
	 * Formats the PasswordData object (without its password property) into a String
	 * @return the formatted data (String)
	 */
	public String toString() {
		return String.format("Application: %s, Username: %s\n", application, username);
	}
	
	public boolean equals(Object object) {
		
		PasswordData newObject = (PasswordData) object;
		if(newObject != null && this.application.equals(newObject.application) && this.password.equals(newObject.password)) {
			return true;
		}
		return false;
		
	}
	
	/**
	 * Returns the application name of the PasswordData
	 * @return application name of the PasswordData (String)
	 */
	public String getApplication() {
		return application;
	}
	/**
	 * Sets the application name of the PasswordData
	 * @param application (String) - application name of the PasswordData
	 */
	public void setApplication(String application) {
		this.application = application;
	}
	/**
	 * Returns the username of the PasswordData
	 * @return username of the PasswordData (String)
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * Sets the username of the PasswordData
	 * @param username (String) - username of the PasswordData
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * Returns the password of the PasswordData
	 * @return password of the PasswordData (String)
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Sets the password of the PasswordData
	 * @param password (String) - password of the PasswordData
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * Gets the passwordID of the PasswordData
	 * @return passwordID of the PasswordData (int)
	 */
	public int getPasswordID() {
		return passwordID;
	}
	/**
	 * Sets the password id of the PasswordData
	 * @param passwordID (int) - password id of the PasswordData
	 */
	public void setPasswordID(int passwordID) {
		this.passwordID = passwordID;
	}
	/**
	 * Gets the userID of the PasswordData
	 * @return user id of the PasswordData (int)
	 */
	public int getUserID() {
		return userID;
	}
	/**
	 * Sets the userID of the PasswordData
	 * @param userID (int) - user id of the PasswordData
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
}
