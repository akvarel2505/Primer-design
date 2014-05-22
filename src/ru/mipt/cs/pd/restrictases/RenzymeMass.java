package ru.mipt.cs.pd.restrictases;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import ru.mipt.cs.pd.dna.SimpleExtract;
import ru.mipt.cs.pd.utils.SuffixAutomata;

/**
 * Created by futame on 29.03.14.
 */

public class RenzymeMass{
    protected ArrayList<Renzyme> mainRenzymeStructure;
    private int n = 0;

    static File eSelectedFile = new File("rFiles/bigenzymes.txt");
    static SuffixAutomata suffixAutomata = new SuffixAutomata();

    public static void setDefaultFile() {
        String nameOfDefaultFile = eSelectedFile.getAbsolutePath();
        try {
            PrintStream printStream = new PrintStream(fileName);
            printStream.println(nameOfDefaultFile);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    static String fileName = "rFiles/default.txt";
    static boolean fileChoosen = false;

    public static File geteSelectedFile() {
        return eSelectedFile;
    }
    public static void seteSelectedFile(File eSelectedFile) {
        RenzymeMass.eSelectedFile = eSelectedFile;
        fileChoosen = true;
    }

    public RenzymeMass(){

        if (!fileChoosen){
            try{
                FileReader fileReader = new FileReader(fileName);
                Scanner scanner = new Scanner(fileReader);
                if (scanner.hasNextLine()) {
                    eSelectedFile = new File(scanner.nextLine());
                } else {
                    eSelectedFile = null;
                }
                fileReader.close();
                scanner.close();

            } catch (IOException e){
                e.printStackTrace();
            }
        }

        mainRenzymeStructure = new ArrayList<Renzyme>();
        try{
            try {
                try{
                FileReader fileReader = new FileReader(eSelectedFile);
                Scanner scanner = new Scanner(fileReader);
                n = 0;
                while (scanner.hasNext()){
                        n++;
                        String name = scanner.next();
                    if (scanner.hasNext()){
                        String place = scanner.next();
                          if (place.toString() != ""){
                            Renzyme renzyme = new Renzyme(name,place);
                            mainRenzymeStructure.add(renzyme);
                        } else {
                             // System.out.println(name + " is empty ");
                        }
                    }
                }
                fileReader.close();
                scanner.close();
                }
                catch (NullPointerException e){

                }
                }
                catch (FileNotFoundException e){

                }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void addRenzyme(String name,String place){
        Renzyme renzyme = new Renzyme(name,place);
        mainRenzymeStructure.add(renzyme);
    }

    public void deleteRenzyme(String name){
        int n = mainRenzymeStructure.size();
        for (int i = 0; i < n; ++i){
            if (mainRenzymeStructure.get(i).getName() == name){
                mainRenzymeStructure.remove(i);
                break;
            }
        }
    }

   /*
    * Данные функции возвращают список из Renzyme,список посорчен по возврастанию по дальности от pos в DNAstring справа и слева соответственно.
    */
    public ArrayList<Renzyme> getMainRenzymeStructure() {
       return mainRenzymeStructure;
   }
    public RenzymeWithANumber[] findLeft(int pos){
        //Возвращает все рестриктазы, чье начало не правее pos
        ArrayList<Renzyme> left = new ArrayList<Renzyme>();
        for(int i = 0;i < n; ++i){
            if (mainRenzymeStructure.get(i).getFirstPlace() <= pos){
                left.add(mainRenzymeStructure.get(i));
            }
        }
        RenzymeWithANumber[] r = new RenzymeWithANumber[left.size()];

        for (int i = 0; i < left.size(); ++i){
            r[i] = new RenzymeWithANumber(pos,left.get(i));
            int j;
            ArrayList<SimpleExtract> posPlaces = r[i].getPosPlaces();
            for (j = 0; j < posPlaces.size(); ++j){
                if (posPlaces.get(j).getBegin() <= pos)
                {
                    r[i].number = pos - posPlaces.get(j).getBegin();
                }
                else {
                    break;
                }
            }
            --j;
            if ((posPlaces.get(j).getBegin() <= pos)&&(posPlaces.get(j).getEnd() >= pos)){
                r[i].setIntersect(true);
            }

        }
        Arrays.sort(r);
        return r;
    }
    public RenzymeWithANumber[] findRight(int pos){
        ArrayList<Renzyme> right = new ArrayList<Renzyme>();
        for(int i = 0;i < n; ++i){
            if (mainRenzymeStructure.get(i).getEndingOfLastPlace() > pos){
                right.add(mainRenzymeStructure.get(i));
            }
        }
        RenzymeWithANumber[] r = new RenzymeWithANumber[right.size()];
        for (int i = 0; i < right.size(); ++i){
            r[i] = new RenzymeWithANumber(pos,right.get(i));
            int k = 0;
            ArrayList<SimpleExtract> posPlaces = r[i].renzyme.getPosPlaces();
            int j;
            for (j = posPlaces.size() - 1; j >= 0; --j){
                if (posPlaces.get(j).getEnd() >= pos)
                {
                    r[i].number = posPlaces.get(j).getEnd() - pos;
                }
                else{
                    break;
                }
            }
            ++j;
            if ((posPlaces.get(j).getBegin() <= pos)&&(posPlaces.get(j).getEnd() >= pos)){
                r[i].setIntersect(true);
            }

        }
        Arrays.sort(r);
        return r;
    }

    public RenzymeWithANumber[] findLeft(int begin, int end){
        RenzymeWithANumber[] renzymeWithANumbers = findLeft(end);
        int genomeLength = end - begin + 1;
        for (int i = 0; i < renzymeWithANumbers.length; ++i){
            renzymeWithANumbers[i].setGenomeLength(genomeLength);
            if ((renzymeWithANumbers[i].number > genomeLength)&&((renzymeWithANumbers[i].number - renzymeWithANumbers[i].renzyme.length() + 1) <= genomeLength)){
                renzymeWithANumbers[i].setIntersect(true);
            }
        }

        return  renzymeWithANumbers;
    }

    public RenzymeWithANumber[] findRight(int begin, int end){
        RenzymeWithANumber[] renzymeWithANumbers = findRight(begin);
        int genomeLength = end - begin + 1;
        for (int i = 0; i < renzymeWithANumbers.length; ++i){
            renzymeWithANumbers[i].setGenomeLength(genomeLength);
            if ((renzymeWithANumbers[i].number > genomeLength)&&((renzymeWithANumbers[i].number - renzymeWithANumbers[i].renzyme.length() + 1) <= genomeLength)){
                renzymeWithANumbers[i].setIntersect(true);
            }
        }
        return  renzymeWithANumbers;
    }

    public int size(){
        return n;
    }
    
    public static void updateSuffixAutomata(){
    	suffixAutomata = new SuffixAutomata();
    }

}
