package ru.mipt.cs.pd.dna;

public class Feature extends SimpleExtract{

	private String typeF, nameF;
	private int begin, end;
	Object tag;
	
	public String getType() {
		return typeF;
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

