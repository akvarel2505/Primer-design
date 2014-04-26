package ru.mipt.cs.pd.primers;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ru.mipt.cs.pd.dna.EnvironmentConstants;
import ru.mipt.cs.pd.primers.interfaces.intFRMSimplePrimers;

public class PNLSolutionParameters extends JPanel{
	
	private JLabel lblPrimerConc, lblSaltConc, lblMgConc;
	private JButton btnApply;
	private JTextField txtPrimerConc, txtSaltConc, txtMgConc;
	private intFRMSimplePrimers frmParent;
	
	public PNLSolutionParameters(intFRMSimplePrimers parent){
		
		lblPrimerConc = new JLabel(LabelsEN.lblPrimerConc);
		lblSaltConc = new JLabel(LabelsEN.lblSaltConc);
		lblMgConc = new JLabel(LabelsEN.lblMgConc);
		btnApply = new JButton(LabelsEN.apply);
		txtPrimerConc = new JTextField(String.format("%.0f", EnvironmentConstants.primerConc));
		txtSaltConc = new JTextField(String.format("%.0f", EnvironmentConstants.saltConc));
		txtMgConc = new JTextField(String.format("%.0f", EnvironmentConstants.MgConc));
		frmParent=parent;
		
		//button apply listener //TODO
		btnApply.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				EnvironmentConstants.primerConc=Float.parseFloat(txtPrimerConc.getText());
				EnvironmentConstants.saltConc=Float.parseFloat(txtSaltConc.getText());
				EnvironmentConstants.MgConc=Float.parseFloat(txtMgConc.getText());
				EnvironmentConstants.reCalculateTm();
				try{
					frmParent.setInfoAboutEdited();
				}
				catch (NullPointerException e){}
			}
		});
		
		
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
		Hor.addComponent(btnApply, GroupLayout.Alignment.CENTER);
		
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
		Ver.addComponent(btnApply);
		
		layout.setVerticalGroup(Ver);
		
		layout.linkSize(lblPrimerConc, lblSaltConc, lblMgConc);
		layout.linkSize(txtPrimerConc, txtSaltConc, txtMgConc);
		
		setVisible(true);
	}
}
