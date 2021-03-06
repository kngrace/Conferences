package model;

import java.io.File;

import control.ManuscriptControl;

/**
 * Model for the manuscript reviews.
 * @author Eric Miller
 * @version 0.5
 */
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
	
	/**
	 * Construct a review without an ID known.
	 * @param theReviewer User creating the review.
	 * @param theFileName The name of the file for the review.
	 * @param theFile The physical file being used as the review.
	 * @param theManuscript The Manuscript tied to this Review.
	 */
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
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Review by ");
		sb.append(myReviewer);
		sb.append(" for ");
		sb.append(myManuscript);
		return sb.toString();
	}
	
	public boolean equals(final Object o) {
		boolean equal = false;
		if (o instanceof Review) {
			Review r = (Review) o;
			if (r.getID() == myID) {
				equal = true;
			}
		}
		return equal;
	}
	
	public int hashCode() {
		return (myID * 33) + 411;
	}
}
