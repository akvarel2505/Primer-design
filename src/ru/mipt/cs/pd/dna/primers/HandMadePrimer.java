package ru.mipt.cs.pd.dna.primers;

import ru.mipt.cs.pd.dna.EnvironmentConstants;

public class HandMadePrimer extends Primer{
	
	private String sequence;
	
	public HandMadePrimer(String s){
		sequence=s;
		calculateTm();
		percentageGC();
		ru.mipt.cs.pd.dna.EnvironmentConstants.DNAs.add(this);
		if (getLength()>EnvironmentConstants.minLength) findFalseSites();
	}
	
	public String toString(){
		return sequence;
	}

	public int getLength() {
		return sequence.trim().length();
	}
}
