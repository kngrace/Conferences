package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
import model.Review;
import model.Session;
import model.Status;
import model.User;
import control.ManuscriptControl;
import control.UserControl;

/**
 * Represents the sub-program chair view of a particular conference. 
 * 
 * @author Nikhila Potharaj
 * @version 06.06.2014
 *
 */
public class SPCTab {

	/**
	 * The main panel holding all of the components. 
	 */
	private JPanel my_panel;

	/**
	 * The conference for this particular SPC tab.
	 */
	private Conference my_conference;

	/**
	 * The session with the current user. 
	 */
	private Session my_session;

	/**
	 * Create the application.
	 * 
	 * @param the_conference to be assigned to my_conference
	 * @param the_session to be assigned to my_session
	 */
	public SPCTab(Conference the_conference, Session the_session) {
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

		//List of manuscripts assigned to this SPC. 
		List<Manuscript> list = ManuscriptControl.getManuscripts(my_conference, my_session.getCurrentUser(), AccessLevel.SUBPROGRAMCHAIR);
		int i = 11;
		if(list != null && !list.isEmpty()) { 

			//when list is not empty all the manuscripts assigned to this user are displayed. 
			for(final Manuscript m: list) { 
				if(m.isSubmitted()) {

					//Title of the file
					JLabel title = new JLabel("Title: " + String.valueOf(m.getFile().getName()));
					title.setBounds(10, i, 318, 14);
					my_panel.add(title);

					//Download button to download the manuscript file. 
					JButton download = new JButton("Download");
					download.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {


							FileCopier copy = new FileCopier();
							final JFileChooser fc = new JFileChooser(); 
							try {

								int result = fc.showSaveDialog(my_panel);
								File input = fc.getSelectedFile();
								if(result == JFileChooser.APPROVE_OPTION) {
									copy.copyFileTo(m.getFile(), input);
									JOptionPane.showMessageDialog(my_panel, new JLabel("File has been saved."));
								}
							}  catch (IOException e) { //exception captured. 
								JOptionPane.showMessageDialog(my_panel, new JLabel("No File Found"));
							} catch (Exception e) { //exception handled. 
								JOptionPane.showMessageDialog(my_panel, new JLabel("No File Found"));
							}




						} 
					});
					download.setBounds(343, i - 4, 150, 23);
					my_panel.add(download);

					//Reviewers assigned to a particular mansucript m. 
					List<User> r = ManuscriptControl.getReviewers(m);
					String concat = "Reviewers: ";
					if(r == null || r.isEmpty()) { //displays none if none. 
						concat = concat + "NONE";
					} else { // displays their names if there are reviewers assined. 

						for(User u: r) {
							concat = concat + u.getFirstName() + " " + u.getLastName();
						}
					}


					JLabel reviewer = new JLabel(concat);
					reviewer.setBounds(10, i + 25, 318, 14);
					my_panel.add(reviewer);

					//Adds reviewers to the manuscript 
					final List<User> u = UserControl.getUsers();
					String[] users = new String[u.size() + 1];
					users[0] = "Select a User";
					for(int j = 0; j < u.size(); j++) {
						users[j + 1] = u.get(j).getFirstName();
					}
					JComboBox comboBox_1 = new JComboBox(users);
					comboBox_1.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							JComboBox cb = (JComboBox)e.getSource();
							int index = cb.getSelectedIndex();
							if(((ManuscriptControl.getManuscripts(my_conference, u.get(index - 1), AccessLevel.REVIEWER) != null) 
									&& (ManuscriptControl.getManuscripts(my_conference, u.get(index - 1), AccessLevel.REVIEWER).size() < 4)) 
									&& (!m.getAuthor().equals(u.get(index - 1)))) {
								m.assignReviewer(u.get(index - 1), my_session);
								System.out.println(m.getSPC(my_session));
								JOptionPane.showMessageDialog(my_panel, "Reviewer has been added");
							} else if(m.getAuthor().equals(u.get(index - 1))) {
								JOptionPane.showMessageDialog(my_panel, "User is an author for this manuscript!");
							} else {
								JOptionPane.showMessageDialog(my_panel, "User is already a Reviewer for 4 Manuscripts!");
							}



						}

					});
					comboBox_1.setBounds(343, i + 22, 150, 20);
					my_panel.add(comboBox_1);

					//shows the reviews for the manuscript. 
					JLabel reviews = new JLabel("Reviews: ");
					reviews.setBounds(10, i + 50, 64, 20);
					my_panel.add(reviews);
					final List<Review> r1 = m.getReviews(my_session);
					final JComboBox reviews1 = new JComboBox();
					reviews1.addItem("Select a Review");
					reviews1.setSelectedIndex(0);

					if(r1 != null && !r1.isEmpty()) {

						for(int k = 0; k < r1.size(); k++) {
							reviews1.addItem(r1.get(k).getReviewer().getFirstName());
						}


						reviews1.addActionListener(new ActionListener() {

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
											copy.copyFileTo(r1.get(index - 1).getFile(), input);
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
					}

						reviews1.setBounds(83, i + 47, 218, 20);
						my_panel.add(reviews1);


						if (m.getRecommendStatus() != Status.UNDECIDED){
							
							JLabel j = new JLabel(m.getRecommendStatus().toString());
							j.setBounds(343, i + 47, 100, 20);
							my_panel.add(j);
							
							
						} else {
						
							//Drop-down menu for the recommendation of a manuscript. 
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
										m.setRecommendStatus(Status.APPROVED, my_session);
									} else {
										m.setRecommendStatus(Status.REJECTED, my_session);
									}

									JOptionPane.showMessageDialog(my_panel, 
											new JLabel("Recommendation Status Set."));
								}

							});
							comboBox.setBounds(343, i + 47, 100, 20);
							my_panel.add(comboBox);
						}
						i += 82;
				}

						
			}

		}  else { // If there are no manuscripts 
			JLabel none = new JLabel("No Manuscripts Assigned");
			none.setBounds(15, 15, 200, 20);
			my_panel.add(none);

		}
	}

	/**
	 * Returns the main panel with all of the components. 
	 * 
	 * @return my_panel.
	 */
	public JPanel getPanel() {
		return my_panel; 
	}

}
