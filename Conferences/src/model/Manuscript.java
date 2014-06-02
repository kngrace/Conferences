/**
 * TCSS 360 Software Development and Quality Assurance
 * Conferences Project - Group 3
 */

package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Observable;
import control.*;

/**
 * Manuscript stores the manuscripts to be submitted to a Conference. It stores
 * fields of a unique ID number, a User author, the conference it belongs to,
 * a file name, a file, a tag informing the User if it has been submitted,
 * it's recommendation status (given by the Sub-Program Chair), it's final 
 * recommendation status (given by the Program Chair), the Sub-Program Chair
 * this manuscript is assigned to, and a list of Reviewers to review the
 * manuscript.
 * @author Trevor Jennings
 * @version 2 June 2014
 */
public class Manuscript extends Observable {
	
	private int myID;
	private User myAuthor;
	private Conference myConference;
	private String myFileName;
	private File myFile;
	private boolean isSubmitted; // = true
	private Status myRecommendStatus; // = UNDECIDED
	private Status myFinalStatus; // = UNDECIDED
	private User mySPC;
	private List<User> myReviewers;
	
	/*
	 * DEPRECATED
	 * 
	public Manuscript(ManuscriptBuilder theManuscriptBuilder) {
		
		myID = theManuscriptBuilder.myID;
		myAuthor = theManuscriptBuilder.myAuthor;
		myConference = theManuscriptBuilder.myConference;
		myFileName = theManuscriptBuilder.myFileName;
		myFile = theManuscriptBuilder.myFile;
		isSubmitted = theManuscriptBuilder.isSubmitted;
		myRecommendStatus = theManuscriptBuilder.myRecommendStatus;
		myFinalStatus = theManuscriptBuilder.myFinalStatus;
		mySPC = theManuscriptBuilder.mySPC;
		
		// Call this control method AFTER fields have been assigned
		// to ensure proper data is saved in database.
		ManuscriptControl.createManuscript(this);
	} */
	
	/**
	 * Constructor creates Manuscript object that currently has persistent data in
	 * the database i.e. this Manuscript has been created before in a previous Session.
	 * Therefore, it accepts an ID as a parameter rather than having to create a new
	 * one.
	 * @param theID the Manuscripts unique ID number.
	 * @param theAuthor the Manuscript's User author.
	 * @param theConference the Conference this Manuscript belongs to.
	 * @param theFileName the String filename of this Manuscript.
	 * @param theFile the File for this Manuscript.
	 * @param theSPC the User Sub-Program Chair this Manuscript is assigned to.
	 */
	public Manuscript(int theID, User theAuthor, Conference theConference, 
			String theFileName, File theFile, User theSPC) {
		myID = theID;
		myAuthor = theAuthor;
		myConference = theConference;
		myFileName = theFileName;
		myFile = new File(myFileName);
		myRecommendStatus = Status.UNDECIDED;
		myFinalStatus = Status.UNDECIDED;
		mySPC = theSPC;
		isSubmitted = true;
	}
	
	/**
	 * Constructor creates Manuscript object that currently has does not have
	 * persistent data in the database i.e. this Manuscript is being created for 
	 * the first time. Therefore, an ID must be generated and assigned. This is 
	 * done by calling the createConference() method from ManuscriptControl, which
	 * returns an integer, representing the Manuscript's ID.
	 * @param theID the Manuscripts unique ID number.
	 * @param theAuthor the Manuscript's User author.
	 * @param theConference the Conference this Manuscript belongs to.
	 * @param theFileName the String filename of this Manuscript.
	 * @param theFile the File for this Manuscript.
	 * @param theSPC the User Sub-Program Chair this Manuscript is assigned to.
	 */
	public Manuscript(User theAuthor, Conference theConference, 
			String theFileName, File theFile, User theSPC) {
		myAuthor = theAuthor;
		myConference = theConference;
		myFileName = theFileName;
		myFile = new File(myFileName);
		myRecommendStatus = Status.UNDECIDED;
		myFinalStatus = Status.UNDECIDED;
		mySPC = theSPC;
		isSubmitted = true;
		// assign ID AFTER field have been initialized, so that all fields
		// get stored in the database.
		myID = ManuscriptControl.createManuscript(this);
	}
	
	public int getId() {
		return myID;
	}
	
	public User getAuthor() {
		return myAuthor;
	}
	
	public Conference getConference() {
		return myConference;
	}
	
	public File getFile() {
		return myFile;
	}
	
	public boolean isSubmitted() {
		return isSubmitted;
	}
	
	public Status getRecommendStatus() {
		return myRecommendStatus;
	}
	
	public Status getFinalStatus(Session theSession) {
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    return myFinalStatus;
		} else {
			throw new IllegalStateException("User does not have access to get final status!");
		}
	}
	
	public User getSPC(Session theSession) {
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    return mySPC;
		} else {
			throw new IllegalStateException("User does not have access to get final status!");
		}
	}
	
	public List<User> getReviewers(Session theSession) {
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    return myReviewers;
		} else {
			throw new IllegalStateException("User does not have access to get final status!");
		}
	}
	
	// Since unsubmmit() is an Author action which has the lowest access level clearance,
	// it does not need security via a Session parameter.
	public void unsubmit() {
		isSubmitted = false;
		ManuscriptControl.updateManuscript(this);
		notifyObservers();
	}
	
	public void submit() {
		try {
		    myFile = new File(myFileName);
		    isSubmitted = true;
		    ManuscriptControl.updateManuscript(this);
		    notifyObservers();
		} catch (NullPointerException npe) {
			System.out.println("File name is null!");
		} finally {}
	}
	
	/**
	 * @return A list of the Reviews for this Manuscript.
	 * ATTN: I changed this methods signature, removing the User parameter.
	 *       Class Diagram should be updated.
	 */
	public List<Review> getReviews(Session theSession) {
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    return ManuscriptControl.getReviews(this, theSession.getCurrentUser());
		} else {
			throw new IllegalStateException("User does not have access to get reviews!");
		}
	}
	
    
	public boolean addReview(Review theReview, Session theSession) {
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    if (this.getReviews(theSession).size() < 4) {
		    	ManuscriptControl.addReview(this, theReview);
		    	notifyObservers();
		    	return true;
		    } else {
		    	System.out.println("Cannot add Review. This manuscript already has 4 Reviews.");
		    	return false;
		    }
		} else {
			return false;
		}
	} 
	public void setRecommendStatus(Status theStatus, Session theSession) {
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    myRecommendStatus = theStatus;
		    ManuscriptControl.updateManuscript(this);
		    notifyObservers();
		} else {
			throw new IllegalStateException("User does not have access to recommend a manuscript!");
		}
	}
	
	public void setFinalStatus(Status theStatus, Session theSession) {
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    myFinalStatus = theStatus;
		    ManuscriptControl.updateManuscript(this);
		    notifyObservers();
		} else {
			throw new IllegalStateException("User does not have access to set final status"
					+ "to a manuscript!");
		}
	}
	
	public void assignSPC(User theSPC, Session theSession) {
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    mySPC = theSPC;
		    ManuscriptControl.updateManuscript(this);
		    notifyObservers();
		} else {
			throw new IllegalStateException("User does not have access to set subprogramchair"
					+ "to a manuscript!");
		}
	}
	
	/**
	 * Adds a Reviewer to this Manuscript.
	 * @param theReviewer the User to be added to the list as a reviewer.
	 * @return boolean on whether the addition was successful
	 * ATTN: this method must be added to class diagram. Also, new method
	 * addReviewer() added to ManuscriptControl.
	 */
	public boolean addReviewer(User theReviewer, Session theSession) {
		
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    if (myReviewers.size() < 4) {
			    ManuscriptControl.addReviewer(this, theReviewer);
			    notifyObservers();
			    return true;
		    } else {
		    	System.out.println("Attempting to add Reviewer when list is full!");
		    	return false;
		    }
		} else {
			throw new IllegalStateException("User does not have access to add Reviewer "
					+ "to Manuscript!");
		}
	}
	
	/**
	 * Private helper returns whether this Session in this Conference has a particular
	 * AccessLevel. The Conference calls the method while the Session is passed as a 
	 * parameter. 
	 * @param theAccessLevel The AccessLevel to be compared with.
	 * @param theSession The Session has a current User who has an AccessLevel with this
	 * Conference.
	 * @return true if this Session at this Conference has at least this AccessLevel, 
	 * false otherwise.
	 */
	private boolean sessionHasAccessLevelOf(AccessLevel theAccessLevel, Session theSession) {
		return ConferenceControl.getAccessLevel(this.getConference(), theSession.getCurrentUser()).
				compareTo(theAccessLevel) >= 0;
	}
	
	/* DEPRECATED
	public static class ManuscriptBuilder {
		
		private int myID;
		private User myAuthor;
		private Conference myConference;
		private String myFileName;
		private File myFile;
		private boolean isSubmitted; // = true
		private Status myRecommendStatus; // = UNDECIDED
		private Status myFinalStatus; // = UNDECIDED
		private User mySPC;
		private List<User> myReviewers;
		
		public ManuscriptBuilder(int theID, User theAuthor, String theFileName) {
			// required fields
			myID = theID;
			myAuthor = theAuthor;
		    myFile = new File(theFileName);
		}
		
		public ManuscriptBuilder conference(Conference theConference) {
			myConference = theConference;
			return this;
		}
		
		public ManuscriptBuilder isSubmitted(boolean isSubmittedP) {
			isSubmitted = isSubmittedP;
			return this;
		}
		
		public ManuscriptBuilder recommendStatus(Status theRecommendStatus) {
			myRecommendStatus = theRecommendStatus;
			return this;
		}
		
		public ManuscriptBuilder finalStatus(Status theFinalStatus) {
			myFinalStatus = theFinalStatus;
			return this;
		}
		
		public ManuscriptBuilder spc(User theSPC) {
			mySPC = theSPC;
			return this;
		}
		
		public ManuscriptBuilder reviewers(List<User> theReviewers) {
			myReviewers = theReviewers;
			return this;
		}
		public Manuscript build() {
			return new Manuscript(this);
		}
	} */

}
