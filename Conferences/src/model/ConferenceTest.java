package model;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for class Constructor. 
 * @author Trevor Jennings
 * @version 2 June 2014
 */

public class ConferenceTest {
	
	private Conference myConference1;
	private Conference myConference2;
	private User myUser1;
	private User myUser2;
	private Session mySession1;
	private Session mySession2;

	@Before
	public void setUp() throws Exception {
		myUser1 = new User(0012, "tjennz12", "xyz", "tcj12@uw.edu", "Trevor", "Jennings",
				"123 North Lane Drive, Tacoma, WA 98406");
		myUser2 = new User(0014, "jocuz1018", "jor18", "jocuz1018@gmail.com", null, 
				"Jennings", null);
		mySession1 = new Session(myUser1);
		mySession2 = new Session(myUser2);
		myConference1 = new Conference(0013, "Conference A", myUser1, Date.valueOf("2015-04-13"),
				Date.valueOf("2015-05-13"), Date.valueOf("2015-05-20"), 
				Date.valueOf("2015-05-22"), "Tacoma, WA", "Description for Conference1");
		myUser1.setAccess(myConference1, AccessLevel.PROGRAMCHAIR);
		myUser2.setAccess(myConference1, AccessLevel.REVIEWER);
		myUser2.setAccess(myConference2, AccessLevel.PROGRAMCHAIR);
		
		
		myConference2 = new Conference("Conference B", myUser2, Date.valueOf("2015-08-13"),
				Date.valueOf("2015-08-13"), Date.valueOf("2015-08-20"), 
				Date.valueOf("2015-08-22"), "Tacoma, WA", "Description for Conference 2");
				
		
	}

	@Test
	public void testSetName() {
	     String newName = "This is Conference A's new name.";
	     // Should be allowed to change as this Session has this access.
	     boolean setNameSuccess = myConference1.setName(mySession1, newName);
	     assertEquals("Name should be changed.", myConference1.getName(), 
	    		 "This is Conference A's new name.");	  
	     assertTrue("Name change should have been successful.", setNameSuccess);
	     setNameSuccess = myConference1.setName(mySession2, 
	    		 "This should NOT be Conference A's new name.");
	     assertEquals("Name should NOT have been changed.", myConference1.getName(), 
	    		 "This is Conference A's new name.");
	     assertFalse("Name should NOT have been changed.", setNameSuccess);    
	}
	
	@Test
	public void testSetDescription() {
		String description1 = "This is the changed description.";
		String description2 = "Description should NOT be changed to this.";
		boolean setDescriptionSuccess = myConference1.setDescription(mySession1, 
				"This is the changed description.");
		assertEquals("Description should be changed.", description1, myConference1.getDescription());
		assertTrue("Description change should have been successful.", setDescriptionSuccess);
		setDescriptionSuccess = myConference1.setDescription(mySession2, description2);
		assertEquals("Description should not have been changed.", description1, 
				myConference1.getDescription());
		assertFalse("Description change should not have been successful.", setDescriptionSuccess);		
	}
	
	@Test
	public void testSetPaperStart() {
		Date paperStart1 = Date.valueOf("2015-04-14");
		Date paperStart2 = Date.valueOf("2015-04-19");
		boolean setPaperStartSuccess = myConference1.setPaperStart(mySession1, paperStart1);
		assertEquals("Paper start should be changed.", paperStart1, myConference1.getPaperStart());
		assertTrue("Paper Start change should have been successful.", setPaperStartSuccess);
		setPaperStartSuccess = myConference1.setPaperStart(mySession2, paperStart2);
		assertEquals("Paper start should NOT be changed.", paperStart1, myConference1.getPaperStart());
		assertFalse("Paper Start change should NOT have been successful.", setPaperStartSuccess);
	}
	
	@Test
	public void testSetPaperEnd() {
		Date paperEnd1 = Date.valueOf("2015-05-14");
		Date paperEnd2 = Date.valueOf("2015-05-19");
		boolean setPaperEndSuccess = myConference1.setPaperEnd(mySession1, paperEnd1);
		assertEquals("Paper End should be changed.", paperEnd1, myConference1.getPaperStart());
		assertTrue("Paper End change should have been successful.", setPaperEndSuccess);
		setPaperEndSuccess = myConference1.setPaperEnd(mySession2, paperEnd2);
		assertEquals("Paper End should NOT be changed.", paperEnd1, myConference1.getPaperStart());
		assertFalse("Paper End change should NOT have been successful.", setPaperEndSuccess);
	}
	
	@Test
	public void testSetConferenceStart() {
		Date conferenceStart1 = Date.valueOf("2015-05-25");
		Date conferenceStart2 = Date.valueOf("2015-05-26");
		boolean setConferenceStartSuccess = myConference1.setConferenceStart(mySession1, conferenceStart1);
		assertEquals("Conference start should be changed.", conferenceStart1, 
				myConference1.getConferenceStart());
		assertTrue("Conference Start change should have been successful.", setConferenceStartSuccess);
		setConferenceStartSuccess = myConference1.setConferenceStart(mySession2, conferenceStart2);
		assertEquals("Conference start should NOT be changed.", conferenceStart1, 
				myConference1.getConferenceStart());
		assertFalse("Conference Start change should NOT have been successful.", setConferenceStartSuccess);
	}
	
	@Test
	public void testSetConferenceEnd() {
		Date conferenceEnd1 = Date.valueOf("2015-05-30");
		Date conferenceEnd2 = Date.valueOf("2015-05-31");
		boolean setConferenceEndSuccess = myConference1.setConferenceEnd(mySession1, 
				conferenceEnd1);
		assertEquals("Conference End should be changed.", conferenceEnd1, myConference1.getConferenceEnd());
		assertTrue("Conference End change should have been successful.", setConferenceEndSuccess);
		setConferenceEndSuccess = myConference1.setConferenceEnd(mySession2, conferenceEnd2);
		assertEquals("Conference End should NOT be changed.", conferenceEnd1, 
				myConference1.getConferenceEnd());
		assertFalse("Conference End change should NOT have been successful.", setConferenceEndSuccess);
	}
	
	@Test
	public void testSetLocation() {
		String location1 = "This is the changed location.";
		String location2 = "Location should NOT be changed to this.";
		boolean setLocationSuccess = myConference1.setLocation(mySession1, location1);
		assertEquals("Description should be changed.", location1, myConference1.getLocation());
		assertTrue("Description change should have been successful.", setLocationSuccess);
		setLocationSuccess = myConference1.setLocation(mySession2, location2);
		assertEquals("Description should NOT have been changed.", location1, 
				myConference1.getLocation());
		assertFalse("Description change should not have been successful.", setLocationSuccess);		
	}
	
}
