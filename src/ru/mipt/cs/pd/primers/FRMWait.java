package ru.mipt.cs.pd.primers;

import java.awt.Container;
import java.awt.Point;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class FRMWait extends JFrame{
	
	private JLabel lblWait = new JLabel();
	
	public FRMWait(Point p){
			
		ImageIcon icon = new ImageIcon("wait.jpg", "some image");
		lblWait = new JLabel(icon);
		
		makeDesign();
			
		setTitle(LabelsEN.lblWait);
		setAlwaysOnTop(true);
		setLocation(p);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		pack();
		setResizable(false);
		setVisible(true);
	}
	
	private void makeDesign(){
		
		Container cont = this.getContentPane();
		cont.add(lblWait);
	
	}
	
}
