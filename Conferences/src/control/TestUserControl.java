package control;

import static org.junit.Assert.assertEquals;
import model.User;

import org.junit.Before;
import org.junit.Test;

/**
 * Test the UserControl methods.
 * 
 * @author Kirsten Grace
 * @version 5.28.14
 *
 */

public class TestUserControl {

	/** User object for testing. */
	private User myUser;
	
	@Before
	public void setUp() {
		
		myUser = new User("testUser", "password", "email", "first", "last", "address");
		
	}
	
	@Test
	public void testAuthenticate() {
		
		assertEquals("Password check failed.", myUser, 
				UserControl.authenticate("testUser", "password"));
		
	}
	
	
}
