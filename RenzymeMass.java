import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by futame on 29.03.14.
 */

public class RenzymeMass{
    protected ArrayList<Renzyme> mainRenzymeStructure = new ArrayList<Renzyme>();
    private int n = 0;
    ArrayList<Renzyme> right;

    public static File geteSelectedFile() {
        return eSelectedFile;
    }
    public static void seteSelectedFile(File eSelectedFile) {
        RenzymeMass.eSelectedFile = eSelectedFile;
    }
    static File eSelectedFile = new File("rFiles/example.txt");
    static SuffixAutomata suffixAutomata = new SuffixAutomata();
    RenzymeMass(){
        try{
            System.out.println(eSelectedFile);
            FileReader fileReader = new FileReader(eSelectedFile);
            Scanner scanner = new Scanner(fileReader);
            String line;
            while (scanner.hasNext()){
                    n++;
                //Problem part of code!!!
                    String name = scanner.next();
                    String place = scanner.next();
                      if (place.toString() != ""){
                        Renzyme renzyme = new Renzyme(name,place);
                        mainRenzymeStructure.add(renzyme);
                    } else {
                        //Ошибка!
                    }
            }
            fileReader.close();
            scanner.close();

        } catch (IOException e){
            Error error = new Error(e.getMessage());
        }
    }

   /*
    * Данные функции возвращают список из Renzyme,список посорчен по возврастанию по дальности от pos в DNAstring справа и слева соответственно.
    */
    public ArrayList<Renzyme> getMainRenzymeStructure() {
       return mainRenzymeStructure;
   }
    public ArrayList<Renzyme> findLeft(int pos){
        //Возвращает все рестриктазы, чье начало не правее pos
        ArrayList<Renzyme> left = new ArrayList<Renzyme>();
        for(int i = 0;i < n; ++i){
            if (mainRenzymeStructure.get(i).getFirstPlace() < pos){
                left.add(mainRenzymeStructure.get(i));
            }
        }
        RenzymeWithANumber[] r = new RenzymeWithANumber[left.size()];
        for (int i = 0; i < left.size(); ++i){
            r[i] = new RenzymeWithANumber();
            r[i].renzyme = left.get(i);
            int k = 0;
            ArrayList<SimpleExtract> posPlaces = r[i].renzyme.getPosPlaces();
            for (int j = 0; j < posPlaces.size(); ++j){
                if (posPlaces.get(j).getBegin() <= pos)
                {
                    r[i].number = pos - posPlaces.get(j).getBegin();
                }
                else break;
            }

        }
        Arrays.sort(r);
        left = new ArrayList<Renzyme>();
        for (int i = 0; i < r.length; ++i){
            left.add(r[i].renzyme);
        }
        return left;
    }
    public ArrayList<Renzyme> findRight(int pos){
        ArrayList<Renzyme> right = new ArrayList<Renzyme>();
        for(int i = 0;i < n; ++i){
            if (mainRenzymeStructure.get(i).getEndingOfLastPlace() > pos){
                right.add(mainRenzymeStructure.get(i));
            }
        }
        RenzymeWithANumber[] r = new RenzymeWithANumber[right.size()];
        for (int i = 0; i < right.size(); ++i){
            r[i] = new RenzymeWithANumber();
            r[i].renzyme = right.get(i);
            int k = 0;
            ArrayList<SimpleExtract> posPlaces = r[i].renzyme.getPosPlaces();
            for (int j = posPlaces.size() - 1; j >= 0; --j){
                if (posPlaces.get(j).getEnd() <= pos)
                {
                    r[i].number = posPlaces.get(j).getEnd() - pos;
                }
                else break;
            }
        }
        Arrays.sort(r);
        right = new ArrayList<Renzyme>();
        for (int i = 0; i < r.length; ++i){
            right.add(r[i].renzyme);
        }
        return right;
    }
    public int size(){
        return n;
    }
}
