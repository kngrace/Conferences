package control;

import static org.junit.Assert.*;

import java.sql.Date;

import model.Conference;

import org.junit.Before;
import org.junit.Test;

public class TestConferenceControl {

	private Conference testConference;

	@Before
	public void setUp() throws Exception {
		
		testConference = new Conference("Conference Control Tester", UserControl.getUserByID(1), Date.valueOf("2015-04-13"),
				Date.valueOf("2015-05-13"), Date.valueOf("2015-05-20"), 
				Date.valueOf("2015-05-22"), "Tacoma, WA", "Description for Conference Control Tester");
	}

	@Test
	public void testCreateConference() {
	assertSame("Conference did not get added to database.", testConference, ConferenceControl.getConferenceByID(
			testConference.getId()));
	assertTrue("Conference did not get added to database.", testConference.getId() != -1);
	}
//
//	@Test
//	public void testUpdateConference() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetConferences() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetConferencesUser() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetConferencesUserAccessLevel() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSearchConferences() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetConferenceByID() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetAccessLevel() {
//		fail("Not yet implemented");
//	}

}
