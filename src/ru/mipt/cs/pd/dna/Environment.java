package ru.mipt.cs.pd.dna;

import java.util.ArrayList;

import ru.mipt.cs.pd.dna.primers.AutoPrimers;
import ru.mipt.cs.pd.dna.primers.Primer;
import ru.mipt.cs.pd.restrictases.RenzymeWithANumber;

public class Environment {
	
	// THE MAIN DNA
	
	public static String theMainDNA="";
	
	public static void setMainDNA(String str){
		theMainDNA=str;
	}
	
	public static String getMainDNA(){
		return theMainDNA;
	}
	
	public static int getLengthOfMainDNA(){
		return theMainDNA.length();
	}
	
	//
	
	public static float primerConc = 200; //nM
	public static float saltConc = 50; //mM
	public static float MgConc = 0;     //mM
	public static float desiredTm = 63;
	
	public static final int minLength=5;
	
	public static ArrayList<AbstractDNA> DNAs = new ArrayList<AbstractDNA>();
	
	public static ArrayList<Primer> leftPrimers=new ArrayList<Primer>();
	public static ArrayList<Primer> rightPrimers=new ArrayList<Primer>();
	public static ArrayList<RenzymeWithANumber> leftRenzymes = new ArrayList<RenzymeWithANumber>(); 
	public static ArrayList<RenzymeWithANumber> rightRenzymes= new ArrayList<RenzymeWithANumber>();
	
	public static void reCalculateTm(){
		int max=DNAs.size();
		for (int i=0; i<max; i++){
			DNAs.get(i).calculateTm(); 
		}		
	}
	
	public static void printAllDNAs(){
		int max=DNAs.size();
		String str;
		for (int i=0; i<max; i++){
			str = DNAs.get(i).toString();
			str = String.format("%d "+str, i);
			System.out.println(str);
		}
	}
	
}
