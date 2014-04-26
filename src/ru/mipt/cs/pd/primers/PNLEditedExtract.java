package ru.mipt.cs.pd.primers;

import java.awt.event.KeyEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ru.mipt.cs.pd.dna.HandMadeDNA;
import ru.mipt.cs.pd.primers.interfaces.intPnlEditedExtract;
import ru.mipt.cs.pd.references.FirstFrame;

public class PNLEditedExtract extends JPanel implements intPnlEditedExtract{
	
	private JButton btnAnalyseFalseSites, btnReference;  //twoBut
	private JTextField txtEditPrimer;
	private JLabel infoAboutEdited;
	
	private HandMadeDNA currentlyEdited; 
	
	public PNLEditedExtract(){
		
		txtEditPrimer=new JTextField(LabelsEN.initHandPrimer);
		
		btnAnalyseFalseSites = new JButton(LabelsEN.btnAnalyseFalseSites);
		btnAnalyseFalseSites.addActionListener(new PressedBtnAnalyseFalseSites());		
		
		btnReference = new JButton(LabelsEN.reference);
		btnReference.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				FirstFrame x=new FirstFrame();
			}
		});
		
		
		infoAboutEdited = new JLabel(LabelsEN.infoAboutEdited);
		
	
		txtEditPrimer.addKeyListener(new java.awt.event.KeyAdapter() {
			
		       public void keyReleased(KeyEvent e) {
		 
		    	   try {
		    		   setInfoAboutEdited();
		    	   }
		    	   catch (java.lang.NullPointerException ff) {ff.printStackTrace();}
		    	   catch (java.lang.StringIndexOutOfBoundsException ff){System.out.println("CCC");}
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

	@Override
	public void setInfoAboutEdited() {
		   String str1 = txtEditPrimer.getText();
		   currentlyEdited = new HandMadeDNA(str1); 
		   String str2=String.format(LabelsEN.formatInfoAboutEdited, currentlyEdited.getTm(), currentlyEdited.getPercentageGC(), currentlyEdited.getLength());
		   infoAboutEdited.setText(str2);	
	}
	
}
