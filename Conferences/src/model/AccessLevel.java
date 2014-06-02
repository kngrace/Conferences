/**
 * TCSS 360 Software Development and Quality Assurance
 * Conferences Project - Group 3
 */

package model;

import java.util.Comparator;

/**
 * AccessLevel is an Enum to enumerate the hierarchy of access levels
 * particular Users have for particular Conferences. The access levels
 * vary in number between integers 0 and 2 but have been described here
 * as enumerations for code readability. 
 * @author Trevor Jennings
 * @version 2 June 2014
 */
public enum AccessLevel implements Comparator<AccessLevel> {
	
	/**
	 * Enum "constructors" assign integer values to each enumeration
	 */
	AUTHOR(0), REVIEWER(1), SUBPROGRAMCHAIR(2), PROGRAMCHAIR(3);
	
	/**
	 * The enumeration's integer value.
	 */
	private int myValue;
	
	/**
	 * Constructor creates new AccessLevel with integer value being
	 * passed as parameter. Will not likely need to be used for
	 * the Conferences project.
	 * @param theValue the enum's value.
	 */
	private AccessLevel(int theValue) {
		myValue = theValue;
	}
	
	public int getValue() {
		return myValue;
	}
	
	/**
	 * AccessLevel implements Comparable interface. Compare's two
	 * enum's integer values, returning the difference between the
	 * first and second parameters.
	 * @param al1 the first AccessLevel to be compared.
	 * @param al2 the second AccessLevel to be compared.
	 * @return the difference between the two parameter's values.
	 */
	public int compare(AccessLevel al1, AccessLevel al2) {
		return al1.myValue - al2.myValue;
	}
    
	/**
	 * Returns the AccessLevel of the passed integer value. If the integer
	 * value does not match an existing AccessLevel, the lowest AccessLevel
	 * (integer value 0, AccessLevel.AUTHOR) is returned.
	 * @param theValue the integer value of an existing AccessLevel.
	 * @return the AccessLevel of the passed integer value.
	 */
	public static AccessLevel accessLevelOf(int theValue) {
		switch (theValue) {
		    case 0 : return AccessLevel.AUTHOR;
		    case 1 : return AccessLevel.REVIEWER;
		    case 2 : return AccessLevel.SUBPROGRAMCHAIR;
		    case 3 : return AccessLevel.PROGRAMCHAIR;
		    default : return AccessLevel.AUTHOR;
		}
	}
	
	/**
	 * Returns the integer value of the passed AccessLevel. If the
	 * AccessLevel does not exist, the lowest integer value for
	 * AccessLevel's is returned (0). 
	 * @param theAccessLevel the AccessLevel who's integer value is 
	 * to be returned.
	 * @return the integer value of the passed AccessLevel.
	 */
	public static int getValueOf(AccessLevel theAccessLevel) {
		switch (theAccessLevel) {
		    case AUTHOR : return 0;
		    case REVIEWER : return 1;
		    case SUBPROGRAMCHAIR : return 2;
		    case PROGRAMCHAIR : return 3;
		    default : return 0;
		}
	}
}