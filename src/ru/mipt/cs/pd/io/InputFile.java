package ru.mipt.cs.pd.io;
import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import ru.mipt.cs.pd.MainFrame;
import ru.mipt.cs.pd.dna.Environment;
import ru.mipt.cs.pd.dna.Feature;
import ru.mipt.cs.pd.restrictases.Renzyme;
import ru.mipt.cs.pd.restrictases.RenzymeMass;


public class InputFile extends MainFrame{
	
	public static ArrayList<Feature> feature = new ArrayList<Feature>();
	
	// The types of features
	protected static String CDS = " CDS ", gene = " gene ", miscFeature = " misc_feature ", misc_binding = " misc_binding ";
	protected static String linear = " linear ", circular = " circular ";
	protected static String type[] = {CDS, gene, miscFeature, misc_binding};
	protected static String form[] = {linear, circular};
	protected static int number = 0;   //number of features
	public static String zy = Environment.theMainDNA, formDNA;
	
	public static void OpenFile(String format, String path, JTextArea txtMain){
		if (format.equals("raw")){
			zy = "";
    		try {
    			FileInputStream fileStream = new FileInputStream(path);
    			Scanner scanner = new Scanner(fileStream);
    			while (scanner.hasNextLine()) {
    				String line = scanner.nextLine();
    				zy = zy + line;
    			}
    			scanner.close();
    		} catch (FileNotFoundException e) {
    			e.printStackTrace();
    		}
		}
		else if (format.equals("fasta")){
			String zy = "";
    		try {
    			FileInputStream fileStream = new FileInputStream(path);
    			Scanner scanner = new Scanner(fileStream);
    			String line = scanner.nextLine();
    			while (scanner.hasNextLine()) {
    				line = scanner.nextLine();
    				zy = zy + line;
    			}
    			scanner.close();
    		} catch (FileNotFoundException e) {
    			e.printStackTrace();
    		}
		}
		else if (format.equals("seq")){
			String zy = "";
			int i = 0, j = 0, b = 0, b1, e = 0, e1;
			
			Feature buf;
			for (i = 0; i<4; i++){                                     //i - порядковый номер названия feature
    		try {
    			FileInputStream fileStream = new FileInputStream(path);
    			Scanner scanner = new Scanner(fileStream);
    			Scanner auxScan;
    			String line = scanner.nextLine();
    			for (j = 0; j<2; j++){
    				if (line.indexOf(form[j]) != (-1)){
    					formDNA = form[j];
    				}
    			}
    			while (scanner.hasNextLine()) {
    				line = scanner.nextLine();
    				
    				
    				if ((line.indexOf(type[i]) != -1)&&(line.indexOf("complement")==(-1))){
    					
    					buf = new Feature();
    					buf.setType(type[i]);
    					
    					String[] parts = line.split("\\.\\.");
   
    					auxScan = new Scanner(parts[1]);
    					
    					e = auxScan.nextInt();
    				
    					String[] parts2 = parts[0].split(type[i]);
    					String[] resBeg = parts2[parts2.length-1].split("\\W");
    					auxScan = new Scanner(resBeg[resBeg.length-1]);
    					
    					b = auxScan.nextInt();
    					buf.setLocation(b, e);
    					boolean flag=true;
    					while ((scanner.hasNextLine())&&(flag)) {
    	    				line = scanner.nextLine();
    	    				if (line.indexOf("/label") != -1){
    	    					j = line.indexOf("/label");
    	    					String strName = line.substring(j+7);
    	    					buf.name(strName);
    	    					flag=false;
    	    				}
    					}
    					feature.add(buf);
    				}
    			}
    			scanner.close();
    		} catch (FileNotFoundException ee1) {
    			ee1.printStackTrace();
    		}
			}
    		/////////////////////////////////////
    		try {
    			FileInputStream fileStream = new FileInputStream(path);
    			Scanner scanner = new Scanner(fileStream);
    			while (scanner.hasNextLine()) {
    				String line = scanner.nextLine();
    				if (line.indexOf("ORIGIN") != -1){
    					while (scanner.hasNextLine()) {
    	    				line = scanner.nextLine();
    	    				for (i = 0; i < line.length(); i++){ 
    	    					String symbol = String.valueOf(line.charAt(i));
    	    					if ((symbol.equals("a")) || (symbol.equals("c")) || (symbol.equals("g")) || (symbol.equals("t"))){
    	    						zy = zy + symbol;
    	    					}
    	    				}
    					}
    				}
    		
    			}
    			scanner.close();
    		} catch (FileNotFoundException ee) {
    			ee.printStackTrace();
    		}
    		/////////////////////////////////////
    		txtMain.setText(zy);
    		Environment.setMainDNA(zy);
    		RenzymeMass.updateSuffixAutomata();
		}
		else{
			 createGUI();         		
		}
	}



	public static void createGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Information");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel label = new JLabel("Unsupported format");
        frame.getContentPane().add(label);
        
        frame.setPreferredSize(new Dimension(200, 100));
        
        frame.pack();
        frame.setVisible(true);          
   }


}
