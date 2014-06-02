/**
 * TCSS 360 Software Development and Quality Assurance
 * Conferences Project - Group 3
 */

package model;

import java.util.Observable;

import java.util.Random;

import control.ConferenceControl;
import java.sql.Date;

/**
 * Conferences store the Conference object's unique ID number, name, the 
 * assigned Program Chair, a description of the Conference, the start and
 * end dates of manuscript submission and the Conference itself and the 
 * location. Two constructors used depending on whether a new Conference
 * is being added TO the database or it's being recreated FROM the database
 * at the start of a new user session.
 * @author Trevor Jennings
 * @version 2 June 2014
 */

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
	private Date myPaperStart;
	
	/**
	 * The Conference's end date and time for Manuscript and Review submission.
	 */
	private Date myPaperEnd;
	
	/**
	 * The Conference's start date and time.
	 */
	private Date myConferenceStart;
	
	/**
	 * The Conference's end date and time.
	 */
	private Date myConferenceEnd;
	
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
	/*
	public Conference(ConferenceBuilder theCB, Session theSession) {
		
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
		   
	} */
	
	/**
	 * Constructor for Conference that already exists in database. For example, the Conference
	 * was created in a previous Session and has an ID as persistent date in the database.
	 * When a User logs off, then logs on again sometime later, this Conference object 
	 * needs to be recreated (Conference objects are not persistent).
	 * @param theID the Conference's ID
	 * @param theName the Conference's name
	 * @param theProgramChair the Conference's Program Chair
	 * @param thePaperStart the Conference's Date for accepting Manuscripts.
	 * @param thePaperEnd the Conference's Date for no longer accepting Manuscripts.
	 * @param theConferenceStart the Conference's start Date.
	 * @param theConferenceEnd the Conference's end Date.
	 * @param theLocation the Conference's location.
	 * @param theSession the current Session. Used for security as Users with AccessLevel
	 * less than that of a Program Chair cannot create a Conference.
	 */
	public Conference(int theID, String theName, User theProgramChair, Date thePaperStart,
			Date thePaperEnd, Date theConferenceStart, Date theConferenceEnd,
			String theLocation) {
		
			myID = theID;
			myName = theName;
			myProgramChair = theProgramChair;
			myPaperStart = thePaperStart;
			myPaperEnd = thePaperEnd;
			myConferenceStart = theConferenceStart;
			myConferenceEnd = theConferenceEnd;
			myLocation = theLocation;
	}
	
	/**
	 * Constructor for a Conference being created for the first time. In other words,
	 * there is no persistent date representing this Conference in the database. 
	 * In this case, the Constructor's ID is not passed as a parameter (it does
	 * not yet exist) but rather is generated and assigned by calling the method
	 * createConference() from the ConferenceControl class.
	 * @param theName the Conference's name
	 * @param theProgramChair the Conference's Program Chair
	 * @param thePaperStart the Conference's Date for accepting Manuscripts.
	 * @param thePaperEnd the Conference's Date for no longer accepting Manuscripts.
	 * @param theConferenceStart the Conference's start Date.
	 * @param theConferenceEnd the Conference's end Date.
	 * @param theLocation the Conference's location.
	 * @param theSession the current Session. Used for security as Users with AccessLevel
	 * less than that of a Program Chair cannot create a Conference.
	 */
	public Conference(String theName, User theProgramChair, Date thePaperStart,
			Date thePaperEnd, Date theConferenceStart, Date theConferenceEnd,
			String theLocation) {
			myName = theName;
			myProgramChair = theProgramChair;
			myPaperStart = thePaperStart;
			myPaperEnd = thePaperEnd;
			myConferenceStart = theConferenceStart;
			myConferenceEnd = theConferenceEnd;
			myLocation = theLocation;
			// assign ID AFTER fields are initialized, so they get stored in the
			// database.
			myID = ConferenceControl.createConference(this);
		
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
	
	public Date getPaperStart() {
		return myPaperStart;
	}
	
	public Date getPaperEnd() {
		return myPaperEnd;
	}
	
	public Date getConferenceStart() {
		return myConferenceStart;
	}
	
	public Date getConferenceEnd() {
		return myConferenceEnd;
	}
	
	public String getLocation() {
		return myLocation;
	}
	
	public AccessLevel getAccessLevel(Session theSession) {
		return theSession.getCurrentUser().getAccess(this);
	}
	
	public boolean setName(Session theSession, String theName) {
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    myName = theName;
		    ConferenceControl.updateConference(this);
		    notifyObservers();
		    return true;
		} else {
			return false;
		}
	}
	
	public boolean setDescription(Session theSession, String theDescription) {
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    myDescription = theDescription;
		    ConferenceControl.updateConference(this);
		    notifyObservers();
		    return true;
		} else {
			return false;
		}
	}
	
	public boolean setPaperStart(Session theSession, Date thePaperStart) {
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    myPaperStart = thePaperStart;
		    ConferenceControl.updateConference(this);
		    notifyObservers();
		    return true;
		} else {
			return false;
		}
	}
	
	public boolean setPaperEnd(Session theSession, Date thePaperEnd) {
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    myPaperStart = thePaperEnd;
		    ConferenceControl.updateConference(this);
		    notifyObservers();
		    return true;
		} else {
			return false;
		}
	}
	
	public boolean setConferenceStart(Session theSession, Date theConferenceStart) {
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    myConferenceStart = theConferenceStart;
		    ConferenceControl.updateConference(this);
		    notifyObservers();
		    return true;
		} else {
			return false;
		}
	}
	
    public boolean setConferenceEnd(Session theSession, Date theConferenceEnd) {
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    myConferenceEnd = theConferenceEnd;
		    ConferenceControl.updateConference(this);
		    notifyObservers();
		    return true;
		} else {
			return false;
		}
	}
    
	public boolean setLocation(Session theSession, String theLocation) {
		if (sessionHasAccessLevelOf(AccessLevel.PROGRAMCHAIR, theSession)) {
		    myLocation = theLocation;
		    ConferenceControl.updateConference(this);
		    notifyObservers();
		    return true;
		} else {
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
	
	/*
	private static class ConferenceBuilder {
		
		private int myID;
		private String myName;
		private User myProgramChair;
		private String myDescription;
		private Date myPaperStart;
		private Date myPaperEnd;
		private Date myConferenceStart;
		private Date myConferenceEnd;
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
		
		public ConferenceBuilder paperStart(Date thePaperStart) {
			myPaperStart = thePaperStart;
			return this;
		}
		
		public ConferenceBuilder paperEnd(Date thePaperEnd) {
			myPaperEnd = thePaperEnd;
			return this;
		}
		
		public ConferenceBuilder conferenceStart(Date theConferenceStart) {
			myConferenceStart = theConferenceStart;
			return this;
		}
		
		public ConferenceBuilder conferenceEnd(Date theConferenceEnd) {
			myConferenceEnd = theConferenceEnd;
			return this;
		}
		
		public ConferenceBuilder location(String theLocation) {
			myLocation = theLocation;
			return this;
		}
		
		public Conference build(Session theSession) {
			return new Conference(this, theSession);
		}
	} */

}
