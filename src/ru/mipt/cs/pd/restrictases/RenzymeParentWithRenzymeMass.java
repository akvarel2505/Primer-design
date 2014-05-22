package ru.mipt.cs.pd.restrictases;
/**
 * Created by futame on 09.04.14.
 */
public abstract class RenzymeParentWithRenzymeMass{
    public static RenzymeMass renzymeArrayList = new RenzymeMass();
    
    public static void update(){
    	renzymeArrayList = new RenzymeMass();
    }
}
