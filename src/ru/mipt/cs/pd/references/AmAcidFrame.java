package ru.mipt.cs.pd.references;

import java.awt.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class AmAcidFrame extends JFrame {

	public AmAcidFrame() {
	}

	public AmAcidPane addAmAcidPane(AmAcidFrame frame) {
		final AmAcidPane pane = new AmAcidPane();
		pane.setBackground(Color.WHITE);
		pane.setSize(650, 500);
		pane.setLocation(20, 100);
		pane.setLayout(null);
		pane.setVisible(false);
		frame.add(pane);
		return pane;
	}
	
	public JButton addButton(AmAcidFrame fr, String str, int loc1, int loc2) {
		JButton button = new JButton(str);
		button.setSize(100, 40);
		button.setLocation(loc1, loc2);
//		button.setBackground(Color.GRAY);
		fr.add(button);
		return button;
	}

	
	
}
