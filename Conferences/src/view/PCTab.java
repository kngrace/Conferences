package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import control.ConferenceControl;
import control.ManuscriptControl;
import control.UserControl;
import model.AccessLevel;
import model.Conference;
import model.Manuscript;
import model.Session;
import model.Status;
import model.User;


public class PCTab {

	private JFrame frame;
	
	private JPanel panel;
	
	private Conference my_conference;
	
	private Session my_session;

	/**
	 * Create the application.
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
		frame = new JFrame();
		frame.setBounds(100, 100, 554, 359);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(Color.ORANGE);
		panel.setBounds(0, 0, 537, 318);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		List<Manuscript> lst = ManuscriptControl.getManuscripts(my_conference, 
				my_session.getCurrentUser());
		
		int i = 11;
		if(lst != null && !lst.isEmpty()) {
		for(final Manuscript m: lst) {
			JLabel title = new JLabel("Title: " + m.getFile().getName());
			title.setBounds(10, 11, 264, 14);
			panel.add(title);
			
			if(m.getSPC(my_session) != null) {
				JLabel spc_name = new JLabel("SubProgram Chair: " 
						+ m.getSPC(my_session).getFirstName() + " " 
						+ m.getSPC(my_session).getLastName());
				spc_name.setBounds(10, i + 21, 264, 14);
				panel.add(spc_name);
			} else {
				final List<User> users = UserControl.getUsers();
				users.remove(m.getAuthor());
				for(User u: users) {
					if((ConferenceControl.getAccessLevel(my_conference, u).getValue()) < 1) {
						users.remove(u);
					}
				}
				
				String[] us = new String[users.size() + 1];
				
				for(int j = 0; i < users.size(); i++) {
					us[j + 1] = users.get(i).getFirstName() + " " + users.get(i).getLastName();
				}
				JComboBox comboBox = new JComboBox(us);
				comboBox.setSelectedIndex(0);
				comboBox.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JComboBox cb = (JComboBox)e.getSource();
						int index = cb.getSelectedIndex();
						(users.get(index - 1)).setAccess(my_conference, AccessLevel.SUBPROGRAMCHAIR);
					}
					
				});
				comboBox.setBounds(10, i + 21, 264, 14);
				panel.add(comboBox);
			}
			
			JLabel reviews = new JLabel("Reviews: ");
			if(m.getReviews(my_session).isEmpty()) {
				reviews.setText("Reviews: NONE");
			}
			reviews.setBounds(10, i + 42, 78, 14);
			panel.add(reviews);
			
			JLabel rec = new JLabel(m.getRecommendStatus().toString());
			rec.setBounds(284, i + 21, 126, 14);
			panel.add(rec);
			
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
			
			JLabel author = new JLabel("Author: " 
					+ m.getAuthor().getFirstName() + " " 
					+ m.getAuthor().getLastName());
			author.setBounds(284, i, 159, 14);
			panel.add(author);
			
			i += 77;
		}
		} else {
			JLabel none = new JLabel("No Manuscripts Submitted");
			none.setBounds(15, 15, 200, 20);
			panel.add(none);
			
		}
		
	}
	
	public JPanel getPanel() {
		return panel;
	}
}
