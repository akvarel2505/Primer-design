package ru.mipt.cs.pd.utils;

public class DNAStringUtils {
	
	public static String complement(String str){
		return ru.mipt.cs.pd.restrictases.Renzyme.inverse(str); //TODO
	}
	
	public static String palindrom(String str){
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
	
	public static String comRev(String str){
		
		String str1 = complement(str);
		String res = palindrom(str1);
		
		return res;
		
	};
	
	
}
