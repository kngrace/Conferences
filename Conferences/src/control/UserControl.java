/**
 * Class that communicates with the database to store/retrieve data.
 * 
 * @author Kirsten Grace
 * @version 5.12.14
 */

package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.AccessLevel;
import model.Conference;
import model.User;

public class UserControl {

	private static Connection connection = null;
	
	private UserControl() {
		// Do Nothing (stop trying to create me)
	}
	
	public static Boolean updateUser(User theUser){
		return false;	
	}
	
	public static int createUser(User theUser){
		if (connection == null) {
			connection = JDBCConnection.getConnection();
		}
		
		try {
			//https://developer.salesforce.com/page/Secure_Coding_SQL_Injection

				// Add the conference to the database
				PreparedStatement pstmt = connection.prepareStatement("INSERT INTO conferences "
						+ "(id, email, first_name, last_name, address, username, password"
						+ ") VALUES (?, ?, ?, ?, ?, ?, ?)");
				pstmt.setInt(1, theUser.getId());
				pstmt.setString(2, theUser.getEmail());  
				pstmt.setString(3, theUser.getFirstName());
				pstmt.setString(4, theUser.getLastName());
				pstmt.setString(5, theUser.getAddress());
				pstmt.setString(6, theUser.getUsername());
				pstmt.setString(7, theUser.getPassword()); //:(
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
				if(connection == null) {
					connection = JDBCConnection.getConnection();
				}
				 
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
											rs.getInt("user_id"), 
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
		if(connection == null) {
			connection = JDBCConnection.getConnection();
		}

		User login = null;
		try {
			PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM users WHERE username=?");
			pstmt.setString(1, the_user);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.getString("password").equals(the_pass)) {
				
				// Iterate through ResultSet, creating/adding each conference to the List
							login = new User(
										rs.getInt("user_id"), 
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
	
	
}
