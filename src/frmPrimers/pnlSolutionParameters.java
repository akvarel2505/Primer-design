package frmPrimers;

import java.awt.GridLayout;

import javax.swing.*;

public class pnlSolutionParameters extends JPanel{
	
	private JLabel lblPrimerConc, lblSaltConc, lblMgConc;
	private JButton apply;
	private JTextField txtPrimerConc, txtSaltConc, txtMgConc;
	
	public pnlSolutionParameters(){
		
		lblPrimerConc = new JLabel(labelsEN.lblPrimerConc);
		lblSaltConc = new JLabel(labelsEN.lblSaltConc);
		lblMgConc = new JLabel(labelsEN.lblMgConc);
		apply = new JButton(labelsEN.apply);
		txtPrimerConc = new JTextField(String.format("%.2f", DNA.EnvironmentConstants.primerConc));
		txtSaltConc = new JTextField(String.format("%.2f", DNA.EnvironmentConstants.saltConc));
		txtMgConc = new JTextField(String.format("%.2f", DNA.EnvironmentConstants.MgConc));
		
		
		//button apply listener //TODO
		
		
		//DESIGN
		
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		int i;
		
		//horizontal
		GroupLayout.ParallelGroup Hor = layout.createParallelGroup();
		
		GroupLayout.SequentialGroup[] tableH = new GroupLayout.SequentialGroup[3];
		for (i=0; i<3; i++) tableH[i] = layout.createSequentialGroup();
		
		tableH[0].addComponent(lblPrimerConc);
		tableH[0].addComponent(txtPrimerConc);
		tableH[1].addComponent(lblSaltConc);
		tableH[1].addComponent(txtSaltConc);
		tableH[2].addComponent(lblMgConc);
		tableH[2].addComponent(txtMgConc);
		
		for (i=0; i<3; i++) Hor.addGroup(tableH[i]);
		Hor.addComponent(apply, GroupLayout.Alignment.CENTER);
		
		layout.setHorizontalGroup(Hor);
		
		//vertical
		GroupLayout.SequentialGroup Ver = layout.createSequentialGroup();
		
		GroupLayout.ParallelGroup[] tableV = new GroupLayout.ParallelGroup[3];
		for (i=0; i<3; i++) tableV[i] = layout.createParallelGroup();
		
		tableV[0].addComponent(lblPrimerConc);
		tableV[0].addComponent(txtPrimerConc);
		tableV[1].addComponent(lblSaltConc);
		tableV[1].addComponent(txtSaltConc);
		tableV[2].addComponent(lblMgConc);
		tableV[2].addComponent(txtMgConc);
		
		for (i=0; i<3; i++) Ver.addGroup(tableV[i]);
		Ver.addComponent(apply);
		
		layout.setVerticalGroup(Ver);
		
		layout.linkSize(lblPrimerConc, lblSaltConc, lblMgConc);
		layout.linkSize(txtPrimerConc, txtSaltConc, txtMgConc);
		
		setVisible(true);
	}
}
