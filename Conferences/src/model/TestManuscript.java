package model;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Date;
import java.util.List;

import java.io.FileNotFoundException;

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
	private Manuscript myManuscript2;
	private Manuscript myManuscript3;
	private Conference myConference1;
	private Review myReview1;
	private Review myReview2;
	Session mySession1;
	Session mySession2;

	@Before
	public void setUp() throws Exception {
		myUser1 = new User(randomString(), "trevorcarljennings", "tcj12@gmail.com", 
			"Trevor", "Jennings", "123 N Lane Dr, Tacoma, WA 98406"); 
		myUser2 = new User(randomString(), "jor18", "jocuz1018@gmail.com", "Jordan", 
				"Jennings", null);
		myConference1 = new Conference("Conference A", myUser1, Date.valueOf("2015-04-13"),
				Date.valueOf("2015-05-13"), Date.valueOf("2015-05-20"), 
				Date.valueOf("2015-05-22"), "Tacoma, WA", "Description for Conference1");
		myManuscript1 = new Manuscript(myUser1, myConference1, "sample1.txt", 
				new File("sample1.txt"));
		myManuscript2 = new Manuscript(myUser2, myConference1, "sample2.txt",
				new File("sample2.txt"));
		myManuscript3 = new Manuscript(myUser2, myConference1, null, new File("sample3.txt"));
		myReview1 = new Review(myUser2, "review1.txt", new File("review1.txt"), 
				myManuscript1);
		//myUser2.setAccess(myConference1, AccessLevel.AUTHOR);
		//myUser1.setAccess(myConference1, AccessLevel.PROGRAMCHAIR);
		mySession1 = new Session(myUser1);
		mySession2 = new Session(myUser2);
		myManuscript2.submit(mySession2);
		
	}
	
	public static String randomString() {
		StringBuilder sb = new StringBuilder(10);
		for (int i = 0; i < 20; i++) {
			char c = (char) ((57 * Math.random()) + 65);
			sb.append(c);
		}
		return sb.toString();
	}
	

	@Test
	public void testGetFinalStatus() {
		myManuscript1.setFinalStatus(Status.APPROVED, mySession1);
		assertEquals("Final status should be APPROVED.", myManuscript1.getFinalStatus(mySession1), 
				Status.APPROVED);
	}
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testGetFinalStatusException() {
		exception.expect(IllegalStateException.class);
		myManuscript1.setFinalStatus(Status.REJECTED, mySession2);
		// mySession2 only has the lowest access for myConference1 which myManuscript1 is part of.
		// Thus, an exception should be thrown.
		myManuscript1.getFinalStatus(mySession2);
	}
	
	@Test
	public void testSubmit() {
		myManuscript1.setIsSubmitted(false);
		try {
		    myManuscript1.submit(mySession1);
		} catch (Exception e) {
			System.err.println("File does not exist!");
		}
		assertTrue("Manuscript should now be submitted.", myManuscript1.isSubmitted());
		exception.expect(Exception.class);
		try {
			myManuscript3.submit(mySession1);
		} catch (FileNotFoundException e) {
			System.err.println("File does not exist!");
		} catch (Exception e) {
			System.err.println("Exception was THROWN: " + mySession1.getCurrentUser() + " is not " + myManuscript3.getAuthor());
		}	
	}
	
	@Test
	public void testUnsubmit() {
		
	}
}
