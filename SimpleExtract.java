
public class SimpleExtract extends ParentWithMainDNA{
	private int begin,end;
	
	SimpleExtract(int b, int e){
		begin=b;
		end=e;
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
