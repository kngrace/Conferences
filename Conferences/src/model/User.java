package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class designed to model a user for our conference management system.
 * @author Eric Miller
 * @version 0.3
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
	private String my_firstname;

	/**
	 * User's full name.
	 */
	private String my_lastname;
	
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
	private Map<Conference, AccessLevel> my_access;
	
	/**
	 * Instantiate the user with all required fields pre-filled.
	 * @param the_id Unique identifier for this specific user.
	 * @param the_email User's primary e-mail address.
	 * @param the_name User's full name.
	 * @param the_address User's home address.
	 */
	public User(final int the_id, final String the_email, final String the_first, final String the_last, final String the_address) {
		my_id = the_id;
		my_email = the_email;
		my_firstname = the_first;
		my_lastname = the_last;
		my_address = the_address;
		
		// Create empty structures for conferences and access to be filled later.
		my_conferences = new ArrayList<Conference>();    // = null;
		my_access = new HashMap<Conference, AccessLevel>();  // = null;
	}
	
	/**
	 * Assigns the user an access level for a given conference. If the conference is not
	 * in the user's conference list, it is added.
	 * @param the_con Conference to assign privileges to.
	 * @param the_level The access level to this conference that the user will gain.
	 */
	public void setAccess(final Conference the_con, final AccessLevel the_level) {	
		if (!my_conferences.contains(the_con)) { // if you're updating, does it matter if you check?
			my_conferences.add(the_con);
			my_access.put(the_con, the_level);
		} else {
			my_access.remove(the_con);  // do you have to remove first??
			my_access.put(the_con, the_level);
		}
		// Once you update, you need to update the database too. we need to come up with something here...
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
			return;
		} else {
			//If the user only has one assigned duty to the conference, remove the conference from
			//their list entirely.
			my_conferences.remove(the_con);
			my_access.remove(the_con);
		}
		
		// same as updating, need to update database..
	}
	
	/**
	 * 'Getter' class to retrieve the user's unique identifier.
	 * @return An int that uniquely identifies the user.
	 */
	public int getId() {
		return my_id;
	}
	
	/**
	 * 'Getter' class to retrieve the user's first name.
	 * @return User's first name.
	 */
	public String getFirstName() {
		return my_firstname;
	}
	
	/**
	 * 'Getter' class to retrieve the user's last name.
	 * @return User's last name.
	 */
	public String getLastName() {
		return my_lastname;
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
	
	public List<Conference> getConferences() {
		return my_conferences;  // check if conferences is null, if so - request from ConferenceControl via 
		// getConferences(User)  (so it'd be getConferences(This) )
	}
	
	public void setFirstName(final String the_name) {
		my_firstname = the_name;
		//update database
	}
	
	public void setLastName(final String the_name) {
		my_firstname = the_name;
		//update database
	}
	
	public void setEmail(final String the_mail) {
		my_email = the_mail;
		//update database
	}
	
	public void setAddress(final String the_address) {
		my_address = the_address;
		//update database
	}
}
