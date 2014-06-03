package model;

public enum Status {
	APPROVED(2), UNDECIDED(1), REJECTED(0);
	private int myValue;
	
	
	public Status(int theValue) {
		myValue = theValue;
	}
	
}
