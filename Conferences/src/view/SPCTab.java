package view;

import java.awt.Color;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import control.ManuscriptControl;
import model.Conference;
import model.Manuscript;
import model.Review;
import model.Session;
import model.User;


public class SPCTab {

	private JFrame frame;
	
	private JPanel panel;
	
	private Conference my_conference;
	
	private Session my_session;

	/**
	 * Create the application.
	 */
	public SPCTab(Conference the_conf, Session the_sess) {
		my_conference = the_conf;
		my_session = the_sess;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 554, 359);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.ORANGE);
		panel.setBounds(0, 0, 536, 318);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		//TODO Needs to be updated !!!!
		List<Manuscript> list = ManuscriptControl.getManuscripts(my_conference, my_session.getCurrentUser());
		
		for(Manuscript m: list) { 
		JLabel title = new JLabel("Title: " + m.getFile().getName());
		title.setBounds(10, 11, 318, 14);
		panel.add(title);
		
		JButton download = new JButton("Download");
		download.setBounds(343, 7, 150, 23);
		panel.add(download);
		
		List<User> r = m.getReviewers(my_session);
		
		String concat = "Reviewers: ";
		for(User u: r) {
			concat = concat + u.getFirstName() + " " + u.getLastName();
		}
		if(r == null || r.isEmpty()) {
			concat = concat + "NONE";
		}
		JLabel reviewer = new JLabel(concat);
		reviewer.setBounds(10, 36, 318, 14);
		panel.add(reviewer);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(343, 33, 150, 20);
		panel.add(comboBox_1);
		
		JLabel reviews = new JLabel("Reviews: ");
		reviews.setBounds(10, 61, 318, 14);
		panel.add(reviews);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(343, 58, 150, 20);
		panel.add(comboBox);
		
		JLabel label = new JLabel("Title: ");
		label.setBounds(10, 93, 318, 14);
		panel.add(label);
		
		JButton button = new JButton("Download");
		button.setBounds(343, 89, 150, 23);
		panel.add(button);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(343, 115, 150, 20);
		panel.add(comboBox_2);
		
		JLabel label_1 = new JLabel("Reviewers:");
		label_1.setBounds(10, 118, 318, 14);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("Reviews: ");
		label_2.setBounds(10, 143, 318, 14);
		panel.add(label_2);
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setBounds(343, 140, 150, 20);
		panel.add(comboBox_3);
		}
	}
	
	public JPanel getPanel() {
		return panel; 
	}

}
