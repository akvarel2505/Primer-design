package ru.mipt.cs.pd.dna;

public class ExtractFromMainDNA extends AbstractDNA{

	protected int beg,end;
	
	public ExtractFromMainDNA(int b, int e){
		beg=b;
		end=e;
		calculateTm();
		percentageGC=percentageGC();
		ru.mipt.cs.pd.dna.EnvironmentConstants.DNAs.add(this);
	}
	
	public String toString(){
		String res=theMainDNA.substring(beg,end);
		return res;
	}
	
	protected void findFalseSites(){ //find, but not return
		
	}
	
	
}
