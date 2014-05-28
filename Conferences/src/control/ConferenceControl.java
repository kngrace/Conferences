/**
 * Class that communicates with the database to store/retrieve data.
 * The Connection object this class uses is globally shared and thus
 * is retrieved via JDBCConnection.
 * 
 * @author Kirsten Grace
 * @version 5.22.14
 */

package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

		//System.out.println(connection); Test code
		if(connection == null) {
			connection = JDBCConnection.getConnection();
		}

		// The following code safely takes all fields from the conference object
		// and inserts the information into the database by matching database fields.
		try {
//			https://developer.salesforce.com/page/Secure_Coding_SQL_Injection
			PreparedStatement pstmt = connection.prepareStatement("INSERT INTO conferences "
					+ "(name, program_chair, description, accept_papers_start, accept_papers_end,"
					+ " conference_start, conference_end) VALUES (?, ?, ?, ?, ?, ?, ?)");
			pstmt.setString(1, theConference.getName());
			pstmt.setInt(2, theConference.getProgramChair().getId());  
			pstmt.setString(3, theConference.getDescription());
			pstmt.setDate(4, theConference.getPaperStart());
			pstmt.setDate(5, theConference.getPaperStart());
			pstmt.setDate(6, theConference.getConfStart());
			pstmt.setDate(7, theConference.getConfEnd());
			pstmt.executeUpdate();

//			IGNORE THIS COMMENT CHUNK
//			statement.executeUpdate("INSERT INTO conferences (name, email) VALUES (?, ?)");
//			statement.value(1, theName);
//			query.value(2, theEmail);

			// This code retrieves the ID # of the newly inserted conference.
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
			ResultSet rs = statement.executeQuery("select last_insert_rowid()");
			System.out.println("The ID of this conference is: " + rs.getInt("last_insert_rowid()"));
			return rs.getInt("last_insert_rowid()");

		} catch(SQLException e){
			// if the error message is "out of memory", 
			// it probably means no database file is found
			System.err.println(e.getMessage());
		}
		
		return -1; // Returns -1 if an error is encountered.
	}


	/**
	 * 
	 * @param theConference
	 * @return 
	 */
	public static String updateConference(Conference theConference){

		if(connection == null) {
			connection = JDBCConnection.getConnection();
		}

		try {

//			https://developer.salesforce.com/page/Secure_Coding_SQL_Injection
			PreparedStatement pstmt = connection.prepareStatement("INSERT INTO conferences "
					+ "(name, program_chair, description, accept_papers_start, accept_papers_end,"
					+ " conference_start, conference_end) VALUES (?, ?, ?, ?, ?, ?, ?)");
			pstmt.setString(1, theConference.getName());
			pstmt.setInt(2, theConference.getProgramChair().getId());  
			pstmt.setString(3, theConference.getDescription());
			pstmt.setDate(4, theConference.getPaperStart());
			pstmt.setDate(5, theConference.getPaperStart());
			pstmt.setDate(6, theConference.getConfStart());
			pstmt.setDate(7, theConference.getConfEnd());
			pstmt.executeUpdate();

//			statement.executeUpdate("INSERT INTO conferences (name, email) VALUES (?, ?)");
//			statement.value(1, theName);
//			query.value(2, theEmail);

			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
			ResultSet rs = statement.executeQuery("select last_insert_rowid()");
			System.out.println("The ID of this conference is: " + rs.getInt("last_insert_rowid()"));
	//		return rs.getInt("last_insert_rowid()");

		}
		catch(SQLException e)
				//| ClassNotFoundException e)
		{
			// if the error message is "out of memory", 
			// it probably means no database file is found
		//	System.err.println(e.getMessage());
			return e.getMessage(); // error occurred
		}

		return null; // no error
	}

	public static List<Conference> getConferences(){
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

	public static AccessLevel getAccessLevel(Conference theCon, User theUser){
		return null;
	}




}