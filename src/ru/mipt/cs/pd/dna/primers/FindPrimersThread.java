package ru.mipt.cs.pd.dna.primers;

import java.util.ArrayList;

import ru.mipt.cs.pd.dna.Environment;
import ru.mipt.cs.pd.dna.primers.AutoPrimersAlg.Enzyme;
import ru.mipt.cs.pd.utils.DNAStringUtils;

public class FindPrimersThread extends Thread{
	
	private Enzyme enzyme;
	private String mainDNA;
	private ArrayList<AutoPrimer> res;
	
	private final float desTm;
	private final boolean ifRight;
	private final static int enzymeOffset=6;
	private final static int maxLength=50;
	private final static int minLength=12;
	
	public FindPrimersThread(Enzyme enzyme, String mainDNA, ArrayList<AutoPrimer> result, boolean ifRight){
		desTm=Environment.desiredTm;
		this.enzyme=enzyme;
		this.mainDNA=mainDNA;
		this.res=result;
		this.ifRight=ifRight;
	}
	
	public void run(){
		
		//initialization
		int begPos=enzyme.begin-enzymeOffset;
		if (begPos<0) begPos=0;
		int endPos=begPos+maxLength;
		if (endPos>mainDNA.length()) endPos=mainDNA.length();
		
		int newBegPos;
		AutoPrimer curr;
		
		int bufBeg, bufEnd;
		
		int endOfSearch = enzyme.begin + enzyme.length + enzymeOffset;
		
		//search throw all good 3'-ends
		while (endPos>=endOfSearch){
			if (if3EndGood(endPos)){
				//try to find desired Tm
				newBegPos=findProperTm(begPos, endPos);
				
				if (ifRight){
					bufBeg = mainDNA.length() - endPos;
					bufEnd = mainDNA.length() - newBegPos;
				}
				else{
					bufBeg = newBegPos; 
					bufEnd = endPos;
				}
				
				curr = new AutoPrimer(bufBeg, bufEnd, ifRight);				
				res.add(curr);
			}			
			endPos--;
		}
		
	}
	
	private int findProperTm(final int begPos, final int endPos){
		
		//initialization
		
		int newBegPos=0;
		float currTm;
		
		int minBegPos, maxBegPos;
		if ((endPos-maxLength)<0) minBegPos=0;
		else minBegPos=endPos-maxLength;
		maxBegPos=endPos-minLength;
		if (maxBegPos>begPos) maxBegPos=begPos;
		
		currTm=DNAStringUtils.calculateTm(mainDNA.substring(minBegPos, endPos));
		if ((desTm>currTm)||(ifNear(desTm,currTm))) return minBegPos;
		
		currTm=DNAStringUtils.calculateTm(mainDNA.substring(maxBegPos, endPos));
		if ((desTm<currTm)||ifNear(desTm,currTm)) return minBegPos;
		
		int farPos=minBegPos;
		int nearPos=maxBegPos;
		
		//binary search
		while (farPos < nearPos) {
	        newBegPos = farPos + (nearPos - farPos)/2;
	        currTm=DNAStringUtils.calculateTm(mainDNA.substring(newBegPos,endPos));
	        if (currTm < desTm) nearPos = newBegPos;
	        else farPos = newBegPos + 1;
	    }
		
		return newBegPos;
		
	}
	
	private boolean ifNear(float a, float b){
		final float delta=2;
		return (Math.abs(a-b)<delta);
	};
	
	private boolean if3EndGood(int pos){
		boolean res=true;
		
		if (pos<minLength) return false;
		
		int kGC=0;
		int i=pos-2;
		char ch=mainDNA.charAt(pos-1);
		
		if (ifGC(ch)) { //it's good when the end of primer is GC
			kGC++;
			while (i>pos-6){
				ch=mainDNA.charAt(i);
				if (ifGC(ch)) kGC++;
				i--;
			}
		}
		if ((kGC<3)||(kGC==5)) res=false;
		return res;
	}

	private boolean ifGC(char ch) {
		return ((ch=='g')||(ch=='G')||(ch=='c')||(ch=='C'));
	}

}
