package model;

/**
 * Status enumeration gives an intuitive names to the categories of 
 * manuscript submission status. The integer values given
 * do not imply a ranking, rather are just used for convenience
 * for storage in the database.
 * @author Trevor Jennings
 * @version 3 June 2014
 */
public enum Status {
	
	APPROVED(2), UNDECIDED(1), REJECTED(0);
	
	private int myValue;
	
	private Status(int theValue) {
		myValue = theValue;
	}
	
	public int getValue() {
		return myValue;
	}
	
	public static Status getStatus(int theValue) throws Exception {
		switch (theValue) {
		    case 0 : return Status.REJECTED;
		    case 1 : return Status.UNDECIDED;
		    case 2 : return Status.APPROVED;
		    default : throw new Exception("Invalid Input!");
		}
	}
	
	public static int getValue(Status theStatus) throws Exception {
		switch (theStatus) {
		    case REJECTED : return 0;
		    case UNDECIDED : return 1;
		    case APPROVED : return 2;
		    default : throw new Exception("Invalid Input!");
		}
	}
	
}
