package model;

public enum Status {
	
	APPROVED(2), UNDECIDED(1), REJECTED(0);
	
	private int myValue;
	
	private Status(int theValue) {
		myValue = theValue;
	}
	
	public int getValue() {
		return myValue;
	}
	
	public Status getStatus(int theValue) throws Exception {
		switch (theValue) {
		    case 0 : return Status.REJECTED;
		    case 1 : return Status.UNDECIDED;
		    case 2 : return Status.APPROVED;
		    default : throw new Exception("Invalid Input!");
		}
	}
	
	public int getValue(Status theStatus) throws Exception {
		switch (theStatus) {
		    case REJECTED : return 0;
		    case UNDECIDED : return 1;
		    case APPROVED : return 2;
		    default : throw new Exception("Invalid Input!");
		}
	}
	
}
