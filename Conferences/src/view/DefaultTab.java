package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import control.ConferenceControl;
import control.ManuscriptControl;
import model.AccessLevel;
import model.Conference;
import model.Manuscript;
import model.Session;


public class DefaultTab {

	private JFrame frame;
	private JPanel panel;
	private Conference myConference;
	private Session session;
	private MyFile file;

	/**
	 * Create the application.
	 */
	public DefaultTab(Conference conference, Session the_session) {
		myConference = conference;
		session = the_session;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 565, 398);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 531, 318);
		frame.getContentPane().add(scrollPane);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setBackground(Color.ORANGE);
		panel.setLayout(null);
		
		JLabel title = new JLabel(myConference.getName());
		title.setBounds(114, 32, 336, 14);
		panel.add(title);
		
		JLabel desc = new JLabel(myConference.getDescription());
		desc.setBounds(114, 57, 322, 69);
		panel.add(desc);
		
		JLabel prog_lab = new JLabel("Program Chair:");
		prog_lab.setBounds(23, 137, 137, 14);
		panel.add(prog_lab);
		
		JLabel papStrtLab = new JLabel("Submissions Open:");
		papStrtLab.setBounds(23, 174, 119, 14);
		panel.add(papStrtLab);
		
		JLabel pap_start = new JLabel(myConference.getPaperStart().toString());
		pap_start.setBounds(154, 174, 106, 14);
		panel.add(pap_start);
		
		JLabel confStrtLab = new JLabel("Conference Starts:");
		confStrtLab.setBounds(23, 199, 121, 14);
		panel.add(confStrtLab);
		
		JLabel confEndLab = new JLabel("Conference Ends:");
		confEndLab.setBounds(270, 199, 121, 14);
		panel.add(confEndLab);
		
		JButton add_manuscript = new JButton("Add Manuscript...");
		add_manuscript.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Date date = new Date();
				if((myConference.getPaperStart().before(date) 
						&& myConference.getPaperEnd().after(date)) 
						&& (ManuscriptControl.getManuscripts(myConference, session.getCurrentUser(), AccessLevel.AUTHOR).size() < 4)) {
					
		            final JFileChooser fc = new JFileChooser(); 
		            int returnVal = fc.showOpenDialog(panel);
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		                File file_select = fc.getSelectedFile();
		                
		                File output = new File(file_select.getName());
		                MyFile copy = new MyFile();
		                try {
							copy.copyFile(file_select, output);
							Manuscript man = new Manuscript(session.getCurrentUser(), 
			                		myConference, file_select.getName(), file_select);
							JOptionPane.showMessageDialog(panel, new JLabel("File has been submitted."));
							if(man.getFile().getName().equals(output.getName())) {
								System.out.println("true");
							}
							System.out.println(ConferenceControl.getAccessLevel(myConference, session.getCurrentUser()));
						} catch (IOException e) {
							JOptionPane.showMessageDialog(panel, new JLabel("Please choose a valid file .txt"));
							
						}
		            } 
				} else {
					JLabel deadline = new JLabel("No Submissions Allowed Past Deadline!");
					deadline.setBounds(23, 285, 300, 23);
					panel.add(deadline);
					panel.repaint();
				}

			}
		});
		add_manuscript.setBounds(23, 255, 155, 23);
		panel.add(add_manuscript);
		
		JLabel papEndLab = new JLabel("Deadline:  ");
		papEndLab.setBounds(270, 174, 89, 14);
		panel.add(papEndLab);
		
		JLabel pap_end = new JLabel(myConference.getPaperEnd().toString());
		pap_end.setBounds(413, 174, 106, 14);
		panel.add(pap_end);
		
		JLabel conf_start = new JLabel(myConference.getConferenceStart().toString());
		conf_start.setBounds(154, 199, 89, 14);
		panel.add(conf_start);
		
		JLabel conf_end = new JLabel(myConference.getConferenceEnd().toString());
		conf_end.setBounds(413, 199, 106, 14);
		panel.add(conf_end);
		
		JLabel titleLab = new JLabel("Title:");
		titleLab.setBounds(22, 32, 46, 14);
		panel.add(titleLab);
		
		JLabel conf_descLab = new JLabel("Description:");
		conf_descLab.setBounds(22, 84, 71, 14);
		panel.add(conf_descLab);
		
		JLabel prog_chair = new JLabel(myConference.getProgramChair().getFirstName() 
				+ " " + myConference.getProgramChair().getLastName());
		prog_chair.setBounds(193, 137, 137, 14);
		panel.add(prog_chair);
	}
	public JPanel getPanel() {
		return panel;
	}
}
