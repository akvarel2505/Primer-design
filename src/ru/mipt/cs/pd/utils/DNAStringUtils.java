package ru.mipt.cs.pd.utils;

import ru.mipt.cs.pd.dna.Environment;

public class DNAStringUtils {
	
	public static String complement(final String s){
		char[] s1 = new char[s.length()];
        for (int i = 0;i < s.length(); ++i){
            switch (s.charAt(i)){
                case 'a':
                   s1[i] = 't';
                   break;
                case 't':
                    s1[i] ='a';
                    break;
                case 'g':
                    s1[i] = 'c';
                    break;
                case 'c':
                    s1[i] = 'g';
                    break;
                default:
                    //Error e = new Error("Strange symbol!");
            }
        }
        return new String(s1);
	}
	
	public static String palindrom(final String str){
		int max=str.length()-1;
		String res=str.substring(0);
		
		char[] work = res.toCharArray();
		
		char buf;
		int k=max/2;
		for (int i=0; i<=k; i++){
			buf=work[i];
			work[i]=work[max-i];
			work[max-i]=buf;
		}
		
		res = new String(work);
		return res;
	}
	
	public static String comRev(final String str){
		
		String str1 = complement(str);
		String res = palindrom(str1);
		
		return res;
		
	};
	
	public static float percentageGC(final String str){
		int i, gc=0;
		float res;
		for (i=0; i<str.length(); i++){
			if ("GcgC".contains(str.substring(i,i+1))){
				gc++;
			}
		}	
		res=100*(float)gc/(float)str.length();
		return res;
	}
	
	public static float calculateTm(final String str){
		
		final String ntides[] = {"aa",      "ac",       "ag",          "at",        "ca",        "cc",         "cg",             "ct",       "ga",          "gc",       "gg",         "gt",        "ta",        "tc",             "tg",             "tt"};
		final float dH[] = {(float)-7.9, (float)-8.4,  (float)-7.8,  (float)-7.2 , (float)-8.5, (float)-8,    (float)-10.6,  (float)-7.8, (float)-8.2,   (float)-9.8, (float)-8,   (float)-8.4,  (float)-7.2,  (float)-8.2,   (float)-8.5 ,    (float)-7.9};
		final float dS[] = {(float)-22.2,(float)-22.4, (float)-21, (float)-20.4 , (float)-22.7, (float)-19.9, (float)-27.2, (float)-21.0, (float)-22.2, (float)-24.4, (float)-19.9, (float)-22.4, (float)-21.3, (float)-22.2, (float)-22.7,   (float)-22.2};
		
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
		
        float Tm=(float)res;
        
		return Tm;
	
	}
	
	
}
