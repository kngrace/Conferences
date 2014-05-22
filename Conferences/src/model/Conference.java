package model;

import java.util.Observable;
import java.util.Random;

public class Conference extends Observable {
	
	/**
	 * Conference identification number. Assigned using pseudo-random number
	 * generator.
	 */
	private int myID;
	
	/**
	 * The Conference's name.
	 */
	private String myName;
	
	/**
	 * The Conference's Program Chair.
	 */
	private User myProgramChair;
	
	/**
	 * The Conference's description.
	 */
	private String myDescription;
	
	/**
	 * The Conference's start date and time for Manuscript and Review submission.
	 */
	private DateTime myPaperStart;
	
	/**
	 * The Conference's end date and time for Manuscript and Review submission.
	 */
	private DateTime myPaperEnd;
	
	/**
	 * The Conference's start date and time.
	 */
	private DateTime myConferenceStart;
	
	/**
	 * The Conference's end date and time.
	 */
	private DateTime myConferenceEnd;
	
	/**
	 * The conference's location.
	 */
	private String myLocation;
	
	/**
	 * Constructor implements Build Pattern due to high number of fields. Inputs
	 * a ConferenceBuilder and also a Session for security. 
	 * @param theCB the conference builder (see nested class below)
	 * @param theSession Session object disallows Conference creation without 
	 * proper access level.
	 */
	public Conference(ConferenceBuilder theCB, Session theSession) {
		
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    myID = theCB.myID;
		    myName = theCB.myName;
		    myProgramChair = theCB.myProgramChair;
		    myDescription = theCB.myDescription;
		    myPaperStart = theCB.myPaperStart;
		    myPaperEnd = theCB.myPaperEnd;
		    myConferenceStart = theCB.myConferenceStart;
		    myConferenceEnd = theCB.myConferenceEnd;
		    myLocation = theCB.myLocation;
		    
		    ConferenceControl.createConference(this);    // add Conference to database
		    
		} else {
			throw new IllegalStateException("User attempting to create new Conference without"
					+ "the proper access level!");		
		}
	}
	
	public int getId() {
		return myID;
	}
		
	public String getName() {
		return myName;
	}
	
	public User getProgramChair() {
		return myProgramChair;
	}
	
	public String getDescription() {
		return myDescription;
	}
	
	public DateTime getPaperStart() {
		return myPaperStart;
	}
	
	public DateTime getPaperEnd() {
		return myPaperEnd;
	}
	
	public DateTime getConferenceStart() {
		return myConferenceStart;
	}
	
	public DateTime getConferenceEnd() {
		return myConferenceEnd;
	}
	
	public String getLocation() {
		return myLocation;
	}
	
	public AccessLevel getAccessLevel(Session theSession) {
		// the method below needs to be added to ConferenceControl
		return ConferenceControl.getAccessLevel(this, theSession.getCurrentUser());
	}
	
	public boolean setName(Session theSession, String theName) {
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    myName = theName;
		    notifyObservers();
		    return true;
		} else {
			throw new IllegalStateException("User not authorized to set names!");
			return false;
		}
	}
	
	public boolean setDescription(Session theSession, String theDescription) {
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    myDescription = theDescription;
		    notifyObservers();
		    return true;
		} else {
			throw new IllegalStateException("User not authorized to set names!");
			return false;
		}
	}
	
	public boolean setPaperStart(Session theSession, DateTime thePaperStart) {
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    myPaperStart = thePaperStart;
		    notifyObservers();
		    return true;
		} else {
			throw new IllegalStateException("User not authorized to set paper start!");
			return false;
		}
	}
	
	public boolean setPaperEnd(Session theSession, DateTime thePaperEnd) {
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    myPaperStart = thePaperEnd;
		    notifyObservers();
		    return true;
		} else {
			throw new IllegalStateException("User not authorized to set paper end!");
			return false;
		}
	}
	
	public boolean setConferenceStart(Session theSession, DateTime theConferenceStart) {
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    myConferenceStart = theConferenceStart;
		    notifyObservers();
		    return true;
		} else {
			throw new IllegalStateException("User not authorized to set conference start!");
			return false;
		}
	}
	
    public boolean setConferenceEnd(Session theSession, DateTime theConferenceEnd) {
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    myConferenceEnd = theConferenceEnd;
		    notifyObservers();
		    return true;
		} else {
			throw new IllegalStateException("User not authorized to set conference end!");
			return false;
		}
	}
    
	public boolean setLocation(Session theSession, String theLocation) {
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    myLocation = theLocation;
		    notifyObservers();
		    return true;
		} else {
			throw new IllegalStateException("User not authorized to set location!");
			return false;
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
		return this.getAccessLevel(theSession).compareTo(theAccessLevel) >= 0;
	}
	
	private static class ConferenceBuilder {
		
		private int myID;
		private String myName;
		private User myProgramChair;
		private String myDescription;
		private DateTime myPaperStart;
		private DateTime myPaperEnd;
		private DateTime myConferenceStart;
		private DateTime myConferenceEnd;
		private String myLocation;
		
		public ConferenceBuilder(int theID, String theName, User theProgramChair) {
			myID = theID;
			myName = theName;
			myProgramChair = theProgramChair;
		}
		
		public ConferenceBuilder description(String theDescription) {
			myDescription = theDescription;
			return this;
		}
		
		public ConferenceBuilder paperStart(DateTime thePaperStart) {
			myDateTime = theDateTime;
			return this;
		}
		
		public ConferenceBuilder paperEnd(DateTime thePaperEnd) {
			myPaperEnd = thePaperEnd;
			return this;
		}
		
		public ConferenceBuilder conferenceStart(DateTime theConferenceStart) {
			myConferenceStart = theConferenceStart;
			return this;
		}
		
		public ConferenceBuilder conferenceEnd(DateTime theConferenceEnd) {
			myConferenceEnd = theConferenceEnd;
			return this;
		}
		
		public ConferenceBuilder location(String theLocation) {
			myLocation = theLocation;
			return this;
		}
		
		public Conference build() {
			return new Conference(this);
		}
	}

}
