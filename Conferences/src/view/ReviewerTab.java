package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.AccessLevel;
import model.Conference;
import model.Manuscript;
import model.Review;
import model.Session;
import model.Status;
import control.ConferenceControl;
import control.ManuscriptControl;

/**
 * Represents the author view of the conference. 
 * 
 * @author Nikhila Potharaj, Eric Miller
 * @version 06.09.2014
 *
 */
public class ReviewerTab {

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
	public ReviewerTab(Conference the_conference, Session the_session) {
		my_conference = the_conference;
		my_session = the_session;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("unchecked")
	private void initialize() {

		my_panel = new JPanel();
		my_panel.setBackground(Color.ORANGE);
		my_panel.setBounds(0, 0, 560, 330);
		my_panel.setLayout(null);

		//Gets list of manuscripts for which the user is a reviewer.

		List<Manuscript> lst = ManuscriptControl.getManuscripts(my_conference, my_session.getCurrentUser(), AccessLevel.REVIEWER);
		
		int i = 11;
		if(lst != null && !lst.isEmpty()) { // displays manuscripts if list is not empty. 
			for(final Manuscript m: lst) {

				if(m.isSubmitted()) {

					//Title of manuscript
					JLabel title = new JLabel("Title: " + String.valueOf(m.getFile().getName()));
					title.setBounds(10, i, 270, 14);
					my_panel.add(title);

					//Function to resubmit the manuscript only if before the deadline. 
					JButton submit = new JButton("Review");
					submit.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							Date date = new Date();
							if(my_conference.getPaperStart().before(date) 
									&& my_conference.getConferenceEnd().after(date)) {

								final JFileChooser fc = new JFileChooser(); 
								int returnVal = fc.showOpenDialog(my_panel);
								if (returnVal == JFileChooser.APPROVE_OPTION) {
									File file = fc.getSelectedFile();
									File output = new File(file.getName());
									FileCopier copy = new FileCopier();	
									try {
										copy.copyFileFrom(file, output);
										
										List<Review> revs = m.getReviews(my_session);
										Review the_rev = null;
										for (Review r : revs) {
											if (r.getReviewer().equals(my_session.getCurrentUser())) {
												the_rev = r;
												break;
											}
										}
										if (the_rev == null) {		
											Review rev = new Review(my_session.getCurrentUser(), 
													file.getName(), output, m);
											if(rev.getFile().getName().equals(output.getName())) {
												m.addReview(rev, my_session);
												JOptionPane.showMessageDialog(my_panel, new JLabel("Review has been submitted."));
											}
										} else {
											the_rev.setFile(output);
											JOptionPane.showMessageDialog(my_panel, new JLabel("Review has been re-ubmitted."));
										}
									} catch (IOException e) {
										JOptionPane.showMessageDialog(my_panel, new JLabel("Please choose a valid file .txt"));
									} catch (Exception e) {
										JOptionPane.showMessageDialog(my_panel, new JLabel("Please choose a valid file .txt"));;
									}


								} 
							} else { //message if it is past the deadline. 
								JLabel deadline = new JLabel("No reviews accepted after the conference has started!");
								deadline.setBounds(10, 5, 300, 23);
								my_panel.add(deadline);
								my_panel.repaint();
							}

						}

					});
					submit.setBounds(10, i + 25, 89, 23);
					my_panel.add(submit);

					//function to delete the review if before the deadline. 
					
					//TO DO *************
					
					//JButton delete = new JButton("Delete");
					/*delete.addActionListener(new ActionListener() {

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
					});*/
					
					
					//delete.setBounds(125, i + 25, 89, 23);
					//my_panel.add(delete);

					//Download button to download the manuscript file. 
					JButton download = new JButton("Download");
					download.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							Date date = new Date();
							if(my_conference.getPaperStart().before(date) 
									&& my_conference.getConferenceStart().after(date)) {

								List<Review> revs = m.getReviews(my_session);
								Review the_rev = null;
								for (Review r : revs) {
									if (r.getReviewer().equals(my_session.getCurrentUser())) {
										the_rev = r;
										break;
									}
								}
								
								if (the_rev == null) {
									JOptionPane.showMessageDialog(my_panel, new JLabel("No review found!"));
								} else {	
									FileCopier copy = new FileCopier();
									final JFileChooser fc = new JFileChooser(); 
									try {
		
										int result = fc.showSaveDialog(my_panel);
										File input = fc.getSelectedFile();
										if(result == JFileChooser.APPROVE_OPTION) {
											copy.copyFileTo(the_rev.getFile(), input);
											JOptionPane.showMessageDialog(my_panel, new JLabel("File has been saved."));
										}
									} catch (IOException e) {
										JOptionPane.showMessageDialog(my_panel, new JLabel("No File Found"));
									} catch (Exception e) {
										JOptionPane.showMessageDialog(my_panel, new JLabel("No File Found"));
									}
								}
							} 

						}

					});
					download.setBounds(238, i + 25, 108, 23);
					my_panel.add(download);

					
					//V   Probably not needed for this class.    V
					
					//If a final decision has been made other than Undecided the authors can see the reviews
					/*if(m.getFinalStatus(my_session) != Status.UNDECIDED) { 
						final List<Review> r = m.getReviews(my_session);
						final JComboBox reviews = new JComboBox();
						reviews.setSelectedIndex(0);
						reviews.addItem("Select a Review");
						if(r != null && !r.isEmpty()) {
							
							for(int k = 0; k < r.size(); k++) {
								reviews.addItem("Review " + k);
							}
							
					
							reviews.addActionListener(new ActionListener() {
	
								@Override
								public void actionPerformed(ActionEvent e) {
									
									JComboBox cb = (JComboBox)e.getSource();
									int index = cb.getSelectedIndex();
									
									if(index != 0) {
										FileCopier copy = new FileCopier();
										final JFileChooser fc = new JFileChooser(); 
										try {
											
											int result = fc.showSaveDialog(my_panel);
											File input = fc.getSelectedFile();
											if(result == JFileChooser.APPROVE_OPTION) {
												copy.copyFileTo(r.get(index - 1).getFile(), input);
												JOptionPane.showMessageDialog(my_panel, new JLabel("File has been saved."));
											}
										} catch (IOException e1) {
											JOptionPane.showMessageDialog(my_panel, new JLabel("No File Found"));
										} catch (Exception e2) {
											JOptionPane.showMessageDialog(my_panel, new JLabel("No File Found"));
										}
									}
								
								
								
							}
							
						});
						reviews.setBounds(370, i + 24, 143, 23);
						my_panel.add(reviews);
					}
					i += 64;
				}*/
			}}
		} else { // If there are no reviews 
			JLabel none = new JLabel("No Reviews Submitted");
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
