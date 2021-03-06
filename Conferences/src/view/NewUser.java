package view;

import java.awt.Color;
import java.awt.EventQueue;
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
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import control.UserControl;
import model.User;
/**
 * This is the NewUser class that allows
 * for a new user to take part in the 
 * program.
 * 
 * @author Nikhila and Marilyn
 * @version 05/28/2014
 *
 */
public class NewUser {

	private JFrame frame;
	private JTextField email;
	private JTextField add;
	private JTextField textField_2;
	private JTextField frstName;
	private JTextField lstName;
	private JTextField usrName;
	private JPanel panel_1;
	private JPanel panel;
	
	private String user_first;
	private String user_last;
	private String user_email;
	private String user_address;
	private String username; 
	private String password;
	private JButton submitButton;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewUser window = new NewUser();
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
	public NewUser() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.ORANGE);
		frame.setBounds(100, 100, 706, 476);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		panel_1 = new JPanel();
		panel_1.setBackground(Color.ORANGE);
		panel_1.setBounds(0, 0, 690, 437);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblPleaseFillIn = new JLabel("Please Fill in the Following Information:");
		lblPleaseFillIn.setBounds(12, 100, 275, 14);
		panel_1.add(lblPleaseFillIn);
		
		JLabel lblFirstName = new JLabel("First Name: ");
		lblFirstName.setBounds(12, 125, 69, 14);
		panel_1.add(lblFirstName);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(12, 186, 69, 14);
		panel_1.add(lblEmail);
		
		JLabel lblAddress = new JLabel("Address: ");
		lblAddress.setBounds(12, 214, 67, 14);
		panel_1.add(lblAddress);
		
		email = new JTextField();
		email.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				//do nothing
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				user_email = email.getText();
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				user_email = email.getText();
				
			}
		});
		email.setBounds(78, 183, 209, 20);
		panel_1.add(email);
		email.setColumns(10);
		
		add = new JTextField();
		add.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				//do nothing
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				user_address = add.getText();
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				user_address = add.getText();
				
			}
		});
		add.setBounds(78, 213, 209, 75);
		panel_1.add(add);
		add.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				//do nothing
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				password = textField_2.getText();
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				password = textField_2.getText();
				
			}
		});
		textField_2.setColumns(10);
		textField_2.setBounds(78, 331, 86, 20);
		panel_1.add(textField_2);
		
		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(12, 155, 69, 14);
		panel_1.add(lblLastName);
		
		frstName = new JTextField();
		frstName.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				//do nothing
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				user_first = frstName.getText();
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				user_first = frstName.getText();
				
			}
		});
		frstName.setColumns(10);
		frstName.setBounds(78, 122, 86, 20);
		panel_1.add(frstName);
		
		lstName = new JTextField();
		lstName.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				//do nothing
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				user_last = lstName.getText();
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				user_last = lstName.getText();
				
			}
		});
		lstName.setBounds(78, 152, 86, 20);
		panel_1.add(lstName);
		lstName.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(12, 299, 67, 14);
		panel_1.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 334, 67, 14);
		panel_1.add(lblPassword);
		
		usrName = new JTextField();
		usrName.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				//do nothing
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				username = usrName.getText();
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				username = usrName.getText();
				
			}
		});
		usrName.setColumns(10);
		usrName.setBounds(78, 296, 86, 20);
		panel_1.add(usrName);
		frame.getContentPane().add(panel_1);
		
		panel = new JPanel();
		panel.setBackground(new Color(130, 75, 207));
		panel.setBounds(0, 0, 715, 82);
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("TCSBanner2.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		panel.add(picLabel);
		panel_1.add(panel);
			
	
	}
	
	public ArrayList<String> getInfo() {
		ArrayList<String> info = new ArrayList<String>();
		info.add(username);
		info.add(password);
		info.add(user_email);
		info.add(user_first);
		info.add(user_last);
		info.add(user_address);
		return info; 
		
	}
	
	public JPanel getPanel_1() {
		return panel_1;
	}
	public JPanel getPanel() {
		return panel;
	}
}
