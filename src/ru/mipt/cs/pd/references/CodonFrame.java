package ru.mipt.cs.pd.references;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

import org.apache.http.client.ClientProtocolException;


@SuppressWarnings("serial")
public class CodonFrame extends JFrame {
	
	public CodonFrame() {
		createGUI2();
	}

	public static void createGUI2() {
		final JFrame frame = new JFrame("Codon inormation");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1000, 700));
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		JFrame.setDefaultLookAndFeelDecorated(true);

		final JTextArea list1 = new JTextArea();
		list1.setText("Enter some correct name, press the button\nand wait 1-3 seconds");
		list1.setBackground(Color.WHITE);
		list1.setSize(400, 1500);
		list1.setLocation(25, 120);
		list1.setVisible(true);
		frame.add(list1);
		JScrollPane scrollPane = new JScrollPane(list1);
		scrollPane.setSize(400, 500);
		scrollPane.setLocation(25, 120);
		frame.add(scrollPane);
		

		final JTextField name1 = new JTextField("Enter name of the organism");
		name1.setSize(200, 30);
		name1.setLocation(30, 30);
		frame.add(name1);

		JButton button1 = new JButton("Start!");
		button1.setSize(100, 40);
		button1.setLocation(300, 30);
		button1.setBackground(Color.WHITE);
		frame.add(button1);

		final JPanel pane2 = new JPanel();
		pane2.setBackground(Color.WHITE);
		pane2.setSize(400, 600);
		pane2.setLocation(500, 25);
		pane2.setLayout(null);
		pane2.setVisible(true);
		frame.add(pane2);
		
		// codon table 1
		final JEditorPane list11 = new JEditorPane();
		list11.setText("Enter the index,\npress the button\nand wait \n1-3 seconds");
		list11.setBackground(Color.WHITE);
		list11.setSize(120, 600);
		list11.setLocation(20, 70);
		list11.setVisible(true);
		pane2.add(list11);
		final JEditorPane list22 = new JEditorPane();
		list22.setBackground(Color.WHITE);
		list22.setSize(120, 600);
		list22.setLocation(150, 70);
		list22.setVisible(true);
		pane2.add(list22);

		final JTextField index = new JTextField("Enter the index");
		index.setSize(200, 30);
		index.setLocation(20, 20);
		pane2.add(index);
		index.setBackground(Color.GRAY);
		index.setVisible(true);

		JButton button2 = new JButton("Start!");
		button2.setSize(100, 40);
		button2.setLocation(280, 10);
		button2.setBackground(Color.WHITE);
		pane2.add(button2);

		class MyListener1 implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				pane2.setVisible(true);
				Net list = new Net();
				String a = new String("");
				try {
					String speciesName = name1.getText();
					if (speciesName.indexOf(' ')<0) {
						a = list.getListOfSpecies(speciesName);
					}
					else{
						list11.setText("There mustn't be any spaces!");
					}
				} catch (ClientProtocolException e1) {
					list11.setText("Connection error");
					e1.printStackTrace();
				} catch (IOException e1) {
					list11.setText("error");
				}
				list1.setText(a);
			}
		}
		ActionListener startListener = new MyListener1();
		button1.addActionListener(startListener);

		class MyListener2 implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				pane2.setVisible(true);
				Net listNet1 = new Net();
				Net listNet2 = new Net();
				try {
					listNet1.getTable1(index.getText());
					File f1 = new File("file1");
					FileReader in1 = new FileReader(f1);
					char[] arr1 = new char[(int) f1.length()];
					int read1 = in1.read(arr1);
					list11.setText(String.valueOf(arr1, 0, read1));

					listNet2.getTable2(index.getText());
					File f2 = new File("file2");
					FileReader in2 = new FileReader(f2);
					char[] arr2 = new char[(int) f2.length()];
					int read = in2.read(arr2);
					list22.setText(String.valueOf(arr2, 0, read));
					in2.close();

				} catch (ClientProtocolException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		ActionListener start2Listener = new MyListener2();
		button2.addActionListener(start2Listener);

		JLabel labelInf = new JLabel("Codon - frequency(per 1000) - (number)");
		labelInf.setSize(300, 20);
		labelInf.setLocation(20, 50);
		pane2.add(labelInf);
		
		frame.setLayout(null);
		frame.pack();
		frame.setVisible(true);
	}

	
}
