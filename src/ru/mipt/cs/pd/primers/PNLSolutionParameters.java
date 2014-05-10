package ru.mipt.cs.pd.primers;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ru.mipt.cs.pd.dna.Environment;
import ru.mipt.cs.pd.primers.interfaces.IntFRMSimplePrimers;

public class PNLSolutionParameters extends JPanel{
	
	private JLabel lblPrimerConc, lblSaltConc, lblMgConc, lblTm;
	private JButton btnApply;
	private JTextField txtPrimerConc, txtSaltConc, txtMgConc, txtTm;
	private IntFRMSimplePrimers frmParent;
	
	public PNLSolutionParameters(IntFRMSimplePrimers parent){
		
		lblPrimerConc = new JLabel(LabelsEN.lblPrimerConc);
		lblSaltConc = new JLabel(LabelsEN.lblSaltConc);
		lblMgConc = new JLabel(LabelsEN.lblMgConc);
		lblTm = new JLabel(LabelsEN.desTm);
		btnApply = new JButton(LabelsEN.apply);
		txtPrimerConc = new JTextField(String.format("%.0f", Environment.primerConc));
		txtSaltConc = new JTextField(String.format("%.0f", Environment.saltConc));
		txtMgConc = new JTextField(String.format("%.0f", Environment.MgConc));
		txtTm = new JTextField(String.format("%.0f", Environment.desiredTm));
		frmParent=parent;
		
		//button apply listener
		btnApply.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Environment.primerConc=Float.parseFloat(txtPrimerConc.getText());
				Environment.saltConc=Float.parseFloat(txtSaltConc.getText());
				Environment.MgConc=Float.parseFloat(txtMgConc.getText());
				Environment.desiredTm=Float.parseFloat(txtTm.getText());
				Environment.reCalculateTm();
				try{
					frmParent.setInfoAboutEdited();
				}
				catch (NullPointerException e){}
			}
		});
		
		makeDesign();
			
		enableEdit();
		setVisible(true);

	}
	
	public void enableEdit(){
		txtPrimerConc.setEditable(true);
		txtSaltConc.setEditable(true);
		txtMgConc.setEditable(true);
		txtTm.setEditable(true);
		btnApply.setEnabled(true);
	}
	
	public void disableEdit(){
		txtPrimerConc.setEditable(false);
		txtSaltConc.setEditable(false);
		txtMgConc.setEditable(false);
		txtTm.setEditable(false);
		btnApply.setEnabled(false);
	}
	
	private void makeDesign(){
		//DESIGN
		
				GroupLayout layout = new GroupLayout(this);
				setLayout(layout);
				layout.setAutoCreateContainerGaps(true);
				layout.setAutoCreateGaps(true);
				
				int i;
				final int tblSize=4;
				//horizontal
				GroupLayout.ParallelGroup Hor = layout.createParallelGroup();
				
				GroupLayout.SequentialGroup[] tableH = new GroupLayout.SequentialGroup[tblSize];
				for (i=0; i<tblSize; i++) tableH[i] = layout.createSequentialGroup();
				
				tableH[0].addComponent(lblPrimerConc);
				tableH[0].addComponent(txtPrimerConc);
				tableH[1].addComponent(lblSaltConc);
				tableH[1].addComponent(txtSaltConc);
				tableH[2].addComponent(lblMgConc);
				tableH[2].addComponent(txtMgConc);
				tableH[3].addComponent(lblTm);
				tableH[3].addComponent(txtTm);
				
				for (i=0; i<tblSize; i++) Hor.addGroup(tableH[i]);
				Hor.addComponent(btnApply, GroupLayout.Alignment.CENTER);
				
				layout.setHorizontalGroup(Hor);
				
				//vertical
				GroupLayout.SequentialGroup Ver = layout.createSequentialGroup();
				
				GroupLayout.ParallelGroup[] tableV = new GroupLayout.ParallelGroup[tblSize];
				for (i=0; i<tblSize; i++) tableV[i] = layout.createParallelGroup();
				
				tableV[0].addComponent(lblPrimerConc);
				tableV[0].addComponent(txtPrimerConc);
				tableV[1].addComponent(lblSaltConc);
				tableV[1].addComponent(txtSaltConc);
				tableV[2].addComponent(lblMgConc);
				tableV[2].addComponent(txtMgConc);
				tableV[3].addComponent(lblTm);
				tableV[3].addComponent(txtTm);
				
				for (i=0; i<tblSize; i++) Ver.addGroup(tableV[i]);
				Ver.addComponent(btnApply);
				
				layout.setVerticalGroup(Ver);
				
				layout.linkSize(lblPrimerConc, lblSaltConc, lblMgConc, lblTm);
				layout.linkSize(txtPrimerConc, txtSaltConc, txtMgConc, txtTm);
			
	}
}
