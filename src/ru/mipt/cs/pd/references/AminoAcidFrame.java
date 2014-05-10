package ru.mipt.cs.pd.references;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class AminoAcidFrame {

	public AminoAcidFrame() {
		try {
			createGUI();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createGUI() {

		// Main frame
		final JFrame frame = new JFrame("Информация об аминокислотах");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1000, 650));
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		JFrame.setDefaultLookAndFeelDecorated(true);

		// Info Pane
		JPanel infoPane = new JPanel();
		infoPane.setBackground(Color.WHITE);
		infoPane.setSize(200, 400);
		infoPane.setLocation(760, 150);
		infoPane.setLayout(null);
		frame.add(infoPane);
		JLabel labelInf = new JLabel("Amino acid info");
		labelInf.setSize(200, 20);
		labelInf.setLocation(765, 120);
		frame.add(labelInf);

		JTextArea acidName = new JTextArea("kkk");
		acidName.setSize(150, 20);
		acidName.setLocation(765, 220);
		acidName.setBackground(Color.GRAY);
		infoPane.add(acidName);

		// //////// NONPOLAR PANE

		final AmAcidPane nonPolPane = new AmAcidPane();
		nonPolPane.setBackground(Color.WHITE);
		nonPolPane.setSize(700, 500);
		nonPolPane.setLocation(25, 100);
		nonPolPane.setLayout(null);
		nonPolPane.setVisible(false);
		frame.add(nonPolPane);

		// adding images to nonPolPane
		nonPolPane.addAmAcid1(nonPolPane, "Glycine", "glycine.jpg");
		nonPolPane.addAmAcid2(nonPolPane, "Alanine", "alanine.jpg");
		nonPolPane.addAmAcid3(nonPolPane, "Valine", "valine.jpg");
		nonPolPane.addAmAcid4(nonPolPane, "Leucine", "leucine.jpg");
		nonPolPane.addAmAcid5(nonPolPane, "Methionine", "methionine.jpg");
		nonPolPane.addAmAcid6(nonPolPane, "Isoleucine", "isoleucine.jpg");

		// adding images to polPane
		final AmAcidPane polPane = new AmAcidPane();
		polPane.setBackground(Color.WHITE);
		polPane.setSize(700, 500);
		polPane.setLocation(25, 100);
		polPane.setLayout(null);
		polPane.setVisible(false);
		frame.add(polPane);

		//
		polPane.addAmAcid1(polPane, "Serine", "ser.jpg");
		polPane.addAmAcid2(polPane, "Theronine", "thr.jpg");
		polPane.addAmAcid3(polPane, "Cysteine", "cys.jpg");
		polPane.addAmAcid4(polPane, "Proline", "pro.jpg");
		polPane.addAmAcid5(polPane, "Asparagine", "asn.jpg");
		polPane.addAmAcid6(polPane, "Glutamine", "gln.jpg");

		final AmAcidPane aromPane = new AmAcidPane();
		aromPane.setBackground(Color.WHITE);
		aromPane.setSize(700, 500);
		aromPane.setLocation(25, 100);
		aromPane.setLayout(null);
		aromPane.setVisible(false);

		frame.add(polPane);
		aromPane.addAmAcid1(polPane, "Phenylalanine", "phe.jpg");
		aromPane.addAmAcid2(polPane, "Tyrosine", "tyr.jpg");
		aromPane.addAmAcid3(polPane, "Tryptophan", "trp.jpg");

		// imagePane1Ile.addMouseListener(new ileInfoListener());

		// Buttons
		JButton nonPButton = new JButton("Nonpolar");
		nonPButton.setSize(100, 40);
		nonPButton.setLocation(30, 25);
		frame.add(nonPButton);
		JButton polButton = new JButton("Polar");
		polButton.setSize(100, 40);
		polButton.setLocation(150, 25);
		frame.add(polButton);
		JButton aromButton = new JButton("Aromatic");
		aromButton.setSize(100, 40);
		aromButton.setLocation(270, 25);
		frame.add(aromButton);

		// обработка нажатия
		class AmAcidActionListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				nonPolPane.setVisible(true);
			}
		}
		ActionListener nonPolActionListener = new AmAcidActionListener();
		nonPButton.addActionListener(nonPolActionListener);
		polButton.addActionListener(nonPolActionListener);
		frame.setLayout(null);
		frame.pack();
		frame.setVisible(true);
	}

}
