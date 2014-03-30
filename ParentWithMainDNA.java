
public abstract class ParentWithMainDNA {
	protected static String theMainDNA;
	protected SuffixAutomata suffixAutomata;
	
	public void setMainDNA(String str){
		theMainDNA=str;
		suffixAutomata=new SuffixAutomata(str);
	}
}
