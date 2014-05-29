package view;
import java.awt.Color;


public class UserScreen {

	private JFrame frame;
	private JPanel AuthorTab;
	private JPanel ReviewTab;
	private JPanel SPCTab;
	private JPanel PCTab;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserScreen window = new UserScreen();
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
	public UserScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		UserBorder border = new UserBorder();
		frame.getContentPane().setBackground(new Color(176, 196, 222));
		frame.setBounds(100, 100, 699, 467);
		frame.getContentPane().add(border.getPanel_1());
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(147, 61, 536, 367);
		frame.getContentPane().add(tabbedPane);
		
		AuthorTab = new JPanel();
		AuthorTab.setBackground(new Color(135, 206, 235));
		AuthorTab.setToolTipText("");
		tabbedPane.addTab("Author", null, AuthorTab, null);
		
		ReviewTab = new JPanel();
		ReviewTab.setBackground(new Color(255, 255, 0));
		tabbedPane.addTab("Reviewer", null, ReviewTab, null);
		
		SPCTab = new JPanel();
		SPCTab.setBackground(new Color(255, 215, 0));
		tabbedPane.addTab("Sub-Program Chair", null, SPCTab, null);
		
		PCTab = new JPanel();
		PCTab.setBackground(new Color(255, 165, 0));
		tabbedPane.addTab("Program Chair", null, PCTab, null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public JPanel getAuthorTab() {
		return AuthorTab;
	}
	public JPanel getReviewTab() {
		return ReviewTab;
	}
	public JPanel getSPCTab() {
		return SPCTab;
	}
	public JPanel getPCTab() {
		return PCTab;
	}
}
