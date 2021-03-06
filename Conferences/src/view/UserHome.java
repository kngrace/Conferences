package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import model.Session;

/**
 * Represents the home page for a user. 
 * 
 * @author Nikhila Potharaj
 * @version 06.06.2014
 */
public class UserHome {

	/**
	 * Main panel containing all of the borders, navigation and the center panel
	 */
	private JPanel panel;

	/**
	 * Session with the current user.
	 */
	private Session session;

	/**
	 * Panel for repainting 
	 */
	private JPanel panel_3;

	/**
	 * The panel for submissions button.
	 */
	private JPanel sub_panel;

	/**
	 * Panel for conferences button.
	 */
	private JPanel conf_panel;

	/**
	 * Panel for all conferences button.
	 */
	private JPanel confs_panel;

	/**
	 * Creates the class.
	 * 
	 * @param the_session to be assigned to session
	 */
	public UserHome(Session the_session) {
		session = the_session;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		panel = new JPanel();
		panel.setBackground(Color.ORANGE);
		panel.setBounds(0, 0, 715, 500);
		panel.setLayout(null);

		panel.setBackground(Color.ORANGE);

		//Top border of the panel.
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 83, 148, 400);
		panel.add(panel_1);
		panel_1.setBackground(new Color(176, 196, 222));
		panel_1.setLayout(null);

		//Navigation on the left side of the window. 
		JLabel lblNavigation = new JLabel("Navigation");
		lblNavigation.setHorizontalAlignment(SwingConstants.CENTER);
		lblNavigation.setFont(new Font("Book Antiqua", Font.PLAIN, 17));
		lblNavigation.setBounds(10, 11, 128, 23);
		panel_1.add(lblNavigation);

		//Submissions button which shows conferences to which papers were submitted. 
		JButton btnSubmissions = new JButton("Submissions");
		
		btnSubmissions.setIcon(new ImageIcon("C:\\Users\\pothnik\\Pictures\\leaf.jpg"));
		btnSubmissions.setBounds(10, 59, 128, 23);
		btnSubmissions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Working");
				panel_3.removeAll();
				panel_3.setLayout(null);
				MySubmissions mySub = new MySubmissions(session);
				sub_panel = mySub.getPanel();
				panel_3.add(sub_panel);
				panel_3.validate();
				panel_3.repaint();

			}
		});
		panel_1.add(btnSubmissions);

		//Opens the conferences panel where the conferences in which the user has  a role in.
		JButton confButton = new JButton("Conferences");
		confButton.setBounds(10, 93, 128, 23);
		confButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MyConferences myConf = new MyConferences(session);
				panel_3.removeAll();
				panel_3.setLayout(null);
				conf_panel = myConf.getPanel();
				panel_3.add(conf_panel);
				panel_3.validate();
				panel_3.repaint();
			}
		});
		panel_1.add(confButton);

		//All conferences button in which all of the conferences in database are displayed.
		JButton allConfButton = new JButton("All Conferences");
		allConfButton.setBounds(10, 127, 128, 23);
		allConfButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AllConferences allconf = new AllConferences(session);
				panel_3.removeAll();
				panel_3.setLayout(null);
				confs_panel = allconf.getPanel();
				panel_3.add(confs_panel);
				panel_3.validate();
				panel_3.repaint();
			}
		});
		panel_1.add(allConfButton);

		//Panel at the top. (Kirsten edited this banner)
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(130, 75, 207));
		panel_2.setBounds(0, 0, 715, 82);
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("TCSBanner2.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		panel_2.add(picLabel);
		panel.add(panel_2);

		panel_3 = new JPanel();
		panel_3.setBounds(147, 83, 600, 400);

		panel_3.setLayout(null);
		panel_3.setBackground(Color.ORANGE);
		
		
		BufferedImage myPicture2 = null;
		try {
			myPicture = ImageIO.read(new File("welcome.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JLabel picLabel2 = new JLabel(new ImageIcon(myPicture));
		panel_3.add(picLabel2);
		
		panel.add(panel_3);
		
		
	}

	/**
	 * Getter. main panel / home screen. 
	 * 
	 * @return panel
	 */
	public JPanel getPanel() {
		return panel;
	}


	/**
	 * Panel that keeps changing or repainted - center panel. 
	 * 
	 * @return panel_3
	 */
	public JPanel getPanel_3() {
		return panel_3;
	}
}
