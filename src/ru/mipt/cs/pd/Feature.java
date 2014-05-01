public class Feature extends SimpleExtract{

	private String typeF, nameF;
	int begin, end;
	Object tag;
	
	public void type(String type){
		typeF = type;
	}
	public void location(int b, int e){
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
