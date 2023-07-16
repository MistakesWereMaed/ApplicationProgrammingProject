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
	 * The PasswordData that is constructed with an application name, username, and password
	 * @param application associated with this data
	 * @param username associated with this data
	 * @param password associated with this data
	 */
	public PasswordData(int userID, String application, String username, String password) {
		
		this.userID = userID;
		this.application = application;
		this.username = username;
		this.password = password;
		
	}
	
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
		
		return String.format(application + "," + username + "," + password + "\n");
		
	}
	/**
	 * Formats the PasswordData object (without its password property) into a String
	 * @return the formatted data (String)
	 */
	public String toString() {
		
		return String.format("Application: " + application + ", Username: " + username);
		
	}
	
	public boolean equals(Object o) {
		
		PasswordData newO = (PasswordData) o;
		if(newO != null && this.application.equals(newO.application) && this.password.equals(newO.password)) {
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
	 * @param String application name of the PasswordData
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
	 * @param String username of the PasswordData
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
	 * @param String password of the PasswordData
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public int getPasswordID() {
		return passwordID;
	}

	public void setPasswordID(int passwordID) {
		this.passwordID = passwordID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	
}
