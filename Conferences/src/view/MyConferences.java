package view;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Conference;
import model.Session;
import control.ConferenceControl;


public class MyConferences {
	
	/**
	 * Session with the current user. 
	 */
	private Session session;
	
	/**
	 * Main panel containing all of the components.
	 */
	private JPanel panel;
	
	/**
	 * Panel_1 containing the components inside the panel.
	 */
	private JPanel panel_1;
	
	/**
	 * Creating the application.
	 * 
	 * @param the_session to be assigned to session
	 */
	public MyConferences(Session the_session) {
		session = the_session;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		panel = new JPanel();
		panel_1 = new JPanel();
		panel.setBounds(0, 0, 683, 428);
		panel_1.setBounds(0, 0, 536, 345);
		panel.setLayout(null);
		panel_1.setLayout(null);
		
		panel.add(panel_1);
				
		panel.setLayout(null);
		panel_1.setBackground(Color.ORANGE);
		
		//Gets the list of the conference that the user has a role in and then displays them.
		final List<Conference> lst = ConferenceControl.getConferences(session.getCurrentUser());
		
		//Selects a conference from the drop-down menu. 
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
		
		int x = 17;
		//Displays all of the conferences and their submission dates and roles 
		if(lst != null && !lst.isEmpty()) {
			for(int i = 0; i < lst.size(); i++) {
				
				JLabel label = new JLabel(lst.get(i).getName());
				x += 70;
				label.setBounds(21, x, 400, 20);
				panel_1.add(label);
		
				JLabel dateSub = new JLabel("Open: " + lst.get(i).getPaperStart().toString());
				
				dateSub.setBounds(21, x + 20, 100, 20);
				panel_1.add(dateSub);
				
				JLabel dateConf = new JLabel("Deadline: " + lst.get(i).getPaperEnd().toString());
				
				dateConf.setBounds(184, x + 20, 200, 20);
				
				panel_1.add(dateConf);
				
				JLabel access = new JLabel("Roles: " + lst.get(i).getAccessLevel(session));
				access.setBounds(21, x + 40, 200, 20);
				
				panel_1.add(access);
			}
		} else { //If the user has no role in any conference then this message is displayed. 
			JLabel empty = new JLabel("No Conferences Found!");
			empty.setBounds(27, 80, 400, 20);
			panel_1.add(empty);
		}
	}
	
	/**
	 * returns the main panel.
	 * @return panel
	 */
	public JPanel getPanel() {
		return panel;
	}
	
	/**
	 * Returns the panel_1 that represents this panel. 
	 * @return panel_1
	 */
	public JPanel getPanel_1() {
		return panel_1;
	}
}
