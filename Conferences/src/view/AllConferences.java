package view;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.AccessLevel;
import model.Conference;
import model.Session;
import model.User;
import control.ConferenceControl;


public class AllConferences {

	private JFrame frame;
	
	
	private Session session;
	
	private JPanel panel;
	
	private JPanel panel_1;
	

	/**
	 * Create the application.
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
		
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.ORANGE);
		frame.setBounds(100, 100, 699, 467);
		frame.getContentPane().setLayout(null);
		
		
		panel = new JPanel();
		panel_1 = new JPanel();
		panel.setPreferredSize(new Dimension(683, 428));
		panel_1.setBounds(0, 0, 536, 345);
		panel_1.setPreferredSize(new Dimension(536, 345));
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		panel_1.setLayout(null);
		panel_1.setBackground(Color.ORANGE);
		
		panel.add(new JScrollPane(panel_1));
				
		panel.setLayout(null);
		
		final List<Conference> lst = ConferenceControl.getConferences();
		
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
		
		JButton btnNewButton1 = new JButton("Add A Conference");
		btnNewButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//new window for adding a conf.
			}
		});
		btnNewButton1.setBounds(283, 22, 174, 23);
		panel_1.add(btnNewButton1);
		
		int x = 27;
		
		if(lst != null) {
			for(int i = 0; i < lst.size(); i++) {
				
				JLabel label = new JLabel(lst.get(i).getName());
				x += 50;
				label.setBounds(21, x, 400, 20);
				panel_1.add(label);
		
				JLabel dateSub = new JLabel("Open: " + lst.get(i).getPaperStart());
				
				dateSub.setBounds(21, x + 20, 100, 20);
				panel_1.add(dateSub);
				
				JLabel dateConf = new JLabel("Deadline: " + lst.get(i).getPaperEnd());
				
				dateConf.setBounds(184, x + 20, 200, 20);
				
				panel_1.add(dateConf);
				
			}
		} else {
			JLabel empty = new JLabel("No Conferences Found!");
			empty.setBounds(27, 80, 400, 20);
			panel_1.add(empty);
		}
		
		
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	public JPanel getPanel_1() {
		return panel_1;
	}
}
