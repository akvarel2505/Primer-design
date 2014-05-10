package ru.mipt.cs.pd.dna.primers;

import java.util.ArrayList;

import ru.mipt.cs.pd.dna.Environment;
import ru.mipt.cs.pd.restrictases.RenzymeWithANumber;
import ru.mipt.cs.pd.utils.DNAStringUtils;

public class AutoPrimersAlg{
	
	private AutoPrimers result;
	private int geneBegin, geneEnd;
	private ArrayList<Enzyme> leftEnz;
	private ArrayList<Enzyme> rightEnz;
	
	class Enzyme{
		int begin;
		int length;
		
		public Enzyme(int b, int l){
			begin=b;
			length=l;
		}
		
	}
	
	public AutoPrimersAlg(int geneB, int geneE, ArrayList<RenzymeWithANumber> leftRenz, ArrayList<RenzymeWithANumber> rightRenz){
		result = new AutoPrimers();
		
		geneBegin=geneB;
		geneEnd=geneE;
		
		//convenience format of restriction enzymes
		leftEnz = changeLeft(leftRenz);
		rightEnz = changeRight(rightRenz);
	}
	
	public AutoPrimers getResult(){
		
		//main calculations
		makeThreads(leftEnz, result.left);
		makeThreads(rightEnz, result.right);
		
		//sort primers
		//TODO
		
		return result;
	}

	private void makeThreads(ArrayList<Enzyme> enzymes, ArrayList<AutoPrimer> res){
		
		String mainDNA;	
		boolean ifRight;
		if (enzymes.equals(leftEnz)) {
			mainDNA = Environment.getMainDNA();
			ifRight=false;
		}
		else {
			ifRight=true;
			mainDNA=DNAStringUtils.comRev(Environment.getMainDNA());
		}

		int max=enzymes.size();
		
		FindPrimersThread[] threads = new FindPrimersThread[max];
		
		for (int i=0; i<max; i++){
			//a thread
			threads[i] = new FindPrimersThread(enzymes.get(i), mainDNA, res, ifRight);
			threads[i].start();
		}
		
		for (int i=0; i<max; i++){
			try {
				threads[i].join();
			} catch (InterruptedException e) {}
		}
		
	}

	private ArrayList<Enzyme> changeRight(ArrayList<RenzymeWithANumber> renz){
		ArrayList<Enzyme> res = new ArrayList<Enzyme>();
		Enzyme curr;
		RenzymeWithANumber curr2;
		int max=renz.size();
		for (int i=0; i<max; i++){
			curr2=renz.get(i);
			int beg = geneEnd + curr2.number + 1;
			beg=Environment.getLengthOfMainDNA()-beg;
			curr = new AutoPrimersAlg.Enzyme(beg, curr2.renzyme.getPlace().length());
			res.add(curr);
		}
		return res;
	}
	
	private ArrayList<Enzyme> changeLeft(ArrayList<RenzymeWithANumber> renz){
		ArrayList<Enzyme> res = new ArrayList<Enzyme>();
		Enzyme curr;
		RenzymeWithANumber curr2;
		int max=renz.size();
		for (int i=0; i<max; i++){
			curr2=renz.get(i);
			int beg = geneBegin - curr2.number;
			curr = new AutoPrimersAlg.Enzyme(beg, curr2.renzyme.getPlace().length());
			res.add(curr);
		}
		return res;
	}
}
