package application.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseManager {

	//Local MySQL credentials, these can be updated to any remote host 
	private static final String URL = "jdbc:mysql://localhost:3306/ApplicationProjectDB";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "ApplicationProject";
	
	private static Connection con = null;
	private static Statement st = null;
	
	//TODO: update all functions to work with an ID (not implemented yet)
	
	/**
	 * Tests all database functionality and prints the output of each function call
	 */
	public static void test() {
		
		User testUser;
		User testUserUpdated;
		PasswordData testPassword;
		PasswordData testPasswordUpdated;
		
		testUser = new User("name", "username", "password");
		testUser.setUserID(insertUser(testUser));
		getUser("username", "password");
		testUserUpdated = new User(testUser.getUserID(), "NAME", "USERNAME", "PASSWORD");
		updateUser(testUserUpdated);
		getUser("USERNAME", "PASSWORD");
		
		testPassword = new PasswordData(testUserUpdated.getUserID(), "application", "username", "password");
		testPassword.setPasswordID(insertPassword(testPassword));
		getPasswords(testUserUpdated.getUserID());
		testPasswordUpdated = new PasswordData(testUserUpdated.getUserID(), testPassword.getPasswordID(), "APPLICATION", "USERNAME", "PASSWORD");
		updatePassword(testPasswordUpdated);
		getPasswords(testUserUpdated.getUserID());
		
		removePassword(testPasswordUpdated.getPasswordID());
		removeUser(testUserUpdated.getUserID());
	}
	
	/**
	 * Connects to the given database url. Creates a connection / statement and saves both in global vars
	 * @param url String url of the desired database
	 * @param user String username for the desired database
	 * @param pass String password for the desired database
	 * @return Boolean the success state of the connection
	 */
	public static boolean connect() {
		
		String success = "Database connection established";
		String fail = "Database connection failed: ";
		
		try {
			//Register driver and get connection
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
	 * @return Boolean the success state of closing the connection
	 */
	public static boolean close() {
		
		String success = "Database connection closed";
		String fail = "Failed to close connection: ";
		
		try {
			//Close connection if was previously opened
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
	 * @param uname String username of the user
	 * @param pass Password of the user
	 * @return User object containing all retrieved user data
	 */
	public static User getUser(String uname, String pass) {
		//SQL query string to get user data matching the args
		String queryStr = String.format(
				"SELECT * FROM USER "
				+ "WHERE USER_UNAME = '%s' "
				+ "AND USER_PASSWORD = '%s'", uname, pass);
		
		String action = "Retrieving user...";
		String success = "Retrieved user: ";
		String fail = "Failed to retrieve user: ";
		
		User user = null;
		
		connect();
		System.out.println(action);
		
		try {
			
			//Get the result of the query
			ResultSet rs = st.executeQuery(queryStr);
			rs.next();
			//Create a User object to package the query results in
			user = new User(
					rs.getInt("USER_ID"),
					rs.getString("USER_NAME"),
					rs.getString("USER_UNAME"),
					rs.getString("USER_PASSWORD"));
			
			System.out.print(success + user);
			
		} catch (SQLException e) {
			System.err.println(fail + e.getMessage());
		}
		
		close();
		
		return user;
		
	}
	
	/**
	 * Queries the database to insert the given user data
	 * @param user User object containing all data to be inserted
	 * @return Boolean the success state of the insert query
	 */
	public static int insertUser(User user) {
		//SQL query string to insert the user data in the USER table
		String insertQueryStr = String.format(
				"INSERT INTO USER "
				+ "VALUES (null, '%s', '%s', '%s')"
				, user.getName(), user.getUsername(), user.getPassword());
		String selectQueryStr = "SELECT LAST_INSERT_ID()";
		
		String action = "Inserting user...";
		String success = "User inserted successfully";
		String fail = "Failed to insert user: ";
		
		int userID = -1;
		
		connect();
		System.out.println(action);
		
		try {
			
			st.executeUpdate(insertQueryStr);
			ResultSet rs = st.executeQuery(selectQueryStr);
			rs.next();
			userID = rs.getInt(1);
			System.out.println(success);
			
		} catch (SQLException e) {
			System.err.println(fail + e.getMessage());	
		}
		
		close();
		return userID;
		
	}
	
	/**
	 * Queries the database to update the specified user with the given data
	 * @param uname String username of the user to be updated
	 * @param updatedData User new data to set the given user to
	 * @return Boolean the success state of the update query
	 */
	public static boolean updateUser(User user) {
		
		//SQL query string to update the specified data in the USER table
		String queryStr = String.format(
				"UPDATE USER "
				+ "SET USER_NAME = '%s', "
				+ "USER_UNAME = '%s', "
				+ "USER_PASSWORD = '%s'"
				+ "WHERE USER_ID = '%d'", 
				user.getName(), user.getUsername(), user.getPassword(), user.getUserID());
		
		String action = "Updating user...";
		String success = "User update successfully";
		String fail = "Failed to update user: ";
		
		return genericQuery(queryStr, action, success, fail);
		
	}
	
	/**
	 * Queries the database to delete the given user
	 * @param uname String username of the user to be deleted
	 * @return Boolean the success state of the remove query
	 */
	public static boolean removeUser(int userID) {
		
		//SQL query string to delete the given user from the USER table
		String queryStr = String.format(
				"DELETE FROM USER "
				+ "WHERE USER_ID = '%d'", userID);
		
		String action = "Removing user...";
		String success = "User removed successfully";
		String fail = "Failed to remove user: ";
		
		return genericQuery(queryStr, action, success, fail);
		
	}
		
	/**
	 * Queries the database to get all of the passwords associated with the given username
	 * @param uname String username of the user who's passwords are being retrieved
	 * @return ArrayList<PasswordData> the list of all of the given user's passwords
	 */
	public static ArrayList<PasswordData> getPasswords(int userID) {
		
		//SQL query string to get the password data from the given user
		String queryStr = String.format(
				"SELECT * FROM PASSWORD "
				+ "WHERE USER_ID = '%d'", userID);
		
		String action = "Retrieving passwords...";
		String success = "Retrieved password: ";
		String fail = "Failed to retrieve passwords: ";
		
		ArrayList<PasswordData> passwords = new ArrayList<PasswordData>();
		
		connect();
		System.out.println(action);
		
		try {
			
			//Get the result of the query
			ResultSet rs = st.executeQuery(queryStr);
			//Create a PasswordData object for each row of the result set to package the query results in
			while(rs.next()) {
				
				PasswordData password = new PasswordData(
						rs.getInt("USER_ID"),
						rs.getInt("PWD_ID"),
						rs.getString("PWD_APP"),
						rs.getString("PWD_UNAME"),
						rs.getString("PWD_PASSWORD"));
				
				passwords.add(password);
				
				System.out.print(success + password.toString_WithPassword());
				
			}
			
		} catch (SQLException e) {
			System.err.println(fail + e.getMessage());
		}
		
		close();
		
		return passwords;
		
	}
	
	/**
	 * Queries the database to insert the given password with the username as its foreign key
	 * @param uname String username of the user adding inserting the password
	 * @param password PasswordData containing all data being inserted into the Password table
	 * @return Boolean the success state of the insert query
	 */
	public static int insertPassword(PasswordData password) {
		
		//SQL query string to insert the password data into the PASSWORD table
		String insertQueryStr = String.format(
				"INSERT INTO PASSWORD "
				+ "VALUES ('%d', null, '%s', '%s', '%s')", 
				password.getUserID(), password.getApplication(), password.getUsername(), password.getPassword());
		String selectQueryStr = "SELECT LAST_INSERT_ID()";
		
		String action = "Inserting password...";
		String success = "Password inserted successfully";
		String fail = "Failed to insert password: ";
		
		int passwordID = -1;
		
		connect();
		System.out.println(action);
		
		try {
			
			st.executeUpdate(insertQueryStr);
			ResultSet rs = st.executeQuery(selectQueryStr);
			rs.next();
			passwordID = rs.getInt(1);
			System.out.println(success);
			
		} catch (SQLException e) {
			System.err.println(fail + e.getMessage());	
		}
		
		close();
		return passwordID;
		
	}
	
	/**
	 * Queries the database to update the given users specified password
	 * @param uname String username of the user that password belongs to
	 * @param app String application containing the password being updated
	 * @param password PasswordData new data that will be stored in the database
	 * @return Boolean the success state of the query
	 */
	public static boolean updatePassword(PasswordData password) {
		
		//SQL query string to update the password data of the PASSWORD table
		String queryStr = String.format(
				"UPDATE PASSWORD "
				+ "SET PWD_APP = '%s', "
				+ "PWD_UNAME = '%s', "
				+ "PWD_PASSWORD = '%s' "
				+ "WHERE PWD_ID = '%d'",
				password.getApplication(), password.getUsername(), password.getPassword(), password.getPasswordID());
		
		String action = "Updating password...";
		String success = "Password updated successfully";
		String fail = "Failed to update password: ";
		
		return genericQuery(queryStr, action, success, fail);
		
	}
	
	/**
	 * Queries the database to delete the given password
	 * @param uname String username of the user the password belongs to
	 * @param app String application of the password being deleted
	 * @return Boolean the success state of the query
	 */
	public static boolean removePassword(int passwordID) {
		
		//SQL query string to remove the given password data from the PASSWORD table
		String queryStr = String.format(
				"DELETE FROM PASSWORD "
				+ "WHERE PWD_ID = '%d'", 
				passwordID);
		
		String action = "Removing password...";
		String success = "Password removed successfully";
		String fail = "Failed to remove password: ";
		
		return genericQuery(queryStr, action, success, fail);
		
	}
	
	/**
	 * Helper function to query the database using the given query string
	 * @param queryStr String query to be executed
	 * @return Boolean the success state of the query
	 */
	private static boolean genericQuery(String queryStr, String actionMessage, String successMessage, String failMessage) {
		
		connect();
		System.out.println(actionMessage);
		
		try {
			
			st.executeUpdate(queryStr);
			System.out.println(successMessage);
			
		} catch (SQLException e) {
			
			System.err.println(failMessage + e.getMessage());
			return false;
					
		}
		
		close();
		return true;
		
	}
}
