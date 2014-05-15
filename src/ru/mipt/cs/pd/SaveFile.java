package ru.mipt.cs.pd.io;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import ru.mipt.cs.pd.dna.Feature;

public class SaveFile extends InputFile{
	
	public static void createFile(String str) {

		int lengthZy, strNumber, numberOfFeatures, i, global;
		String current;
		String form = "linear";
		String color1="", color2="";
		String zy = str;
		StringBuilder text = new StringBuilder();
		text.append("LOCUS " +
				form + '\n' +
				"FEATURES Location/Qualifiers" + '\n');
		numberOfFeatures = feature.size();
		for (i = 0; i < numberOfFeatures; i++){
			Feature buf = InputFile.feature.get(i);
			text.append("     " +
				buf.getType() + "             " +
				buf.getBegin() + ".." + buf.getEnd() + '\n'+
				"                     /gene=" + "'" + buf.getName() + "'" + '\n' +
				"                     /label=" + buf.getName() + '\n' +
	            "                     /ApEinfo_fwdcolor=" + color1 + '\n' +
	            "                     /ApEinfo_revcolor=" + color2 + '\n');
		}
		text.append("ORIGIN" + '\n');
		lengthZy = zy.length();
		if ((lengthZy % 60) > 0){
			strNumber = (lengthZy / 60) + 1;
		}
		else{
			strNumber = lengthZy / 60;
		}
		for (i = 0; i < strNumber - 1; i++){   //TODO
			text.append("      ");
			current = zy.substring(i*60 + 1, (i + 1)*60);
			text.append((i*60 + 1) + current + '\n');
		}
		/*current = zy.substring((i+1)*60 + 1, lengthZy);
		text.append(((i+1)*60 + 1) + current + '\n');
		text.append("//");*/
		
        String filename = JOptionPane.showInputDialog("Name the file");
        JFileChooser savefile = new JFileChooser();
        savefile.setSelectedFile(new File(filename));
        savefile.showSaveDialog(savefile);
        BufferedWriter writer;
        int sf = savefile.showSaveDialog(null);
        if(sf == JFileChooser.APPROVE_OPTION){
            try {
            	writer = new BufferedWriter(new FileWriter(savefile.getSelectedFile()));
                writer.write(text.toString());
            	writer.close();
                JOptionPane.showMessageDialog(null, "File has been saved","File Saved",JOptionPane.INFORMATION_MESSAGE);
               
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(sf == JFileChooser.CANCEL_OPTION){
            JOptionPane.showMessageDialog(null, "File save has been canceled");
        }
    }

}
