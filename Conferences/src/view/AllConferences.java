package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import model.Conference;
import model.Session;
import control.ConferenceControl;


/**
 * 
 * @author Nikhila Potharaj
 * @version 06.06.2014
 * 
 * Displays all of the conferences that are in the database.
 * This is the page that shows every Conference.
 */
public class AllConferences {

	/**
	 * The current session containing the user that is currently using 
	 * the system. Session of user.
	 */
	private Session session;

	/**
	 * The main panel with all of the components needed for each conference. 
	 */
	private JPanel panel;

	/**
	 * Panel that can be used for other windows. 
	 */
	private JPanel panel_1;


	/**
	 * Create the application. this is the 
	 * Initialize part of the project.
	 */
	public AllConferences(Session the_session) {
		session = the_session;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("unchecked")
	private void initialize() {

		panel = new JPanel();
		panel_1 = new JPanel();
		panel.setBounds(0, 0, 600, 400);
		panel_1.setPreferredSize(new Dimension(600, 1000));
		panel.setLayout(null);
		panel_1.setLayout(null);
		panel_1.setBackground(Color.ORANGE);


		panel.setLayout(null);

		final List<Conference> lst = ConferenceControl.getConferences();

		/**
		 * Creates a drop-down for all of the conferences to be selected. 
		 */
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
					panel.removeAll();
					panel.add(tabs.getTab());
					panel.repaint();	
				}
			});
		} else {
			comboBox.setEnabled(false);
		}
		comboBox.setBounds(21, 22, 174, 23);
		panel_1.add(comboBox);

		//Users need to fill out this information in order to create a conference.
		JButton btnNewButton1 = new JButton("Add A Conference");
		btnNewButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel_1.removeAll();
				AddConference ac = new AddConference(session); 
				panel_1.add(ac.getPanel_1());
				panel_1.repaint();
				
			}
		});
		btnNewButton1.setBounds(283, 22, 174, 23);
		panel_1.add(btnNewButton1);

		int x = 27;
		//If list of conferences is not null they are all displayed if it is null
		// there is a message that displays. 
		if(lst != null) {
			for(int i = 0; i < lst.size(); i++) {

				panel_1.setPreferredSize(new Dimension(550, Math.max((i*50 + 250), 1000)));
				
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
		} else { // Displays when no conferences are in the database. 
			JLabel empty = new JLabel("No Conferences Found!");
			empty.setBounds(27, 80, 400, 20);
			panel_1.add(empty);
		}
		
		

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(0, 0, 550, 360);
		scrollPane.setViewportView(panel_1);
		
		panel.add(scrollPane);
		

		
	}
	
	/**
	 * The panel with all of the components.
	 * 
	 * @return the panel
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * Panel that can be repainted.
	 * 
	 * @return panel_1
	 */
	public JPanel getPanel_1() {
		return panel_1;
	}
}
