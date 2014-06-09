package view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;

import model.AccessLevel;
import model.Conference;
import model.Session;
import control.ConferenceControl;

/**
 * The User screen for a particular conference. 
 * 
 * @author Nikhila Potharaj
 * @version 06.06.2014
 *
 */
public class UserScreen implements Observer {

	/**
	 * TODO - Placeholder for the reviewer tab inside a conference. 
	 */
	private JPanel myReviewerTab;

	/**
	 * The conference for this screen. 
	 */
	private Conference myConference;

	/**
	 * The session for the user that is currently using the system. 
	 */
	private Session mySession;

	/**
	 * The tabbedpane containing all the tabs that a user can view. 
	 */
	private JTabbedPane myTabbedPane;

	/**
	 * Create the application.
	 * 
	 * @param the_conference to be assigned to myConference
	 * @param the_session to be assigned to mySession
	 */
	public UserScreen(Conference the_conference, Session the_session) {
		myConference = the_conference;
		mySession = the_session;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		myTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		myTabbedPane.setBounds(0, 0, 564, 370);
		myTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		myReviewerTab = new JPanel();
		myReviewerTab.setBounds(0, 0, 550, 400);

		//Default tab before the user is an author. 
		myTabbedPane.addTab("Default ", null, new DefaultTab(myConference, mySession).getPanel(), null);
		
		//By the access level, the panes are either visible or they are not. 
		AccessLevel access = ConferenceControl.getAccessLevel(myConference, mySession.getCurrentUser()); 
		System.out.println(access);
		if(access != null) {
			if(access == AccessLevel.AUTHOR) { // when the user is an author
				//AuthorTab only
				myTabbedPane.addTab("Author", null, 
						new AuthorTab(myConference, mySession).getPanel(), null);	
			} else if(access == AccessLevel.REVIEWER) { // when access level is up to a reviewer
				myTabbedPane.addTab("Author", null, 
						new AuthorTab(myConference, mySession).getPanel(), null);	
				myTabbedPane.addTab("Reviewer", null, myReviewerTab, null);
			} else if(access == AccessLevel.SUBPROGRAMCHAIR) { // access level to an SPC
				myTabbedPane.addTab("Author", null, 
						new AuthorTab(myConference, mySession).getPanel(), null);	
				myTabbedPane.addTab("Reviewer", null, myReviewerTab, null);
				myTabbedPane.addTab("Sub-Program Chair", null, 
						new SPCTab(myConference, mySession).getPanel(), null);
			} else if(access == AccessLevel.PROGRAMCHAIR) { //access level of a PC
				myTabbedPane.addTab("Author", null, 
						new AuthorTab(myConference, mySession).getPanel(), null);	
				myTabbedPane.addTab("Reviewer", null, myReviewerTab, null);
				myTabbedPane.addTab("Sub-Program Chair", null, 
						new SPCTab(myConference, mySession).getPanel(), null);
				myTabbedPane.addTab("Program Chair", null, 
						new PCTab(myConference, mySession).getPanel_1(), null);
			}
		}
	}

	/**
	 * Returns the tabbed pane containing all of the tabs, back to the window. 
	 * 
	 * @return myTabbedPane
	 */
	public JTabbedPane getTab() {
		return myTabbedPane;
	}

	/**
	 * TODO still need to figure out the dynamic changes for all the tabs.
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println("changes");

	}
}
