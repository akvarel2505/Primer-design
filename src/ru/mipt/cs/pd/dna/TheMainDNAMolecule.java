package ru.mipt.cs.pd.dna;

public class TheMainDNAMolecule extends ParentWithMainDNA {
	
	protected int currentShunt;
	protected int startHighlight, endHighlight;
	
	public TheMainDNAMolecule(String str){
		super.setMainDNA(str);
	}

}
