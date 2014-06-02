/**
 * Class that communicates with the database to store/retrieve data.
 * The Connection object this class uses is globally shared and thus
 * is retrieved via JDBCConnection. (UNDER TEST: Also stores a Map of all 
 * Manuscript objects already loaded into memory to prevent duplication.)
 * 
 * @author Kirsten Grace
 * @version 6.02.14
 */


package control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.AccessLevel;
import model.Conference;
import model.Manuscript;
import model.Review;
import model.User;

public class ManuscriptControl {
	
	/** The connection object this class uses to connect with the database. */
	private static Connection connection = null;
	
	/** The map of reference for all Manuscript objects currently loaded into memory. */
	private static Map<Integer, Manuscript> manuscriptMap = 
			new HashMap<Integer, Manuscript>();
	
	/**
	 * Private constructor to prevent instantiation. 
	 */
	private ManuscriptControl() {
		// Do Nothing (stop trying to create me)
	}
	
	/**
	 * Use this method to submit a freshly created manuscript to the database and
	 * retrieve its unique ID number. Do NOT use this method to update a manuscript
	 * that has already been submitted to the database previously. If an error is 
	 * encountered and no ID is generated, a value of -1 is returned instead.
	 * 
	 * @param theManuscript The java object representation of a manuscript.
	 * @return The unique ID of the newly added manuscript.
	 */
	public static int createManuscript(Manuscript theManuscript) {
		checkConnection();
		try {
//			https://developer.salesforce.com/page/Secure_Coding_SQL_Injection
		    
			// Add the manuscript to the database
			PreparedStatement pstmt = connection.prepareStatement("INSERT INTO manuscripts "
					+ "(author, conference, file_name, file_blob) VALUES (?, ?, ?, ?)");
			pstmt.setInt(1, theManuscript.getAuthor().getId());
			pstmt.setInt(2, theManuscript.getConference().getId());  
			pstmt.setString(3, theManuscript.getFile().getName());
			pstmt.setBinaryStream(4, new FileInputStream(theManuscript.getFile()), 
										theManuscript.getFile().length());
			pstmt.executeUpdate();

			// Retrieve the ID from the database for the recently inserted manuscript
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
			ResultSet rs = statement.executeQuery("select last_insert_rowid()");
			int manuscriptID = rs.getInt("last_insert_rowid()");
			
			// Create entry into the users_manuscripts table to add the author's relation
			pstmt = connection.prepareStatement("INSERT INTO users_manuscripts "
					+ "(user_id, manuscript_id, can_submit) VALUES (?, ?, 1)");
			pstmt.setInt(1, theManuscript.getAuthor().getId());
			pstmt.setInt(2, manuscriptID);
			pstmt.executeUpdate();
			
			// Add this manuscript to the Map
			manuscriptMap.put(manuscriptID, theManuscript);
			
			return manuscriptID;
		} catch(SQLException e){
			// if the error message is "out of memory", 
			// it probably means no database file is found
			System.err.println(e.getMessage());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return -1;
	}
	
	public static Boolean updateManuscript(Manuscript theManuscript) {
		return false;
	}
	
	public static List<Manuscript> getManuscripts(Conference theCon, User theUser){
		return null;
	}
	
	public static List<Review> getReviews(Manuscript theMan, User theUser){
		return null;
	}
	
	public static List<Manuscript> getUnassignedSPC(Conference theCon, User theUser){
		return null;
	}
	
	public static List<Manuscript> getAcceptedManuscripts(Conference theCon){
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
