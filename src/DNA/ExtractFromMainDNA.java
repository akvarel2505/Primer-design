package DNA;

public class ExtractFromMainDNA extends AbstractDNA{

	protected int beg,end;
	
	public ExtractFromMainDNA(int b, int e){
		beg=b;
		end=e;
		Tm=calculateTm();
		percentageGC=percentageGC();
	}
	
	public String toString(){
		String res=theMainDNA.substring(beg,end);
		return res;
	}
	
	protected void findFalseSites(){ //find, but not return
		
	}
	
	
}
