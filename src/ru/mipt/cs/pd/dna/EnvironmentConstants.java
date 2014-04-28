package ru.mipt.cs.pd.dna;

import java.util.ArrayList;

import ru.mipt.cs.pd.primers.PNLSolutionParameters;

public class EnvironmentConstants {
	public static float primerConc = 200; //nM
	public static float saltConc = 50; //mM
	public static float MgConc = 0;     //mM
	
	public static final int minLength=5;
	
	public static ArrayList<AbstractDNA> DNAs = new ArrayList<AbstractDNA>();
	
	// listen to changes of constants
	public static void reCalculateTm(){
		
		int max=DNAs.size();
		for (int i=0; i<max; i++){
			DNAs.get(i).calculateTm(); 
		}
	
		
	}
	
	public static PNLSolutionParameters pnlSolutionParameters; //created on the FRMSimplePrimers
	
}
