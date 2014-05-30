package model;

public class Date implements Comparable<Date> {
	
	private int myYear;
	private int myMonth;
	private int myDay;
	private int myHour;
	private int myMinute;
	
	public Date(int theYear, int theMonth, int theDay, int theHour, int theMinute) {
		myYear = theYear;
		myMonth = theMonth;
		myDay = theDay;
		myHour = theHour;
		myMinute = theMinute;
	}
	
	public int compareTo(Date other) {
		if (this.equals(other)) {
			return 0;
		} else {
			return this.inMinutes() - other.inMinutes();
		}
	}
	
	/**
	 * Helper method returns DateTime converted to minutes assuming the "beginning of time"
	 * has all fields of 0. Assumes 60 minutes/hour, 24 hours/day, 31 days/month and 12 months/year.
	 * @return DateTime in minutes.
	 */
	private int inMinutes() {
		return myYear * 535680 + myMonth * 44640 + myDay * 1440 + myHour * 60 + myMinute;
	}

}
