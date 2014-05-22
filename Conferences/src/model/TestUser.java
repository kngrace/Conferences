/**
 * 
 */
package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author pothnik
 * 
 */
public class TestUser {
	
	/**
	 * User to be tested
	 */
	private User myUser;
	
	private Conference myConference;

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
	 * Testing Remove Access in the User class
	 */
	@Test
	public void testRemoveAccess() {
		
	}

}
