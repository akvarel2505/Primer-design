package ru.mipt.cs.pd.dna;

public class MakeComplement {
	public static void ComplementExtract(int begin, int end, String zy){
		String[] symbolZy = {"a", "t", "g", "c"} , symbolComplement = {"t", "a", "c", "g"};
		int i, j = 0;
		for (i = begin; i <= end; i++){
			symbolZy[j] = String.valueOf(zy.charAt(i));
			for (j = 0; j < 3; j++){
				if (symbolZy[j].equals(symbolComplement[j])){
					zy.replace(symbolZy[j], symbolComplement[j]);
				}
			}
		}
	}
	
}
