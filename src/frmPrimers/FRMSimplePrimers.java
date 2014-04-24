package frmPrimers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;






import frmPrimers.interfaces.intFRMSimplePrimers;
import DNA.HandMadeDNA;
import DNA.SimpleExtract;
import restrictionEnzymes.RenzymeMass;
import restrictionEnzymes.RenzymeWithANumber;

public class FRMSimplePrimers extends JFrame implements intFRMSimplePrimers{
	
	private TitledBorder brgLblPrimer, brdEdit, brdSol;
	
	//panels
	private pnlGeneAndRenzymes geneAndRenzymes;
	private pnlEditedExtract editedExtract;
	private pnlSolutionParameters solutionParameters;
	private pnlPreviousPrimers previousPrimers;
	
	public FRMSimplePrimers(SimpleExtract passedGene, RenzymeMass restr){
		initComponents(passedGene, restr);
	}
	
	private void initComponents(SimpleExtract gene, RenzymeMass restr){
		
		
		geneAndRenzymes = new pnlGeneAndRenzymes(gene, restr, this);
		
		editedExtract = new pnlEditedExtract();
		brdEdit = new TitledBorder(labelsEN.editTitledBrd);
		editedExtract.setBorder(brdEdit);
		
		previousPrimers=new pnlPreviousPrimers();
		brgLblPrimer = new TitledBorder(labelsEN.infoPrevPrimer);
		previousPrimers.setBorder(brgLblPrimer);
		
		solutionParameters=new pnlSolutionParameters();
		brdSol = new TitledBorder(labelsEN.lblSolutionParameters);
		solutionParameters.setBorder(brdSol);
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); ///именно dispose, а не exit
		setTitle(labelsEN.primerTitle);

		/*============================================*/
		
		
		// DESIGN OF THE FRAME		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		
		//creating horizontal group, ordinate axis is compressed	
		
		GroupLayout.SequentialGroup topGrp = layout.createSequentialGroup();
		topGrp.addComponent(previousPrimers);
		topGrp.addComponent(solutionParameters);
		
		GroupLayout.ParallelGroup HORbigGroup = layout.createParallelGroup();
		HORbigGroup.addGroup(topGrp);
		HORbigGroup.addComponent(editedExtract);
		HORbigGroup.addComponent(geneAndRenzymes, GroupLayout.Alignment.CENTER);
	
		
		layout.setHorizontalGroup(HORbigGroup);
	
		
		//creating vertical group, X axis is compressed	

		GroupLayout.ParallelGroup VtopGrp = layout.createParallelGroup();
		VtopGrp.addComponent(previousPrimers);
		VtopGrp.addComponent(solutionParameters);
		
		GroupLayout.SequentialGroup VERbigGroup = layout.createSequentialGroup();
		VERbigGroup.addGroup(VtopGrp);
		VERbigGroup.addComponent(editedExtract);
		VERbigGroup.addComponent(geneAndRenzymes);
		
		layout.setVerticalGroup(VERbigGroup);
		
		//finally
		setSize(new Dimension(900,600));
		
		setVisible(true);
	}

	@Override
	public JTextField getTxtEditPrimer() {
		return editedExtract.getTxtEditPrimer();
	}

	@Override
	public JLabel getInfoAboutEdited() {
		return editedExtract.getInfoAboutEdited();
	}


	
}


