import java.io.File;
import java.util.ArrayList;

/**
 * Created by futame on 29.03.14.
 */
//сначала в файле идет имя рестриктазы, потом место, где она режет.
    //на вход нужны прописные буквы
public class Renzyme {//implements Надин интерфейс :)
    private String place;

    private String name;
    private int numOfPos;
    private int[] posPlaces53;

    private int[] posPlaces35;
    private ArrayList<SimpleExtract> posPlaces = new ArrayList<SimpleExtract>();
//Считается, что в исходном файле место написано от 5' к 3'
    Renzyme(String name1,String place1){
        name = name1;
        place = place1;
        setNumOfPos();
        setPosPlaces();
        this.print();
    }
    public void setNumOfPos(){
        numOfPos = RenzymeMass.suffixAutomata.numOfOccurrences(place) + RenzymeMass.suffixAutomata.numOfOccurrences(inverse(place));
    }
    public void setPosPlaces(){
        posPlaces53 = RenzymeMass.suffixAutomata.getOccurrences(place);
        posPlaces35 = RenzymeMass.suffixAutomata.getOccurrences(inverse(place));
        int n = posPlaces53.length + posPlaces35.length;
        int j53 = 0;
        int j35 = 0;
        for (int i=0; i < n; ++ i){
            SimpleExtract simpleExtract = new SimpleExtract();
            if (j53 >= posPlaces53.length) {
                simpleExtract.setBegin(posPlaces35[j35]);
                simpleExtract.setEnd(posPlaces35[j35]+place.length());
                ++j35;
                posPlaces.add(simpleExtract);
            } else {
                if (j35 >= posPlaces35.length){
                    simpleExtract.setBegin(posPlaces53[j53]);
                    simpleExtract.setEnd(posPlaces53[j53]+place.length());
                    j53++;
                    posPlaces.add(simpleExtract);
                }else {
                    if (posPlaces35[j35] > posPlaces53[j53]){
                        simpleExtract.setBegin(posPlaces53[j53]);
                        simpleExtract.setEnd(posPlaces53[j53]+place.length());
                        j53++;
                        posPlaces.add(simpleExtract);
                    } else {
                        simpleExtract.setBegin(posPlaces35[j35]);
                        simpleExtract.setEnd(posPlaces35[j35]+place.length());
                        ++j35;
                        posPlaces.add(simpleExtract);
                    }
                }

            }
        }

    }
    public int getNumOfPos(){
        return numOfPos;
    }
    public ArrayList<SimpleExtract> getPosPlaces(){
        return posPlaces;
    }
    public String getName() {
        return name;
    }
    public String getPlace() {
        return place;
    }
    public int[] getPosPlaces53() {
        return posPlaces53;
    }
    public int[] getPosPlaces35() {
        return posPlaces35;
    }
    public int getFirstPlace(){
        if (posPlaces.size() > 0) return posPlaces.get(0).getBegin();
        return ParentWithMainDNA.theMainDNA.length();
    }
    public int getEndingOfLastPlace(){
        if (posPlaces.size() > 0){
            return posPlaces.get(posPlaces.size()-1).getEnd();
        }
        return 0;
    }
    public String inverse(String s){
        char[] s1 = new char[s.length()];
        for (int i = 0;i < s.length(); ++i){
            switch (s.charAt(i)){
                case 'a':
                   s1[i] = 't';
                   break;
                case 't':
                    s1[i] ='a';
                    break;
                case 'g':
                    s1[i] = 'c';
                    break;
                case 'c':
                    s1[i] = 'g';
                    break;
                default:
                    Error e = new Error("Strange symbol!");
            }
        }
        return new String(s1);

    }
    public String toString(){
        String s = name + " (" + numOfPos + ')';
        return s;

    }
    public void print(){
        System.out.println(name + " " + place);
        for (int i = 0; i < posPlaces.size(); ++i)
            System.out.print(posPlaces.get(i).getBegin() + " ");
        System.out.println();
    }
}
