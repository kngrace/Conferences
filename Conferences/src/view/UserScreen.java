package view;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import control.ConferenceControl;
import model.AccessLevel;
import model.Conference;
import model.Session;


public class UserScreen {

	private JFrame frame;
	private JPanel AuthorTab;
	private JPanel ReviewTab;
	private JPanel SPCTab;
	private JPanel PCTab;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	
	private Conference myConference;
	private Session mySession;
	
	private JTabbedPane tabbedPane;

	/**
	 * Create the application.
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
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(176, 196, 222));
		frame.setBounds(100, 100, 699, 467);
		frame.getContentPane().setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 536, 367);
		frame.getContentPane().add(tabbedPane);
		
		JPanel DefaultTab = new JPanel();
		tabbedPane.addTab("Default ", null, new DefaultTab(myConference, mySession).getPanel(), null);
		DefaultTab.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 531, 318);
		//DefaultTab.add(panel);
		panel.setLayout(null);
		panel.add(new DefaultTab(myConference, mySession).getPanel());

		AuthorTab = new JPanel();
		AuthorTab.setBackground(new Color(135, 206, 235));
		AuthorTab.setToolTipText("");
		tabbedPane.addTab("Author", null, AuthorTab, null);
		AuthorTab.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 531, 318);
		AuthorTab.add(panel_1);
		panel_1.setLayout(null);
		
		ReviewTab = new JPanel();
		ReviewTab.setBackground(new Color(255, 255, 0));
		ReviewTab.setLayout(null);
		
		panel_2 = new JPanel();
		panel_2.setBounds(0, 0, 531, 318);
		ReviewTab.add(panel_2);
		panel_2.setLayout(null);
		
		SPCTab = new JPanel();
		SPCTab.setBackground(new Color(255, 215, 0));
		
		SPCTab.setLayout(null);
		
		panel_3 = new JPanel();
		panel_3.setBounds(0, 0, 531, 318);
		SPCTab.add(panel_3);
		panel_3.setLayout(null);
		
		PCTab = new JPanel();
		PCTab.setBackground(new Color(255, 165, 0));
		
		PCTab.setLayout(null);
		
		panel_4 = new JPanel();
		panel_4.setBounds(0, 0, 531, 318);
		PCTab.add(panel_4);
		panel_4.setLayout(null);
		
		
		AccessLevel access = ConferenceControl.getAccessLevel(myConference, mySession.getCurrentUser()); 
		System.out.println(access);
		if(access != null) {
			if(access == AccessLevel.AUTHOR) {
				//AuthorTab only
				tabbedPane.addTab("Author", null, 
						new AuthorTab(myConference, mySession).getPanel(), null);	
			} else if(access == AccessLevel.REVIEWER) {
				tabbedPane.addTab("Author", null, 
						new AuthorTab(myConference, mySession).getPanel(), null);	
				tabbedPane.addTab("Reviewer", null, ReviewTab, null);
			} else if(access == AccessLevel.SUBPROGRAMCHAIR) {
				tabbedPane.addTab("Author", null, 
						new AuthorTab(myConference, mySession).getPanel(), null);	
				tabbedPane.addTab("Reviewer", null, ReviewTab, null);
				tabbedPane.addTab("Sub-Program Chair", null, SPCTab, null);
			} else if(access == AccessLevel.PROGRAMCHAIR) {
				tabbedPane.addTab("Author", null, 
						new AuthorTab(myConference, mySession).getPanel(), null);	
				tabbedPane.addTab("Reviewer", null, ReviewTab, null);
				tabbedPane.addTab("Sub-Program Chair", null, SPCTab, null);
				tabbedPane.addTab("Program Chair", null, 
						new PCTab(myConference, mySession).getPanel(), null);
			}
		}
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public JTabbedPane getTab() {
		return tabbedPane;
	}
	
	public JPanel getAuthorTab() {
		return AuthorTab;
	}
	public JPanel getReviewTab() {
		return ReviewTab;
	}
	public JPanel getSPCTab() {
		return SPCTab;
	}
	public JPanel getPCTab() {
		return PCTab;
	}
}
