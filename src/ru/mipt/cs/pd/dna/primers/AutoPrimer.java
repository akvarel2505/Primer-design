package ru.mipt.cs.pd.dna.primers;

import ru.mipt.cs.pd.dna.Environment;

public class AutoPrimer extends Primer{

	private boolean ifRight;
	
	public AutoPrimer(int b, int e, boolean ifR){
		beg=b;
		end=e;
		ifRight=ifR;
		calculateTm();
		percentageGC();
		synchronized (Environment.DNAs){
			ru.mipt.cs.pd.dna.Environment.DNAs.add(this);
		}
		findFalseSites();
	}
	
	public String toString(){
		String res=Environment.theMainDNA.substring(beg,end);
		return res;
	}

	@Override
	public int getLength() {
		return end-beg;
	}

	@Override
	public boolean ifRight() {
		return ifRight;
	}
	
	
}
