package model;

public class Review {
	private int myID;
	private User myReviewer;
	private String myFileName;
	public Review(int theID, User theReviewer, String theFileName) {
		myID = theID;
		myReviewer = theReviewer;
		myFileName = theFileName;
	}
	public Review(User theReviewer, String theFileName, Session theSession) {
		myReviewer = theReviewer;
		myFileName = theFileName;
		myID = ManuscriptControl.createReview(this);
	}
	
}
