package application.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * DatabaseManager is a class containing all methods needed to interact with the chosen Database
 * @author Benjamin Samuel (MistakesWereMaed)
 */
public class DatabaseManager {

	//Local MySQL credentials, these can be updated to any remote host 
	private static final String URL = "jdbc:mysql://localhost:3306/ApplicationProjectDB";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "ApplicationProject";
	
	private static Connection con = null;
	private static Statement st = null;
	
	/**
	 * Tests all database functions
	 */
	public static void test() {
		
		User testUser;
		User testUserUpdated;
		PasswordData testPassword;
		PasswordData testPasswordUpdated;
		
		testUser = new User("name", "username", "password");
		testUser.setID(insertUser(testUser));
		getUser("username", "password");
		testUserUpdated = new User(testUser.getID(), "NAME", "USERNAME", "PASSWORD");
		updateUser(testUserUpdated);
		getUser("USERNAME", "PASSWORD");
		
		testPassword = new PasswordData(testUserUpdated.getID(), "application", "username", "password");
		testPassword.setPasswordID(insertPassword(testPassword));
		getPasswords(testUserUpdated.getID());
		testPasswordUpdated = new PasswordData(testUserUpdated.getID(), testPassword.getPasswordID(), "APPLICATION", "USERNAME", "PASSWORD");
		updatePassword(testPasswordUpdated);
		getPasswords(testUserUpdated.getID());
		
		removePassword(testPasswordUpdated.getPasswordID());
		removeUser(testUserUpdated.getID());
	}
	
	/**
	 * Connects to the given database url. Creates a connection / statement and saves both in global vars
	 * @param url (String) - url of the desired database
	 * @param username (String) - username for the desired database
	 * @param password (String) - password for the desired database
	 * @return success state of the connection (boolean)
	 */
	public static boolean connect() {
		
		String success = "Database connection established";
		String fail = "Database connection failed: ";
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			st = con.createStatement();
			
			System.out.println(success);
			
        }
        catch (Exception e) {
        	
        	System.err.println(fail + e.getMessage());
        	return false;
        	
        }
		return true;
		
	}

	/**
	 * Closes the database connection and statement
	 * @return success state of closing the connection (boolean)
	 */
	public static boolean close() {
		
		String success = "Database connection closed";
		String fail = "Failed to close connection: ";
		
		try {
			
			if(con != null) {
				con.close();
				System.out.println(success);
			}
			st.close();
			
		} catch (SQLException e) {
			
			System.err.println(fail + e.getMessage());
			return false;
		}
		return true;
		
	}
	
	/**
	 * Queries the database to get the user data associated with the given username and password
	 * @param username (String) - username of the user
	 * @param password (String) - password of the user
	 * @return object containing all retrieved user data (User)
	 */
	public static User getUser(String uname, String pass) {
		
		String queryStr = String.format(
				"SELECT * FROM USER "
				+ "WHERE USER_UNAME = '%s' "
				+ "AND USER_PASSWORD = '%s'", uname, pass);
		
		String action = "Retrieving user...";
		String success = "Retrieved user ";
		String fail = "Failed to retrieve user: ";
		
		User user = null;
		
		connect();
		System.out.println(action);
		
		try {
			
			ResultSet rs = st.executeQuery(queryStr);
			rs.next();
			user = new User(
					rs.getInt("USER_ID"),
					rs.getString("USER_NAME"),
					rs.getString("USER_UNAME"),
					rs.getString("USER_PASSWORD"));
			
			System.out.println(success + user.getID());
			
		} catch (SQLException e) {
			System.err.println(fail + e.getMessage());
		}
		
		close();
		return user;
		
	}
	
	/**
	 * Queries the database to get the user data associated with the given ID
	 * @param userID (int) - id of the user
	 * @return object containing all retrieved user data (User)
	 */
	public static User getUser(int userID) {
		
		String queryStr = String.format(
				"SELECT * FROM USER "
				+ "WHERE USER_ID = '%d'", userID);
		
		String action = "Retrieving user...";
		String success = "Retrieved user ";
		String fail = "Failed to retrieve user: ";
		
		User user = null;
		
		connect();
		System.out.println(action);
		
		try {
			
			ResultSet rs = st.executeQuery(queryStr);
			rs.next();
			user = new User(
					rs.getInt("USER_ID"),
					rs.getString("USER_NAME"),
					rs.getString("USER_UNAME"),
					rs.getString("USER_PASSWORD"));
			
			System.out.println(success + user.getID());
			
		} catch (SQLException e) {
			System.err.println(fail + e.getMessage());
		}
		
		close();
		return user;
		
	}
	
	/**
	 * Queries the database to insert the given user data
	 * @param user (User) - object containing all data to be inserted
	 * @return the success state of the insert query (boolean)
	 */
	public static int insertUser(User user) {
		
		String insertQueryStr = String.format(
				"INSERT INTO USER "
				+ "VALUES (null, '%s', '%s', '%s')"
				, user.getName(), user.getUsername(), user.getPassword());
		String selectQueryStr = "SELECT LAST_INSERT_ID()";
		
		String action = "Inserting user...";
		String success = "Inserted user ";
		String fail = "Failed to insert user: ";
		int userID = -1;
		
		connect();
		System.out.println(action);
		
		try {
			
			st.executeUpdate(insertQueryStr);
			ResultSet rs = st.executeQuery(selectQueryStr);
			rs.next();
			userID = rs.getInt(1);
			System.out.println(success + userID);
			
		} catch (SQLException e) {
			System.err.println(fail + e.getMessage());	
		}
		
		close();
		return userID;
		
	}
	
	/**
	 * Queries the database to update the specified user with the given data
	 * @param user (User) - new data to set the given user to
	 * @return the success state of the update query (boolean)
	 */
	public static boolean updateUser(User user) {
		
		String queryStr = String.format(
				"UPDATE USER "
				+ "SET USER_NAME = '%s', "
				+ "USER_UNAME = '%s', "
				+ "USER_PASSWORD = '%s'"
				+ "WHERE USER_ID = '%d'", 
				user.getName(), user.getUsername(), user.getPassword(), user.getID());
		
		String action = "Updating user...";
		String success = "Updated user " + user.getID();
		String fail = "Failed to update user: ";
		
		return genericQuery(queryStr, action, success, fail);
		
	}
	
	/**
	 * Queries the database to delete the user with the given userID
	 * @param userID (int) - id of the user to be deleted
	 * @return the success state of the remove query (boolean)
	 */
	public static boolean removeUser(int userID) {
		
		String queryStr = String.format(
				"DELETE FROM USER "
				+ "WHERE USER_ID = '%d'", userID);
		
		String action = "Removing user...";
		String success = "Removed user " + userID;
		String fail = "Failed to remove user: ";
		
		return genericQuery(queryStr, action, success, fail);
		
	}
		
	/**
	 * Queries the database to get all of the passwords associated with the given userID
	 * @param userID (int) - id of the user who's passwords are being retrieved
	 * @return the list of all of the user's passwords (ArrayList)
	 */
	public static ArrayList<PasswordData> getPasswords(int userID) {
		
		String queryStr = String.format(
				"SELECT * FROM PASSWORD "
				+ "WHERE USER_ID = '%d'", userID);
		
		String action = "Retrieving passwords...";
		String success = "Retrieved password ";
		String fail = "Failed to retrieve passwords: ";
		ArrayList<PasswordData> passwords = new ArrayList<PasswordData>();
		
		connect();
		System.out.println(action);
		
		try {
			
			ResultSet rs = st.executeQuery(queryStr);
			while(rs.next()) {
				
				PasswordData password = new PasswordData(
						rs.getInt("USER_ID"),
						rs.getInt("PWD_ID"),
						rs.getString("PWD_APP"),
						rs.getString("PWD_UNAME"),
						rs.getString("PWD_PASSWORD"));
				
				passwords.add(password);
				System.out.println(success + password.getPasswordID());
				
			}
			
		} catch (SQLException e) {
			System.err.println(fail + e.getMessage());
		}
		
		close();
		return passwords;
		
	}
	
	/**
	 * Queries the database to insert the given password
	 * @param password (PasswordData) - object containing all data being inserted into the Password table
	 * @return the success state of the insert query (boolean)
	 */
	public static int insertPassword(PasswordData password) {
		
		String insertQueryStr = String.format(
				"INSERT INTO PASSWORD "
				+ "VALUES ('%d', null, '%s', '%s', '%s')", 
				password.getUserID(), password.getApplication(), password.getUsername(), password.getPassword());
		String selectQueryStr = "SELECT LAST_INSERT_ID()";
		
		String action = "Inserting password...";
		String success = "Inserted password ";
		String fail = "Failed to insert password: ";
		int passwordID = -1;
		
		connect();
		System.out.println(action);
		
		try {
			
			st.executeUpdate(insertQueryStr);
			ResultSet rs = st.executeQuery(selectQueryStr);
			rs.next();
			passwordID = rs.getInt(1);
			System.out.println(success + passwordID);
			
		} catch (SQLException e) {
			System.err.println(fail + e.getMessage());	
		}
		
		close();
		return passwordID;
		
	}
	
	/**
	 * Queries the database to update the given password
	 * @param password (PasswordData) - object containing the new data that will be stored in the database
	 * @return the success state of the query (boolean)
	 */
	public static boolean updatePassword(PasswordData password) {
		
		String queryStr = String.format(
				"UPDATE PASSWORD "
				+ "SET PWD_APP = '%s', "
				+ "PWD_UNAME = '%s', "
				+ "PWD_PASSWORD = '%s' "
				+ "WHERE PWD_ID = '%d'",
				password.getApplication(), password.getUsername(), password.getPassword(), password.getPasswordID());
		
		String action = "Updating password...";
		String success = "Updated password " + password.getPasswordID();
		String fail = "Failed to update password: ";
		
		return genericQuery(queryStr, action, success, fail);
		
	}
	
	/**
	 * Queries the database to delete the given password
	 * @param passwordID (int) - id of the password being deleted
	 * @return the success state of the query (boolean)
	 */
	public static boolean removePassword(int passwordID) {
		
		String queryStr = String.format(
				"DELETE FROM PASSWORD "
				+ "WHERE PWD_ID = '%d'", 
				passwordID);
		
		String action = "Removing password...";
		String success = "Removed password " + passwordID;
		String fail = "Failed to remove password: ";
		
		return genericQuery(queryStr, action, success, fail);
		
	}
	
	/**
	 * Helper function to execute queries that do not return ResultSets
	 * @param queryStr (String) - query to be executed
	 * @return the success state of the query (boolean)
	 */
	private static boolean genericQuery(String queryStr, String action, String success, String fail) {
		
		connect();
		System.out.println(action);
		
		try {
			
			st.executeUpdate(queryStr);
			System.out.println(success);
			
		} catch (SQLException e) {
			
			System.err.println(fail + e.getMessage());
			return false;
					
		}
		
		close();
		return true;
		
	}
}
