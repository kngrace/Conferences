/**
 * Class that communicates with the database to store/retrieve data.
 * 
 * @author Kirsten Grace
 * @version 5.12.14
 */

package control;

import java.util.List;

import model.User;

public class UserControl {

	
	private UserControl() {
		// Do Nothing (stop trying to create me)
	}
	
	public static Boolean updateUser(User theUser){
		return false;	
	}
	
	public static int createUser(User theUser){

		return 0;
	}
	
	public static List<User> getUsers(){
		return null;
	}
	
	public static List<User> searchUsers(String searchKey) {
		return null;
	}
	
	
}
