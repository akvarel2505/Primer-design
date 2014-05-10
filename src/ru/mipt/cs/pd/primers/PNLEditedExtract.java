package ru.mipt.cs.pd.primers;

import java.awt.event.KeyEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ru.mipt.cs.pd.dna.Environment;
import ru.mipt.cs.pd.dna.primers.HandMadePrimer;
import ru.mipt.cs.pd.dna.primers.Primer;
import ru.mipt.cs.pd.primers.interfaces.IntFRMSimplePrimers;
import ru.mipt.cs.pd.primers.interfaces.IntPNLEditedExtract;
import ru.mipt.cs.pd.references.AminoAcidFrame;

public class PNLEditedExtract extends JPanel implements IntPNLEditedExtract{
	
	private JButton btnAnalyseFalseSites, btnReference;  //twoBut
	private JTextField txtEditPrimer;
	private JLabel infoAboutEdited;
	private Primer primer;
	private HandMadePrimer currentlyEdited;
	private IntFRMSimplePrimers parent;
	
	public Primer getPrimer() {
		return primer;
	}
	
	public void setPrimer(Primer x) {
		primer=x;
	}
	
	public PNLEditedExtract(IntFRMSimplePrimers x){
		
		parent=x;
		txtEditPrimer=new JTextField(LabelsEN.initHandPrimer);
		
		btnAnalyseFalseSites = new JButton(LabelsEN.btnAnalyseFalseSites);
		
		btnAnalyseFalseSites.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {				
				try{
					if (Environment.DNAs.contains(primer)){}
					else{
						Environment.DNAs.add(primer);
					}
					FRMPrimerInfo x = new FRMPrimerInfo(primer, parent.getDefaultListModel(), Environment.leftPrimers, null);
				}
				catch (NullPointerException e){}
			}
		});
		
		btnReference = new JButton(LabelsEN.reference);
		btnReference.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AminoAcidFrame x=new AminoAcidFrame();
			}
		});
		
		
		infoAboutEdited = new JLabel(LabelsEN.infoAboutEdited);
		
	
		txtEditPrimer.addKeyListener(new java.awt.event.KeyAdapter() {			
		       public void keyReleased(KeyEvent e) {		 
		    	   try {
		    		   setInfoAboutEdited();
		    	   }
		    	   catch (java.lang.NullPointerException ff) {}
		    	   catch (java.lang.StringIndexOutOfBoundsException ff){}
		    	   
		       }
		});
		
		
		makeDesign();		
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
		    try{
				primer.die();
			}
		    catch (java.lang.NullPointerException e){}
		   String str1 = txtEditPrimer.getText().trim();
		   currentlyEdited = new HandMadePrimer(str1); 
		   String str2=String.format(LabelsEN.formatInfoAboutEdited, currentlyEdited.getTm(), currentlyEdited.getLength(), currentlyEdited.getPercentageGC());
		   infoAboutEdited.setText(str2);
		   primer = currentlyEdited;
	}
	
	private void makeDesign(){
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

	}

}
