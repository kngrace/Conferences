package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class designed to model a user for our conference management system.
 * @author Eric Miller
 * @version 0.2
 * @date 5/19/14
 */
public class User {

	/**
	 * User's unique identifier.
	 */
	private int my_id;
	
	/**
	 * User's full name.
	 */
	private String my_name;

	/**
	 * User's primary e-mail.
	 */
	private String my_email;
	
	/**
	 * User's home address.
	 */
	private String my_address;
	
	/**
	 * User's assigned conferences.
	 */
	private List<Conference> my_conferences;
	
	/**
	 * User's access level to each conference.
	 */
	private Map<Conference, List<AccessLevel>> my_access;
	
	/**
	 * Instantiate the user with all required fields pre-filled.
	 * @param the_id Unique identifier for this specific user.
	 * @param the_email User's primary e-mail address.
	 * @param the_name User's full name.
	 * @param the_address User's home address.
	 */
	public User(final int the_id, final String the_email, final String the_name, final String the_address) {
		my_id = the_id;
		my_email = the_email;
		my_name = the_name;
		my_address = the_address;
		
		// Create empty structures for conferences and access to be filled later.
		my_conferences = new ArrayList<Conference>();
		my_access = new HashMap<Conference, List<AccessLevel>>();
	}
	
	/**
	 * Assigns the user an access level for a given conference. If the conference is not
	 * in the user's conference list, it is added.
	 * @param the_con Conference to assign privileges to.
	 * @param the_level The access level to this conference that the user will gain.
	 */
	public void setAccess(final Conference the_con, final AccessLevel the_level) {	
		if (!my_conferences.contains(the_con)) {
			my_conferences.add(the_con);
			
			List<AccessLevel> arr = new ArrayList<AccessLevel>();
			arr.add(the_level);
			my_access.put(the_con, arr);
		} else {
			List<AccessLevel> prev = my_access.get(the_con);
			
			List<AccessLevel> arr = new ArrayList<AccessLevel>();
			arr.addAll(prev);
			arr.add(the_level);
			my_access.remove(the_con);
			my_access.put(the_con, arr);
		}
	}
	
	/**
	 * Removes one given level of access from the user for a given conference.
	 * If the user only has one responsibility, the entire conference is removed
	 * from their list.
	 * @param the_con The conference to edit.
	 * @param the_level The access level to be removed.
	 */
	public void removeAccess(final Conference the_con, final AccessLevel the_level) {
		if (!my_conferences.contains(the_con)) {
			//If the user is not assigned to the conference to begin with, we have nothing to do here.
		} else if(my_access.get(the_con).size() <= 1) {
			//If the user only has one assigned duty to the conference, remove the conference from
			//their list entirely.
			removeAllAccess(the_con);
		} else {
			List<AccessLevel> prev = my_access.get(the_con);		
			prev.remove(prev.indexOf(the_level));
			my_access.remove(the_con);
			my_access.put(the_con, prev);
			
		}
	}
	
	/**
	 * Strips the user of all access to a given conference.
	 * @param the_con Conference to remove.
	 */
	public void removeAllAccess(final Conference the_con) {
		my_conferences.remove(the_con);
		my_access.remove(the_con);
	}
	
	/**
	 * 'Getter' class to retrieve the user's unique identifier.
	 * @return An int that uniquely identifies the user.
	 */
	public int getId() {
		return my_id;
	}
	
	/**
	 * 'Getter' class to retrieve the user's full name.
	 * @return User's full name. Format: "First (Middle) Last."
	 */
	public String getName() {
		return my_name;
	}
	
	/**
	 * 'Getter' class to retrieve the user's primary e-mail address.
	 * @return User's given e-mail address. Format: "email@domain.ext."
	 */
	public String getEmail() {
		return my_email;
	}
	
	/**
	 * 'Getter' class to retrieve the user's home address.
	 * @return User's home address.
	 */
	public String getAddress() {
		return my_address;
	}
	
	/**
	 * User for testing by the TestUser class, Author: Nikhila Potharaj
	 */
	public boolean isAccessConferences(final Conference the_con, final AccessLevel the_level) {
		if(my_access.containsKey(the_con)) {
			return true;
		} else {
			return false; 
		}
	}
}