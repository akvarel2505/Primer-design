import java.util.ArrayList;


public class ExtractFromMainDNA extends AbstractDNA{

	protected int beg,end;
	protected ArrayList<Edition> changes; //if it was edited by user
	
	public ExtractFromMainDNA(int b, int e){
		beg=b;
		end=e;
	}
	
	protected float calculateTm(){
		float res=0;
		//
		return res;
	}
	
	public String toString(){
		String res="actg";
		//
		return res;
	}
	
	protected void findFalseSites(){ //find, but not return
		
	} 
	
}

class Edition{
	int begin,end;
	String change;
}