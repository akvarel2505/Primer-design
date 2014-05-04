package ru.mipt.cs.pd.dna;

public abstract class AbstractDNA extends ParentWithMainDNA{
	
	// thermodynamics parameters
	final String ntides[] = {"aa",      "ac",       "ag",          "at",        "ca",        "cc",         "cg",             "ct",       "ga",          "gc",       "gg",         "gt",        "ta",        "tc",             "tg",             "tt"};
	final float dH[] = {(float)-7.9, (float)-8.4,  (float)-7.8,  (float)-7.2 , (float)-8.5, (float)-8,    (float)-10.6,  (float)-7.8, (float)-8.2,   (float)-9.8, (float)-8,   (float)-8.4,  (float)-7.2,  (float)-8.2,   (float)-8.5 ,    (float)-7.9};
	final float dS[] = {(float)-22.2,(float)-22.4, (float)-21, (float)-20.4 , (float)-22.7, (float)-19.9, (float)-27.2, (float)-21.0, (float)-22.2, (float)-24.4, (float)-19.9, (float)-22.4, (float)-21.3, (float)-22.2, (float)-22.7,   (float)-22.2};
	
	protected float Tm;
	protected float percentageGC;
	public abstract String toString();
	public abstract int getLength();
	
	protected float percentageGC(){
		int i, gc=0;
		float res;
		String str=toString();
		for (i=0; i<str.length(); i++){
			if ("GcgC".contains(str.substring(i,i+1))){
				gc++;
			}
		}
			
		res=100*(float)gc/(float)str.length();
		percentageGC=res;
		
		return res;
	}
	
	protected float calculateTm(){
		
		String str = toString();
		double res=0;
		
		float primerConc=Environment.primerConc;
		float salt=Environment.saltConc;
		float mg=Environment.MgConc;		
		
		float H=0;
		float S=0;
		
		//terminal corrections
		if ((str.charAt(0)=='G')||(str.charAt(0)=='g')||(str.charAt(0)=='C')||(str.charAt(0)=='c')) {
			H+=0.1; S+=-2.8; }	
		if ("AaTt".contains(str.subSequence(0, 1))){
			H+=2.3;	S+=4.1;	}
		if ("AaTt".contains(str.subSequence(str.length()-1, str.length()))){
			H+=2.3; S+=4.1; }
		if ("GcCg".contains(str.subSequence(str.length()-1, str.length()))){
			H+=0.1; S+=-2.8; }
		
		// effect on entropy by salt correction; von Ahsen et al 1999
        // Increase of stability due to presence of Mg;
        double salt_effect = (salt/1000)+((mg*140/1000));
        // effect on entropy
        S+=(0.368 * (str.length()-1) * Math.log(salt_effect));
        
		//base pairs, nearest neighbour
        try{
        int i;
		for (i=0; i<(str.length()-1); i++){
			int j=0;
			while (!str.regionMatches(true, i, ntides[j], 0, 2)){
				j++;
			}
			H+=dH[j];
			S+=dS[j];
		}
		
		res=((1000*H)/(S+(1.987*Math.log(primerConc/2000000000))))-273.15;
        }
        
        catch (java.lang.ArrayIndexOutOfBoundsException e) {res=-1234;}
		
        Tm=(float)res;
        
		return Tm;
	}
	
	public float getTm(){
		return Tm;
	};
	
	public float getPercentageGC(){
		return percentageGC;
	};
	
}


