package ru.mipt.cs.pd.dna;

public class SimpleExtract extends ParentWithMainDNA{
	private int begin,end;
	
	public SimpleExtract(int b, int e){
		begin=b;
		end=e;
	}
	
	SimpleExtract(){
	}
	
	public int getBegin() {
		return begin;
	}
	
	public void setBegin(int begin) {
		this.begin = begin;
	}
	
	public int getEnd() {
		return end;
	}
	
	public void setEnd(int end) {
		this.end = end;
	}
	
}
