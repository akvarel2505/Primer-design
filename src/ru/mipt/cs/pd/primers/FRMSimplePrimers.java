package ru.mipt.cs.pd.primers;

import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import ru.mipt.cs.pd.dna.SimpleExtract;
import ru.mipt.cs.pd.primers.interfaces.intFRMSimplePrimers;
import ru.mipt.cs.pd.restrictases.RenzymeMass;

public class FRMSimplePrimers extends JFrame implements intFRMSimplePrimers{
	
	private TitledBorder brgLblPrimer, brdEdit, brdSol;
	
	//panels
	private PNLGeneAndRenzymes geneAndRenzymes;
	private PNLEditedExtract editedExtract;
	private PNLSolutionParameters solutionParameters;
	private PNLPreviousPrimers previousPrimers;
	
	public FRMSimplePrimers(SimpleExtract passedGene, RenzymeMass restr){
		initComponents(passedGene, restr);
	}
	
	private void initComponents(SimpleExtract gene, RenzymeMass restr){
		
		
		geneAndRenzymes = new PNLGeneAndRenzymes(gene, restr, this);
		
		editedExtract = new PNLEditedExtract();
		brdEdit = new TitledBorder(LabelsEN.editTitledBrd);
		editedExtract.setBorder(brdEdit);
		
		previousPrimers=new PNLPreviousPrimers();
		brgLblPrimer = new TitledBorder(LabelsEN.infoPrevPrimer);
		previousPrimers.setBorder(brgLblPrimer);
		
		solutionParameters=new PNLSolutionParameters(this);
		brdSol = new TitledBorder(LabelsEN.lblSolutionParameters);
		solutionParameters.setBorder(brdSol);
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); ///именно dispose, а не exit
		setTitle(LabelsEN.primerTitle);

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

	@Override
	public void setInfoAboutEdited() {
		editedExtract.setInfoAboutEdited();
	}

	@Override
	public PNLEditedExtract getPNLEditedExtract() {
		return editedExtract;
	}


	
}


