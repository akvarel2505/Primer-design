package DNA;

public abstract class ParentWithMainDNA {
	protected static String theMainDNA;
	
	
	public void setMainDNA(String str){
		theMainDNA=str;
	
	}
	
	public String getMainDNA(){
		return theMainDNA;
	}
	
	public static int getLengthOfMainDNA(){
		return theMainDNA.length();
	}
}
