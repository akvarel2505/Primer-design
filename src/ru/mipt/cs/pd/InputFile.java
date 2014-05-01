import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class InputFile extends MainFrame{
	
	// The types of features
	protected String  CDS = "CDS", gene = "gene", miscFeature = "misc_feature", misc_binding = "misc_binding";
	protected String type[] = {CDS, gene, miscFeature, misc_binding};
	protected int number = 0;   //number of features
	public static String zy;
	
	public void OpenFile(String format, String name){
		if (format == "raw"){
			zy = "";
    		try {
    			FileInputStream fileStream = new FileInputStream(name);
    			Scanner scanner = new Scanner(fileStream);
    			while (scanner.hasNextLine()) {
    				String line = scanner.nextLine();
    				zy = zy + line;
    				System.out.println(line);
    				System.out.println(zy);
    				System.out.println();
    			}
    			scanner.close();
    		} catch (FileNotFoundException e) {
    			e.printStackTrace();
    		}
		}
		else if (format == "fasta"){
			String zy = "";
    		try {
    			FileInputStream fileStream = new FileInputStream(name);
    			Scanner scanner = new Scanner(fileStream);
    			String line = scanner.nextLine();
    			while (scanner.hasNextLine()) {
    				line = scanner.nextLine();
    				zy = zy + line;
    				System.out.println(line);
    				System.out.println(zy);
    				System.out.println();
    			}
    			scanner.close();
    		} catch (FileNotFoundException e) {
    			e.printStackTrace();
    		}
		}
		else if (format == "genbank"){
			String zy = "";
			int i = 0, b = 0, b1, e = 0, e1;
			
			ArrayList<Feature> feature = new ArrayList<Feature>();
			Feature buf;
			for (i = 0; i<4; i++){
    		try {
    			FileInputStream fileStream = new FileInputStream(name);
    			Scanner scanner = new Scanner(fileStream);
    			while (scanner.hasNextLine()) {
    				String line = scanner.nextLine();
    				if (line.indexOf(type[i]) != -1){
    					buf = new Feature();
    					buf.type(type[i]);
    					while(!line.matches("^\\D*$")){
    						b1 = b*10;
    						b = line.charAt(i);
    						b = b1 + b;
    						i++;
    					}
    					while(!line.matches("^\\D*$")){
    						e1 = e*10;
    						e = line.charAt(i);   //важно, что здесь начинается с установеленного в предыдущем цикле i
    						e = e1 + e;
    						i++;
    					}
    					buf.location(b, e);
    					while (scanner.hasNextLine()) {
    	    				line = scanner.nextLine();
    	    				if (line.indexOf("/label") != -1){
    	    					i = line.indexOf("/label");
    	    					name = line.substring(i+8);
    	    					buf.name(name);
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
    			FileInputStream fileStream = new FileInputStream(name);
    			Scanner scanner = new Scanner(fileStream);
    			while (scanner.hasNextLine()) {
    				String line = scanner.nextLine();
    				if (line.indexOf("ORIGIN") != -1){
    					while (scanner.hasNextLine()) {
    	    				line = scanner.nextLine();
    	    				for (i = 0; i < line.length(); i++){ //TODO
    	    					if ((line.charAt(i) == 'a')|| (line.charAt(i) == 'c')){
    	    						zy = zy + line.charAt(i);
    	    					}
    	    				}
    					}
    				}
    			System.out.println(line);
    			System.out.println(zy);
    			System.out.println();
    			}
    			scanner.close();
    		} catch (FileNotFoundException ee) {
    			ee.printStackTrace();
    		}
    		/////////////////////////////////////
		}
		else{
			 createGUI();
        		         		
		}
	}



	public static void createGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Уведомление");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel label = new JLabel("Неподдерживаемый формат файла");
        frame.getContentPane().add(label);
        
        frame.setPreferredSize(new Dimension(200, 100));
        
        frame.pack();
        frame.setVisible(true);          
   
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
             public void run() {
                  createGUI();
             }
        });
   }


}
