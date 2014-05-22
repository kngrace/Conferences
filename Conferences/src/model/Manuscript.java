package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Observable;

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
	
	public List<Reviewer> getReviewers(Session theSession) {
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
		notifyObservers();
	}
	
	public void submit() {
		try {
		    myFile = new File(myFileName);
		    isSubmitted = true;
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
		    return ManuscriptControl.getReviews(this);
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
			throw new IllegalStateException("User does not have access to add review!");
			return false;
		}
	}
	
	public void setRecommendStatus(Status theStatus) {
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    myRecommendStatus = theStatus;
		    notifyObservers();
		} else {
			throw new IllegalStateException("User does not have access to recommend a manuscript!");
		}
	}
	
	public void setFinalStatus(Status theStatus, Session theSession) {
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    myFinalStatus = theStatus;
		    notifyObservers();
		} else {
			throw new IllegalStateException("User does not have access to set final status"
					+ "to a manuscript!");
		}
	}
	
	public void assignSPC(User theSPC, Session theSession) {
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    mySPC = theSCP;
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
	private static boolean sessionHasAccessLevelOf(AccessLevel theAccessLevel, Session theSession) {
		return ConferenceControl.getAccessLevel(this.getConference(), theSession.getCurrentUser()).
				compareTo(theAccessLevel) >= 0;
	}
	
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
			try {
				myFile = new File(theFileName);
			} catch (FileNotFoundException fnfe) {
				System.err.println("File name input not found!");
			}
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
		public Manuscript build(Session theSession) {
			return new Manuscript(this);
		}
	}

}
