package ru.mipt.cs.pd.dna.primers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
		
		//sort
		Comparator compPrimersTm = new Comparator<Primer>(){
			public int compare(Primer o1, Primer o2) {
				float desTm = Environment.desiredTm;
				float p1 = Math.abs(o1.getTm()*100-desTm*100);
				float p2 = Math.abs(o2.getTm()*100-desTm*100);
				return (int)(p1)-(int)(p2);
			}
		};
		Collections.sort(result.left,  compPrimersTm);
		Collections.sort(result.right,  compPrimersTm);
		
		cutDown(result.left, 10);
		cutDown(result.right, 10);
		
		Comparator compPrimersFalseSites = new Comparator<Primer>(){
			public int compare(Primer o1, Primer o2) {
				int p1 = o1.getFalseSites().length;
				int p2 = o2.getFalseSites().length;
				return (p1-p2);
			}
		};
		
		Collections.sort(result.left,  compPrimersFalseSites);
		Collections.sort(result.right, compPrimersFalseSites);
		
		cutDown(result.left, 5);
		cutDown(result.right, 5);
		
		return result;
	}

	private void cutDown(ArrayList<AutoPrimer> array, int num) {
		
		int max=array.size() - 1;
		int extra = max+1 - num;
		Primer buf;
		for (int i=0; i<extra; i++){
			buf = array.remove(max);
			buf.die();
			max--;
		}
		
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
