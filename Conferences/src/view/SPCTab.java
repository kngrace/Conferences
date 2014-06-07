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
		my_panel.setBounds(0, 0, 536, 318);
		my_panel.setLayout(null);

		//List of manuscripts assigned to this SPC. 
		List<Manuscript> list = ManuscriptControl.getManuscripts(my_conference, my_session.getCurrentUser(), AccessLevel.SUBPROGRAMCHAIR);
		int i = 11;
		if(list != null && !list.isEmpty()) { 

			//when list is not empty all the manuscripts assigned to this user are displayed. 
			for(final Manuscript m: list) { 

				//Title of the file
				JLabel title = new JLabel("Title: " + String.valueOf(m.getFile().getName()));
				title.setBounds(10, i, 318, 14);
				my_panel.add(title);

				//Download button to download the manuscript file. 
				JButton download = new JButton("Download");
				download.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						final JFileChooser fc = new JFileChooser(); 
						int returnVal = fc.showSaveDialog(my_panel);
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							File input = new File(m.getFile().getName());
							FileCopier copy = new FileCopier();
							try {
								copy.copyFileTo(m.getFile(), input);

								if(m.getFile().getName().equals(input.getName())) {
									JOptionPane.showMessageDialog(my_panel, new JLabel("File has been saved."));
								}
							} catch (IOException e) { //exception captured. 
								JOptionPane.showMessageDialog(my_panel, new JLabel("No File Found"));
								e.printStackTrace();
							} catch (Exception e) { //exception handled. 
								JOptionPane.showMessageDialog(my_panel, new JLabel("No File Found"));
								e.printStackTrace();
							}


						} 
					} 
				});
				download.setBounds(343, i - 4, 150, 23);
				my_panel.add(download);

				//Reviewers assigned to a particular mansucript m. 
				List<User> r = m.getReviewers(my_session);
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
					users[j + 1] = u.get(j).getFirstName() + " " + u.get(j).getLastName();
				}
				JComboBox comboBox_1 = new JComboBox(users);
				comboBox_1.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JComboBox cb = (JComboBox)e.getSource();
						int index = cb.getSelectedIndex();
						List<Manuscript> reviewers = ManuscriptControl.getManuscripts(my_conference, u.get(index - 1), AccessLevel.REVIEWER);
						
						//only if that reviewer has less than four manuscripts and is not author of the manuscript
						if(((reviewers != null && !reviewers.isEmpty()) && reviewers.size() < 4)
								&& (!m.getAuthor().equals(u.get(index - 1)))) {
							(u.get(index - 1)).setAccess(my_conference, AccessLevel.REVIEWER);
						} else if (m.getAuthor().equals(u.get(index - 1))){ // if reviewer is an author the throws a message
							JDialog something  = new JDialog();
							JDialog warning = new JDialog(something, "User is an author for this manuscript!");
							my_panel.add(warning);
						} 

					}

				});
				comboBox_1.setBounds(343, i + 22, 150, 20);
				my_panel.add(comboBox_1);

				//shows the reviews for the manuscript. 
				JLabel reviews = new JLabel("Reviews: ");
				reviews.setBounds(10, i + 50, 318, 14);
				my_panel.add(reviews);

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
							m.setFinalStatus(Status.APPROVED, my_session);
						} else {
							m.setFinalStatus(Status.REJECTED, my_session);
						}
					}

				});
				comboBox.setBounds(343, i + 47, 150, 20);
				my_panel.add(comboBox);

				i += 82;
			}
			
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
