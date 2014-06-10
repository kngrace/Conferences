package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import model.AccessLevel;
import model.Conference;
import model.Session;
import control.ConferenceControl;

/**
 * Displays all the conferences in which a manuscript was submitted by the user. 
 * 
 * @author Nikhila & Marilyn
 * @version 06.06.2014
 *
 */
public class MySubmissions {

	/**
	 * Session containing the current user.
	 */
	private Session session;

	/**
	 * Panel with all the main information.
	 */
	private JPanel panel;

	/**
	 * panel that can be transported and repainted. 
	 */
	private JPanel panel_1;

	/**
	 * Create the application.
	 * 
	 * @param the_session to be assigned to my_session
	 */
	public MySubmissions(Session the_session) {
		session = the_session;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		panel = new JPanel();
		panel_1 = new JPanel();
		panel.setBounds(0, 0, 600, 400);
		panel_1.setPreferredSize(new Dimension(600, 1000));
		panel.setLayout(null);
		panel_1.setLayout(null);

		panel.add(panel_1);

		panel_1.setBackground(Color.ORANGE);

		panel.setLayout(null);

		//All the conferences in which the current user is an author. 
		final List<Conference> lst = 
				ConferenceControl.getConferences(session.getCurrentUser(), AccessLevel.AUTHOR);
		
		//Ability to select a conferences from the list of conferences 
		final JComboBox comboBox = new JComboBox();
		if(lst != null && !lst.isEmpty()) {
			String label = "Select a Conference";
			comboBox.addItem(label);
			for(int i = 0; i < lst.size(); i++) {
				comboBox.addItem(lst.get(i).getName());
			}
			comboBox.setSelectedItem(0);
			comboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("comes");
					@SuppressWarnings("rawtypes")
					JComboBox cb = (JComboBox)e.getSource();
					int index = cb.getSelectedIndex();
					UserScreen tabs = new UserScreen(lst.get(index - 1), session);
					panel_1.removeAll();
					panel_1.add(tabs.getTab());
					panel_1.repaint();	
				}
			});
		} else {
			comboBox.setEnabled(false);
		}
		comboBox.setBounds(21, 22, 174, 23);
		panel_1.add(comboBox);


		int x = 27;

		// When list is not empty displays all of the conferences and their details
		if((!lst.isEmpty()) && (lst != null)) {
			for(int i = 0; i < lst.size(); i++) {

				panel_1.setPreferredSize(new Dimension(600, Math.max((i*50 + 250), 1000)));
				
				JLabel label = new JLabel(lst.get(i).getName());
				x += 50;
				label.setBounds(21, x, 400, 20);
				panel_1.add(label);

				JLabel dateSub = new JLabel("Open: " + lst.get(i).getPaperStart().toString());

				dateSub.setBounds(21, x + 20, 100, 20);
				panel_1.add(dateSub);

				JLabel dateConf = new JLabel("Deadline: " + lst.get(i).getPaperEnd().toString());

				dateConf.setBounds(184, x + 20, 200, 20);

				panel_1.add(dateConf);



			}
		} else { //When list is empty then no conferences are found message is displayed.
			JLabel empty = new JLabel("No Conferences Found!");
			empty.setBounds(27, 80, 400, 20);
			panel_1.add(empty);
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, 550, 360);
		scrollPane.setViewportView(panel_1);
		
		panel.add(scrollPane);

	}

	/**
	 * Main panel with all of the components. 
	 * 
	 * @return panel
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * Panel_1 that can be repainted. 
	 * 
	 * @return panel_1
	 */
	public JPanel getPanel_1() {
		return panel_1;
	}
}
