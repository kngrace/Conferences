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
 * 
 * @author pothnik
 * @version 05/28
 */
public class UserHome {

	private JFrame frame;
	private JButton btnSubmissions;
	private JButton confButton;
	private JButton allConfButton;
	private JPanel panel_1;
	private JButton logoutButton;
	private JLabel lblNewLabel;
	private JPanel panel;
	private JPanel panel_2;
	private Session session;
	private JPanel panel_3;
	
	private JPanel sub_panel;
	private JPanel conf_panel;
	private JPanel confs_panel;

	/**
	 * Create the application.
	 */
	public UserHome(Session the_session) {
		session = the_session;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.ORANGE);
		frame.setBounds(100, 100, 699, 467);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(Color.ORANGE);
		panel.setBounds(0, 0, 683, 428);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		panel.setBackground(Color.ORANGE);
		
		panel_1 = new JPanel();
		panel_1.setBounds(0, 83, 148, 345);
		panel.add(panel_1);
		panel_1.setBackground(new Color(176, 196, 222));
		panel_1.setLayout(null);
		
		JLabel lblNavigation = new JLabel("Navigation");
		lblNavigation.setHorizontalAlignment(SwingConstants.CENTER);
		lblNavigation.setFont(new Font("Book Antiqua", Font.PLAIN, 17));
		lblNavigation.setBounds(10, 11, 128, 23);
		panel_1.add(lblNavigation);
		
		btnSubmissions = new JButton("Submissions");
		btnSubmissions.setIcon(new ImageIcon("C:\\Users\\pothnik\\Pictures\\leaf.jpg"));
		btnSubmissions.setBounds(10, 59, 128, 23);
		btnSubmissions.addActionListener(new ActionListener() {
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
		panel_1.add(btnSubmissions);
		
		
		confButton = new JButton("Conferences");
		confButton.setBounds(10, 93, 128, 23);
		confButton.addActionListener(new ActionListener() {
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
		panel_1.add(confButton);
		
		allConfButton = new JButton("All Conferences");
		allConfButton.setBounds(10, 127, 128, 23);
		allConfButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
				
				AllConferences allconf = new AllConferences(session);
				panel_3.removeAll();
				panel_3.setLayout(null);
				confs_panel = allconf.getPanel_1();
				panel_3.add(confs_panel);
				panel_3.repaint();
				frame.repaint();
				
				
			}
		});
		panel_1.add(allConfButton);
		
		
		panel_2 = new JPanel();
		panel_2.setBackground(new Color(176, 196, 222));
		panel_2.setBounds(0, 0, 690, 82);
		panel.add(panel_2);
		
		panel_3 = new JPanel();
		panel_3.setBounds(147, 83, 536, 345);
		panel_3.setLayout(null);
		panel.add(panel_3);
		
		panel_3.setBackground(Color.ORANGE);
		
	}

	public JFrame getFrame() {
		return frame;
	}
	public JButton getBtnSubmissions() {
		return btnSubmissions;
	}
	public JButton getConfButton() {
		return confButton;
	}
	public JButton getAllConfButton() {
		return allConfButton;
	}
	public JPanel getPanel() {
		return panel;
	}
	public JPanel getPanel_1() {
		return panel_1;
	}
	public JButton getLogoutButton() {
		return logoutButton;
	}
	
	public JPanel getPanel_3() {
		return panel_3;
	}
}
