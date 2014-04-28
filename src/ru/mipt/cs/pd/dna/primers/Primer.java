package ru.mipt.cs.pd.dna.primers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ru.mipt.cs.pd.dna.AbstractDNA;
import ru.mipt.cs.pd.dna.EnvironmentConstants;
import ru.mipt.cs.pd.utils.DNAStringUtils;

public abstract class Primer extends AbstractDNA {
	
	protected ArrayList<FalseSite> falseSites = new ArrayList<FalseSite>();
	
	public abstract String toString();	
	
	public FalseSite[] getFalseSites(){
		FalseSite res[] = new FalseSite[falseSites.size()];  
		falseSites.toArray(res);
		return res;
	}
	
	public void findFalseSites(){
		String dna53 = theMainDNA;
		
		String dna35 = DNAStringUtils.comRev(theMainDNA);
		
		System.out.println(dna35);
		
		findSites(dna53, 35);
		findSites(dna35, 53);
		
		Collections.sort(falseSites, new Comparator<FalseSite>() {
			public int compare(FalseSite o1, FalseSite o2) {
				return (int)(o2.getTm() - o1.getTm());
			}
		});
		
	}
	
	private void findSites(String theMainDNA, int strand){
		
		String primer = this.toString();
		int primerLength = getLength();
		int mainLength = theMainDNA.length();
		int min=EnvironmentConstants.minLength;
		
		int i,j,k,k0;
		String currStr;
		FalseSite currSite;
		
		i=0; k0=0;
		j=primerLength;
		
		while (i<primerLength-min-1){
	
			while (j>i+min){
				
				currStr=primer.substring(i, j); //possible false site
				
				k=0;
				k0=0;
				while (k<mainLength){
					
					k0=theMainDNA.indexOf(currStr, k);
					//System.out.println(k0);
					if (k0==-1) k=mainLength; // doesn't match
					else{
						currSite = new FalseSite(k0,k0+j-i,i,j,strand);
						if (!ifContained(currSite)) {
							falseSites.add(currSite);
							//if (strand==53){
							//System.out.println(String.format("Find! i=%d j=%d k0=%d", i,j,k0));
							System.out.println(String.format("gc=%.1f",currSite.getPercentageGC())+currSite.toString());
							//System.out.println(currStr);
							//}
						}
						k=k0+j-i-1;
					}	
				}
				
				j--;
			}
			i++;
			j=primerLength;			//TODO - check
		}
		
	}
	
	private boolean ifContained(FalseSite x){
		
		boolean res=false;
		int i=0;
		int max=falseSites.size();
		FalseSite curr;
		
		int prB = x.getPrimerBeg();
		int prE = x.getPrimerEnd();
		int mB = x.getMainBeg();
		
		while ((!res)&&(i<max)){
			curr=falseSites.get(i);
			res = ( ifIncluded(curr.getPrimerBeg(), curr.getPrimerEnd(), prB, prE) 
			&& ((prB-curr.getPrimerBeg())==(mB-curr.getMainBeg())) );
			i++;
			
		}
		
		return res;
	}
	
	private boolean ifIncluded(int b1, int e1, int b2, int e2){
		boolean res;
		res=((e2<=e1)&&(b2>=b1));
		return res;
	}
}
