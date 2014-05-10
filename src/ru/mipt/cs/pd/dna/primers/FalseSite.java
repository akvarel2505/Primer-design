package ru.mipt.cs.pd.dna.primers;

import ru.mipt.cs.pd.dna.AbstractDNA;
import ru.mipt.cs.pd.dna.Environment;
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
		synchronized (Environment.DNAs){
			ru.mipt.cs.pd.dna.Environment.DNAs.add(this);
		}
	}
	
	public String toString(){ //for calculations
		if (strand==35) return Environment.theMainDNA.substring(mainBeg, mainEnd);
		else return DNAStringUtils.comRev(Environment.theMainDNA).substring(mainBeg, mainEnd);
	}
	
	public String toString(boolean a){ //for frame
		String res;
		res=toString()+String.format(LabelsEN.frmtInfoFalseSite, Tm, percentageGC, strand, mainBeg, mainEnd);
		return res;
	}
	
	//TODO
	//calculateTm() - another method
	
	public void die(){
		synchronized (Environment.DNAs){
			Environment.DNAs.remove(this);
		}
	}
	
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
	
	public void setPrimerLocation(int b, int e){
		primerBeg=b;
		primerEnd=e;
	}

}
