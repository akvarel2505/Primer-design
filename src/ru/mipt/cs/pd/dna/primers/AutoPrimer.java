package ru.mipt.cs.pd.dna.primers;

import ru.mipt.cs.pd.dna.AbstractDNA;
import ru.mipt.cs.pd.dna.Environment;

public class AutoPrimer extends Primer{

	protected int beg,end;
	
	public AutoPrimer(int b, int e){
		beg=b;
		end=e;
		calculateTm();
		percentageGC();
		ru.mipt.cs.pd.dna.Environment.DNAs.add(this);
		findFalseSites();
	}
	
	public String toString(){
		String res=theMainDNA.substring(beg,end);
		return res;
	}

	@Override
	public int getLength() {
		return beg-end;
	}
	
	
}
