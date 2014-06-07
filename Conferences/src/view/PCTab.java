package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.AccessLevel;
import model.Conference;
import model.Manuscript;
import model.Session;
import model.Status;
import model.User;
import control.ManuscriptControl;
import control.UserControl;

/**
 * 
 * @author Nikhila Potharaj 
 * @version 06.06.2014
 * 
 * Represents the PC tab for a conference. 
 *
 */
public class PCTab {
	
	/**
	 * Panel containing all of the components.
	 */
	private JPanel panel;
	
	/**
	 * The conference for this tab.
	 */
	private Conference my_conference;
	
	/**
	 * The user using the system. 
	 */
	private Session my_session;

	/**
	 * Create the application.
	 * 
	 * @param the_conference to be assigned to my_conference
	 * @param the_session to be assigned to my_session
	 */
	public PCTab(Conference the_conference, Session the_session) {
		my_conference = the_conference;
		my_session = the_session;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("unchecked")
	private void initialize() {
		
		panel = new JPanel();
		panel.setBackground(Color.ORANGE);
		panel.setBounds(0, 0, 537, 318);
		panel.setLayout(null);
		
		List<Manuscript> lst = ManuscriptControl.getManuscripts(my_conference, 
				my_session.getCurrentUser(), AccessLevel.PROGRAMCHAIR);
		
		int i = 11;
		
		
		// Adds in all of the components needed for one manuscript.
		if(lst != null && !lst.isEmpty()) {
		for(final Manuscript m: lst) {
			
			//title
			JLabel title = new JLabel("Title: " + m.getFile().getName());
			title.setBounds(10, i, 264, 14);
			panel.add(title);
			
			//Author of the manuscript.
			JLabel author = new JLabel("Author: " 
					+ m.getAuthor().getFirstName() + " " 
					+ m.getAuthor().getLastName());
			author.setBounds(284, i, 159, 14);
			panel.add(author);
			
			//Download button to download the manuscript
			JButton download = new JButton("Download");
			download.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					Date date = new Date();
					if(my_conference.getPaperStart().before(date) 
							&& my_conference.getPaperEnd().after(date)) {
						
			            final JFileChooser fc = new JFileChooser(); 
			            int returnVal = fc.showSaveDialog(panel);
			            if (returnVal == JFileChooser.APPROVE_OPTION) {
			                File input = new File(m.getFile().getName());
			                FileCopier copy = new FileCopier();
			                try {
								copy.copyFileTo(m.getFile(), input);

				                if(m.getFile().getName().equals(input.getName())) {
									JOptionPane.showMessageDialog(panel, new JLabel("File has been saved."));
								}
							} catch (IOException e) {
								JOptionPane.showMessageDialog(panel, new JLabel("No File Found"));
								e.printStackTrace();
							} catch (Exception e) {
								JOptionPane.showMessageDialog(panel, new JLabel("No File Found"));
								e.printStackTrace();
							}
			                
			                
			            } 
					} 
					
				}
			});
			download.setBounds(425, i, 100, 23);
			panel.add(download);
			
			
			JLabel spc = new JLabel("SubProgram Chair: ");
			spc.setBounds(10, i + 21, 114, 14);
			panel.add(spc);
			
			//If there is no SPC then the PC can choose an SPC drop down of users and 
			// warnings when necessary. If there is an SPC name is displayed.
			if(m.getSPC(my_session) != null) {
				JLabel spc_name = new JLabel(m.getSPC(my_session).getFirstName() + " " 
						+ m.getSPC(my_session).getLastName());
				
				spc_name.setBounds(134, i + 18, 150, 20);
				panel.add(spc_name);
				
			} else {
				final List<User> users = UserControl.getUsers();
				
				String[] us = new String[users.size() + 1];
				us[0] = "Select a User";
				for(int j = 0; i < users.size(); i++) {
					us[j + 1] = users.get(j).getFirstName() + " " + users.get(j).getLastName();
				}
				
				JComboBox comboBox = new JComboBox(us);
				comboBox.setSelectedIndex(0);
				comboBox.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JComboBox cb = (JComboBox)e.getSource();
						int index = cb.getSelectedIndex();
						if(((ManuscriptControl.getManuscripts(my_conference, users.get(index - 1), AccessLevel.SUBPROGRAMCHAIR) != null) 
								&& (ManuscriptControl.getManuscripts(my_conference, users.get(index - 1), AccessLevel.SUBPROGRAMCHAIR).size() < 4)) 
								&& (!m.getAuthor().equals(users.get(index - 1)))) {
							(users.get(index - 1)).setAccess(my_conference, AccessLevel.SUBPROGRAMCHAIR);
						} else if(m.getAuthor().equals(users.get(index - 1))) {
							JDialog something  = new JDialog();
							JDialog warning = new JDialog(something, "User is an author for this manuscript!");
							panel.add(warning);
						} else {
							JDialog something  = new JDialog();
							JDialog warning = new JDialog(something, "User is already a SubProgram Chair for 4 Manuscripts!");
							panel.add(warning);
						}
						
					}
					
				});
				comboBox.setBounds(134, i + 18, 126, 20);
				panel.add(comboBox);
			}
			
			//Reviews submitted for the manuscript
			JLabel reviews = new JLabel("Reviews: ");
			if(m.getReviews(my_session).isEmpty()) {
				reviews.setText("Reviews: NONE");
			}
			reviews.setBounds(10, i + 42, 78, 14);
			panel.add(reviews);
			
			JLabel rec = new JLabel(m.getRecommendStatus().toString());
			rec.setBounds(284, i + 21, 126, 14);
			panel.add(rec);
			
			//Final decision drop down 
			JComboBox comboBox = new JComboBox();
			comboBox.addItem("Undecided");
			comboBox.addItem("Approved");
			comboBox.addItem("Rejected");
			comboBox.setSelectedIndex(0);
			comboBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JComboBox cb = (JComboBox)e.getSource();
					int index = cb.getSelectedIndex();
					if(index == 1) {
						m.setFinalStatus(Status.APPROVED, my_session);
					} else {
						m.setFinalStatus(Status.REJECTED, my_session);
					}
				}
				
			});
			comboBox.setBounds(284, i + 39, 126, 20);
			panel.add(comboBox);
			
			
			i += 77;
		}
		} else { // If there are no manuscripts 
			JLabel none = new JLabel("No Manuscripts Submitted");
			none.setBounds(15, 15, 200, 20);
			panel.add(none);
			
		}
		
	}
	
	/**
	 * Returns the panel with all of the components. 
	 * 
	 * @return panel 
	 */
	public JPanel getPanel() {
		return panel;
	}
}
