package DNA;



public class HandMadeDNA extends AbstractDNA{
	
	private String sequence;
	
	public HandMadeDNA(String s){
		sequence=s;
		Tm=calculateTm();
		percentageGC=percentageGC();
	}
	
	public String toString(){
		return sequence;
	}
	
	protected void findFalseSites(){ //find, but not return
		
	}

	public int getLength() {
		return sequence.length();
	}
}
