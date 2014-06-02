package view;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Conference;
import model.Session;
import control.ConferenceControl;


public class MySubmissions {

	private JFrame frame;
	
	private UserBorder border;
	
	private Session session;
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MySubmissions window = new MySubmissions();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MySubmissions() {
		border = new UserBorder();
		//session = the_session;
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
		
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 683, 428);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		panel.add(border.getPanel());
				
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//todo
			}
		});
		btnNewButton.setBounds(304, 98, 162, 32);
		panel.add(btnNewButton);
		
		List<Conference> lst = ConferenceControl.getConferences();
		
		JLabel lab = new JLabel("hello");
		
		int x = 170;
		
		for(int i = 0; i < lst.size(); i++) {
			
			JLabel label = new JLabel(lst.get(i).getName());
			x += 50;
			label.setBounds(166, x, 400, 20);
			
			JLabel dateSub = new JLabel(lst.get(i).getPaperStart().toString());
			
			dateSub.setBounds(166, x + 20, 200, 20);
			
			JLabel dateConf = new JLabel(lst.get(i).getPaperEnd().toString());
			
			dateSub.setBounds(381, x + 20, 200, 20);
			
			panel.add(label);
			panel.add(dateSub);
			panel.add(dateConf);
			
		}
		
		
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
}
