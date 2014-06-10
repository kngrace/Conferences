package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
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
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import model.AccessLevel;
import model.Conference;
import model.Manuscript;
import model.Review;
import model.Session;
import model.Status;
import model.User;
import control.ConferenceControl;
import control.ManuscriptControl;
import control.UserControl;

/**
 * 
 * @author Nikhila Potharaj 
 * @version 06.06.2014
 * 
 * Represents the PC tab for a conference. 
 * The Program Chair is a vital important
 * user in this program.
 */
public class PCTab {

	/**
	 * Panel containing all of the components.
	 */
	private JPanel out_panel;

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

		out_panel = new JPanel();

		panel = new JPanel();
		panel.setBackground(Color.ORANGE);
		out_panel.setPreferredSize(new Dimension(550, 400));
		out_panel.setBackground(Color.ORANGE);
		panel.setLayout(null);

		panel.setPreferredSize(new Dimension(550, 400));



		List<Manuscript> lst = ManuscriptControl.getManuscripts(my_conference, 
				my_session.getCurrentUser(), AccessLevel.PROGRAMCHAIR);

		int i = 11;


		/**
		 * Adds in all of the components needed for one manuscript.
		 */
		if(lst != null && !lst.isEmpty()) {
			for(final Manuscript m: lst) {
				if(m.isSubmitted()) {

					panel.setPreferredSize(new Dimension(550, i + 1000));
					out_panel.validate();
					panel.validate();

					/**
					 * title for the JLabel
					 */
					JLabel title = new JLabel("Title: " + m.getFile().getName());
					title.setBounds(10, i, 264, 14);
					panel.add(title);

					/**
					 * Author of the manuscript.
					 */
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



								FileCopier copy = new FileCopier();
								final JFileChooser fc = new JFileChooser(); 
								try {

									int result = fc.showSaveDialog(panel);
									File input = fc.getSelectedFile();
									if(result == JFileChooser.APPROVE_OPTION) {
										copy.copyFileTo(m.getFile(), input);
										JOptionPane.showMessageDialog(panel, new JLabel("File has been saved."));
									}
								}  catch (IOException e) {
									JOptionPane.showMessageDialog(panel, new JLabel("No File Found"));
								} catch (Exception e) {
									JOptionPane.showMessageDialog(panel, new JLabel("No File Found"));
								}
							} 

						}
					});
					download.setBounds(425, i, 100, 23);
					panel.add(download);


					JLabel spc = new JLabel("SubProgram Chair: ");
					spc.setBounds(10, i + 21, 114, 14);
					panel.add(spc);

					/**If there is no SPC then the PC can choose an SPC drop down of users and 
					// warnings when necessary. If there is an SPC name is displayed.
					*/
					if(m.getSPC(my_session) != null) {
					//	System.out.println("came in" );
						JLabel spc_name = new JLabel(m.getSPC(my_session).getFirstName() + " " 
								+ m.getSPC(my_session).getLastName());
						System.out.println(m.getSPC(my_session).getFirstName() + " " 
								+ m.getSPC(my_session).getLastName());
						spc_name.setBounds(134, i + 18, 150, 20);
						panel.add(spc_name);

					} else {
						final List<User> users = UserControl.getUsers();

						String[] us = new String[users.size() + 1];
						us[0] = "Select a User";
						for(int j = 0; j < users.size(); j++) {
							us[j + 1] = users.get(j).getFirstName() + " " + users.get(j).getLastName();
						}

						final JComboBox comboBox = new JComboBox(us);
						comboBox.setSelectedIndex(0);
						comboBox.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								JComboBox cb = (JComboBox)e.getSource();
								int index = cb.getSelectedIndex();
								if(((ManuscriptControl.getManuscripts(my_conference, users.get(index - 1), AccessLevel.SUBPROGRAMCHAIR) != null) 
										&& (ManuscriptControl.getManuscripts(my_conference, users.get(index - 1), AccessLevel.SUBPROGRAMCHAIR).size() < 4)) 
										&& (!m.getAuthor().equals(users.get(index - 1)))) {
									m.assignSPC(users.get(index - 1), my_session);
									System.out.println(m.getSPC(my_session));
									JOptionPane.showMessageDialog(panel, "Sub-Program Chair has been added");
								} else if(m.getAuthor().equals(users.get(index - 1))) {
									JOptionPane.showMessageDialog(panel, "User is an author for this manuscript!");
								} else {
									JOptionPane.showMessageDialog(panel, "User is already a SubProgram Chair for 4 Manuscripts!");
								}



							}

						});
						comboBox.setBounds(134, i + 18, 126, 20);
						panel.add(comboBox);
					}

					/**
					 * Reviews submitted for the manuscript
					 */
					JLabel reviews = new JLabel("Reviews: ");
					if(m.getReviews(my_session).isEmpty()) {
						reviews.setText("Reviews: NONE");
					}
					reviews.setBounds(10, i + 42, 78, 14);
					panel.add(reviews);

					JLabel rec = new JLabel("Recommend Status: " 
					+ m.getRecommendStatus().toString());
					rec.setBounds(284, i + 21, 250, 14);
					panel.add(rec);

					
					if (m.getRecommendStatus() != Status.UNDECIDED){
						
						JLabel j = new JLabel("Final Status: " + 
						m.getFinalStatus(my_session).toString());
						j.setBounds(284, i + 39, 250, 20);
						panel.add(j);
						
						
					} else {
					
					
					/**
					 * Final decision drop down 
					 */
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
							} else if(index == 2){
								m.setFinalStatus(Status.REJECTED, my_session);
							}
							JOptionPane.showMessageDialog(panel, 
									new JLabel("Final Status Set."));
							
						}

					});
					comboBox.setBounds(284, i + 39, 126, 20);
					panel.add(comboBox);

					}
					
					final List<Review> r1 = m.getReviews(my_session);
					final JComboBox reviews1 = new JComboBox();
					reviews1.addItem("Select a Review");
					reviews1.setSelectedIndex(0);

					if(r1 != null && !r1.isEmpty()) {

						for(int k = 0; k < r1.size(); k++) {
							reviews1.addItem(r1.get(k).getReviewer().getFirstName() + " " + (r1.get(k).getReviewer().getLastName()));
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

										int result = fc.showSaveDialog(panel);
										File input = fc.getSelectedFile();
										if(result == JFileChooser.APPROVE_OPTION) {
											copy.copyFileTo(r1.get(index - 1).getFile(), input);
											JOptionPane.showMessageDialog(panel, new JLabel("File has been saved."));
										}
									} catch (IOException e1) {
										JOptionPane.showMessageDialog(panel, new JLabel("No File Found"));
									} catch (Exception e2) {
										JOptionPane.showMessageDialog(panel, new JLabel("No File Found"));
									}
								}



							}

						});
					}

					reviews1.setBounds(134, i + 39, 126, 20);
					panel.add(reviews1);


					i += 77;
				}
			}
		} else { // If there are no manuscripts 
			JLabel none = new JLabel("No Manuscripts Submitted");
			none.setBounds(15, 15, 200, 20);
			panel.add(none);

		}

		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getViewport().setBackground(Color.ORANGE);
		scrollPane.setPreferredSize(new Dimension(550, 340));
		out_panel.add(scrollPane);


	}

	/**
	 * Returns the panel with all of the components. 
	 * 
	 * @return panel 
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * Returns the panel with all of the components. 
	 * 
	 * @return panel 
	 */
	public JPanel getPanel_1() {
		return out_panel;
	}

}
