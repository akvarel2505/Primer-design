package ru.mipt.cs.pd.primers;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PNLPreviousPrimers extends JPanel{
	
	private JLabel previousPrimer;
	
	public PNLPreviousPrimers(){
		previousPrimer=new JLabel(LabelsEN.infoPrevPrimer);
		add(previousPrimer);
		setVisible(true);
	}

}
