package model;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * 
 * @author Trevor Jennings
 *
 */
public class TestManuscript {
	
	private User myUser1;
	private User myUser2;
	private Manuscript myManuscript1;
	private Conference myConference1;
	private Review myReview1;
	private Review myReview2;
	Session mySession1;
	Session mySession2;

	@Before
	public void setUp() throws Exception {
		myUser1 = new User(0001, "tjennz12", "trevorcarljennings", "tcj12@gmail.com", 
			"Trevor", "Jennings", "123 N Lane Dr, Tacoma, WA 98406");
		myUser2 = new User(0014, "jocuz1018", "jor18", "jocuz1018@gmail.com", null, 
				"Jennings", null);
		myConference1 = new Conference(0020, "Conference A", myUser1, Date.valueOf("2015-04-13"),
				Date.valueOf("2015-05-13"), Date.valueOf("2015-05-20"), 
				Date.valueOf("2015-05-22"), "Tacoma, WA", "Description for Conference1");
		myManuscript1 = new Manuscript(0012, myUser1, myConference1, "sample1.txt",
			new File("sample1.txt"), Status.APPROVED, Status.REJECTED, true);
		myReview1 = new Review(0040, myUser2, "myReview1.txt", new File("myReview1.txt"), 
				myManuscript1);
		//myUser2.setAccess(myConference1, AccessLevel.AUTHOR);
		//myUser1.setAccess(myConference1, AccessLevel.PROGRAMCHAIR);
		mySession1 = new Session(myUser1);
		mySession2 = new Session(myUser2);
		
	}

	@Test
	public void testGetFinalStatus() {
		assertEquals("Final status should be rejected.", myManuscript1.getFinalStatus(mySession1), 
				Status.REJECTED);
	}
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testGetFinalStatusException() {
		exception.expect(IllegalStateException.class);
		// mySession2 only has Author access for myConference1 which myManuscript1 is part of.
		// Thus, an exception should be thrown.
		myManuscript1.getFinalStatus(mySession2);
	}
	
	

}
