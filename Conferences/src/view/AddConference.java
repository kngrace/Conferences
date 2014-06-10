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
import model.Conference;
import model.Session;
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
	
	/**
	 * This is the JButton that submits the 
	 * new added conference.
	 */
	private JButton submitButton;
	
	/**
	 * This is the Session created.
	 */
	private Session session;

	/**
	 * This is the first panel for the GUI.
	 */
	private JPanel panel_1;
	
	/**
	 * This is the conference title.
	 */
	private String conf_title;

	/**
	 * This is the conference description.
	 */
	private String conf_des;
	
	/**
	 * This is the conference location.
	 */
	private String conf_loc;
	
	private String yps;
	
	private String mps;
	
	private String dps;
	
	private String ype;
	
	private String mpe;
	
	private String dpe;
	
	private String ycs;
	
	private String mcs;
	
	private String dcs;
	
	private String yce;
	
	private String mce;
	
	private String dce;

	private String name;

	private User progChair;

	private Date startDatePaper;

	private Date endDatePaper;

	private Date confStartDate;

	private Date confEndDate;

	private String confLocation;

	private String confDescription;

	protected Conference Conference;

	/**
	 * Creates the application for AddConference, 
	 * and constructor. 
	 */
	public AddConference(Session the_session, String theName, User theProgramChair, Date thePaperStart,
			   Date thePaperEnd, Date theConferenceStart, Date theConferenceEnd,
			   String theLocation, String theDescription) {
		
		session = the_session;
		
		name = theName;
		progChair = theProgramChair;
		startDatePaper = thePaperStart;
		endDatePaper = thePaperEnd;
		confStartDate = theConferenceStart;
		confEndDate = theConferenceEnd;
		confLocation = theLocation;
		confDescription = theDescription;
	
		initialize();
	}

	/**
	 * This method Initialize the contents of the frame. 
	 */
	private void initialize() {

		panel_1 = new JPanel();
		panel_1.setBackground(Color.ORANGE);
		panel_1.setBounds(0, 0, 600, 400);
		panel_1.setLayout(null);
		
		JLabel lblPleaseFillIn = new JLabel("Add a Conference");
		lblPleaseFillIn.setBounds(12, 28, 275, 14);
		panel_1.add(lblPleaseFillIn);
		
		JLabel pChairLabel = new JLabel("Program Chair");
		pChairLabel.setBounds(10, 69, 91, 20);
		panel_1.add(pChairLabel);
		
		JLabel UserName = new JLabel(session.getCurrentUser().getFirstName() + " " + session.getCurrentUser().getLastName());
		UserName.setBounds(111, 69, 200, 20);
		panel_1.add(UserName);
		
		
		JLabel ConfDisLabel = new JLabel("Conference Description");
		ConfDisLabel.setBounds(12, 176, 133, 20);
		panel_1.add(ConfDisLabel);
		
		final JTextField conf_titleTxt = new JTextField();
		conf_titleTxt.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				//do nothing
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				conf_title = conf_titleTxt.getText();
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				conf_title = conf_titleTxt.getText();
				
			}
		});
		
		conf_titleTxt.setBounds(115, 119, 186, 26);
		panel_1.add(conf_titleTxt);
		conf_titleTxt.setColumns(10);
		
		JLabel PaperStrtLAb = new JLabel("Paper Start");
		PaperStrtLAb.setBounds(12, 221, 103, 20);
		panel_1.add(PaperStrtLAb);
		
		
		final JTextField txtpapstrtY = new JTextField();
		txtpapstrtY.setText("YYYY");
		txtpapstrtY.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				//do nothing
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				yps = txtpapstrtY.getText();
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				yps = txtpapstrtY.getText();
				
			}
		});
		txtpapstrtY.setBounds(115, 215, 62, 26);
		panel_1.add(txtpapstrtY);
		txtpapstrtY.setColumns(10);
		
		final JTextField papstrtM = new JTextField();
		papstrtM.setText("MM");
		papstrtM.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				//do nothing
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				mps = papstrtM.getText();
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				mps = papstrtM.getText();
				
			}
		});
		papstrtM.setBounds(181, 215, 54, 26);
		panel_1.add(papstrtM);
		papstrtM.setColumns(10);
		
		final JTextField papstrtD = new JTextField();
		papstrtD.setText("DD");
		papstrtD.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				//do nothing
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				dps = papstrtD.getText();
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				dps = papstrtD.getText();
			
			}
		});
		papstrtD.setBounds(239, 215, 62, 26);
		panel_1.add(papstrtD);
		papstrtD.setColumns(10);
		
		JLabel conf_titlelab = new JLabel("Conference Title");
		conf_titlelab.setBounds(12, 122, 91, 20);
		panel_1.add(conf_titlelab);
		
		JTextField desc_confTxt = new JTextField();
		desc_confTxt.setBounds(168, 156, 358, 49);
		panel_1.add(desc_confTxt);
		desc_confTxt.setColumns(10);
		
		JLabel lblPaperend = new JLabel("Paper End");
		lblPaperend.setBounds(311, 221, 91, 20);
		panel_1.add(lblPaperend);
		
		final JTextField papendY = new JTextField();
		papendY.setText("YYYY");
		papendY.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				//do nothing
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				ype = papendY.getText();
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				ype = papendY.getText();
			
			}
		});
		papendY.setColumns(10);
		papendY.setBounds(406, 218, 62, 26);
		panel_1.add(papendY);
		
		final JTextField papendM = new JTextField();
		papendM.setText("MM");
		papendM.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				//do nothing
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				mpe = papendM.getText();
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				mpe = papendM.getText();
			
			}
		});
		papendM.setColumns(10);
		papendM.setBounds(472, 218, 54, 26);
		panel_1.add(papendM);
		
		final JTextField papendD = new JTextField();
		papendD.setText("DD");
		papendD.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				//do nothing
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				dpe = papendD.getText();
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				dpe = papendD.getText();
			
			}
		});
		papendD.setColumns(10);
		papendD.setBounds(530, 218, 62, 26);
		panel_1.add(papendD);
		
		JLabel conf_strtLab = new JLabel("Conference Start");
		conf_strtLab.setBounds(12, 265, 103, 20);
		panel_1.add(conf_strtLab);
		
		final JTextField confstrtY = new JTextField();
		confstrtY.setText("YYYY");
		confstrtY.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				//do nothing
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				ycs = confstrtY.getText();
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				ycs = confstrtY.getText();
			
			}
		});
		confstrtY.setColumns(10);
		confstrtY.setBounds(115, 259, 62, 26);
		panel_1.add(confstrtY);
		
		final JTextField confstrtM = new JTextField();
		confstrtM.setText("MM");
		confstrtM.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				//do nothing
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				mcs = confstrtM.getText();
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				mcs = confstrtM.getText();
			
			}
		});
		confstrtM.setColumns(10);
		confstrtM.setBounds(181, 259, 54, 26);
		panel_1.add(confstrtM);
		
		final JTextField confstrtD = new JTextField();
		confstrtD.setText("DD");
		confstrtD.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				//do nothing
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				dcs = confstrtD.getText();
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				dcs = confstrtD.getText();
			
			}
		});
		confstrtD.setColumns(10);
		confstrtD.setBounds(239, 259, 62, 26);
		panel_1.add(confstrtD);
		
		JLabel lblConferenceEnd = new JLabel("Conference End");
		lblConferenceEnd.setBounds(311, 265, 85, 20);
		panel_1.add(lblConferenceEnd);
		
		final JTextField confendY = new JTextField();
		confendY.setText("YYYY");
		confendY.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				//do nothing
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				yce = confendY.getText();
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				yce = confendY.getText();
			
			}
		});
		confendY.setColumns(10);
		confendY.setBounds(406, 262, 62, 26);
		panel_1.add(confendY);
		
		final JTextField confendM = new JTextField();
		confendM.setText("MM");
		confendM.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				//do nothing
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				mce = confendM.getText();
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				mce = confendM.getText();
			
			}
		});
		confendM.setColumns(10);
		confendM.setBounds(472, 262, 54, 26);
		panel_1.add(confendM);
		
		final JTextField confendD = new JTextField();
		confendD.setText("DD");
		confendY.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				//do nothing
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				dce = confendD.getText();
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				dce = confendD.getText();
			
			}
		});
		confendD.setColumns(10);
		confendD.setBounds(530, 262, 62, 26);
		panel_1.add(confendD);
		
		/**
		 * This is the Submit button for the 
		 * adding of the conferences.
		 * 
		 * Date.getValue("yyyy-mm-dd") returns a Date object
		 * 
		 *  java.util.Date utilDate = new java.util.Date();
    	 *  java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		 */
		JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				//create conference object
				panel_1.repaint();
			}
		});
		submit.setBounds(292, 337, 129, 23);
		panel_1.add(submit);
		
		JLabel lblConferenceLocation = new JLabel("Conference Location");
		lblConferenceLocation.setBounds(311, 122, 136, 20);
		panel_1.add(lblConferenceLocation);
		
		JTextField conf_locTxt = new JTextField();
		conf_locTxt.setColumns(10);
		conf_locTxt.setBounds(445, 119, 146, 26);
		panel_1.add(conf_locTxt);
	}
	public JPanel getPanel_1() {
		return panel_1;
	}
}
		
