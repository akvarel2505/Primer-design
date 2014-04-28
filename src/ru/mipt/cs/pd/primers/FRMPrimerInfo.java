package ru.mipt.cs.pd.primers;

import javax.swing.*;

import ru.mipt.cs.pd.dna.EnvironmentConstants;
import ru.mipt.cs.pd.dna.primers.FalseSite;
import ru.mipt.cs.pd.dna.primers.Primer;
import ru.mipt.cs.pd.references.FirstFrame;

public class FRMPrimerInfo extends JFrame{
	
	private JLabel lblPrimer;
	private JList<String> listFalseSites;
	private JScrollPane scrlFalseSites;
	private PNLSolutionParameters envirInfo;
	private JButton btnBack = new JButton("Back");
	
	public FRMPrimerInfo(Primer currPrimer){
		
		this.setTitle(LabelsEN.headPrimerInfo);
		this.setSize(640, 480);
		
		lblPrimer = new JLabel("Here will be some information about primer");
		envirInfo = new PNLSolutionParameters(null);
		envirInfo.disableEdit();
		
		listFalseSites = new JList<String>(formList(currPrimer));
		listFalseSites.setLayoutOrientation(JList.VERTICAL);
		listFalseSites.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrlFalseSites=new JScrollPane(listFalseSites);
		scrlFalseSites.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrlFalseSites.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		btnBack.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				dispose();
			}
		});
		
		//DESIGN
		
		GroupLayout layout = new GroupLayout(this.getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		
		//creating horizontal group, ordinate axis is compressed	
		
		GroupLayout.SequentialGroup HOR = layout.createSequentialGroup();
		HOR.addComponent(scrlFalseSites);
		HOR.addComponent(envirInfo);
		
		GroupLayout.ParallelGroup HORbigGroup = layout.createParallelGroup();
		HORbigGroup.addComponent(lblPrimer, GroupLayout.Alignment.CENTER);
		HORbigGroup.addGroup(GroupLayout.Alignment.CENTER, HOR);
		HORbigGroup.addComponent(btnBack, GroupLayout.Alignment.CENTER);
		
		layout.setHorizontalGroup(HORbigGroup);
	
		
		//creating vertical group, X axis is compressed	

		//new buttons
		
		GroupLayout.ParallelGroup VER = layout.createParallelGroup();
		VER.addComponent(scrlFalseSites);
		VER.addComponent(envirInfo);
		//
				
		GroupLayout.SequentialGroup VERbigGroup = layout.createSequentialGroup();
		VERbigGroup.addComponent(lblPrimer);
		VERbigGroup.addGroup(VER);
		VERbigGroup.addComponent(btnBack);
		
		layout.setVerticalGroup(VERbigGroup);
		
		setVisible(true);
		
	}
	
	private String[] formList(Primer pr){
		
		FalseSite[] ar = pr.getFalseSites();
		int max=ar.length;
		String[] res = new String[max];
		
		for (int i=0; i<max; i++){
			res[i]=ar[i].toString(true);
		}
		return res;
	};
	
}
