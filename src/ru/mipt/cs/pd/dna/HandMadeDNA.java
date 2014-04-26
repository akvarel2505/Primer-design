package ru.mipt.cs.pd.dna;



public class HandMadeDNA extends AbstractDNA{
	
	private String sequence;
	
	public HandMadeDNA(String s){
		sequence=s;
		calculateTm();
		percentageGC=percentageGC();
		ru.mipt.cs.pd.dna.EnvironmentConstants.DNAs.add(this);
	}
	
	public String toString(){
		return sequence;
	}
	
	protected void findFalseSites(){ //find, but not return
		
	}

	public int getLength() {
		return sequence.length();
	}
}
