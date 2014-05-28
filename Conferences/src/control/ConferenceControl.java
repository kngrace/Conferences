/**
 * Class that communicates with the database to store/retrieve data.
 * The Connection object this class uses is globally shared and thus
 * is retrieved via JDBCConnection.
 * 
 * @author Kirsten Grace
 * @version 5.28.14
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

public class ConferenceControl {

	/** The connection object this class uses to connect with the database. */
	private static Connection connection = null;

	/**
	 * Private constructor to prevent instantiation. 
	 */
	private ConferenceControl() {
		// Do Nothing (stop trying to create me)
	}

	/**
	 * Use this method to submit a freshly created conference to the database and
	 * retrieve its unique ID number. Do NOT use this method to update a conference
	 * that has already been submitted to the database previously. If an error is 
	 * encountered and no ID is generated, a value of -1 is returned instead.
	 * 
	 * @param theConference The java object representation of a conference.
	 * @return The unique ID of the newly added conference.
	 */
	public static int createConference(Conference theConference) {
		// Establish a connection if one does not exist
		if(connection == null) {
			connection = JDBCConnection.getConnection();
		}

		try {
//			https://developer.salesforce.com/page/Secure_Coding_SQL_Injection
			
			// Add the conference to the database
			PreparedStatement pstmt = connection.prepareStatement("INSERT INTO conferences "
					+ "(name, program_chair, description, accept_papers_start, accept_papers_end,"
					+ " conference_start, conference_end) VALUES (?, ?, ?, ?, ?, ?, ?)");
			pstmt.setString(1, theConference.getName());
			pstmt.setInt(2, theConference.getProgramChair().getId());  
			pstmt.setString(3, theConference.getDescription());
			pstmt.setDate(4, theConference.getPaperStart());
			pstmt.setDate(5, theConference.getPaperStart());
			pstmt.setDate(6, theConference.getConferenceStart());
			pstmt.setDate(7, theConference.getConferenceEnd());
			pstmt.executeUpdate();

			// Retrieve the ID from the database for the recently inserted conference
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
			ResultSet rs = statement.executeQuery("select last_insert_rowid()");
			int conferenceID = rs.getInt("last_insert_rowid()");
			
			// Create entry into the users_conferences table to add the program chair's access_level
			pstmt = connection.prepareStatement("INSERT INTO users_conferences "
					+ "(user_id, conference_id, access_level) VALUES (?, ?, ?)");
			pstmt.setInt(1, theConference.getProgramChair().getId());
			pstmt.setInt(2, conferenceID);
			pstmt.setInt(3, AccessLevel.PROGRAMCHAIR.value());
			pstmt.executeUpdate();
			
			return conferenceID;
		} catch(SQLException e){
			// if the error message is "out of memory", 
			// it probably means no database file is found
			System.err.println(e.getMessage());
		}

		return -1;
	}

	
	/**
	 * Use this method to update a conference in the database after one of it's 
	 * fields has been changed. The conference MUST have its ID number correctly
	 * set or the update will have unintended results. If an error is encountered, 
	 * the error message will be returned. If no error is encountered, null is returned.
	 * 
	 * @param theConference the java object representation of the conference
	 * @return The error message encountered or null if no error is encountered
	 */
	public static String updateConference(Conference theConference){
		// Establish a connection if one does not exist
		if(connection == null) {
			connection = JDBCConnection.getConnection();
		}

		try {

			// Update the conference within the database
//			https://developer.salesforce.com/page/Secure_Coding_SQL_Injection
			PreparedStatement pstmt = connection.prepareStatement("UPDATE conferences SET name=?, "
					+ "program_chair=?, description=?, accept_papers_start=?, accept_papers_end=?, "
					+ "conference_start=?, conference_end=? WHERE id=?");
			pstmt.setString(1, theConference.getName());
			pstmt.setInt(2, theConference.getProgramChair().getId());  
			pstmt.setString(3, theConference.getDescription());
			pstmt.setDate(4, theConference.getPaperStart());
			pstmt.setDate(5, theConference.getPaperStart());
			pstmt.setDate(6, theConference.getConferenceStart());
			pstmt.setDate(7, theConference.getConferenceEnd());
			pstmt.setInt(8, theConference.getId());
			pstmt.executeUpdate();
		} catch(SQLException e) {
			// if the error message is "out of memory", 
			// it probably means no database file is found
			
			return e.getMessage(); // error occurred
		}

		return null; // no error
	}

	// NOT DONE YET!!
	public static List<Conference> getConferences(){
		// Establish a connection if one does not exist
		if(connection == null) {
			connection = JDBCConnection.getConnection();
		}
		 
		List<Conference> result = new ArrayList<Conference>(); // Create the empty list
		try {
			// Load all of the conferences from the database into a ResultSet 
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
			ResultSet rs = statement.executeQuery("SELECT * FROM conferences AS c JOIN users AS u ON c.program_chair=u.id");
			
			// Iterate through ResultSet, creating/adding each conference to the List
			while (rs.next()){
//				result.add(new Conference(rs.getString("name"), new User(rs.getInt("program_chair"), null, null, null, null)), ,
//				name program_chair  description  accept_papers_start  accept_papers_end conference_start conference_end 
//				pstmt.setString(1, theConference.getName());
//				pstmt.setInt(2, theConference.getProgramChair().getId());  
//				pstmt.setString(3, theConference.getDescription());
//				pstmt.setDate(4, theConference.getPaperStart());
//				pstmt.setDate(5, theConference.getPaperStart());
//				pstmt.setDate(6, theConference.getConferenceStart());
//				pstmt.setDate(7, theConference.getConferenceEnd());
//				pstmt.setInt(8, theConference.getId());
			}
//			AccessLevel al = AccessLevel.valueOf(rs.getInt("access_level"));
//			
//			return al;

		}catch(SQLException e) {
			// if the error message is "out of memory", 
			// it probably means no database file is found
			
			// Do not print error if the error is because no results were found
		//	if (!e.getMessage().equals("ResultSet closed")){ 
				System.err.println("SQL Error: " + e.getMessage());
			//}
		}

		return null;
	}

	public static List<Conference> getConferences(User theUser){
		return null;
	}

	public static List<Conference> getConferences(User theUser, AccessLevel access){
		return null;
	}

	public static List<Conference> searchConferences(String searchString) {
		return null;
	}

	/**
	 * Use this method to retrieve the AccessLevel of a specific user for
	 * a specific conference. If the user does not exist in the database
	 * with a specific AccessLevel, then the AccessLevel of AUTHOR is returned.
	 * 
	 * @param theCon The conference to be matched
	 * @param theUser The user to be matched
	 * @return The AccessLevel of a specific user conference combination
	 */
	public static AccessLevel getAccessLevel(Conference theCon, User theUser){
		// Establish a connection if one does not exist
		if(connection == null) {
			connection = JDBCConnection.getConnection();
		}
		
		// Request the AccessLevel from the database users_conferences
		try {
			int conferenceID = theCon.getId();
			int userID = theUser.getId();

			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
			ResultSet rs = statement.executeQuery("SELECT access_level FROM users_conferences WHERE conference_id=" 
					+ conferenceID + " AND user_id=" + userID);
			
		//	System.out.println("The value is: " + rs.getInt("access_level"));
			AccessLevel al = AccessLevel.valueOf(rs.getInt("access_level"));
			
			return al;

		}catch(SQLException e) {
			// if the error message is "out of memory", 
			// it probably means no database file is found
			
			// Do not print error if the error is because no results were found
			if (!e.getMessage().equals("ResultSet closed")){ 
				System.err.println("SQL Error: " + e.getMessage());
			}
		}
		
		return AccessLevel.AUTHOR; // No entry existed in the table
	}

}
