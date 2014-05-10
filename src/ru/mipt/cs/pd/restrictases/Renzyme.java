package ru.mipt.cs.pd.restrictases;

import java.util.ArrayList;

import ru.mipt.cs.pd.dna.Environment;
import ru.mipt.cs.pd.dna.SimpleExtract;

/**
 * Created by futame on 29.03.14.
 */
//сначала в файле идет имя рестриктазы, потом место, где она режет.
    //на вход нужны прописные буквы
public class Renzyme {
    private String place;

    private String name;
    private int numOfPos;
    private ArrayList<SimpleExtract> posPlaces = new ArrayList<SimpleExtract>();
//Считается, что в исходном файле место написано от 5' к 3'
    Renzyme(String name1,String place1){
        name = name1;
        place = place1;
        setNumOfPos();
        setPosPlaces();
        this.print();
    }

    //setters area
    public void setNumOfPos(){
        numOfPos = RenzymeMass.suffixAutomata.numOfOccurrences(place);
    }
    public void setPosPlaces(){
        int[] posPlaces53 = RenzymeMass.suffixAutomata.getOccurrences(place);
        for(int i = 0; i < posPlaces53.length; ++i ) {
            SimpleExtract simpleExtract = new SimpleExtract(posPlaces53[i],posPlaces53[i] + place.length() - 1);
            posPlaces.add(simpleExtract);

        }
    }

    //getters area
    public int getNumOfPos(){
        return numOfPos;
    }
    public String getName() {
        return name;
    }
    public String getPlace() {
        return place;
    } public int getFirstPlace(){
        if (posPlaces.size() > 0) return posPlaces.get(0).getBegin();
        return Environment.getLengthOfMainDNA();
    }
    public ArrayList<SimpleExtract> getPosPlaces(){
        return posPlaces;
    }

    public int getEndingOfLastPlace(){
        if (posPlaces.size() > 0){
            return posPlaces.get(posPlaces.size()-1).getEnd();
        }
        return 0;
    }

    
    public String toString(){
        String s = name + " (" + numOfPos + ") " + place;
        return s;

    }

    public int length(){
        return place.length();
    }

    public void print(){
        for (int i = 0; i < posPlaces.size(); ++i)
            System.out.print(posPlaces.get(i).getBegin() + " ");
        System.out.println();
    }
    
    //Nadia's code
    public boolean check(int b, int e){
    	boolean res=false;
    	int br, er;
    	
    	int max=posPlaces.size();
    	int i=0;
    	
    	while  ((i<max)&&(!res)){
    		br=posPlaces.get(i).getBegin();
    		er=posPlaces.get(i).getEnd();
    		if ((br>b)&&(br<e)&&(er>b)&&(er<e)) res=true;
    		i++;
    	}
    	
    	return res;
    }
    
}
