package view;
import java.awt.EventQueue;


public class NewUser {

	private JFrame frame;
	private JTextField email;
	private JTextField add;
	private JTextField textField_2;
	private JTextField frstName;
	private JTextField lstName;
	private JTextField usrName;
	private JPanel panel_1;
	private JPanel panel;
	
	private String user_first;
	private String user_last;
	private String user_email;
	private String user_address;
	private String username; 
	private JButton submitButton;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewUser window = new NewUser();
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
	public NewUser() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.ORANGE);
		frame.setBounds(100, 100, 706, 476);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		panel_1 = new JPanel();
		panel_1.setBackground(Color.ORANGE);
		panel_1.setBounds(0, 0, 690, 437);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblPleaseFillIn = new JLabel("Please Fill in the Following Information:");
		lblPleaseFillIn.setBounds(12, 100, 275, 14);
		panel_1.add(lblPleaseFillIn);
		
		JLabel lblFirstName = new JLabel("First Name: ");
		lblFirstName.setBounds(12, 125, 69, 14);
		panel_1.add(lblFirstName);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(12, 186, 69, 14);
		panel_1.add(lblEmail);
		
		JLabel lblAddress = new JLabel("Address: ");
		lblAddress.setBounds(12, 214, 67, 14);
		panel_1.add(lblAddress);
		
		email = new JTextField();
		email.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				user_email = email.getText();
			}
		});
		email.setBounds(78, 183, 209, 20);
		panel_1.add(email);
		email.setColumns(10);
		
		add = new JTextField();
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				user_address = add.getText();
			}
		});
		add.setBounds(78, 213, 209, 75);
		panel_1.add(add);
		add.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(78, 331, 86, 20);
		panel_1.add(textField_2);
		
		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(12, 155, 69, 14);
		panel_1.add(lblLastName);
		
		frstName = new JTextField();
		frstName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				user_first = frstName.getText();
			}
		});
		frstName.setColumns(10);
		frstName.setBounds(78, 122, 86, 20);
		panel_1.add(frstName);
		
		lstName = new JTextField();
		lstName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				user_last = lstName.getText();
			}
		});
		lstName.setBounds(78, 152, 86, 20);
		panel_1.add(lstName);
		lstName.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(12, 299, 67, 14);
		panel_1.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 334, 67, 14);
		panel_1.add(lblPassword);
		
		usrName = new JTextField();
		usrName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username = usrName.getText();
				/**
				 * User dummy = UserControl.getUser(username);
				 * if(dummy != null) {
				 * 	lblNewLabel = new JLabel("Username is already used!");
				 *  lblNewLabel.setForeground(Color.RED);
		         *  lblNewLabel.setBounds(176, 299, 179, 14);
		         *  panel_1.add(lblNewLabel);
		         * }
				 */
			}
		});
		usrName.setColumns(10);
		usrName.setBounds(78, 296, 86, 20);
		panel_1.add(usrName);
		frame.getContentPane().add(panel_1);
		
		panel = new JPanel();
		panel.setBackground(new Color(176, 196, 222));
		panel.setBounds(0, 0, 690, 82);
		panel_1.add(panel);
		
		submitButton = new JButton("Submit Form");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("working!!!");
				//User new_user = new User(id, email, user_first, user_last, user_address);
				//UserControl.createUser(new_user);
			}
		});
		submitButton.setBounds(78, 374, 144, 23);
		panel_1.add(submitButton);
		
	
	}
	public JPanel getPanel_1() {
		return panel_1;
	}
	public JPanel getPanel() {
		return panel;
	}
}
