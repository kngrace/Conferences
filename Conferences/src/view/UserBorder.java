package view;
import java.awt.EventQueue;


public class UserBorder {

	private JFrame frame;
	private JButton btnSubmissions;
	private JButton confButton;
	private JButton allConfButton;
	private JPanel panel_1;
	private JButton logoutButton;
	private JLabel lblNewLabel;
	private JPanel panel;
	private JPanel panel_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserBorder window = new UserBorder();
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
	public UserBorder() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.ORANGE);
		frame.setBounds(100, 100, 699, 467);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(Color.ORANGE);
		panel.setBounds(0, 0, 683, 428);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		panel_1 = new JPanel();
		panel_1.setBounds(0, 83, 148, 345);
		panel.add(panel_1);
		panel_1.setBackground(new Color(176, 196, 222));
		panel_1.setLayout(null);
		
		JLabel lblNavigation = new JLabel("Navigation");
		lblNavigation.setHorizontalAlignment(SwingConstants.CENTER);
		lblNavigation.setFont(new Font("Book Antiqua", Font.PLAIN, 17));
		lblNavigation.setBounds(10, 11, 128, 23);
		panel_1.add(lblNavigation);
		
		btnSubmissions = new JButton("Submissions");
		btnSubmissions.setIcon(new ImageIcon("C:\\Users\\pothnik\\Pictures\\leaf.jpg"));
		btnSubmissions.setBounds(10, 59, 128, 23);
		panel_1.add(btnSubmissions);
		
		logoutButton = new JButton("Logout");
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		logoutButton.setBounds(20, 311, 103, 23);
		panel_1.add(logoutButton);
		
		confButton = new JButton("Conferences");
		confButton.setBounds(10, 93, 128, 23);
		panel_1.add(confButton);
		
		allConfButton = new JButton("All Conferences");
		allConfButton.setBounds(10, 127, 128, 23);
		panel_1.add(allConfButton);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(251, 162, 324, 145);
		panel.add(lblNewLabel);
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\pothnik\\Pictures\\welcome.jpg"));
		
		panel_2 = new JPanel();
		panel_2.setBackground(new Color(176, 196, 222));
		panel_2.setBounds(0, 0, 690, 82);
		panel.add(panel_2);
	}

	public JFrame getFrame() {
		return frame;
	}
	public JButton getBtnSubmissions() {
		return btnSubmissions;
	}
	public JButton getConfButton() {
		return confButton;
	}
	public JButton getAllConfButton() {
		return allConfButton;
	}
	public JPanel getPanel() {
		return panel;
	}
	public JPanel getPanel_1() {
		return panel_1;
	}
	public JButton getLogoutButton() {
		return logoutButton;
	}
}
