package ru.mipt.cs.pd.dna.primers;

import ru.mipt.cs.pd.dna.AbstractDNA;
import ru.mipt.cs.pd.primers.LabelsEN;
import ru.mipt.cs.pd.utils.DNAStringUtils;

public class FalseSite extends AbstractDNA{

	private int mainBeg, mainEnd, primerBeg, primerEnd;
	private int strand;
	
	public FalseSite(int mainB, int mainE, int primerB, int primerE, int strand){
		this.mainBeg=mainB;
		this.mainEnd=mainE;
		this.primerBeg=primerB;
		this.primerEnd=primerE;
		this.strand=strand;
		calculateTm();
		percentageGC();
		ru.mipt.cs.pd.dna.EnvironmentConstants.DNAs.add(this);
	}
	
	public String toString(){ //for calculations
		if (strand==35) return theMainDNA.substring(mainBeg, mainEnd);
		else return DNAStringUtils.comRev(theMainDNA).substring(mainBeg, mainEnd);
	}
	
	public String toString(boolean a){ //for frame
		String res;
		res=toString()+String.format(LabelsEN.frmtInfoFalseSite, Tm, percentageGC, strand, mainBeg, mainEnd);
		return res;
	}
	
	// end of code
	//getters
	//
	
	public int getLength() {
		return (mainEnd-mainBeg);
	}
	
	public int getMainBeg() {
		return mainBeg;
	}

	public int getMainEnd() {
		return mainEnd;
	}

	public int getPrimerBeg() {
		return primerBeg;
	}

	public int getPrimerEnd() {
		return primerEnd;
	}

}
