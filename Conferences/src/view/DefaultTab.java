package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.AccessLevel;
import model.Conference;
import model.Manuscript;
import model.Session;
import control.ManuscriptControl;


public class DefaultTab extends Observable {

	/**
	 * Main panel containing all of the components. 
	 */
	private JPanel panel;
	
	/**
	 * Conference for this particular tab. 
	 */
	private Conference myConference;
	
	/**
	 * The session which has the current user. 
	 */
	private Session session;

	/**
	 * Create the application.
	 * 
	 * @param the_conference to be assigned to myConference
	 * @param the_session to be assigned to mySession
	 */
	public DefaultTab(Conference the_conference, Session the_session) {
		myConference = the_conference;
		session = the_session;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		panel = new JPanel();
		panel.setBounds(0, 0, 560, 330);
		panel.setBackground(Color.ORANGE);
		panel.setLayout(null);
		
		// Title of conference
		JLabel title = new JLabel(String.valueOf(myConference.getName()));
		title.setBounds(114, 32, 336, 14);
		panel.add(title);
		
		JLabel titleLab = new JLabel("Title:");
		titleLab.setBounds(22, 32, 46, 14);
		panel.add(titleLab);
		
		// Description for conference
		JLabel desc = new JLabel(String.valueOf(myConference.getDescription()));
		desc.setBounds(114, 57, 322, 69);
		panel.add(desc);
		
		JLabel conf_descLab = new JLabel("Description:");
		conf_descLab.setBounds(22, 84, 71, 14);
		panel.add(conf_descLab);
		
		// Program manager for conference		
		JLabel prog_chair = new JLabel(String.valueOf(myConference.getProgramChair().getFirstName()) 
				+ " " + String.valueOf(myConference.getProgramChair().getLastName()));
		prog_chair.setBounds(193, 137, 137, 14);
		panel.add(prog_chair);
		
		JLabel prog_lab = new JLabel("Program Chair:");
		prog_lab.setBounds(23, 137, 137, 14);
		panel.add(prog_lab);
		
		// Date of start of submissions 
		JLabel pap_start = new JLabel(String.valueOf(myConference.getPaperStart()));
		pap_start.setBounds(154, 174, 106, 14);
		panel.add(pap_start);
		
		JLabel papStrtLab = new JLabel("Submissions Open:");
		papStrtLab.setBounds(23, 174, 119, 14);
		panel.add(papStrtLab);
	
		
		//Labels for the deadline of the submissions/edit/delete. 
		JLabel pap_end = new JLabel(String.valueOf(myConference.getPaperEnd()));
		pap_end.setBounds(413, 174, 106, 14);
		panel.add(pap_end);
		
		JLabel papEndLab = new JLabel("Deadline:  ");
		papEndLab.setBounds(270, 174, 89, 14);
		panel.add(papEndLab);
		
		//Start date for the conference
		JLabel conf_start = new JLabel(String.valueOf(myConference.getConferenceStart()));
		conf_start.setBounds(154, 199, 89, 14);
		panel.add(conf_start);
		
		JLabel confStrtLab = new JLabel("Conference Starts:");
		confStrtLab.setBounds(23, 199, 121, 14);
		panel.add(confStrtLab);
		
		// label for the End date for the conference
		JLabel conf_end = new JLabel(String.valueOf(myConference.getConferenceEnd().toString()));
		conf_end.setBounds(413, 199, 106, 14);
		panel.add(conf_end);
		
		JLabel confEndLab = new JLabel("Conference Ends:");
		confEndLab.setBounds(270, 199, 121, 14);
		panel.add(confEndLab);
		
		// JButton to add a manuscript to this conference. 
		JButton add_manuscript = new JButton("Add Manuscript...");
		add_manuscript.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Date date = new Date();
				
				//Can only add a 4 manuscripts, and within the date of submissions.
				if((myConference.getPaperStart().before(date) 
						&& myConference.getPaperEnd().after(date)) 
						&& (ManuscriptControl.getManuscripts(myConference, session.getCurrentUser(), AccessLevel.AUTHOR).size() < 4)) {
					
		            final JFileChooser fc = new JFileChooser(); 
		            int returnVal = fc.showOpenDialog(panel);
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		                File file_select = fc.getSelectedFile();
		                
		                File output = new File(file_select.getName());
		                FileCopier copy = new FileCopier();
		                try {
							copy.copyFileFrom(file_select, output);
							Manuscript man = new Manuscript(session.getCurrentUser(), 
			                		myConference, file_select.getName(), file_select);
							if(man.getFile().getName().equals(output.getName())) {
								JOptionPane.showMessageDialog(panel, new JLabel("File has been submitted."));
							}
						} catch (IOException e) {
							JOptionPane.showMessageDialog(panel, new JLabel("Please choose a valid file .txt"));
							
						}
		            } 
				} else { // Warning if adding a manuscript after deadline. 
					JLabel deadline = new JLabel("No Submissions Allowed Past Deadline!");
					deadline.setBounds(23, 285, 300, 23);
					panel.add(deadline);
					panel.repaint();
				}

			}
		});
		add_manuscript.setBounds(23, 255, 155, 23);
		panel.add(add_manuscript);	
	}
	
	/**
	 * Returns this panel with all of the components to be added to the main window. 
	 * 
	 * @return the panel.
	 */
	public JPanel getPanel() {
		return panel;
	}
}
