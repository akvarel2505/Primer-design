package ru.mipt.cs.pd.primers.interfaces;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ru.mipt.cs.pd.dna.primers.AutoPrimers;
import ru.mipt.cs.pd.primers.PNLEditedExtract;
import ru.mipt.cs.pd.primers.PNLPreviousPrimers;

public interface IntFRMSimplePrimers {
	public void dispose();
	public JTextField getTxtEditPrimer();
	public JLabel getInfoAboutEdited();
	public void setInfoAboutEdited();
	public PNLEditedExtract getPNLEditedExtract();
	public PNLPreviousPrimers getPNLPrevPrmers();
	public DefaultListModel getDefaultListModel();
	public JTextArea getTxtShowGene();
	
	public void showPrimers(AutoPrimers res);
	public void clearAutoPrimers();
	public void clearAll();
}
