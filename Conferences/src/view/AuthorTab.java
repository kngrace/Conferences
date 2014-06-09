package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.AccessLevel;
import model.Conference;
import model.Manuscript;
import model.Session;
import model.Status;
import control.ManuscriptControl;

/**
 * Represents the author view of the conference. 
 * 
 * @author Nikhila Potharaj
 * @version 06.06.2014
 *
 */
public class AuthorTab {

	/**
	 * Main panel containing all of the components. 
	 */
	private JPanel my_panel;

	/**
	 * The conference that is currently used for this tab. 
	 */
	private Conference my_conference;

	/**
	 * The current user for this system at this time. 
	 */
	private Session my_session;


	/**
	 * Create the application.
	 * 
	 * @param the_conference to be assigned to myConference
	 * @param the_session to be assigned to mySession
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

		my_panel = new JPanel();
		my_panel.setBackground(Color.ORANGE);
		my_panel.setBounds(0, 0, 560, 330);
		my_panel.setLayout(null);

		//Gets the manuscripts and displays them along with their own additional functions. 
		List<Manuscript> lst = ManuscriptControl.getManuscripts(my_conference, my_session.getCurrentUser(), AccessLevel.AUTHOR);
		int i = 11;
		if(lst != null && !lst.isEmpty()) { // displays manuscripts if list is not empty. 
			for(final Manuscript m: lst) {

				if(m.isSubmitted()) {

					//Title of manuscript
					JLabel title = new JLabel("Title: " + String.valueOf(m.getFile().getName()));
					title.setBounds(10, i, 270, 14);
					my_panel.add(title);


					JLabel statusLab = new JLabel("Status: ");
					statusLab.setBounds(250, i, 100, 14);
					my_panel.add(statusLab);

					//Current final status of the manuscript 
					JLabel status = new JLabel(String.valueOf(m.getFinalStatus(my_session)));
					status.setBounds(311, i, 156, 14);
					my_panel.add(status);

					//Function to resubmit the manuscript only if before the deadline. 
					JButton resubmit = new JButton("Resubmit");
					resubmit.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							Date date = new Date();
							if(my_conference.getPaperStart().before(date) 
									&& my_conference.getPaperEnd().after(date)) {

								final JFileChooser fc = new JFileChooser(); 
								int returnVal = fc.showOpenDialog(my_panel);
								if (returnVal == JFileChooser.APPROVE_OPTION) {
									File file = fc.getSelectedFile();
									File output = new File(file.getName());
									FileCopier copy = new FileCopier();
									try {
										copy.copyFileFrom(file, output);
										m.submit(my_session);
										Manuscript man = new Manuscript(my_session.getCurrentUser(), 
												my_conference, file.getName(), file);
										if(man.getFile().getName().equals(output.getName())) {
											JOptionPane.showMessageDialog(my_panel, new JLabel("File has been re-submitted."));
										}
									} catch (IOException e) {
										JOptionPane.showMessageDialog(my_panel, new JLabel("Please choose a valid file .txt"));
										e.printStackTrace();
									} catch (Exception e) {
										JOptionPane.showMessageDialog(my_panel, new JLabel("Please choose a valid file .txt"));
										e.printStackTrace();
									}


								} 
							} else { //message if it is past the deadline. 
								JLabel deadline = new JLabel("No Submissions Allowed Past Deadline!");
								deadline.setBounds(10, 5, 300, 23);
								my_panel.add(deadline);
								my_panel.repaint();
							}

						}

					});
					resubmit.setBounds(10, i + 25, 89, 23);
					my_panel.add(resubmit);

					//function to delete the manuscript if before the deadline. 
					JButton delete = new JButton("Delete");
					delete.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							Date date = new Date();
							if(my_conference.getPaperStart().before(date) 
									&& my_conference.getPaperEnd().after(date)) {
								try {
									int reply = JOptionPane.showConfirmDialog(my_panel, "Are you sure you want to delete this manuscript?");
									if(reply == JOptionPane.YES_OPTION) {
										m.unsubmit(my_session);

										JOptionPane.showMessageDialog(my_panel, "Successfully Deleted");
									}
									my_panel.repaint();
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else { // message if it is past the deadline. 
								JOptionPane.showMessageDialog(my_panel, "Cannot change submission after Deadline");
							}
						} 
					});
					delete.setBounds(125, i + 25, 89, 23);
					my_panel.add(delete);

					//Download button to download the manuscript file. 
					JButton download = new JButton("Download");
					download.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							Date date = new Date();
							if(my_conference.getPaperStart().before(date) 
									&& my_conference.getPaperEnd().after(date)) {

								File input = new File(m.getFile().getName());
								FileCopier copy = new FileCopier();
								final JFileChooser fc = new JFileChooser(); 
								fc.setSelectedFile(input);
								int returnVal = fc.showSaveDialog(my_panel);
								if (returnVal == JFileChooser.APPROVE_OPTION) {
									
									try {
										copy.copyFileTo(m.getFile(), input);
										
										if(m.getFile().getName().equals(input.getName())) {
											JOptionPane.showMessageDialog(my_panel, new JLabel("File has been saved."));
										}
									} catch (IOException e) {
										JOptionPane.showMessageDialog(my_panel, new JLabel("No File Found"));
										e.printStackTrace();
									} catch (Exception e) {
										JOptionPane.showMessageDialog(my_panel, new JLabel("No File Found"));
										e.printStackTrace();
									}


								} 
							} 

						}

					});
					download.setBounds(238, i + 25, 108, 23);
					my_panel.add(download);

					//If a final decision has been made other than Undecided the authors can see the reviews
					if(m.getFinalStatus(my_session) != Status.UNDECIDED) { 
						JButton reviews = new JButton("Download Reviews");
						reviews.setBounds(370, 35, 143, 23);
						my_panel.add(reviews);
					}
					i += 64;
				}
			}
		} else { // If there are no manuscripts 
			JLabel none = new JLabel("No Manuscripts Submitted");
			none.setBounds(15, 15, 200, 20);
			my_panel.add(none);
		}
	}


	/**
	 * Returns the panel with all of the manuscripts displayed and their functions. 
	 * 
	 * @return the panel. 
	 */
	public JPanel getPanel() {
		return my_panel;
	}

}
