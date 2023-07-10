package application.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;


/**
 * User is a class containing all instance and static variables and methods for the User data object
 * @author Benjamin Samuel (MistakesWereMaed)
 */
public class User {

	private static String LOGIN_FILE_PATH = "./src/application/data/login.csv";
	
	private String username;
	private String password;
	private String name;
	private ArrayList<PasswordData> passwords = new ArrayList<PasswordData>();
	private PasswordData selectedPassword;
	private int selectedPasswordIndex;

	public static HashMap<String, User> users = new HashMap<String, User>();
	public static User currentUser;
	
	/**
	 * The User object that is constructed with a name, username, and password
	 * @param String name of the User
	 * @param String username of the User's account
	 * @param String password of the User's account
	 */
	public User(String name, String username, String password) 
	{
		this.name = name;
		this.username = username;
		this.password = password;
	}
	/**
	 * Initializes the HashMap of Users with data from thier data files
	 */
	public static void initialize() {
		
		if(loadUsers(LOGIN_FILE_PATH)) {
			
			for(User tempUser : users.values()) {
				tempUser.loadPasswords("./src/application/data/" + tempUser.getUsername() + ".csv");
			}
			
		}
		
	}
	/**
	 * Adds all users from the data file to the HashMap of Users
	 * @param String filePath the path to the data file
	 * @return whether the data was loaded successfully or not (boolean)
	 */
	public static boolean loadUsers(String filePath) {
		
		try {
			
			if(new File(filePath).createNewFile()) {
				return true;
			}
			
			File inputFile;
			Scanner scanner;
			String line;
			String[] tokens;
			
			inputFile = new File(filePath);
			scanner = new Scanner(inputFile);
			
			while(scanner.hasNext()) {
				
				line = EncryptorDecryptor.decryptLine(scanner.nextLine());
				tokens = line.split(",");
				
				users.put(tokens[1].trim(), new User(
						tokens[0].trim(), tokens[1].trim(), tokens[2].trim()
					));
				
			}
			
			scanner.close();
			return true;
			
		} catch(IOException e) {
			
			System.err.println(e.getMessage());
			return false;
			
		}
		
	}
	/**
	 * Creates a new User object from the given args and adds it to the HashMap of Users
	 * @param String name of the new User
	 * @param String username of the new User
	 * @param String password of the new User
	 * @return whether the User was created and added successfully or not (boolean)
	 */
	public static boolean addUser(String name, String username, String password) {
		
		if(users.containsKey(username)) {
			return false;
		} else {
			
			users.put(username, new User(name, username, password));
			saveUsers();
			return true;
		}
		
	}
	/**
	 * Removes the User from the HashMap of Users and deletes the associated data file
	 * @param String username
	 * @return whether the User was deleted successfully or not (boolean)
	 */
	public static boolean removeUser(String username) {
		
		if(users.containsKey(username)) {
			
			File userData = new File("./src/application/data/" + users.get(username).getUsername() + ".csv");
			
			if(userData.exists()) {
				userData.delete();
			}
			
			users.remove(username);
			saveUsers();
			return true;
			
		} else {
			return false;
		}
		
	}
	/**
	 * Checks if the User's credentials are correct and sets them as the currentUser
	 * @param String username of the User
	 * @param String password of the User
	 * @return whether the User was logged in successfully or not (boolean)
	 */
	public static boolean login(String username, String password) {
		
		if(validateUser(username, password)) {
			
			currentUser = users.get(username);
			return true;
			
		} else {
			return false;
		}
		
	}
	/**
	 * Writes the HashMap of Users to a data file, and writes each User's PasswordData list to thier own data files
	 * @return whether the data was written successfully or not (boolean)
	 */
	public static boolean saveUsers() {
		
		try {
			
			FileOutputStream outputStream = new FileOutputStream(new File(LOGIN_FILE_PATH));
			for(User user : users.values()) {
				outputStream.write(EncryptorDecryptor.encryptLine(user.toString()));
				user.savePasswords("./src/application/data/" + user.getName() + ".csv");
			}
			outputStream.close();
			return true;
			
		} catch(IOException e) {
			System.err.println(e.getMessage());
			return false;
		}
		
	}
	/**
	 * Validates the credentials of the User
	 * @param String username of the User
	 * @param String password of the User
	 * @return whether the User was validated successfully or not (boolean)
	 */
 	public static boolean validateUser(String username, String password) {
		
		if(users.containsKey(username) && users.get(username).getPassword().equals(password)) {
			return true;
		} else {
			return false;
		}
		
	}
 		
 	
 	/**
 	 * Creates a new PasswordData object and adds it to the User's PasswordData list
 	 * @param String application that is associated with this data
 	 * @param String username that is associated with this data
 	 * @param String password that is associated with this data
 	 * @return whether the PasswordData was added successfully or not (boolean)
 	 */
	public boolean addPassword(String application, String username, String password) {
		
		PasswordData tempData = new PasswordData(application, username, password);
		
		if(passwords.contains(tempData)) {
			return false;
		} else {
			
			passwords.add(tempData);
			return true;
			
		}
		
	}
	/**
	 * Adds the passed PasswordData to the User's PasswordData list
	 * @param PasswordData data to be added to the list
	 * @return whether the PasswordData was added successfully or not (boolean)
	 */
	public boolean addPassword(PasswordData data) {
		
		if(passwords.contains(data)) {
			return false;
		} else {
			
			passwords.add(data);
			return true;
			
		}
		
		
	}
 	/**
 	 * Changes the password property of the PasswordData at the given index of the PasswordData list
 	 * @param int indexOfData to be changed
 	 * @param String newPassword that will be set in the PasswordData
 	 * @return whether the PasswordData was changed successfully or not (boolean)
 	 */
	public boolean changePassword(int indexOfData, String newPassword) {
		
		if(passwords.get(indexOfData) != null) {
			
			passwords.get(indexOfData).setPassword(newPassword);
			return true;
			
		} else {
			return false;
		}
		
	}
	/**
	 * Removes the given PasswordData from the PasswordData list
	 * @param Password data to be removed
	 * @return whether the PasswordData was removed successfully or not (boolean)
	 */
	public boolean removePassword(PasswordData data) {
		
		return passwords.remove(data);
		
	}
	/**
	 * Reads the given data file into the PasswordData list
	 * @param String filePath of the dataFile
	 * @return whether the PasswordData list was loaded successfully or not (boolean)
	 */
	public boolean loadPasswords(String filePath) {
		
		try {
			
			if(new File(filePath).createNewFile()) {
				return true;
			}
			
			File inputFile;
			Scanner scanner;
			String line;
			String[] tokens;
			
			inputFile = new File(filePath);
			scanner = new Scanner(inputFile);
			
			while(scanner.hasNext()) {
				
				line = EncryptorDecryptor.decryptLine(scanner.nextLine());
				tokens = line.split(",");

				passwords.add(
						new PasswordData(tokens[0].trim(), tokens[1].trim(), tokens[2].trim())
					);
				
			}
			
			scanner.close();
			return true;
			
		} catch(IOException e) {
			
			System.err.println(e.getMessage());
			return false;
			
		}
		
	}
	/**
	 * Writes the PasswordData list to the given data file
	 * @param String filePath of the data file
	 * @return whether the PasswordData list was written successfully or not (boolean)
	 */
	public boolean savePasswords(String filePath) {
		
		try {
			
			FileOutputStream outputStream = new FileOutputStream(new File(filePath));
			
			Iterator<PasswordData> it = passwords.iterator();
			while(it.hasNext()) {
				outputStream.write(EncryptorDecryptor.encryptLine(it.next().toString_WithPassword()));
			}
			
			outputStream.close();
			return true;
			
		} catch(IOException e) {
			System.err.println(e.getMessage());
			return false;
		}
		
	}
	
	
	/**
	 * Formats the User data into a String
	 * @return String the formatted data
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
	 * @param String username to be set
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
	 * @param String password to be set
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
	 * @param String name to be set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Returns the User's PasswordData list
	 * @return PasswordData list of the User (ArrayList<PasswordData>)
	 */
	public ArrayList<PasswordData> getPasswords() {
		return passwords;
	}
	/**
	 * Sets the User's PasswordData list
	 * @param ArrayList<PasswordData> passwords to be set
	 */
	public void setPasswords(ArrayList<PasswordData> passwords) {
		this.passwords = passwords;
	}
	/**
	 * Returns the User's selected password
	 * @return selected password of the User (PasswordData)
	 */
	public PasswordData getSelectedPassword() {
		return selectedPassword;
	}
	/**
	 * Sets the User's selected password
	 * @param PasswordData selected password to be set
	 */
	public void setSelectedPassword(PasswordData selectedPassword) {
		this.selectedPassword = selectedPassword;
	}
	/**
	 * Returns the User's selected password index in the PasswordData list
	 * @return selected password index of the User (int)
	 */
	public int getSelectedPasswordIndex() {
		return selectedPasswordIndex;
	}
	/**
	 * Sets the User's selected password index in the PasswordData list
	 * @param int selected password index to be set
	 */
	public void setSelectedPasswordIndex(int selectedPasswordIndex) {
		this.selectedPasswordIndex = selectedPasswordIndex;
	}
	
}
