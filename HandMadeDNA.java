

public class HandMadeDNA extends AbstractDNA{
	
	private String sequence;
	
	public HandMadeDNA(String s){
		sequence=s;
		Tm=calculateTm(200,50,0);
		percentageGC=percentageGC();
	}
	
	public String toString(){
		return sequence;
	}
	
	protected void findFalseSites(){ //find, but not return
		
	}
}
