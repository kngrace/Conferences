package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Session;

/**
 * This is the UserHome Class.
 * UserHome is in charge of the 
 * main GUI screen that the user will
 * default to while using the program.
 * 
 * @author Nikhila and Marilyn 
 * @version 05/28
 */
public class UserHome {

	/**
	 * This is the JFrame used in the program.
	 * It is the main frame named frame.
	 */
	private JFrame frame;
	
	/**
	 * This is the JButton used in the program.
	 * It is the main button used for button 
	 * submissions.
	 */
	private JButton btnSubmissions;
	
	/**
	 * This is the JButton for the conference.
	 * It is for the user to select the conference.
	 */ 
	private JButton confButton;
	
	/**
	 * This is the JButton for selecting all of
	 * the conferences.
	 */ 
	private JButton allConfButton;
	
	/**
	 * This is the JPanel for the conference.
	 * It is for the user to see the panel.
	 */ 
	private JPanel panel_1;
	
	/**
	 * This is the JButton for the user can
	 * log out of the user case.
	 */ 
	private JButton logoutButton;
	
	/**
	 * This is the JLabel for the conference.
	 */ 
	private JLabel lblNewLabel;
	
	/**
	 * This is the JButton for the conference.
	 * It is for the user to select the conference.
	 */ 
	private JPanel panel;
	
	/**
	 * This is the JPanel for the conference.
	 * It is the second panel for the user.
	 */ 
	private JPanel panel_2;
	
	/**
	 * This is the Session object that 
	 * stores the specific session of the user.
	 */
	private Session session;
	
	/**
	 * This is the JPanel for the conference.
	 * It is the third panel available.
	 */ 
	private JPanel panel_3;
	
	/**
	 * This is the JPanel for the sub panel
	 * It exists within the panels.
	 */ 
	private JPanel sub_panel;
	
	/**
	 * This is the JPanel for the conference panel.
	 * It is the first one for the conference..
	 */
	private JPanel conf_panel;
	
	/**
	 * This is the second JPanel for the conference.
	 */ 
	private JPanel confs_panel;

	/**
	 * Creates the application.
	 * This is the UserHome Constructor, with session 
	 * object.
	 */
	public UserHome(Session the_session) {
		session = the_session;
		initialize();
	}

	/**
	 * This is the initialize class that inititializes
	 * the background color, layout type, etc.
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.ORANGE);
		frame.setBounds(100, 100, 699, 467);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		/**
		 * This is the JPanel that shows the orange color that
		 * is used throughout the program. Sets the appropriate 
		 * bounds in each.
		 */
		panel = new JPanel();
		panel.setBackground(Color.ORANGE);
		panel.setBounds(0, 0, 683, 428);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		/**
		 * This is the JPanel that shows the grey color that
		 * is used throughout the program. Sets the appropriate 
		 * bounds in each.
		 */
		panel_1 = new JPanel();
		panel_1.setBounds(0, 83, 148, 345);
		panel.add(panel_1);
		panel_1.setBackground(new Color(176, 196, 222));
		panel_1.setLayout(null);
		
		/**
		 * This is the JLabel that shows the Navigation bar. 
		 * The same label font is used throughout the program.
		 */
		JLabel lblNavigation = new JLabel("Navigation");
		lblNavigation.setHorizontalAlignment(SwingConstants.CENTER);
		lblNavigation.setFont(new Font("Book Antiqua", Font.PLAIN, 17));
		lblNavigation.setBounds(10, 11, 128, 23);
		panel_1.add(lblNavigation);
		
		/**
		 * This is the JButton that shows the Submissions for the 
		 * page. There is also an additional leaf image that is 
		 * shown for an added touch of design.
		 */
		btnSubmissions = new JButton("Submissions");
		btnSubmissions.setIcon(new ImageIcon("C:\\Users\\pothnik\\Pictures\\leaf.jpg"));
		btnSubmissions.setBounds(10, 59, 128, 23);
		btnSubmissions.addActionListener(new ActionListener() {
			
			/**
			 * This deletes the content of panel_3, and then
			 * repaints the frame.
			 */
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Working");
				panel_3.removeAll();
				panel_3.setLayout(null);
				MySubmissions mySub = new MySubmissions(session);
				sub_panel = mySub.getPanel_1();
				panel_3.add(sub_panel);
				panel_3.repaint();
				frame.repaint();
			}
		});
		
		/**
		 * This adds the button to the panel_1.
		 */
		panel_1.add(btnSubmissions);
		
		/**
		 * This is the JButton for Conferences that allows
		 * for the conferenes to be selected by the user.
		 */
		confButton = new JButton("Conferences");
		confButton.setBounds(10, 93, 128, 23);
		confButton.addActionListener(new ActionListener() {
			
			/**
			 * This is the action for the conferences to be
			 * added.
			 */
			public void actionPerformed(ActionEvent arg0) {
				
				MyConferences myConf = new MyConferences(session);
				panel_3.removeAll();
				panel_3.setLayout(null);
				conf_panel = myConf.getPanel_1();
				panel_3.add(conf_panel);
				panel_3.repaint();
				frame.repaint();
			}
		});
		
		/**
		 * This adds the button to the panel_1.
		 */
		panel_1.add(confButton);
		
		/**
		 * This is the JButton that allows for all of the 
		 * Conferences.
		 */
		allConfButton = new JButton("All Conferences");
		allConfButton.setBounds(10, 127, 128, 23);
		allConfButton.addActionListener(new ActionListener() {
			
			/**
			 * This is the actions performed for the conference
			 * button.
			 */
			public void actionPerformed(ActionEvent arg0) {
				
				/**
				 * This is the part that shows all of the
				 * available conferences.
				 */
				AllConferences allconf = new AllConferences(session);
				panel_3.removeAll();
				panel_3.setLayout(null);
				confs_panel = allconf.getPanel_1();
				panel_3.add(confs_panel);
				panel_3.repaint();
				frame.repaint();
			}
		});
		
		/**
		 * This adds the button to the panel_1.
		 */
		panel_1.add(allConfButton);
		
		/**
		 * panel_2 is a JPanel that will show the
		 * new color within the specified bounds.
		 */
		panel_2 = new JPanel();
		panel_2.setBackground(new Color(176, 196, 222));
		panel_2.setBounds(0, 0, 690, 82);
		panel.add(panel_2);
		
		/**
		 * This is the third panel used for the UserHome.
		 */
		panel_3 = new JPanel();
		panel_3.setBounds(147, 83, 536, 345);
		panel_3.setLayout(null);
		panel.add(panel_3);
	}

	/**
	 * This is the getter for getting the 
	 * frame and then returns the frame.
	 */
	public JFrame getFrame() {
		return frame;
	}
	
	/**
	 * This is the getter for getting the 
	 * submisisons and then returns the btnSubmissions..
	 */
	public JButton getBtnSubmissions() {
		return btnSubmissions;
	}
	
	/**
	 * This is the getter for getting the 
	 * Conferences and then returns the confButton.
	 */
	public JButton getConfButton() {
		return confButton;
	}
	
	/**
	 * This is the getter for getting all of the 
	 * conferences and then returns the allComfButton.
	 */
	public JButton getAllConfButton() {
		return allConfButton;
	}
	
	/**
	 * This is the getter for getting the 
	 * panel and then returns the panel.
	 */
	public JPanel getPanel() {
		return panel;
	}
	
	/**
	 * This is the getter for getting the 
	 * panel_1 and then returns the panel_1.
	 */
	public JPanel getPanel_1() {
		return panel_1;
	}
	
	/**
	 * This is the getter for getting the 
	 * logOutButton and then returns it.
	 */
	public JButton getLogoutButton() {
		return logoutButton;
	}
	
	/**
	 * This is the getter for getting the 
	 * panel_3 and then returns the it.
	 */
	public JPanel getPanel_3() {
		return panel_3;
	}
}
