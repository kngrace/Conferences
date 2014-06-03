package view;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.AccessLevel;
import model.Conference;
import model.Session;
import model.User;
import control.ConferenceControl;


public class MySubmissions {

	private JFrame frame;
	
	
	private Session session;
	
	private JPanel panel;
	
	private JPanel panel_1;
	

	/**
	 * Create the application.
	 */
	public MySubmissions(Session the_session) {
		session = the_session;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.ORANGE);
		frame.setBounds(100, 100, 699, 467);
		frame.getContentPane().setLayout(null);
		
		
		panel = new JPanel();
		panel_1 = new JPanel();
		panel.setBounds(0, 0, 683, 428);
		panel_1.setBounds(0, 0, 536, 345);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		panel_1.setLayout(null);
		
		panel.add(panel_1);
				
		panel.setLayout(null);
		
		final List<Conference> lst = 
				ConferenceControl.getConferences(session.getCurrentUser(), AccessLevel.AUTHOR);
		
		final JButton select = new JButton("Select A Conference");
		if(lst.isEmpty() || lst == null) {
			select.setEnabled(false);
		}
		
	
		select.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				PopupMenu pop = new PopupMenu(lst);
				pop.show((Component)arg0.getSource(), 0, 0);  
				
				
			}
		});
		select.setBounds(21, 22, 174, 23);
		panel_1.add(select);
		
		
		int x = 27;
		
		if((!lst.isEmpty()) && (lst != null)) {
			for(int i = 0; i < lst.size(); i++) {
				
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
