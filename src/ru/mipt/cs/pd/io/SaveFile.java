package ru.mipt.cs.pd.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import ru.mipt.cs.pd.dna.Feature;

public class SaveFile extends InputFile {

	public static void createFile(String str) {

		int lengthZy, strNumber, numberOfFeatures, i;
		String current;
		String form = formDNA;
		String color1 = "", color2 = "";
		String zy = str;
		StringBuilder text = new StringBuilder();
		text.append("LOCUS ").append(form).append("\n")
				.append("FEATURES Location/Qualifiers" + '\n');
		numberOfFeatures = feature.size();
	
		for (i = 0; i < numberOfFeatures; i++) {
			Feature buf = InputFile.feature.get(i);
			text.append("     " + buf.getType() + "             "
					+ buf.getBegin() + ".." + buf.getEnd() + '\n'
					+ "                     /gene=" + "'" + buf.getName() + "'"
					+ '\n' + "                     /label=" + buf.getName()
					+ '\n' + "                     /ApEinfo_fwdcolor=" + color1
					+ '\n' + "                     /ApEinfo_revcolor=" + color2
					+ '\n');
		}
		text.append("ORIGIN" + '\n');
		lengthZy = zy.length();
		if ((lengthZy % 60) > 0) {
			strNumber = (lengthZy / 60) + 1;
		} else {
			strNumber = lengthZy / 60;
		}
		for (i = 0; i < strNumber; i++) { // TODO
			text.append("\t");
			int con = (i + 1) * 60;
			int max000 = zy.length();
			if (con<max000){
				current = zy.substring(i * 60, con);
			}
			else{
				current = zy.substring(i * 60);
			}
			text.append((i * 60 + 1) + " " + current + '\n');
		}
		

		String filename = JOptionPane.showInputDialog("Name the file");
		JFileChooser savefile = new JFileChooser();
		savefile.setSelectedFile(new File(filename));
		int sf = savefile.showSaveDialog(savefile);
		Writer writer;
		if (sf == JFileChooser.APPROVE_OPTION) {
			try {
				writer = new FileWriter(savefile.getSelectedFile());
				writer.write(text.toString());
				writer.close();
				JOptionPane.showMessageDialog(null, "File has been saved",
						"File Saved", JOptionPane.INFORMATION_MESSAGE);

			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (sf == JFileChooser.CANCEL_OPTION) {
			JOptionPane.showMessageDialog(null, "File save has been canceled");
		}
	}

}
