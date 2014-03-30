import java.util.ArrayList;

public abstract class AbstractDNA extends ParentWithMainDNA{
	
	protected float Tm=-1;
	protected ArrayList<ExtractFromMainDNA> falseSites;
	
	protected abstract float calculateTm();
	public abstract String toString();	
	protected abstract void findFalseSites();
	
	public float getTm(){
		if (Tm<0) calculateTm();
		return Tm;
	}
	
	public ExtractFromMainDNA[] getFalseSites(){
		ExtractFromMainDNA res[] = new ExtractFromMainDNA[falseSites.size()];  
		falseSites.toArray(res);
		return res;
	}
	
}
