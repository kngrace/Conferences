package model;

import java.io.File;

import control.ManuscriptControl;

public class Review {
	private final int myID;
	private final User myReviewer;
	private String myFileName;
	private File myFile;
	private final Manuscript myManuscript;
	
	public Review(final int theID, final User theReviewer, final String theFileName,
			final File theFile, final Manuscript theManuscript) {
		myID = theID;
		myReviewer = theReviewer;
		myFileName = theFileName;
		myFile = theFile;
		myManuscript = theManuscript;
	}
	
	public Review(final User theReviewer, final String theFileName, 
			final File theFile, final Manuscript theManuscript) {
		myReviewer = theReviewer;
		myFileName = theFileName;
		myFile = theFile;
		myManuscript = theManuscript;
		myID = ManuscriptControl.createReview(this);
	}
	
	public int getID() {
		return myID;
	}
	
	public User getReviewer() {
		return myReviewer;
	}
	
	public String getFileName() {
		return myFileName;
	}
	
	public File getFile() {
		return myFile;
	}
	
	public Manuscript getManuscript() {
		return myManuscript;
	}
	
	public void setFileName(final String theFile) {
		myFileName = theFile;
	}
	
	public void setFile(final File theFile) {
		myFile = theFile;
	}
}
