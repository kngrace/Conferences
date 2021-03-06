/*
 * TCSS 360 Software Development and Quality Assurance
 * Conferences Project - Group 3
 */ 

package control;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.sql.Date;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import model.Conference;
import model.Manuscript;
import model.Session;
import model.User;


/**
 * The class is used as a test for the controller classes. It is not a set
 * of traditionally JUnit tests, but rather a quick way for Kirsten to test
 * code she has written in the control package. 
 * 
 * The code was originally inspired by the Sample.java file posted 
 * on the following website:
 * 
 * https://bitbucket.org/xerial/sqlite-jdbc/overview
 * 
 * Additional comments and methods were written by Kirsten. This is a reference only 
 * and is not part of the working code for the final project.
 */

public class ControllerExample {
	
	private static void test1() throws ClassNotFoundException {
		User testUser = new User(3, null, null, null, 
				null, null, null);
		Conference testConference = new Conference("TestDummy", testUser, Date.valueOf("2014-06-01"), Date.valueOf("2014-06-25"), 
				null, null, null, null);
	//	testConference.setName(null, "Testing Access Levels");
	//	ConferenceControl.updateConference(testConference);
		
		
		System.out.println("The access level for this user is: " 
				+ ConferenceControl.getAccessLevel(testConference , testUser));
		
		System.out.println("The access level for the wrong user is: " 
				+ ConferenceControl.getAccessLevel(testConference , new User(16, null, null, null, null, null, null)));
		
	}
	

	private static void test2() throws ClassNotFoundException {
		System.out.println("The conferences:" + ConferenceControl.getConferences());
		System.out.println("The 3rd conference: " + ConferenceControl.getConferenceByID(3));
	}
	
	private static void test3() throws ClassNotFoundException {
		//System.out.println(UserControl.getUsers());
		
		System.out.println(UserControl.authenticate("kngrace", "password"));
		System.out.println(UserControl.authenticate("kngrace", "password2"));
		
//		System.out.println("ID is " +  new User("hoopla2", "banana", "kirkisajerk@space.com", 
//				"Captain", "Spock", "Enterprise"));
	}
	
	
	private static void test4() throws ClassNotFoundException, FileNotFoundException {
		
		JFrame j = new JFrame();
		JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showOpenDialog(null);
		File f = null;
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       System.out.println("You chose to open this file: " +
	            chooser.getSelectedFile().getName());
	       f = chooser.getSelectedFile();
//	       System.out.println(f.getAbsolutePath());
//	       System.out.println(f.getPath());
//	       
//	       
//	       System.out.println(System.getProperty("user.dir"));
	    }
		j.setVisible(true);
		
		User testUser = new User(3, null, null, null, 
				null, null, null);
		Conference testConference = new Conference(3, "TestDummy", testUser, Date.valueOf("2014-06-01"), Date.valueOf("2014-06-25"), 
				null, null, null, null);
		
		Manuscript m = new Manuscript(testUser, testConference, f.getName(), new File(f.getAbsolutePath()));
		
		Session s = new Session(testUser);
		
		try {
			m.unsubmit(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	

// 2+2 = 5!  (for funsies)
//	public static void main(String[] args) throws Exception {
//		Class cache = Integer.class.getDeclaredClasses()[0];
//		Field c = cache.getDeclaredField("cache");
//		c.setAccessible(true);
//		Integer[] array = (Integer[]) c.get(cache);
//		array[132] = array[133];
//
//		System.out.printf("%d",2 + 2);
//	}

	
	public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException
	{
		
		//.out.println(AccessLevel.PROGRAMCHAIR);
		//System.out.println(AccessLevel.valueOf("PROGRAMCHAIR"));

		//test1();
		test2();
		//test2();
		//test3();
		//test4();
		
		Date D = Date.valueOf("2014-1-12");
		System.out.println(D.toString());
		
/*		
		// load the sqlite-JDBC driver using the current class loader
		Class.forName("org.sqlite.JDBC");
		//this is a change
		Connection connection = null;
		try
		{
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:TestDB.sqlite");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			statement.executeUpdate("drop table if exists person");
			statement.executeUpdate("create table person (id integer, name string)");
			statement.executeUpdate("insert into person values(1, 'leo')");
			statement.executeUpdate("insert into person values(2, 'yui')");
			
		
			// "INSERT INTO users (name, email) VALUES ('" + theName + "', '" + theEmail + '");"
			
			// "INSERT INTO users (name, email) VALUES (?, ?)
			// query.value(1, theName)
			// query.value(2, theEmail)
			
			// protection from sql injection aka Bobby Tables  http://xkcd.com/327/
			
//			String selectStatement = "SELECT * FROM User WHERE userId = ? ";
//			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
//			prepStmt.setString(1, userId);
//			ResultSet rs = prepStmt.executeQuery();
		//	ArrayList listusers = new ArrayList();
			
			// "a" + "b" = "ab" 
			ResultSet rs = statement.executeQuery("select * from person");
			while(rs.next())
			{
				// read the result set
				System.out.println("name = " + rs.getString("name"));
				System.out.println("id = " + rs.getInt("id"));
				
			//	listusers.add(new User(rs.getString("name"), rs.getInt("id")));
			}
		}
		catch(SQLException e)
		{
			// if the error message is "out of memory", 
			// it probably means no database file is found
			System.err.println(e.getMessage());
		}
		finally
		{
			try
			{
				if(connection != null)
					connection.close();
			}
			catch(SQLException e)
			{
				// connection close failed.
				System.err.println(e);
			}
		} */
	}
	
}




//
//if (has_review_access == true) {
//	//do the command
//	hasReviewAccess = rs.getBoolean("has_review_access");
//	
//	
//}
//
//accessLevel = rs.getInt("access_level");
//
//public int getAccessLevel(User myUser, Conference myConference){
//	return Math.max(rs.getInt("access_level"), 0);
//}
//
//int accessLevel = getAccessLevel(theUser, theConference);
//accessLevel 2
//
//if (accessLevel > 0) {
//	// show author gui
//}
//if (accessLevel > 1) {
//	// show reviewer gui
//}
//
//
//0 = author
//1 = reviewer
//2 = spc
//3 = pc
//9 = superuser


//
//-- insert into conferences ( name, program_chair ) values 
//-- ( 'The Apple of Knowledge', 3 );
//-- select last_insert_rowid() ;
//-- select * from conferences where id=last_insert_rowid();
//-- select * from users left join conferences on users.id=conferences.program_chair
//-- delete from conferences where name like '%travel%';
//
