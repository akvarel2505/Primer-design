package ru.mipt.cs.pd.primers.interfaces;

import javax.swing.JLabel;
import javax.swing.JTextField;

public interface intFRMSimplePrimers {
	public void dispose();
	public JTextField getTxtEditPrimer();
	public JLabel getInfoAboutEdited();
	public void setInfoAboutEdited();
}
