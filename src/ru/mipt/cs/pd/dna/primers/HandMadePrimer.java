package ru.mipt.cs.pd.dna.primers;

public class HandMadePrimer extends Primer{
	
	private String sequence;
	
	public HandMadePrimer(String s){
		builder(s);
		beg = 0;
		end = 0;
	}
	
	public HandMadePrimer(int b, int e, String s){
		builder(s);
		beg = b;
		end = e;
	}
	
	private void builder(String s){
		sequence=s;
		calculateTm();
		percentageGC();
		ru.mipt.cs.pd.dna.Environment.DNAs.add(this);
		findFalseSites();
	}

	
	public String toString(){
		return sequence;
	}

	public int getLength() {
		return sequence.trim().length();
	}

	@Override
	public boolean ifRight() {
		return false;
	}
}
