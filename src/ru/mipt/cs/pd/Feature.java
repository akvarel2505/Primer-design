package ru.mipt.cs.pd.dna;

public class Feature extends SimpleExtract{

	private String typeF, nameF;
	private int begin, end;
	Object tag;
	
	public String getType() {
		return typeF;
	}
	
	public int getType(boolean ifForColor) {
		if (getType().equals(" CDS ")) return 1;
		if (getType().equals(" gene ")) return 2;
		if (getType().equals(" miscFeature ")) return 3;
		if (getType().equals(" misc_binding ")) return 4;
		return 0; 
	}
	
	public String getName() {
		return nameF;
	}
	public int getBegin() {
		return begin;
	}
	public int getEnd() {
		return end;
	}
	public Object getTag() {
		return tag;
	}
	
	public void setType(String type){
		typeF = type;
	}
	public void setLocation(int b, int e){
		begin = b;
		end = e;
	}
	public void name(String name){
		nameF=name;
	}
	public void object(Object object){
		
	}
	public String toString(){
		String stringObject = typeF+" "+nameF+String.format(" %d...%d", begin,end);
		return stringObject;
	}
}
