package ru.mipt.cs.pd.restrictases;

import java.util.ArrayList;

import ru.mipt.cs.pd.dna.SimpleExtract;

/**
 * Created by futame on 12.04.14.
 */
public class RenzymeWithANumber implements Comparable<RenzymeWithANumber>{
    public Renzyme renzyme;
    public int number;

    public int getGenomeLength() {
        return genomeLength;
    }

    private int genomeLength = 0;

    public ArrayList<SimpleExtract> getPosPlaces() {
        return posPlaces;
    }

    private ArrayList<SimpleExtract> posPlaces;

    public boolean isIntersect() {
        return intersect;
    }
    public void setIntersect(boolean intersect) {
        this.intersect = intersect;
    }

    private boolean intersect = false;

    public int getPos() {
        return pos;
    }

    private final int pos;

    @Override
    public int compareTo(RenzymeWithANumber r){
        if (r.number == this.number) return 0;
        if (r.number < this.number) return 1;
        return -1;
    }

    RenzymeWithANumber(int pos, Renzyme renzyme){
        this.pos = pos;
        this.renzyme = renzyme;
        posPlaces = renzyme.getPosPlaces();

    }

    public void setGenomeLength(int genomeLength) {
        this.genomeLength = genomeLength;
    }

    public String toString(){
        
        if (intersect) return renzyme.toString() + ' ' + '!';
        else return renzyme.toString();
    }
}

