package ru.mipt.cs.pd.dna;

public class TheMainDNAMolecule{
	
	protected int currentShunt;
	protected int startHighlight, endHighlight;
	
	public TheMainDNAMolecule(String str){
		Environment.setMainDNA(str);
	}

}
