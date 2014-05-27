
package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author pothnik
 * @version 1
 * 
 * Testing class for User
 */
public class TestUser {
	
	/**
	 * User to be tested
	 */
	private User myUser;
	
	/**
	 * Conferecence to use in testing 
	 */
	private Conference myConference;
	
	/**
	 * AccessLevel enumeration for testing 
	 */
	private AccessLevel myAccessLevel;

	/**
	 * Sets up the test fixture
	 */
	@Before
	public void setUp() {
		myUser = new User(1234, "email", "name", "address");
		
		myConference = new Conference();
	}

	/**
	 * Author: Nikhila Potharaj
	 * Testing Remove Access method in the User class
	 */
	@Test
	public void testRemoveAccess() {
		setAccess(myConference, AccessLevel.AUTHOR);
		
		//Tests if given access level is removed
		assertTrue("Working with Access", isAccessConference(myConference, AccessLevel.AUTHOR));
		
		//Tests if an access level not included for the user
		assertTrue("Working No Access", isAccessConference(myConference, AccessLevel.REVIEWER));
		
		setAccess(myConference, AccessLevel.AUTHOR);
		setAccess(myConference, AccessLevel.Reviewer);
		
		//Tests if all the accesses are removed if there is more than one
		assertTrue("Working All Access", isAccessConference(myConference, AccessLevel.REVIEWER));
	}

}
