/**
 * Class that implements the Singleton design pattern so that only
 * one Connection object is created for use by any of the classes
 * within the control package.
 * 
 * @author Kirsten Grace
 * @version 5.15.14
 */

package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {

	private static Connection connection = null;
	
	private JDBCConnection() {
		// don't try to create me
	}

	public static Connection getConnection(){
		if(connection == null){
			setupConnection();
		}
		return connection;
	}
	
	private static void setupConnection(){

		try {
			Class.forName("org.sqlite.JDBC");

			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:ConferenceDB.sqlite");
		}
		catch(SQLException | ClassNotFoundException e)
		{
			// if the error message is "out of memory", 
			// it probably means no database file is found
			System.err.println(e.getMessage());
		}
		// commented out because we don't want to close the connection :-P
		//		finally
		//		{
		//			try
		//			{
		//				//	if(connection != null)
		//				//		connection.close();
		//			}
		//			catch(SQLException e)
		//			{
		//				// connection close failed.
		//				System.err.println(e);
		//			}
		//
		//		}

	}


}


