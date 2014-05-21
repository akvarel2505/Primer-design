package ru.mipt.cs.pd.dna;

import javax.swing.JOptionPane;

public class AddFeature {
	private Feature feature;
	private String nameFeature = "";
	
	public Feature CreateFeature(int begin, int end){
		feature = new Feature();
		feature.setType(" misc_feature ");
		nameFeature = JOptionPane.showInputDialog("Name the feature");
		feature.name(nameFeature);
		feature.setLocation(begin, end);
		return feature;
	}
}
