package ru.mipt.cs.pd.primers.interfaces;

import ru.mipt.cs.pd.dna.primers.AutoPrimers;

public interface IntPNLPreviousPrimers {
	public AutoPrimers getListsOfAutoPrimers();
	public Object highlightPrimer(int b, int e);
	public void dehighlightPrimer(Object tag);
}	
