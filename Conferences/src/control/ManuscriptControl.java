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
import java.io.FileOutputStream;
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
//			https://developer.salesforce.com/page/Secure_Coding_SQL_Injection
			// Add the manuscript to the database
			PreparedStatement pstmt = connection.prepareStatement("INSERT INTO manuscripts "
					+ "(author, conference, file_name, file_blob) VALUES (?, ?, ?, ?)");
			pstmt.setInt(1, theManuscript.getAuthor().getId());
			pstmt.setInt(2, theManuscript.getConference().getId());  
			pstmt.setString(3, theManuscript.getFile().getName());
			
			File f = theManuscript.getFile();
			byte[] fileData = new byte[(int) f.length()];
			DataInputStream dis = new DataInputStream(new FileInputStream(f));
			dis.readFully(fileData);  // read from file into byte[] array
			dis.close();
			
			pstmt.setBytes(4, fileData);
			pstmt.executeUpdate();

			// Retrieve the ID from the database for the recently inserted manuscript
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
			ResultSet rs = statement.executeQuery("select last_insert_rowid()");
			int manuscriptID = rs.getInt("last_insert_rowid()");
			
			while (true) {
				try {
					// Create entry into the users_manuscripts table to add program chair's relation
					pstmt = connection.prepareStatement("INSERT INTO users_manuscripts "
							+ "(user_id, manuscript_id, can_final) VALUES (?, ?, 1)");
					pstmt.setInt(1, theManuscript.getConference().getProgramChair().getId());
					pstmt.setInt(2, manuscriptID);
					pstmt.executeUpdate();	
					break;
					
				} catch (SQLException e) {
					pstmt = connection.prepareStatement("UPDATE users_manuscripts "
							+ "SET can_final=1 WHERE user_id=? AND manuscript_id=?");
					pstmt.setInt(1, theManuscript.getConference().getProgramChair().getId());
					pstmt.setInt(2, manuscriptID);
					pstmt.executeUpdate();	
					
					rs = statement.executeQuery("SELECT CHANGES()");
					int changes = rs.getInt("CHANGES()");
					System.out.println(changes);
					if (changes != 0) break;
				}
			}
			
			while (true) {
				try {
					// Create entry into the users_manuscripts table to add the author's relation
					pstmt = connection.prepareStatement("INSERT INTO users_manuscripts "
							+ "(user_id, manuscript_id, can_submit) VALUES (?, ?, 1)");
					pstmt.setInt(1, theManuscript.getAuthor().getId());
					pstmt.setInt(2, manuscriptID);
					pstmt.executeUpdate();
					break;
					
				} catch (SQLException e) {
					pstmt = connection.prepareStatement("UPDATE users_manuscripts "
							+ "SET can_submit=1 WHERE user_id=? AND manuscript_id=?");
					pstmt.setInt(1, theManuscript.getAuthor().getId());
					pstmt.setInt(2, manuscriptID);
					pstmt.executeUpdate();	
					
					rs = statement.executeQuery("SELECT CHANGES()");
					int changes = rs.getInt("CHANGES()");
					System.out.println(changes);
					if (changes != 0) break;
				}
			}
			
			// Add this manuscript to the Map
			manuscriptMap.put(manuscriptID, theManuscript);
			
			return manuscriptID;
		} catch(SQLException e){
			// if the error message is "out of memory", 
			// it probably means no database file is found
			System.err.println(e.getMessage());
			e.printStackTrace();
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
			System.out.println("I'm updating the manuscript with ID= "
					+ theManuscript.getId());
			// Update the manuscript within the database
//			https://developer.salesforce.com/page/Secure_Coding_SQL_Injection
			PreparedStatement pstmt = connection.prepareStatement("UPDATE manuscripts SET "
					+ "author=?, conference=?, submitted=?, file_name=?, file_blob=? "
					+ "WHERE id=?");
			pstmt.setInt(1, theManuscript.getAuthor().getId());
			pstmt.setInt(2, theManuscript.getConference().getId()); 
			pstmt.setBoolean(3, theManuscript.isSubmitted());  
			pstmt.setString(4, theManuscript.getFile().getName());
			
			File f = theManuscript.getFile();
			byte[] fileData = new byte[(int) f.length()];
			DataInputStream dis = new DataInputStream(new FileInputStream(f));
			dis.readFully(fileData);  // read from file into byte[] array
			dis.close();
			
			pstmt.setBytes(5, fileData);
			pstmt.setInt(6, theManuscript.getId());
			pstmt.executeUpdate();
			
		} catch(SQLException e) {
			// if the error message is "out of memory", 
			// it probably means no database file is found
			e.printStackTrace();
			return e.getMessage(); // error occurred
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}

		return null; // no error
	}
	
	/**
	 * This method returns all manuscripts attached to a specific conference that a
	 * user has access to. A user has access if they are: The author of the manuscript,
	 * a reviewer for a manuscript, the sub program chair for the manuscript or the 
	 * program chair for this conference. Note: This will return all manuscripts if the 
	 * given user is the program chair of the given conference.
	 * 
	 * Returns Null if no manuscripts exist for this criteria.
	 * 
	 * @param theCon The conference that the requested manuscripts are part of
	 * @param theUser The user being used as a filter for the manuscripts
	 * @return A list of manuscripts the user has access to for this conference
	 */
	public static List<Manuscript> getManuscripts(Conference theCon, User theUser){
		checkConnection();		 
		List<Manuscript> result = new ArrayList<Manuscript>();
		try {
//			// Load all of the conferences from the database into a ResultSet 
//			Statement statement = connection.createStatement();
//			statement.setQueryTimeout(30);  // set timeout to 30 sec.
//			ResultSet rs = statement.executeQuery("SELECT c.*, u.id AS user_id, u.email, "
//					+ "u.username, u.password, u.first_name, u.last_name, u.address "
//					+ "FROM conferences AS c JOIN users AS u ON c.program_chair=u.id");
//			return iterateResults(rs);
//
			
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
			ResultSet rs = statement.executeQuery("SELECT m.*, m.id AS manuscript_id, "
					+ "u.id AS user_id, u.email, u.username, u.password, u.first_name, "
					+ "u.last_name, u.address FROM users_manuscripts AS um	"
					+ "JOIN users as u ON um.user_id=u.id JOIN manuscripts as m "
					+ "ON um.manuscript_id=m.id	WHERE user_id=" + theUser.getId());
	
			//testing
						
//						Statement statement2 = connection.createStatement();
//						statement2.setQueryTimeout(30);  // set timeout to 30 sec.
//						ResultSet rs2 = statement2.executeQuery("SELECT * FROM manuscripts");
			while (rs.next()){
				if (manuscriptMap.containsKey(rs.getInt("manuscript_id"))) {
					result.add(manuscriptMap.get(rs.getInt("manuscript_id")));
				} else {
				
					// Load the File first because it is multi-step
					String filename = rs.getString("file_name");
					File blobFile = new File(filename); 
					FileOutputStream outStream  = new FileOutputStream(blobFile); 
					byte[] buffer = rs.getBytes("file_blob"); 
					outStream.write(buffer); 
					outStream.close(); 
					
					Manuscript m = new Manuscript(
							rs.getInt("manuscript_id"), 
							new User(
									rs.getInt("user_id"), 
									rs.getString("username"),
									rs.getString("password"),
									rs.getString("email"), 
									rs.getString("first_name"), 
									rs.getString("last_name"), 
									rs.getString("address")), 
							ConferenceControl.getConferenceByID(rs.getInt("conference")), 
							rs.getString("file_name"), 
							blobFile, 
							Status.getStatus(rs.getInt("rec_status")), 
							Status.getStatus(rs.getInt("final_status")), 
							rs.getBoolean("submitted"));
					manuscriptMap.put(rs.getInt("manuscript_id"), m);
					result.add(m);
				}
			}
			return result;
		}catch(SQLException e) {
			// if the error message is "out of memory", 
			// it probably means no database file is found
			
			// Do not print error if the error is because no results were found
			if (!e.getMessage().equals("ResultSet closed")){ 
				System.err.println("SQL Error: " + e.getMessage());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * This method will add a relation between the user and the manuscript within the
	 * database that indicates they are a reviewer for this manuscript.
	 * 
	 * @param theManuscript The manuscript to add the reviewer to.
	 * @param theReviewer The user to be added as a reviewer.
	 */
	public static void addReviewer(Manuscript theManuscript, User theReviewer) {
		checkConnection();
		PreparedStatement pstmt;
		try {
			
			while (true) {
				try {
					// Create entry into the users_manuscripts table to add the reviewer's relation
					pstmt = connection.prepareStatement("INSERT INTO users_manuscripts "
							+ "(user_id, manuscript_id, can_review) VALUES (?, ?, 1)");
					pstmt.setInt(1, theReviewer.getId());
					pstmt.setInt(2, theManuscript.getId());
					pstmt.executeUpdate();
					break;

				} catch (SQLException e) {
						pstmt = connection.prepareStatement("UPDATE users_manuscripts "
								+ "SET can_review=1 WHERE user_id=? AND manuscript_id=?");
						pstmt.setInt(1, theReviewer.getId());
						pstmt.setInt(2, theManuscript.getId());
						pstmt.executeUpdate();	

						Statement statement = connection.createStatement();
						statement.setQueryTimeout(30);  
						ResultSet rs = statement.executeQuery("SELECT CHANGES()");
						int changes = rs.getInt("CHANGES()");
						//	System.out.println(changes);
						if (changes != 0) break;

				}
			}
		} catch (SQLException e) {
			// if the error message is "out of memory", 
			// it probably means no database file is found
			
			// Do not print error if the error is because no results were found
			if (!e.getMessage().equals("ResultSet closed")){ 
				System.err.println("SQL Error: " + e.getMessage());
			}
		}
	}
	
	/**
	 * This method returns a List<Manuscript> that have the final status marked as
	 * Status.APPROVED for the given conference.
	 * 
	 * @param theConference The conference these manuscripts are attached to.
	 * @return A list of manuscripts with approved status for the given conference.
	 */
	public static List<Manuscript> getAcceptedManuscripts(Conference theConference){
		checkConnection();		 
		List<Manuscript> result = new ArrayList<Manuscript>();
		try {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
			ResultSet rs = statement.executeQuery("SELECT m.*, m.id AS manuscript_id, "
					+ "u.id AS user_id, u.email, u.username, u.password, u.first_name, "
					+ "u.last_name, u.address FROM users_manuscripts AS um	"
					+ "JOIN users as u ON um.user_id=u.id JOIN manuscripts as m "
					+ "ON um.manuscript_id=m.id	WHERE final_status=" 
					+ Integer.toString(Status.APPROVED.getValue())
					+ " AND m.conference=" + Integer.toString(theConference.getId()));
	
			while (rs.next()){
				if (manuscriptMap.containsKey(rs.getInt("manuscript_id"))) {
					result.add(manuscriptMap.get(rs.getInt("manuscript_id")));
				} else {
				
					// Load the File first because it is multi-step
					String filename = rs.getString("file_name");
					File blobFile = new File(filename); 
					FileOutputStream outStream  = new FileOutputStream(blobFile); 
					byte[] buffer = rs.getBytes("file_blob"); 
					outStream.write(buffer); 
					outStream.close(); 
					
					Manuscript m = new Manuscript(
							rs.getInt("manuscript_id"), 
							new User(
									rs.getInt("user_id"), 
									rs.getString("username"),
									rs.getString("password"),
									rs.getString("email"), 
									rs.getString("first_name"), 
									rs.getString("last_name"), 
									rs.getString("address")), 
							ConferenceControl.getConferenceByID(rs.getInt("conference")), 
							rs.getString("file_name"), 
							blobFile, 
							Status.getStatus(rs.getInt("rec_status")), 
							Status.getStatus(rs.getInt("final_status")), 
							rs.getBoolean("submitted"));
					manuscriptMap.put(rs.getInt("manuscript_id"), m);
					result.add(m);
				}
			}
			return result;
		}catch(SQLException e) {
			// if the error message is "out of memory", 
			// it probably means no database file is found
			
			// Do not print error if the error is because no results were found
			if (!e.getMessage().equals("ResultSet closed")){ 
				System.err.println("SQL Error: " + e.getMessage());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	// Does the GUI need this???
		public static List<User> getReviewers(Manuscript theMan) {
			return null;
		}
		
		public static List<Manuscript> getUnassignedSPC(Conference theCon, User theUser){
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
	
	/**
	 * This method will update the recommend status for the specified manuscript
	 * in the database. (To be called by Manuscript's setRecommendStatus() method)
	 * 
	 * Returns the error message as a String if an error is encountered. 
	 * Returns null if no error is encountered.
	 * 
	 * @param theManuscript The manuscript to be updated.
	 * @param theStatus The new Status to be set for this manuscript.
	 */
	public static String updateRecommend(Manuscript theManuscript, Status theStatus) {
		checkConnection();
		try {
			// Update the manuscript within the database
//			https://developer.salesforce.com/page/Secure_Coding_SQL_Injection
			PreparedStatement pstmt = connection.prepareStatement("UPDATE manuscripts "
					+ "SET rec_status=? WHERE id=?");
			pstmt.setInt(1, theStatus.getValue());
			pstmt.setInt(2, theManuscript.getId()); 
			pstmt.executeUpdate();
			
		} catch(SQLException e) {
			// if the error message is "out of memory", 
			// it probably means no database file is found
			e.printStackTrace();
			return e.getMessage(); // error occurred
		}

		return null; // no error
	}
	
	/**
	 * This method will update the final status for the specified manuscript
	 * in the database. (To be called by Manuscript's setFinalStatus() method)
	 * 
	 * Returns the error message as a String if an error is encountered. 
	 * Returns null if no error is encountered.
	 * 
	 * @param theManuscript The manuscript to be updated.
	 * @param theStatus The new Status to be set for this manuscript.
	 */
	public static String updateFinal(Manuscript theManuscript, Status theStatus){
		checkConnection();
		try {
			// Update the manuscript within the database
//			https://developer.salesforce.com/page/Secure_Coding_SQL_Injection
			PreparedStatement pstmt = connection.prepareStatement("UPDATE manuscripts "
					+ "SET final_status=? WHERE id=?");
			pstmt.setInt(1, theStatus.getValue());
			pstmt.setInt(2, theManuscript.getId()); 
			pstmt.executeUpdate();
			
		} catch(SQLException e) {
			// if the error message is "out of memory", 
			// it probably means no database file is found
			e.printStackTrace();
			return e.getMessage(); // error occurred
		}

		return null; // no error
	}
	
	/**
	 * This method will update the SPC assigned to the specified manuscript
	 * in the database. (To be called by Manuscript's assignSPC() method)
	 * Note: If an older SPC was assigned and now being overwritten, it will
	 * NOT delete the "relationship" this older SPC had with the manuscript. 
	 * 
	 * Returns the error message as a String if an error is encountered. 
	 * Returns null if no error is encountered.
	 * 
	 * @param theManuscript The manuscript to be updated.
	 * @param theSPC The user to be set as the sub program chair.
	 */
	public static String updateSPC(Manuscript theManuscript, User theSPC){
		checkConnection();
		try {
			// Update the manuscript within the database
//			https://developer.salesforce.com/page/Secure_Coding_SQL_Injection
			PreparedStatement pstmt = connection.prepareStatement("UPDATE manuscripts "
					+ "SET spc=? WHERE id=?");
			pstmt.setInt(1, theSPC.getId());
			pstmt.setInt(2, theManuscript.getId()); 
			pstmt.executeUpdate();
			
			while (true) {
				try {
					// Create entry into the users_manuscripts table to add the SPC's relation
					pstmt = connection.prepareStatement("INSERT INTO users_manuscripts "
							+ "(user_id, manuscript_id, can_recommend) VALUES (?, ?, 1)");
					pstmt.setInt(1, theSPC.getId());
					pstmt.setInt(2, theManuscript.getId());
					pstmt.executeUpdate();
					break;

				} catch (SQLException e) {
					pstmt = connection.prepareStatement("UPDATE users_manuscripts "
							+ "SET can_recommend=1 WHERE user_id=? AND manuscript_id=?");
					pstmt.setInt(1, theSPC.getId());
					pstmt.setInt(2, theManuscript.getId());
					pstmt.executeUpdate();	

					Statement statement = connection.createStatement();
					statement.setQueryTimeout(30);  
					ResultSet rs = statement.executeQuery("SELECT CHANGES()");
					int changes = rs.getInt("CHANGES()");
					//	System.out.println(changes);
					if (changes != 0) break;
				}
			}
			
		} catch(SQLException e) {
			// if the error message is "out of memory", 
			// it probably means no database file is found
			e.printStackTrace();
			return e.getMessage(); // error occurred
		}

		return null; // no error
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
	
	
//	/**
//	 * Private helper method that will iterate over a ResultSet then returns 
//	 * the List<Manuscript> back.
//	 * 
//	 * @param rs The ResultSet to be iterated over.
//	 * @return The completed List<Manuscript> constructed from the given ResultSet
//	 * @throws SQLException The exception encountered from the ResultSet
//	 */
//	private static List<Conference> iterateResults(ResultSet rs) throws SQLException{
//		
//		List<Manuscript> result = new ArrayList<Manuscript>(); // Create the empty list
//		
//		while (rs.next()){
//			if (manuscriptMap.containsKey(rs.getInt("id"))) {
//				result.add(manuscriptMap.get(rs.getInt("id")));
//			} else {
//				Manuscript m = new Manuscript(rs.getInt("id"), 
//						new User(
//								rs.getInt("user_id"), 
//								rs.getString("username"),
//								rs.getString("password"),
//								rs.getString("email"), 
//								rs.getString("first_name"), 
//								rs.getString("last_name"), 
//								rs.getString("address")), 
//						conference, 
//						filename,
//						file, 
//						spc
//						);
//				manuscriptMap.put(rs.getInt("id"), m);
//				result.add(m);
//			}
//		}
//		return result;
//	}
	
}


//// Load all of the conferences from the database into a ResultSet 
//Statement statement = connection.createStatement();
//statement.setQueryTimeout(30);  // set timeout to 30 sec.
//ResultSet rs = statement.executeQuery("SELECT c.*, u.id AS user_id, u.email, "
//		+ "u.username, u.password, u.first_name, u.last_name, u.address "
//		+ "FROM conferences AS c JOIN users AS u ON c.program_chair=u.id");
//return iterateResults(rs);
//
