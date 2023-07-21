package application.model;

import java.util.ArrayList;

/**
 * User is a class containing all instance and static variables and methods for the User data object
 * @author Benjamin Samuel (MistakesWereMaed)
 */
public class User {

	private int id;
	private String name;
	private String username;
	private String password;
	
	private ArrayList<PasswordData> passwords = new ArrayList<PasswordData>();
	private PasswordData selectedPassword;

	public static User currentUser;
	
	/**
	 * The User object that is constructed with a name, username, and password
	 * @param name (String) - name of the User
	 * @param username (String) - username of the User's account
	 * @param password (String) - password of the User's account
	 */
	public User(String name, String username, String password) 
	{
		this.name = name;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * The User object that is constructed with an ID, name, username, and password
	 * @param id (int) - ID of the User
	 * @param name (String) - name of the User
	 * @param username (String) - username of the User's account
	 * @param password (String) - password of the User's account
	 */
	public User(int id, String name, String username, String password) 
	{
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Creates a new User object from the given args and adds it to the Database
	 * @param name (String) - name of the new User
	 * @param username (String) - username of the new User
	 * @param password (String) - password of the new User
	 * @return Success of adding the User (boolean)
	 */
	public static boolean addUser(String name, String username, String password) {

		User user = new User(name, username, password);
		user.id = DatabaseManager.insertUser(user);
		
		return user.id != -1 ? true : false;
		
	}
	
	/**
	 * Removes the User from the Database and deletes all Passwords associated with the account
	 * @param username (String) - username of the user
	 * @return success of removing the User (boolean)
	 */
	public static boolean removeUser(User user) {
		return DatabaseManager.removeUser(user.id);
	}
	
	/**
	 * Queries the Database to retrieve the User matching the given credentials, sets currentUser to the result
	 * @param username (String) - username of the User
	 * @param password (String) - password of the User
	 * @return success of logging the User in (boolean)
	 */
	public static boolean login(String username, String password) {
		
		currentUser = DatabaseManager.getUser(username, password);
		if(currentUser != null) {
			
			currentUser.loadPasswords();
			return true;
			
		}
		return false;
		
	}
	
	/**
	 * Logs the current User out
	 */
	public static void logout() {
		currentUser = null;
	}
	
	/**
	 * Retrieves the User's passwords from the Database and User.passwords to the result
	 * @return success of loading the passwords (boolean)
	 */
	public boolean loadPasswords() {
		
		if(this.passwords.size() == 0)
			this.passwords = DatabaseManager.getPasswords(this.id);
		return this.passwords.size() > 0 ? true : false;
		
	}
	
	/**
	 * Adds the passed PasswordData to the User's PasswordData list
	 * @param data (PasswordData) - data to be added to the list
	 * @return success of adding the password (boolean)
	 */
	public boolean addPassword(PasswordData data) {
		
		if(passwords.contains(data)) {
			return false;
		} else {
			
			data.setPasswordID(DatabaseManager.insertPassword(data));
			passwords.add(data);
			return data.getPasswordID() != -1 ? true : false;
			
		}
		
	}
	
 	/**
 	 * Updates the Database entry at the password ID to be the new data given
 	 * @param data (PasswordData) - new PasswordData to store
 	 * @return success of changing the password (boolean)
 	 */
	public boolean changePassword(PasswordData data) {
		return DatabaseManager.updatePassword(data);
	}
	
	/**
	 * Removes the given PasswordData from the Database and PasswordData list
	 * @param data (PasswordData) - data to be removed
	 * @return success of removing the password (boolean)
	 */
	public boolean removePassword(PasswordData data) {
		return DatabaseManager.removePassword(data.getPasswordID()) && passwords.remove(data);
	}
	
	/**
	 * Formats the User data into a String
	 * @return the formatted data (String)
	 */
	public String toString() {
		return String.format(name + "," + username + "," + password + "\n");
	}

	/**
	 * Returns the User's username
	 * @return username of the User (String)
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * Sets the User's username
	 * @param username (String) - username to be set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * Returns the User's password
	 * @return password of the User (String)
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Sets the User's password
	 * @param password (String) - password to be set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * Returns the User's name
	 * @return name of the User (String)
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the User's name
	 * @param name (String) - name to be set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Returns the User's PasswordData list
	 * @return list of the User's passwords (ArrayList)
	 */
	public ArrayList<PasswordData> getPasswords() {
		return passwords;
	}
	/**
	 * Sets the User's PasswordData list
	 * @param passwords (ArrayList) - passwords to be set
	 */
	public void setPasswords(ArrayList<PasswordData> passwords) {
		this.passwords = passwords;
	}
	/**
	 * Returns the User's selected password
	 * @return user's currently selected password (PasswordData)
	 */
	public PasswordData getSelectedPassword() {
		return selectedPassword;
	}
	/**
	 * Sets the User's selected password
	 * @param selectedPassword (PasswordData) - password to be selected
	 */
	public void setSelectedPassword(PasswordData selectedPassword) {
		this.selectedPassword = selectedPassword;
	}
	/**
	 * Returns the User's ID
	 * @return ID of the User (int)
	 */
	public int getID() {
		return id;
	}
	/**
	 * Sets the User's ID
	 * @param id (int) - id of the User
	 */
	public void setID(int id) {
		this.id = id;
	}
	
}
