package view;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.Session;
import model.User;
import control.UserControl;


/**
 * Represents the main log-in screen. 
 * 
 * @author Nikhila and Marilyn
 * @version 06.06.2014
 *
 */
public class Login {

	/**
	 * The main window in which the system runs. 
	 */
	private JFrame frame;
	
	/**
	 * The user name text field to be authenticated
	 */
	private JTextField usernameField;
	
	/**
	 * The password to be authenticated
	 */
	private JPasswordField passwordField;
	
	/**
	 * Banner at the top of the window. 
	 */
	private JPanel banner;
	
	/**
	 * Panel for all the components. 
	 */
	private JPanel panel;
	
	/**
	 * The message label for wrong authentication.
	 */
	private JLabel warninglabel;
	
	/**
	 * The username used for authentication.
	 */
	private String username;
	
	/**
	 * password used for authentication.
	 */
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
		frame.setPreferredSize(new Dimension(706, 486));
		frame.setBounds(100, 100, 715, 486);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		frame.setBackground(Color.ORANGE);
		panel = new JPanel();
		panel.setBackground(Color.ORANGE);
		frame.getContentPane().add(panel, "name_1668062565993978");
		panel.setLayout(null);
		
		panel.setBackground(Color.ORANGE);
		
		//Login submit button which authorizes the user's username and password
		JButton loginSubmit = new JButton("Submit");
		loginSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(username + " " + password);
				User test = UserControl.authenticate(username, password);
				if(test == null) {
					warninglabel = new JLabel("The username or password is incorrect please try again!");
					warninglabel.setForeground(Color.RED);
		          	warninglabel.setBounds(227, 128, 322, 14);
		          	panel.add(warninglabel);
		        
		          	frame.getContentPane().add(panel);
				  	frame.repaint();
				  } else {
				  	frame.remove(panel);
				  	Session newUser = new Session(test);
				  	final UserHome home = new UserHome(newUser);

				  	
				  	//Logout button which comes back to the log in page. 
					JButton logoutButton = new JButton("Logout");
					logoutButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Login log = new Login();
							JPanel login = log.getPanel();
							frame.remove(home.getPanel());
							frame.getContentPane().add(panel);
							panel.repaint();
							frame.repaint();
						}
					});
					logoutButton.setBounds(20, 350, 103, 23);
					home.getPanel().add(logoutButton);
					logoutButton.validate();
					frame.getContentPane().add(home.getPanel());
					home.getPanel().validate();
				  	frame.repaint();
				  	frame.setVisible(true);
				  }
			
			}

		});
		loginSubmit.setBounds(240, 233, 89, 23);
		panel.add(loginSubmit);
		
		//If user is not in database fills out information and then comes back to login screen
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
		
		//Listens to the text field for username. 
		usernameField = new JTextField();
		usernameField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				//do nothing
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				username = usernameField.getText();
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
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
		
		//Listens to password field 
		passwordField = new JPasswordField();
		passwordField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				// do nothing
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				password = new String(passwordField.getPassword());		
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				password = new String(passwordField.getPassword());	
			}
		});
		passwordField.setBounds(328, 184, 112, 20);
		panel.add(passwordField);
		
		
		//Banner setup. (Kirsten edited this banner)
		banner = new JPanel();
		banner.setBackground(new Color(130, 75, 207));
		banner.setBounds(0, 0, 715, 82);
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("TCSBanner2.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		banner.add(picLabel);
		panel.add(banner);
		
	}


	/**
	 * Panel for the log-in window components. 
	 * 
	 * @return panel
	 */
	public JPanel getPanel() {
		return panel;
	}
}
