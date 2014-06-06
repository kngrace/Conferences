package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import control.ManuscriptControl;
import model.Conference;
import model.Manuscript;
import model.Session;
import model.Status;


public class AuthorTab {

	private JFrame frame;

	private JPanel panel;
	
	private Conference my_conference;
	
	private Session my_session;
	
	

	/**
	 * Create the application.
	 */
	public AuthorTab(Conference the_conference, Session the_session) {
		my_conference = the_conference;
		my_session = the_session;
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
		
		panel = new JPanel();
		panel.setBackground(Color.ORANGE);
		panel.setBounds(0, 0, 536, 318);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		List<Manuscript> lst = ManuscriptControl.getManuscripts(my_conference, my_session.getCurrentUser());
		int i = 11;
		if(lst != null && !lst.isEmpty()) {
		for(final Manuscript m: lst) {
		JLabel title = new JLabel("Title: " + m.getFile().getName());
		title.setBounds(10, i, 270, 14);
		panel.add(title);
		
		JLabel status = new JLabel(m.getFinalStatus(my_session).toString());
		status.setBounds(311, i, 156, 14);
		panel.add(status);
		
		JButton resubmit = new JButton("Resubmit");
		resubmit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Date date = new Date();
				if(my_conference.getPaperStart().before(date) 
						&& my_conference.getPaperEnd().after(date)) {
					
		            final JFileChooser fc = new JFileChooser(); 
		            int returnVal = fc.showOpenDialog(panel);
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		                File file = fc.getSelectedFile();
		                Manuscript man = new Manuscript(my_session.getCurrentUser(), 
		                		my_conference, file.getName(), file);
		                ManuscriptControl.updateManuscript(man);
		            } 
				} else {
					JLabel deadline = new JLabel("No Submissions Allowed Past Deadline!");
					deadline.setBounds(10, 5, 300, 23);
					panel.add(deadline);
					panel.repaint();
				}
				
			}
			
		});
		resubmit.setBounds(10, i + 25, 89, 23);
		panel.add(resubmit);
		
		JButton delete = new JButton("Delete");
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					m.unsubmit(my_session);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		delete.setBounds(125, i + 25, 89, 23);
		panel.add(delete);
		
		JButton download = new JButton("Download");
		download.setBounds(238, i + 25, 108, 23);
		panel.add(download);
		
		if(m.getFinalStatus(my_session) != Status.UNDECIDED) { 
			JButton reviews = new JButton("Download Reviews");
			reviews.setBounds(370, 35, 143, 23);
			panel.add(reviews);
		}
		 i += 64;
		}
		} 
	}
	
	public JPanel getPanel() {
		return panel;
	}

}
