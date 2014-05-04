package ru.mipt.cs.pd.dna.primers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ru.mipt.cs.pd.dna.AbstractDNA;
import ru.mipt.cs.pd.dna.Environment;
import ru.mipt.cs.pd.primers.LabelsEN;
import ru.mipt.cs.pd.utils.DNAStringUtils;

public abstract class Primer extends AbstractDNA {
	
	protected ArrayList<FalseSite> falseSites = new ArrayList<FalseSite>();
	
	public abstract String toString();	
	
	public String toString(boolean b) {
		int id=Environment.DNAs.indexOf(this);
		float Tm=this.getTm();
		float GC=this.getPercentageGC();
		int length=this.getLength();
		return String.format(LabelsEN.formatPrimer, id, Tm, length, GC);
	}
	
	public FalseSite[] getFalseSites(){
		FalseSite res[] = new FalseSite[falseSites.size()];  
		falseSites.toArray(res);
		return res;
	}
	
	public void findFalseSites(){
		
		String dna53 = theMainDNA;
		String dna35 = DNAStringUtils.comRev(theMainDNA);
		
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
		int min=Environment.minLength;
		
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
					if (k0==-1) k=mainLength; // doesn't match
					else{
						currSite = new FalseSite(k0,k0+j-i,i,j,strand);
						if (ifContained(currSite)) {
							currSite.die();
						}
						else {
							falseSites.add(currSite);
						}
						k=k0+j-i-1;
					}	
				}
				j--;
			}
			i++;
			j=primerLength;
		}
		
		if ((strand==35)&&(!falseSites.isEmpty())){
			FalseSite res=falseSites.get(0);
			if (res.toString().equalsIgnoreCase(this.toString())) {
				res.die();
				falseSites.remove(0);
			}
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

	public void die(){
		int max=falseSites.size();
		for (int i=0; i<max; i++){
			falseSites.get(i).die();
		}
		Environment.DNAs.remove(this);
	}
}
