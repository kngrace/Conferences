package model;

/**
 * 
 * @author Trevor Jennings
 * @version 28 May 2014
 */
public class Session {
	private User myUser;
	public Session(User theUser) {
		myUser = theUser;
	}
	public void setUser(User theUser) {
		myUser = theUser;
	}
	public User getCurrentUser() {
		return myUser;
	}

}
