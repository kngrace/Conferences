/**
 * Class that communicates with the database to store/retrieve data.
 * 
 * @author Eric Miller, Kirsten Grace
 * @version 5.12.14
 */

package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.User;

public class UserControl {

	private static Connection connection = null;
	
	private UserControl() {
		// Do Nothing (stop trying to create me)
	}
	
	public static String updateUser(User theUser){
		checkConnection();
		try {
			// Update the user within the database
//			https://developer.salesforce.com/page/Secure_Coding_SQL_Injection
			PreparedStatement pstmt = connection.prepareStatement("UPDATE users SET "
					+ "email=?, first_name=?, last_name=?, address=?, "
					+ "username=?, password=? WHERE id=?");
			pstmt.setString(1, theUser.getEmail());
			pstmt.setString(2, theUser.getFirstName());  
			pstmt.setString(3, theUser.getLastName());
			pstmt.setString(4, theUser.getAddress());
			pstmt.setString(5, theUser.getUsername());
			pstmt.setString(6, theUser.getPassword());
			pstmt.setInt(7, theUser.getId());
			pstmt.executeUpdate();
		} catch(SQLException e) {
			// if the error message is "out of memory", 
			// it probably means no database file is found
			
			return e.getMessage(); // error occurred
		}
		
		return null; // no error
	}
	
	public static int createUser(User theUser){
		checkConnection();
		try {
			//https://developer.salesforce.com/page/Secure_Coding_SQL_Injection

				// Add the conference to the database
				PreparedStatement pstmt = connection.prepareStatement("INSERT INTO users "
						+ "(email, first_name, last_name, address, username, password"
						+ ") VALUES (?, ?, ?, ?, ?, ?)");
				pstmt.setString(1, theUser.getEmail());  
				pstmt.setString(2, theUser.getFirstName());
				pstmt.setString(3, theUser.getLastName());
				pstmt.setString(4, theUser.getAddress());
				pstmt.setString(5, theUser.getUsername());
				pstmt.setString(6, theUser.getPassword()); //:(
				pstmt.executeUpdate();

				// Retrieve the ID from the database for the recently inserted conference
				Statement statement = connection.createStatement();
				statement.setQueryTimeout(30);  // set timeout to 30 sec.
				ResultSet rs = statement.executeQuery("select last_insert_rowid()");
				int userID = rs.getInt("last_insert_rowid()");

				return userID;
		} catch (final SQLException e) {
			if (!e.getMessage().equals("ResultSet closed")){ 
				System.err.println("SQL Error: " + e.getMessage());
			}
		}
		return 0;
	}
	
	public static List<User> getUsers(){
		// Establish a connection if one does not exist
				checkConnection();
				List<User> result = new ArrayList<User>(); // Create the empty list
				try {
					// Load all of the conferences from the database into a ResultSet 
					Statement statement = connection.createStatement();
					statement.setQueryTimeout(30);  // set timeout to 30 sec.
					ResultSet rs = statement.executeQuery("SELECT * FROM users");
					
					// Iterate through ResultSet, creating/adding each conference to the List
					while (rs.next()){
						result.add(
								new User(
											rs.getInt("id"), 
											rs.getString("username"),
											rs.getString("password"),
											rs.getString("email"), 
											rs.getString("first_name"), 
											rs.getString("last_name"), 
											rs.getString("address")
								)
						);
					}
					return result;

				}catch(SQLException e) {
					// if the error message is "out of memory", 
					// it probably means no database file is found
					
					// Do not print error if the error is because no results were found
					if (!e.getMessage().equals("ResultSet closed")){ 
						System.err.println("SQL Error: " + e.getMessage());
					}
				}

				return null;
	}
	
	public static User authenticate(final String the_user, final String the_pass) {
		checkConnection();
		User login = null;
		try {
			PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM users WHERE username=?");
			pstmt.setString(1, the_user);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.getString("password").equals(the_pass)) {
				
				// Iterate through ResultSet, creating/adding each conference to the List
							login = new User(
										rs.getInt("id"), 
										rs.getString("username"),
										rs.getString("password"),
										rs.getString("email"), 
										rs.getString("first_name"), 
										rs.getString("last_name"), 
										rs.getString("address")
									);
				
				
				
			}
		} catch (final SQLException e) {
			if (!e.getMessage().equals("ResultSet closed")){ 
				System.err.println("SQL Error: " + e.getMessage());
			}
		}

		return login;
	}
	
	public static List<User> searchUsers(String searchKey) {
		return null;
	}
	
	/**
	 * Private helper method that establishes a connection to the database
	 * if one does not already exist.
	 */
	private static void checkConnection(){
		if(connection == null) {
			connection = JDBCConnection.getConnection();
		}
	}
}
