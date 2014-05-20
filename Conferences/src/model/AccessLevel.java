package model;
import java.util.Comparator;

public enum AccessLevel implements Comparator<AccessLevel> {
	AUTHOR(0), REVIEWER(0), SUBPROGRAMCHAIR(1), PROGRAMCHAIR(2);
	private int value;
	private AccessLevel(int theValue) {
		value = theValue;
	}
	public int compare(AccessLevel al1, AccessLevel al2) {
		return al1.value - al2.value;
	}
}
