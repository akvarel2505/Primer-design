package frmPrimers;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class pnlPreviousPrimers extends JPanel{
	
	private JLabel previousPrimer;
	
	public pnlPreviousPrimers(){
		previousPrimer=new JLabel(labelsEN.infoPrevPrimer);
		add(previousPrimer);
		setVisible(true);
	}

}
