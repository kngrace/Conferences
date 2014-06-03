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

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.AccessLevel;
import model.Conference;
import model.Manuscript;
import model.Review;
import model.Status;
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
			
			File f = theManuscript.getFile();
	//		FileInputStream input = new FileInputStream(theManuscript.getFile());
	
			
			byte[] pdfData = new byte[(int) f.length()];
			DataInputStream dis = new DataInputStream(new FileInputStream(f));
			dis.readFully(pdfData);  // read from file into byte[] array
			dis.close();

			
//			https://developer.salesforce.com/page/Secure_Coding_SQL_Injection
			// Add the manuscript to the database
			PreparedStatement pstmt = connection.prepareStatement("INSERT INTO manuscripts "
					+ "(author, conference, file_name, file_blob) VALUES (?, ?, ?, ?)");
			pstmt.setInt(1, theManuscript.getAuthor().getId());
			pstmt.setInt(2, theManuscript.getConference().getId());  
			pstmt.setString(3, theManuscript.getFile().getName());
			pstmt.setBytes(4, pdfData);
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return -1;
	}
	
	/**
	 * Use this method to update a manuscript in the database after one of it's 
	 * fields has been changed. The manuscript MUST have its ID number correctly
	 * set or the update will have unintended results. If an error is encountered, 
	 * the error message will be returned as a String. If no error is encountered, 
	 * null is returned.
	 * 
	 * @param theManuscript the java object representation of the manuscript
	 * @return The error message encountered or null if no error is encountered
	 */
	public static String updateManuscript(Manuscript theManuscript) {
		checkConnection();
		try {
			// Update the conference within the database
//			https://developer.salesforce.com/page/Secure_Coding_SQL_Injection
			PreparedStatement pstmt = connection.prepareStatement("UPDATE manuscript SET "
					+ "author=?, conference=?, rec_status=?, final_status=?, "
					+ "submitted=?, file_name=?, file_blob=? WHERE id=?");
			pstmt.setInt(1, theManuscript.getAuthor().getId());
			pstmt.setInt(2, theManuscript.getConference().getId());  
			pstmt.setInt(3, theManuscript.getRecommendStatus().value());
			pstmt.setInt(4, theManuscript.getFinalStatus().value()); // don't have a session :-(
			pstmt.setInt(5, theManuscript.getSPC().value()); // no session
			pstmt.setBoolean(5, theManuscript.isSubmitted());  
			pstmt.setString(6, theManuscript.getFile().getName());
			pstmt.setBinaryStream(7, new FileInputStream(theManuscript.getFile()), 
					theManuscript.getFile().length());
			pstmt.executeUpdate();
		} catch(SQLException e) {
			// if the error message is "out of memory", 
			// it probably means no database file is found
			
			return e.getMessage(); // error occurred
		}

		return null; // no error
	}
	
	public static List<Manuscript> getManuscripts(Conference theCon, User theUser){
		return null;
	}
	
	public static List<Review> getReviews(Manuscript theMan, User theUser){
		return null;
	}
	
	public static int createReview(Review theReview) {
		return -1;
	}
	
	public static void updateReview(Review theReview) {
		//to do
	}
	
	public static void addReviewer(Manuscript theMan, User theReviewer) {
		// todo  (update users_manuscript)
	}
	
	// Does the GUI need this???
	public static List<User> getReviewers(Manuscript theMan) {
		return null;
	}
	
	public static List<Manuscript> getUnassignedSPC(Conference theCon, User theUser){
		return null;
	}
	
	public static List<Manuscript> getAcceptedManuscripts(Conference theCon){
		return null;
	}
	
	/**
	 * This method will update the recommend status for the specified manuscript
	 * in the database. (To be called by Manuscript's setRecommendStatus() method)
	 * 
	 * @param theMan The manuscript to be updated.
	 * @param theStatus The new Status to be set for this manuscript.
	 */
	public static void updateRecommend(Manuscript theMan, Status theStatus) {
		
	}
	
	/**
	 * This method will update the final status for the specified manuscript
	 * in the database. (To be called by Manuscript's setFinalStatus() method)
	 * 
	 * @param theMan The manuscript to be updated.
	 * @param theStatus The new Status to be set for this manuscript.
	 */
	public static void updateFinal(Manuscript theMan, Status theStatus){
		
	}
	
	/**
	 * This method will update the SPC assigned to the specified manuscript
	 * in the database. (To be called by Manuscript's assignSPC() method)
	 * 
	 * @param theMan
	 * @param theSPC
	 */
	public static void updateSPC(Manuscript theMan, User theSPC){
		
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
	
	
	/**
	 * Private helper method that will iterate over a ResultSet then returns 
	 * the List<Manuscript> back.
	 * 
	 * @param rs The ResultSet to be iterated over.
	 * @return The completed List<Manuscript> constructed from the given ResultSet
	 * @throws SQLException The exception encountered from the ResultSet
	 */
	private static List<Conference> iterateResults(ResultSet rs) throws SQLException{
		
		List<Manuscript> result = new ArrayList<Manuscript>(); // Create the empty list
		
		while (rs.next()){
			if (manuscriptMap.containsKey(rs.getInt("id"))) {
				result.add(manuscriptMap.get(rs.getInt("id")));
			} else {
				Manuscript m = new Manuscript(rs.getInt("id"), 
						new User(
								rs.getInt("user_id"), 
								rs.getString("username"),
								rs.getString("password"),
								rs.getString("email"), 
								rs.getString("first_name"), 
								rs.getString("last_name"), 
								rs.getString("address")), 
						conference, 
						filename,
						file, 
						spc
						);
				manuscriptMap.put(rs.getInt("id"), m);
				result.add(m);
			}
		}
		return result;
	}
	
}
