import java.util.ArrayList;


public class ExtractFromMainDNA extends AbstractDNA{

	protected int beg,end;
	
	public ExtractFromMainDNA(int b, int e){
		beg=b;
		end=e;
		Tm=calculateTm(200,50,0);
		percentageGC=percentageGC();
	}
	
	public String toString(){
		String res=theMainDNA.substring(beg,end);
		return res;
	}
	
	protected void findFalseSites(){ //find, but not return
		
	}
	
	
}
