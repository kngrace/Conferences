package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPopupMenu;

import model.Conference;

public class PopupMenu extends JPopupMenu{
	
	JComboBox<String> comboBox;

	public PopupMenu(List<Conference> lst) {
		setBounds(0, 0, 113, 86);
		
		comboBox = new JComboBox<String>();
		for(Conference c: lst) {
			comboBox.addItem(c.getName());
		}
		add(comboBox);
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index = comboBox.getSelectedIndex();
				
			}
		});
		
	}
}


