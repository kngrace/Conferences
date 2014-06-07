package view;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
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
 * 
 * This is the AddConference Class.
 * This class is the panel that displays the 
 * option for a user to add a conference
 *
 * 
 * @author Nikhila and Marilyn
 * @version 05/28/2014
 *
 */
public class AddConference {

	private JFrame frame;

	private JButton submitButton;

	private JPanel panel_1;
	private JLabel UserFirstName;
	private JLabel UserLastName;
	private JLabel ConfTitleLabel;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;

	/**
	 * Launches the application.
	 * This is the main method.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddConference window = new AddConference();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creates the application for AddConference, 
	 * and constructor. 
	 */
	public AddConference() {
		initialize();
	}

	/**
	 * This method Initialize the contents of the frame. 
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
		
		JLabel lblPleaseFillIn = new JLabel("Please Add a Conference");
		lblPleaseFillIn.setBounds(12, 28, 275, 14);
		panel_1.add(lblPleaseFillIn);
		
		JLabel pChairLabel = new JLabel("Program Chair");
		pChairLabel.setBounds(22, 69, 103, 20);
		panel_1.add(pChairLabel);
		
		UserFirstName = new JLabel("First Name");
		UserFirstName.setBounds(181, 69, 110, 20);
		panel_1.add(UserFirstName);
		
		UserLastName = new JLabel("Last Name");
		UserLastName.setBounds(463, 69, 103, 20);
		panel_1.add(UserLastName);
		
		ConfTitleLabel = new JLabel("Conference Title");
		ConfTitleLabel.setBounds(22, 173, 133, 20);
		panel_1.add(ConfTitleLabel);
		
		textField = new JTextField();
		textField.setBounds(181, 173, 146, 26);
		panel_1.add(textField);
		textField.setColumns(10);
		
		JLabel ConfStartLabel = new JLabel("Conference Start");
		ConfStartLabel.setBounds(15, 256, 133, 20);
		panel_1.add(ConfStartLabel);
		
		textField_1 = new JTextField();
		textField_1.setBounds(181, 253, 110, 26);
		panel_1.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(320, 253, 54, 26);
		panel_1.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(463, 253, 103, 26);
		panel_1.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Conference End");
		lblNewLabel.setBounds(15, 297, 118, 20);
		panel_1.add(lblNewLabel);
		
		textField_4 = new JTextField();
		textField_4.setBounds(181, 310, 110, 26);
		panel_1.add(textField_4);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setBounds(320, 310, 54, 26);
		panel_1.add(textField_5);
		textField_5.setColumns(10);
		
		textField_6 = new JTextField();
		textField_6.setBounds(463, 310, 103, 26);
		panel_1.add(textField_6);
		textField_6.setColumns(10);
	}
}
		
//public Conference(int theID, String theName, User theProgramChair, Date thePaperStart,
//		Date thePaperEnd, Date theConferenceStart, Date theConferenceEnd,
//		String theLocation, String theDescription) {
//
//		myID = theID;
//		myName = theName;
//		myProgramChair = theProgramChair;
//		myPaperStart = thePaperStart;
//		myPaperEnd = thePaperEnd;
//		myConferenceStart = theConferenceStart;
//		myConferenceEnd = theConferenceEnd;
//		myLocation = theLocation;
//		myDescription = theDescription;
//}
