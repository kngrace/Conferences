package view;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Session;
import model.User;
import control.UserControl;


/**
 * 
 * @author Nikhila and Marilyn
 * @version 05/28/2014
 *
 */
public class Login {
	
	

	private JFrame frame;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPanel banner;
	private JPanel panel;
	private JLabel lblNewLabel;
	
	private String username;
	private String password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 706, 476);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		panel = new JPanel();
		panel.setBackground(Color.ORANGE);
		frame.getContentPane().add(panel, "name_1668062565993978");
		panel.setLayout(null);
		
		JButton loginSubmit = new JButton("Submit");
		loginSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				User test = UserControl.authenticate(username, password);
				if(test == null) {
					lblNewLabel = new JLabel("The username or password is incorrect please try again!");
					lblNewLabel.setForeground(Color.RED);
		          	lblNewLabel.setBounds(227, 128, 322, 14);
		          	panel.add(lblNewLabel);
		          	Session newUser = new Session(test);
		          	frame.getContentPane().add(panel);
				  	frame.repaint();
				  } else {
				  	frame.remove(panel);
				  	UserBorder home = new UserBorder();
				 	frame.getContentPane().add(home.getPanel());
				  	frame.repaint();
				  }
			}

		});
		loginSubmit.setBounds(240, 233, 89, 23);
		panel.add(loginSubmit);
		
		
		JButton newUserButton = new JButton("New User?");
		newUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.remove(panel);
				final NewUser user = new NewUser();
				frame.getContentPane().add(user.getPanel_1());
				JButton OkButton = new JButton("Submit Form");
				OkButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						ArrayList<String> temp = user.getInfo();
						User new_user = new User(temp.get(0), temp.get(1), temp.get(2), 
								temp.get(3), temp.get(4), temp.get(5));
						UserControl.createUser(new_user);
				
						frame.remove(user.getPanel_1());
						frame.getContentPane().add(panel);
						frame.repaint();
					}
				});
				OkButton.setBounds(78, 374, 144, 23);
				user.getPanel_1().add(OkButton);
				frame.getContentPane().add(user.getPanel_1());
				frame.repaint();
			}
		});
		newUserButton.setBounds(351, 233, 112, 23);
		panel.add(newUserButton);
		
		usernameField = new JTextField();
		usernameField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username = usernameField.getText();
			}
		});
		usernameField.setBounds(328, 153, 112, 20);
		panel.add(usernameField);
		usernameField.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(252, 156, 66, 14);
		panel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(252, 187, 66, 14);
		panel.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				password = passwordField.getPassword().toString();
			}
		});
		passwordField.setBounds(328, 184, 112, 20);
		panel.add(passwordField);
		
		banner = new JPanel();
		banner.setBackground(new Color(176, 196, 222));
		banner.setBounds(0, 0, 690, 82);
		panel.add(banner);
		
	}


	public JPanel getPanel() {
		return panel;
	}
}
