/*
 * TCSS 360 Software Development and Quality Assurance
 * Conferences Project - Group 3
 */ 

package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.AccessLevel;
import model.Conference;
import model.User;

/**
 * Class that communicates with the database to store/retrieve data.
 * The Connection object this class uses is globally shared and thus
 * is retrieved via JDBCConnection. (UNDER TEST: Also stores a Map of all 
 * Conference objects already loaded into memory to prevent duplication.)
 * 
 * Helpful reference: https://developer.salesforce.com/page/Secure_Coding_SQL_Injection
 * 
 * @author Kirsten Grace
 * @version 6.04.14
 */

public class ConferenceControl {

	/*
	 * ====================
	 * = Fields (Static)  =
	 * ====================
	 */
	
	/** The connection object this class uses to connect with the database. */
	private static Connection connection = null;

	/** The map of reference for all Conference objects currently loaded into memory. */
	private static Map<Integer, Conference> conferenceMap = 
			new HashMap<Integer, Conference>();
	
	/*
	 * =======================
	 * = Private Constructor =
	 * =======================
	 */
	
	/**
	 * Private constructor to prevent instantiation. 
	 */
	private ConferenceControl() {
		// Do Nothing (stop trying to create me)
	}

	/*
	 * =======================================================
	 * = Methods that Create or Edit Conferences in Database =
	 * =======================================================
	 */
	
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
		checkConnection();
		try {	
			// Add the conference to the database
			PreparedStatement pstmt = connection.prepareStatement("INSERT INTO conferences "
					+ "(name, program_chair, description, accept_papers_start, accept_papers_end,"
					+ " conference_start, conference_end) VALUES (?, ?, ?, ?, ?, ?, ?)");
			pstmt.setString(1, theConference.getName());
			pstmt.setInt(2, theConference.getProgramChair().getId());  
			pstmt.setString(3, theConference.getDescription());
			pstmt.setDate(4, theConference.getPaperStart());
			pstmt.setDate(5, theConference.getPaperEnd());
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
			pstmt.setInt(3, AccessLevel.getValueOf(AccessLevel.PROGRAMCHAIR));
			pstmt.executeUpdate();
			
			// Add this conference to the Map
			conferenceMap.put(conferenceID, theConference);
			
			return conferenceID;
		} catch(SQLException e){
			e.printStackTrace();
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
	 * the error message will be returned as a String. If no error is encountered, 
	 * null is returned.
	 * 
	 * @param theConference the java object representation of the conference
	 * @return The error message encountered or null if no error is encountered
	 */
	public static String updateConference(Conference theConference){
		checkConnection();
		try {
			// Update the conference within the database
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

	/*
	 * ======================================================================
	 * = Methods that Retrieve Conferences in Database by specific criteria =
	 * ======================================================================
	 */
	
	/**
	 * This method returns a List<Conference> of ALL conferences within the database.
	 * This method returns an empty list if no results are found.
	 * 
	 * @return a list of all conferences that are saved within the database
	 */
	public static List<Conference> getConferences(){
		checkConnection();		 
		try {
			// Load all of the conferences from the database into a ResultSet 
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
			ResultSet rs = statement.executeQuery("SELECT c.*, u.id AS user_id, u.email, "
					+ "u.username, u.password, u.first_name, u.last_name, u.address "
					+ "FROM conferences AS c JOIN users AS u ON c.program_chair=u.id");
			return iterateResults(rs);

		}catch(SQLException e) {
			// if the error message is "out of memory", 
			// it probably means no database file is found
			// Do not print error if the error is because no results were found
			if (!e.getMessage().equals("ResultSet closed")){ 
				System.err.println("SQL Error: " + e.getMessage());
			}
		}

		return new ArrayList<Conference>();
	}

	/**
	 * Builds and returns a List of the Conference objects that only include 
	 * those Conferences that the User has a recorded AccessLevel for. (This should
	 * only return Conferences that the User has direct ties to, ie: they have submitted
	 * a paper, are assigned as a reviewer, sub program chair or program chair). 
	 * This method returns an empty list if no results are found.
	 * 
	 * @param theUser The User being used as search condition for the Conferences
	 * @return A List of all Conferences the User is a part of
	 */
	public static List<Conference> getConferences(User theUser){
		checkConnection();
		try {
			// Load all conferences from the database that have a relation to specified User
			PreparedStatement pstmt = connection.prepareStatement("SELECT c.*, u.id AS user_id, "
					+ "u.email, u.first_name, u.last_name, u.address, u.username, "
					+ "u.password FROM conferences AS c "
					+ "JOIN users_conferences AS uc ON c.id=uc.conference_id "
					+ "JOIN users AS u ON c.program_chair=u.id WHERE uc.user_id=?");
			pstmt.setInt(1, theUser.getId());
			ResultSet rs = pstmt.executeQuery();
			return iterateResults(rs);
			
		} catch(SQLException e) {
			// if the error message is "out of memory", 
			// it probably means no database file is found
			// Do not print error if the error is because no results were found
			if (!e.getMessage().equals("ResultSet closed")){ 
				System.err.println("SQL Error: " + e.getMessage());
			}
		}

		return new ArrayList<Conference>();
	}

	/**
	 * Builds and returns a List of the Conference objects that only include 
	 * those Conferences that the User has the specific AccessLevel for. 
	 * This method returns an empty list if no results are found.
	 * 
	 * @param theUser The User being used to filter the results
	 * @param theAccess The specific AccessLevel to filter results
	 * @return A List of conferences that the specific user has a specific access level for
	 */
	public static List<Conference> getConferences(User theUser, AccessLevel theAccess){
		checkConnection();
		try {
			// Load all conferences from the database that this user has this access level for
			PreparedStatement pstmt = connection.prepareStatement("SELECT c.*, u.id AS user_id, "
					+ "u.email, u.first_name, u.last_name, u.address FROM conferences AS c "
					+ "JOIN users_conferences AS uc ON c.id=uc.conference_id "
					+ "JOIN users AS u ON c.program_chair=u.id WHERE uc.user_id=? "
					+ "AND uc.access_level=?");
			pstmt.setInt(1, theUser.getId());
			pstmt.setInt(2, theAccess.getValue());
			ResultSet rs = pstmt.executeQuery();
			return iterateResults(rs);

		} catch(SQLException e) {
			// if the error message is "out of memory", 
			// it probably means no database file is found
			// Do not print error if the error is because no results were found
			if (!e.getMessage().equals("ResultSet closed")){ 
				System.err.println("SQL Error: " + e.getMessage());
			}
		}
		return new ArrayList<Conference>();
	}

	/**
	 * Returns a List of Conferences that have the given String as part of their name 
	 * field. For example, if there is a conference named "Happy Days" it will be found 
	 * with ANY of the full or partial search Strings: "Day" "Days" "Happy" "Happy Days".
	 * This method returns an empty list if no results are found.
	 * 
	 * @param searchString The full or partial search string of the conference name
	 * @return A list of all conferences matching the given search string
	 */
	public static List<Conference> searchConferences(String searchString) {
		checkConnection();
		try {
			// Load all conferences from the database that match the search string
			PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM "
					+ "conferences WHERE name LIKE \"%?%\"");
			pstmt.setString(1, searchString);
			ResultSet rs = pstmt.executeQuery();
			return iterateResults(rs);

		}catch(SQLException e) {
			// if the error message is "out of memory", 
			// it probably means no database file is found
			// Do not print error if the error is because no results were found
			if (!e.getMessage().equals("ResultSet closed")){ 
				System.err.println("SQL Error: " + e.getMessage());
			}
		}
		return new ArrayList<Conference>();
	}

	/**
	 * Fetches a single Conference object by it's unique ID.
	 * 
	 * Returns null pointer if no conference exists by that ID.
	 * 
	 * @param theKey The ID of the conference requested
	 * @return The conference that matches the given ID
	 */
	public static Conference getConferenceByID(int theKey) {
		if (conferenceMap.get(theKey) != null) return conferenceMap.get(theKey);
		
		checkConnection();		 
		try {
			// Load all of the conferences from the database into a ResultSet 
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
			ResultSet rs = statement.executeQuery("SELECT c.*, u.id AS user_id, u.email, "
					+ "u.username, u.password, u.first_name, u.last_name, u.address "
					+ "FROM conferences AS c JOIN users AS u ON c.program_chair=u.id "
					+ "WHERE c.id=" + theKey);
			return iterateResults(rs).get(0);

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
	
	/*
	 * ==================================
	 * = Method related to AccessLevel  =
	 * ==================================
	 */

	/**
	 * Use this method to retrieve the AccessLevel of a user for the conference. 
	 * If the user doesn't have a specific access level for the conference then 
	 * a null pointer is returned.
	 * 
	 * @param theConference The conference to be matched
	 * @param theUser The user to be matched
	 * @return The AccessLevel of a specific user conference combination
	 */
	public static AccessLevel getAccessLevel(Conference theConference, User theUser){
		checkConnection();
		// Request the AccessLevel from the database users_conferences
		try {
			int conferenceID = theConference.getId();
			int userID = theUser.getId();

			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
			ResultSet rs = statement.executeQuery("SELECT access_level FROM users_conferences "
					+ "WHERE conference_id=" + conferenceID + " AND user_id=" + userID);
			
			System.out.println("The value is: " + rs.getInt("access_level"));
			AccessLevel al = AccessLevel.accessLevelOf(rs.getInt("access_level"));
			
			return al;

		}catch(SQLException e) {
			// if the error message is "out of memory", 
			// it probably means no database file is found
			// Do not print error if the error is because no results were found
			if (!e.getMessage().equals("ResultSet closed")){ 
				System.err.println("SQL Error: " + e.getMessage());
			}
		}
		
		return null; // No entry existed in the table
	}
	
	/*
	 * ===========================
	 * = Private Helper Methods  =
	 * ===========================
	 */
	
	/**
	 * Private helper method that establishes a connection to the database
	 * if one does not already exist.
	 */
	private static void checkConnection(){
		if(connection == null) {
			connection = JDBCConnection.getConnection();
		}
	}
	
	/**
	 * Private helper method that will iterate over a ResultSet and construct the
	 * Conference and User objects needed then returns the List<Conference> back.
	 * 
	 * @param rs The ResultSet to be iterated over.
	 * @return The completed List<Conference> constructed from the given ResultSet
	 * @throws SQLException The exception encountered from the ResultSet
	 */
	private static List<Conference> iterateResults(ResultSet rs) throws SQLException{
		
		List<Conference> result = new ArrayList<Conference>(); // Create the empty list
		
		while (rs.next()){
			if (conferenceMap.containsKey(rs.getInt("id"))) {
				result.add(conferenceMap.get(rs.getInt("id")));
			} else {
				Conference c = new Conference(
						rs.getInt("id"), 
						rs.getString("name"), 
						User.makeUserID(
								rs.getInt("user_id"), 
								rs.getString("username"),
								rs.getString("password"),
								rs.getString("email"), 
								rs.getString("first_name"), 
								rs.getString("last_name"), 
								rs.getString("address")),
						rs.getDate("accept_papers_start"),
						rs.getDate("accept_papers_end"),
						rs.getDate("conference_start"),
						rs.getDate("conference_end"),
						rs.getString("location"),
						rs.getString("description")
				);
				conferenceMap.put(rs.getInt("id"), c);
				result.add(c);
			}
		}
		return result;
	}
}
	
