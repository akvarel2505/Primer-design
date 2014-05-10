package ru.mipt.cs.pd.dna;

import ru.mipt.cs.pd.utils.DNAStringUtils;

public abstract class AbstractDNA{
	
	protected float Tm;
	protected float percentageGC;
	public abstract String toString();
	public abstract int getLength();
	
	protected float percentageGC(){
		percentageGC=DNAStringUtils.percentageGC(toString());
		return percentageGC;
	}
	
	protected float calculateTm(){	
		Tm = DNAStringUtils.calculateTm(toString());
		return Tm;
	}
	
	public float getTm(){
		return Tm;
	};
	
	public float getPercentageGC(){
		return percentageGC;
	};
	
}


