package ru.mipt.cs.pd.primers;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import ru.mipt.cs.pd.dna.primers.AutoPrimers;
import ru.mipt.cs.pd.dna.primers.FalseSite;
import ru.mipt.cs.pd.dna.primers.Primer;
import ru.mipt.cs.pd.primers.interfaces.IntPNLPreviousPrimers;

public class FRMPrimerInfo extends JFrame{
	
	private JLabel lblPrimer;
	private JList<String> listFalseSites;
	private JScrollPane scrlFalseSites;
	private PNLSolutionParameters envirInfo;
	private JButton btnBack = new JButton(LabelsEN.back);
	private JButton btnAccept = new JButton(LabelsEN.accept);
	private JButton btnDiscard = new JButton(LabelsEN.discard);
	private TitledBorder brdFalseSites = new TitledBorder(LabelsEN.brdFS);
	private Primer primer;
	private DefaultListModel listModel;
	private ArrayList<Primer> parentList;
	private IntPNLPreviousPrimers parentFrame;
	
	public FRMPrimerInfo(Primer currPrimer, DefaultListModel par, ArrayList<Primer> somePrimers, IntPNLPreviousPrimers parr){
		
		parentFrame=parr;

		//Environment.prinAllDNAs();
		
		listModel=par;
		parentList=somePrimers;
		primer=currPrimer;
		
		this.setTitle(LabelsEN.headPrimerInfo);
		this.setSize(640, 480);
		
		//format of the information about primer
		
		String initial = makeInfoString(currPrimer);
		
		lblPrimer = new JLabel(String.format(initial, currPrimer.getTm(),currPrimer.getLength(), currPrimer.getPercentageGC()));
		envirInfo = new PNLSolutionParameters(null);
		envirInfo.disableEdit();
		
		listFalseSites = new JList<String>(formList(currPrimer));
		listFalseSites.setLayoutOrientation(JList.VERTICAL);
		listFalseSites.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrlFalseSites=new JScrollPane(listFalseSites);
		scrlFalseSites.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrlFalseSites.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrlFalseSites.setBorder(brdFalseSites);
		
		btnBack.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				dispose();
			}
		});
		
		btnAccept.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (parentList.contains(primer)){
					
				}
				else{
					parentList.add(primer);
					String str=primer.toString(true);
					listModel.addElement(str);
				}
				dispose();
			}
		});
		
		btnDiscard.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (parentList.contains(primer)){
					int ind=parentList.indexOf(primer);
					parentList.remove(primer);
					//System.out.println("removed from left-right list");
					listModel.remove(ind);
					//System.out.println("removed from visible list");
					
					try{
					if (parentFrame.getListsOfAutoPrimers().left.contains(primer)){
						parentFrame.getListsOfAutoPrimers().left.remove(primer);
					}
					if (parentFrame.getListsOfAutoPrimers().right.contains(primer)){
						parentFrame.getListsOfAutoPrimers().right.remove(primer);
					}
					}
					catch (NullPointerException e) {};
					
					}
					//Environment.prinAllDNAs();
				primer.die();
				dispose();
			}
		});
		
		makeDesign();		
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
	
	private String makeInfoString(Primer currPrimer) {
		
		String res;
		StringBuffer x=new StringBuffer();
		
		res = currPrimer.toString();
		if (currPrimer.ifRight()) x.append(ru.mipt.cs.pd.utils.DNAStringUtils.comRev(res));
		else x.append(res);
		
		String bb=LabelsEN.accent;
		String be=LabelsEN.accentEnd;
		int lenBeg=bb.length();
		int lenEnd=be.length();
		
		//FalseSite arrFS[] = revFalseSites(currPrimer.getFalseSites());
		
		FalseSite arrFS[] = currPrimer.getFalseSites();
		int max=arrFS.length;
		
		for (int i=0; i<max; i++){
			
			if (currPrimer.ifRight()) revFalseSite(arrFS[i], currPrimer);
			
			int  pos = arrFS[i].getPrimerEnd();
			insert(x, pos, be,  arrFS, lenBeg, lenEnd);
			
			pos = arrFS[i].getPrimerBeg();
			insert(x, pos, bb,  arrFS, lenBeg, lenEnd);
			
			checked.add(arrFS[i]);
		}
	
		x.append(LabelsEN.lblMaskPrimerInfo);
		x.insert(0,LabelsEN.html);
		
		res = x.toString();
		return res;
	}
	
	
	private void revFalseSite(FalseSite falseSite, Primer primer) {
		int len = primer.getLength();
		int oldB=falseSite.getPrimerBeg();
		int oldE=falseSite.getPrimerEnd();
		
		falseSite.setPrimerLocation(len-oldE, len-oldB);
	}

	private void insert(StringBuffer x, int pos, String str,  FalseSite[] arrFS, int lenBeg, int lenEnd){
		Pair pair=calculateBegsEnds(pos, arrFS);
		pos += pair.b*lenBeg + pair.e*lenEnd;
		x.insert(pos, str);
	}
	
	private ArrayList<FalseSite> checked = new ArrayList<FalseSite>();
	
	private Pair calculateBegsEnds(int pos, FalseSite[] arrFS) {
		int max=arrFS.length;
		
		Pair res=new Pair();
		
		for (int i=0; i<max; i++){
			if (checked.contains(arrFS[i])){
				if (arrFS[i].getPrimerEnd()<pos) res.e++;
				if (arrFS[i].getPrimerBeg()<pos) res.b++;	
			}
		}
		return res;
	}

	class Pair{
		int b=0;
		int e=0;
	}
	
	private void makeDesign(){
		//DESIGN
		
				GroupLayout layout = new GroupLayout(this.getContentPane());
				getContentPane().setLayout(layout);
				layout.setAutoCreateGaps(true);
				layout.setAutoCreateContainerGaps(true);
				
				//creating horizontal group, ordinate axis is compressed	
				
				GroupLayout.SequentialGroup HOR = layout.createSequentialGroup();
				HOR.addComponent(scrlFalseSites);
				HOR.addComponent(envirInfo);
				
				GroupLayout.SequentialGroup bottomHor = layout.createSequentialGroup();
				bottomHor.addComponent(btnAccept);
				bottomHor.addComponent(btnDiscard);
				bottomHor.addComponent(btnBack);
				
				GroupLayout.ParallelGroup HORbigGroup = layout.createParallelGroup();
				HORbigGroup.addComponent(lblPrimer, GroupLayout.Alignment.CENTER);
				HORbigGroup.addGroup(GroupLayout.Alignment.CENTER, HOR);
				HORbigGroup.addGroup(GroupLayout.Alignment.CENTER, bottomHor);
				
				layout.setHorizontalGroup(HORbigGroup);
			
				
				//creating vertical group, X axis is compressed	

				
				GroupLayout.ParallelGroup VER = layout.createParallelGroup();
				VER.addComponent(scrlFalseSites);
				VER.addComponent(envirInfo);
				//
				
				GroupLayout.ParallelGroup bottomVer = layout.createParallelGroup();
				bottomVer.addComponent(btnAccept);
				bottomVer.addComponent(btnDiscard);
				bottomVer.addComponent(btnBack);
				
				
				GroupLayout.SequentialGroup VERbigGroup = layout.createSequentialGroup();
				VERbigGroup.addComponent(lblPrimer);
				VERbigGroup.addGroup(VER);
				VERbigGroup.addGroup(bottomVer);
				
				layout.setVerticalGroup(VERbigGroup);

	}
	
}
