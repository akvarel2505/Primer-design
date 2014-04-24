package frmPrimers;

import java.awt.event.KeyEvent;

import references.FirstFrame;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import frmPrimers.interfaces.intPnlEditedExtract;
import DNA.HandMadeDNA;

public class pnlEditedExtract extends JPanel implements intPnlEditedExtract{
	
	private JButton btnAnalyseFalseSites, btnReference;  //twoBut
	private JTextField txtEditPrimer;
	private JLabel infoAboutEdited;
	
	private HandMadeDNA currentlyEdited; 
	
	public pnlEditedExtract(){
		
		txtEditPrimer=new JTextField(labelsEN.initHandPrimer);
		
		btnAnalyseFalseSites = new JButton(labelsEN.btnAnalyseFalseSites);
		btnAnalyseFalseSites.addActionListener(new PressedBtnAnalyseFalseSites());		
		
		btnReference = new JButton(labelsEN.reference);
		btnReference.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				FirstFrame x=new FirstFrame();
			}
		});
		
		
		infoAboutEdited = new JLabel(labelsEN.infoAboutEdited);
		
	
		txtEditPrimer.addKeyListener(new java.awt.event.KeyAdapter() {
			
		       public void keyReleased(KeyEvent e) {
		 
		    	   try {
		    		   String str1 = txtEditPrimer.getText();
		    		   currentlyEdited = new HandMadeDNA(str1); 
		    		   String str2=String.format(labelsEN.formatInfoAboutEdited, currentlyEdited.getTm(), currentlyEdited.getPercentageGC(), currentlyEdited.getLength());
		    		   infoAboutEdited.setText(str2);
		    	   }
		    	   catch (java.lang.NullPointerException ff) {}
		    	   catch (java.lang.StringIndexOutOfBoundsException ff){}
		       }
		});
		
		
		//Design
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		
		//Vertical Group
		GroupLayout.ParallelGroup Vert = layout.createParallelGroup();
		
		GroupLayout.SequentialGroup primerWithLabel = layout.createSequentialGroup();
		primerWithLabel.addComponent(infoAboutEdited);
		primerWithLabel.addComponent(txtEditPrimer);
		
		GroupLayout.SequentialGroup twoBut = layout.createSequentialGroup();
		twoBut.addComponent(btnAnalyseFalseSites);
		twoBut.addComponent(btnReference);
		
		Vert.addGroup(GroupLayout.Alignment.TRAILING, primerWithLabel);
		Vert.addGroup(GroupLayout.Alignment.TRAILING, twoBut);
		
		layout.setVerticalGroup(Vert);
		
		//Horizontal Group
		
		GroupLayout.SequentialGroup Hor = layout.createSequentialGroup();
		
		GroupLayout.ParallelGroup VprimerWithLabel = layout.createParallelGroup();
		VprimerWithLabel.addComponent(infoAboutEdited);
		VprimerWithLabel.addComponent(txtEditPrimer);
		
		GroupLayout.ParallelGroup VtwoBut = layout.createParallelGroup();
		VtwoBut.addComponent(btnAnalyseFalseSites);
		VtwoBut.addComponent(btnReference);
		
		Hor.addGroup(VprimerWithLabel);
	    Hor.addGroup(VtwoBut);
		
		layout.setHorizontalGroup(Hor);
		
		layout.linkSize(btnAnalyseFalseSites, btnReference);
		layout.linkSize(SwingConstants.VERTICAL, txtEditPrimer, btnReference, infoAboutEdited);
		
		setVisible(true);
	}

	@Override
	public JTextField getTxtEditPrimer() {
		return txtEditPrimer;
	}

	@Override
	public JLabel getInfoAboutEdited() {
		return infoAboutEdited;
	}
	
}
