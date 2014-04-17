import java.io.*;
import java.util.Scanner;

/**
 * Created by futame on 10.04.14.
 */
public class Example {

    Example(){
        File eSelectedFile = new File("rFiles/example.txt");
        File file = eSelectedFile;
        final int N = 10;
        String[] text = new String[N];
        text[0] = "AA acccgc\n";
        text[1] = "BB ccaa\n";
        text[2] = "CIII acacgtg\n";
        text[3] = "fastII ttaatt\n";
        text[4] = "big accccccccccccccccccccccccccccccccccccccccccccccccccccccccccccttta\n";
        text[5] = "short atctggt\n";
        text[6] = "G gcg\n";
        text[7] = "BlueI gagga\n";
        text[8] = "Smile cgga\n";
        text[9] = "Aq aca\n";


        System.out.println("Writing to file...");
            if (!eSelectedFile.exists()) {
                System.out.println("File is not exist. Have to create...");
                try {
                    eSelectedFile.createNewFile();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        try {
            FileWriter out = new FileWriter(eSelectedFile);
            try {
                System.out.println("Writing...");
                for (int i = 0; i < N; ++i){
                    out.write(text[i]);
                }

            }
            finally {
                out.close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }






    }
}
