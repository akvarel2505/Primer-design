package ru.mipt.cs.pd.references;

import java.awt.Color;

import javax.swing.*;

@SuppressWarnings("serial")
public class AmAcidPane extends JPanel {

	public AmAcidPane() {
	}

	/*
	 * public NonPolPane(String imPath) { JPanel nonPolPane; JLabel labelGly;
	 * JLabel labelIle; JLabel labelAla; JLabel labelVal; JLabel labelMet;
	 * JLabel labelLeu; }
	 */
	public void addAmAcid1(JPanel pane, String name, String path) {
		JLabel label1 = new JLabel(name);
		label1.setSize(100, 20);
		label1.setLocation(100, 20);
		JLabel imageLabel1 = new JLabel(new ImageIcon(path));
		imageLabel1.setSize(200, 150);
		imageLabel1.setLocation(20, 50);
		pane.add(imageLabel1);
		pane.add(label1);
	}

	public void addAmAcid2(JPanel pane, String name, String path) {
		JLabel label2 = new JLabel(name);
		label2.setSize(100, 20);
		label2.setLocation(300, 20);
		JLabel imageLabel2 = new JLabel(new ImageIcon(path));
		imageLabel2.setSize(200, 150);
		imageLabel2.setLocation(220, 50);
		pane.add(imageLabel2);
		pane.add(label2);
	}

	public void addAmAcid3(JPanel pane, String name, String path) {
		JLabel label3 = new JLabel(name);
		label3.setSize(100, 20);
		label3.setLocation(510, 20);
		JLabel imageLabel3 = new JLabel(new ImageIcon(path));
		imageLabel3.setSize(200, 150);
		imageLabel3.setLocation(420, 50);
		pane.add(imageLabel3);
		pane.add(label3);
	}

	public void addAmAcid4(JPanel pane, String name, String path) {
		JLabel label4 = new JLabel(name);
		label4.setSize(100, 20);
		label4.setLocation(100, 230);
		JLabel imageLabel4 = new JLabel(new ImageIcon(path));
		imageLabel4.setSize(200, 150);
		imageLabel4.setLocation(20, 250);
		pane.add(imageLabel4);
		pane.add(label4);
	}

	public void addAmAcid5(JPanel pane, String name, String path) {
		JLabel label5 = new JLabel(name);
		label5.setSize(100, 20);
		label5.setLocation(310, 230);
		JLabel imageLabel5 = new JLabel(new ImageIcon(path));
		imageLabel5.setSize(200, 150);
		imageLabel5.setLocation(220, 265);
		pane.add(imageLabel5);
		pane.add(label5);
	}

	public void addAmAcid6(JPanel pane, String name, String path) {
		JLabel label6 = new JLabel(name);
		label6.setSize(100, 20);
		label6.setLocation(500, 230);
		JLabel imageLabel6 = new JLabel(new ImageIcon(path));
		imageLabel6.setSize(200, 150);
		imageLabel6.setLocation(420, 255);
		pane.add(imageLabel6);
		pane.add(label6);
	}

	public void addAmAcidPane() {
		final AmAcidPane pane = new AmAcidPane();
		pane.setBackground(Color.WHITE);
		pane.setSize(700, 500);
		pane.setLocation(25, 100);
		pane.setLayout(null);
		pane.setVisible(false);
	}
}
