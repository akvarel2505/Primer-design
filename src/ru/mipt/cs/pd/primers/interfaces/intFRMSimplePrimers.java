package ru.mipt.cs.pd.primers.interfaces;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ru.mipt.cs.pd.primers.PNLEditedExtract;

public interface intFRMSimplePrimers {
	public void dispose();
	public JTextField getTxtEditPrimer();
	public JLabel getInfoAboutEdited();
	public void setInfoAboutEdited();
	public PNLEditedExtract getPNLEditedExtract();
	public DefaultListModel getDefaultListModel();
}
