package ru.mipt.cs.pd.references;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class AminoAcidFrame extends AmAcidFrame {
	public AminoAcidFrame() {
		createGUI1();
	}

	public static void createGUI1() {

		// Main frame
		final AmAcidFrame frame = new AmAcidFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1000, 650));
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setVisible(true);
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setLayout(null);

		// Info Pane
		InfoPane infoPane = new InfoPane();
		infoPane.setBackground(Color.WHITE);
		infoPane.setSize(250, 450);
		infoPane.setLocation(710, 100);
		infoPane.setLayout(null);
		frame.add(infoPane);
		JLabel labelInf = new JLabel("Amino acid info");
		labelInf.setSize(200, 20);
		labelInf.setLocation(780, 72);
		frame.add(labelInf);
		

		final AmAcidPane nonPolPane = frame.addAmAcidPane(frame);
		nonPolPane.addAmAcid1(nonPolPane, infoPane, "Glycine", "refFiles/glycine.jpg",
				"refFiles/glycine.txt");
		nonPolPane.addAmAcid2(nonPolPane, infoPane, "Alanine", "refFiles/alanine.jpg",
				"refFiles/alanine.txt");
		nonPolPane.addAmAcid3(nonPolPane, infoPane, "Valine", "refFiles/valine.jpg",
				"refFiles/valine.txt");
		nonPolPane.addAmAcid4(nonPolPane, infoPane, "Leucine", "refFiles/leucine.jpg",
				"refFiles/leucine.txt");
		nonPolPane.addAmAcid5(nonPolPane, infoPane, "Methionine",
				"refFiles/methionine.jpg", "refFiles/methionine.txt");
		nonPolPane.addAmAcid6(nonPolPane, infoPane, "Isoleucine",
				"refFiles/isoleucine.jpg", "refFiles/isoleucine.txt");

		final AmAcidPane polPane = frame.addAmAcidPane(frame);
		polPane.addAmAcid1(polPane, infoPane, "Serine", "refFiles/serine.jpg",
				"refFiles/serine.txt");
		polPane.addAmAcid2(polPane, infoPane, "Threonine", "refFiles/threonine.jpg",
				"refFiles/threonine.txt");
		polPane.addAmAcid3(polPane, infoPane, "Cysteine", "refFiles/cysteine.jpg",
				"refFiles/cysteine.txt");
		polPane.addAmAcid4(polPane, infoPane, "Proline", "refFiles/proline.jpg",
				"refFiles/proline.txt");
		polPane.addAmAcid5(polPane, infoPane, "Asparagine", "refFiles/asparagine.jpg",
				"refFiles/asparagine.txt");
		polPane.addAmAcid6(polPane, infoPane, "Glutamine", "refFiles/glutamine.jpg",
				"refFiles/glutamine.txt");

		final AmAcidPane aromPane = frame.addAmAcidPane(frame);
		aromPane.addAmAcid1(aromPane, infoPane, "Phenylalanine",
				"refFiles/phenylalanine.jpg", "refFiles/phenylalanine.txt");
		aromPane.addAmAcid2(aromPane, infoPane, "Tyrosine", "refFiles/tyrosine.jpg",
				"refFiles/tyrosine.txt");
		aromPane.addAmAcid3(aromPane, infoPane, "Tryptophan",
				"refFiles/tryptophane.jpg", "refFiles/tryptophane.txt");

		final AmAcidPane posChPane = frame.addAmAcidPane(frame);
		posChPane.addAmAcid1(posChPane, infoPane, "Lysine", "refFiles/lysine.jpg",
				"refFiles/lysine.txt");
		posChPane.addAmAcid2(posChPane, infoPane, "Arginine", "refFiles/arginine.jpg",
				"refFiles/arginine.txt");
		posChPane.addAmAcid3(posChPane, infoPane, "Histidine", "refFiles/histidine.jpg",
				"refFiles/histidine.txt");

		final AmAcidPane negChPane = frame.addAmAcidPane(frame);
		negChPane.addAmAcid1(negChPane, infoPane, "Aspartate", "refFiles/aspartate.jpg",
				"refFiles/aspartate.txt");
		negChPane.addAmAcid2(negChPane, infoPane, "Glutamine", "refFiles/glutamine.jpg",
				"refFiles/glutamine.txt");

		// Buttons
		JButton nonPButton = frame.addButton(frame, "Nonpolar", 30, 25);
		JButton polButton = frame.addButton(frame, "Polar", 150, 25);
		JButton aromButton = frame.addButton(frame, "Aromatic", 270, 25);
		JButton posChButton = frame.addButton(frame, "Charged (+)", 390, 25);
		JButton negChButton = frame.addButton(frame, "Charged (-)", 510, 25);

		JButton codButton = new JButton("Codon Info");
		codButton.setSize(150, 35);
		codButton.setLocation(760, 562);
		frame.add(codButton);

		class CodListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				CodonFrame x = new CodonFrame();
			}
		}
		ActionListener codListener = new CodListener();
		codButton.addActionListener(codListener);

		class NonPolListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				nonPolPane.setVisible(true);
				polPane.setVisible(false);
				aromPane.setVisible(false);
				negChPane.setVisible(false);
				posChPane.setVisible(false);
			}
		}
		ActionListener nonPolListener = new NonPolListener();
		nonPButton.addActionListener(nonPolListener);

		class PolListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				polPane.setVisible(true);
				nonPolPane.setVisible(false);
				aromPane.setVisible(false);
				negChPane.setVisible(false);
				posChPane.setVisible(false);
			}
		}
		ActionListener polListener = new PolListener();
		polButton.addActionListener(polListener);

		class AromListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				aromPane.setVisible(true);
				polPane.setVisible(false);
				nonPolPane.setVisible(false);
				negChPane.setVisible(false);
				posChPane.setVisible(false);
			}
		}
		ActionListener aromListener = new AromListener();
		aromButton.addActionListener(aromListener);

		class PosChListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				posChPane.setVisible(true);
				polPane.setVisible(false);
				nonPolPane.setVisible(false);
				aromPane.setVisible(false);
				negChPane.setVisible(false);
			}
		}
		ActionListener posChListener = new PosChListener();
		posChButton.addActionListener(posChListener);

		class NegChListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				negChPane.setVisible(true);
				polPane.setVisible(false);
				nonPolPane.setVisible(false);
				aromPane.setVisible(false);
				posChPane.setVisible(false);
			}
		}
		ActionListener negChListener = new NegChListener();
		negChButton.addActionListener(negChListener);
		frame.pack();	
	}

}
