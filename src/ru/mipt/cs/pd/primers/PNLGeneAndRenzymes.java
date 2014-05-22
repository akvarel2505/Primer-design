package ru.mipt.cs.pd.primers;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

import ru.mipt.cs.pd.dna.Environment;
import ru.mipt.cs.pd.dna.SimpleExtract;
import ru.mipt.cs.pd.dna.primers.AutoPrimers;
import ru.mipt.cs.pd.dna.primers.AutoPrimersAlg;
import ru.mipt.cs.pd.dna.primers.HandMadePrimer;
import ru.mipt.cs.pd.primers.interfaces.IntFRMSimplePrimers;
import ru.mipt.cs.pd.restrictases.RenzymeMass;
import ru.mipt.cs.pd.restrictases.RenzymeWithANumber;


public class PNLGeneAndRenzymes extends JPanel{

	private JButton btnFindAutoPrimers, btnBack; //bottomButtons
	private JButton selectAllR, deselectAllR, selectAllL, deselectAllL;
	private JTextArea txtShowGene;
	private JList<String> leftRenz, rightRenz;
	private JScrollPane scrlRight, scrlLeft, GeneScrollPane;
	private TitledBorder brdLeft, brdRight, brdGene;
	
	private ArrayList<RenzymeWithANumber> selectedRight, selectedLeft; 
	private int geneBegin, geneEnd;
	private Object geneTag;
	private EnzymesWithInfo leftEnz, rightEnz;
	private FRMWait message;

	private IntFRMSimplePrimers parentFrame;
	
	public PNLGeneAndRenzymes(SimpleExtract gene, RenzymeMass restr, IntFRMSimplePrimers parent){
		
		parentFrame=parent;
		
		//creating the buttons
		btnFindAutoPrimers = new JButton(LabelsEN.btnFindAutoPrimers);
		btnBack = new JButton(LabelsEN.btnBack);
		selectAllR = new JButton(LabelsEN.selectAll); 
		deselectAllR = new JButton(LabelsEN.deselectAll);
		selectAllL = new JButton(LabelsEN.selectAll);
		deselectAllL = new JButton(LabelsEN.deselectAll);
		
		makeButtonListeners();		
		organiseRenzymesLists(gene, restr);		
		mouseListenersOnRenzymes();
				
		// work with highlighted text in the Main Molecule
		txtShowGene.addMouseListener(new java.awt.event.MouseListener(){
			public void mouseClicked(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}

			public void mouseReleased(MouseEvent arg0) {
				
				JTextField txtEditPrimer = parentFrame.getTxtEditPrimer();
				JLabel infoAboutEdited = parentFrame.getInfoAboutEdited();
				int beg = txtShowGene.getSelectionStart();
				int end = txtShowGene.getSelectionEnd();
				if ((end-beg)<75) try {
					txtEditPrimer.setText(txtShowGene.getSelectedText());
					HandMadePrimer currentlyEdited = new HandMadePrimer(beg, end, txtEditPrimer.getText());
					String str=String.format(LabelsEN.formatInfoAboutEdited, currentlyEdited.getTm(), currentlyEdited.getLength(), currentlyEdited.getPercentageGC());
					infoAboutEdited.setText(str);
					parentFrame.getPNLEditedExtract().setPrimer(currentlyEdited);
					parentFrame.getPNLEditedExtract().setBegin(beg);
					parentFrame.getPNLEditedExtract().setEnd(end);
				}
				catch (java.lang.NullPointerException e){}
				catch (java.lang.StringIndexOutOfBoundsException e){}
			}
		});

		highlightGene();
		highlightAll(leftEnz);
		highlightAll(rightEnz);				
				
		makeDesign();		
		setVisible(true);

	}
	///END OF FRAME BUILDER
	//////
	////
	//
	//
	
	//nested class
	class EnzymesWithInfo{
		final int size;
		boolean ifLeft;
		RenzymeWithANumber[] array;
		int[] beginings;
		int[] lengths;
		boolean[] ifSelected;
		Object[] tags;
		String[] forList;
		
		//other sites
		int begOfOther[][];
		int sizesOther[];
		Object tagsOfOther[][];
		
		EnzymesWithInfo(RenzymeWithANumber[] arr, boolean ifLeft){
			size=arr.length;
			array=arr;
			this.ifLeft=ifLeft;
			int i;
			
			forList=new String[size];
			lengths=new int[size];
			//array with lengths 
			for (i=0; i<size; i++){
				lengths[i]=arr[i].renzyme.getPlace().length();
				String bbuf=arr[i].toString();
				if (arr[i].renzyme.check(geneBegin,geneEnd)) forList[i]=bbuf+" !!!";
				else forList[i]=bbuf;
			}
			
			//begining in the main molecule
			beginings=new int[size];
			if (!ifLeft){
				for (i=0; i<size; i++) beginings[i]=geneEnd+arr[i].number-lengths[i]+1;
			}
			else for (i=0; i<size; i++) beginings[i]=geneBegin-arr[i].number;
			
			//initialize remaining variables
			ifSelected=new boolean[size];
			tags=new Object[size];
			
			//other restriction sites
			begOfOther = new int[size][];
			tagsOfOther = new Object[size][];
			sizesOther=new int[size];
			
			for (i=0; i<size; i++){
				int k=proceed(i, beginings[i]);
				tagsOfOther[i]=new Object[k];
				sizesOther[i]=k;
			}
			
		} //end of builder
		
		int proceed(int ind, int importantPos){
			
			int nPos=array[ind].renzyme.getNumOfPos()-1;
			begOfOther[ind] = new int[nPos];
			ArrayList<SimpleExtract> buf = array[ind].renzyme.getPosPlaces();
			int i,psbl;
			int curr=0;
			for (i=0; i<(nPos+1); i++){
				psbl=buf.get(i).getBegin();
				if (psbl!=beginings[ind]) {
					begOfOther[ind][curr]=psbl;
					curr++;
				}
			}
			
			return nPos;
		};
	}
	
	private void highlight(EnzymesWithInfo arrRestr, int ind) {
		
		dehighlightGene();
		arrRestr.ifSelected[ind]=true;
		//highlighter for restriction enzymes
		DefaultHighlightPainter redPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.red);
		//for insignificant sites
		DefaultHighlightPainter pinkPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.pink);
		
		Object currH;
		
		//highlight renzyme
		int beg=arrRestr.beginings[ind];
		int len=arrRestr.lengths[ind];
		int end=beg+len;
		try{
				currH=txtShowGene.getHighlighter().addHighlight(beg, end, redPainter);
				arrRestr.tags[ind]=currH;
			
				int kOthers=arrRestr.sizesOther[ind];
				for (int i=0; i<kOthers; i++){
					beg=arrRestr.begOfOther[ind][i];
					end=beg+2;
					currH=txtShowGene.getHighlighter().addHighlight(beg, end, pinkPainter);
					arrRestr.tagsOfOther[ind][i]=currH;
				}
		
		}
		catch (BadLocationException e) {}
		highlightGene();
	}

	private void highlightAll(EnzymesWithInfo arrRestr) {
		
		int max=arrRestr.size;
		
		for (int i=0; i<max; i++){
			if (!(arrRestr.ifSelected[i])){
				highlight(arrRestr, i);
				arrRestr.ifSelected[i]=true;
				if (arrRestr.ifLeft) selectedLeft.add(arrRestr.array[i]);
				else selectedRight.add(arrRestr.array[i]);
			}
		}
		
	}
		
	
	private void dehighlight(EnzymesWithInfo arrRestr, int ind) {
		arrRestr.ifSelected[ind]=false;
		txtShowGene.getHighlighter().removeHighlight(arrRestr.tags[ind]);
		
		int kOthers=arrRestr.sizesOther[ind];
		for (int i=0; i<kOthers; i++){
			txtShowGene.getHighlighter().removeHighlight(arrRestr.tagsOfOther[ind][i]);
		}
	}
	
	
	private void dehighlightAll(EnzymesWithInfo arrRestr) {
		
		int max=arrRestr.size;
		
		for (int i=0; i<max; i++){
			if (arrRestr.ifSelected[i]){
				dehighlight(arrRestr, i);
				arrRestr.ifSelected[i]=false;
				if (arrRestr.ifLeft) selectedLeft.remove(arrRestr.array[i]);
				else selectedRight.remove(arrRestr.array[i]);
			}
		}
	}
	
	private void highlightGene(){
		//highlighter for gene
		DefaultHighlightPainter yellowPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.green);
		
		//highlight gene
		try{
			geneTag=txtShowGene.getHighlighter().addHighlight(geneBegin, geneEnd, yellowPainter);
		}
		catch (BadLocationException e) {}
		
	}
	
	private void dehighlightGene(){
		txtShowGene.getHighlighter().removeHighlight(geneTag);
	}
	
	private void organiseRenzymesLists(SimpleExtract gene, RenzymeMass restr){
		
		geneBegin=gene.getBegin();
		geneEnd=gene.getEnd();		
		
		leftEnz = new EnzymesWithInfo(restr.findLeft(geneBegin),true);
		rightEnz = new EnzymesWithInfo(restr.findRight(geneEnd),false);
			 
		selectedRight = Environment.rightRenzymes;
		selectedLeft = Environment.leftRenzymes;
		
		txtShowGene=new JTextArea();		
		txtShowGene.setEditable(false);
		
		String stringGene = Environment.getMainDNA();
		txtShowGene.setText(stringGene);
		txtShowGene.setCaretPosition(0);
		txtShowGene.setLineWrap(true);
		brdGene = new TitledBorder(LabelsEN.dna53);
		txtShowGene.setBorder(brdGene);
		
		
		// now add txtShowGene to a scroll pane
		GeneScrollPane = new JScrollPane(txtShowGene);
		GeneScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GeneScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		//creating JLists with restriction enzymes
		
		///////////// left
		
		leftRenz = new JList<String>(leftEnz.forList);
		leftRenz.setLayoutOrientation(JList.VERTICAL);
		leftRenz.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrlLeft=new JScrollPane(leftRenz);
		scrlLeft.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrlLeft.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		brdLeft = new TitledBorder(LabelsEN.brdLeftEnz);
		scrlLeft.setBorder(brdLeft);
		
		///////////// right
		
		rightRenz = new JList<String>(rightEnz.forList);
		rightRenz.setLayoutOrientation(JList.VERTICAL);
		rightRenz.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrlRight=new JScrollPane(rightRenz);
		scrlRight.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrlRight.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);		
		
		brdRight = new TitledBorder(LabelsEN.brdRightEnz);
		scrlRight.setBorder(brdRight);

	}
	
	private void makeButtonListeners(){
		
		btnFindAutoPrimers.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent evt) {

				Point p = new Point(btnFindAutoPrimers.getX(), btnFindAutoPrimers.getY()/2);
				message = new FRMWait(p);
				
				SwingUtilities.invokeLater(new Runnable() { 
					 public void run() { 
						//begin algorithm
							parentFrame.clearAutoPrimers();
							AutoPrimersAlg primerChooser = new AutoPrimersAlg(geneBegin, geneEnd, selectedLeft, selectedRight);
							AutoPrimers res = primerChooser.getResult();
							parentFrame.showPrimers(res);
							//end algorithm
							
							message.dispose();
					 } 
					 }); 
			}
		});
		//
		//Buttons for highlighting
		//
		
		selectAllR.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				highlightAll(rightEnz);
			}
		});
		
		deselectAllR.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				dehighlightAll(rightEnz);
			}
		});
		
		selectAllL.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				highlightAll(leftEnz);
			}
		});
		
		deselectAllL.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				dehighlightAll(leftEnz);
			}
		});
		
		//Buttons for highlighting - end
		//
		
		btnBack.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				parentFrame.clearAll();
				Environment.leftRenzymes.clear();
				Environment.rightRenzymes.clear();
				parentFrame.dispose();
			}
		});

	}
	
	private void mouseListenersOnRenzymes(){
		//MOUSE CLICKS on the JLists (followed by highlighting restriction sites in txtShowGene)
		
				leftRenz.addMouseListener(new java.awt.event.MouseListener(){
						@Override
						public void mouseClicked(MouseEvent arg0) {
							try{
							int indLeft=leftRenz.getSelectedIndex();
							RenzymeWithANumber buf=leftEnz.array[indLeft];
							
							if (selectedLeft.contains(buf)) {
								selectedLeft.remove(buf);
								dehighlight(leftEnz,indLeft);
							}
							else {
								selectedLeft.add(buf);
								highlight(leftEnz,indLeft);
							}
						}
						catch (ArrayIndexOutOfBoundsException eee){
							
						}

						}
						public void mouseEntered(MouseEvent arg0) {}
						public void mouseExited(MouseEvent arg0) {}
						public void mousePressed(MouseEvent arg0) {}
						public void mouseReleased(MouseEvent arg0) {}
				});

				rightRenz.addMouseListener(new java.awt.event.MouseListener(){
							@Override
							public void mouseClicked(MouseEvent arg0) {
								
								int indRight=rightRenz.getSelectedIndex();
								
								try{
								
								RenzymeWithANumber buf=rightEnz.array[indRight];
								
								//System.out.println(buf.toString());
								
								if (selectedRight.contains(buf)) {
									selectedRight.remove(buf);
									dehighlight(rightEnz,indRight);
								}
								else {
									selectedRight.add(buf);
									highlight(rightEnz,indRight);
								}
								}
								catch (ArrayIndexOutOfBoundsException eee){
									
								}
								
							}

							public void mouseEntered(MouseEvent arg0) {}
							public void mouseExited(MouseEvent arg0) {}
							public void mousePressed(MouseEvent arg0) {}
							public void mouseReleased(MouseEvent arg0) {}
				});

				// end: work with the lists of restriction enzymes		
	}
	
	private void makeDesign(){
		//DESIGN
				GroupLayout layout = new GroupLayout(this);
				setLayout(layout);
				layout.setAutoCreateGaps(true);
				layout.setAutoCreateContainerGaps(true);
				
				//creating horizontal group, ordinate axis is compressed	
				
				GroupLayout.SequentialGroup HORbottomButtons = layout.createSequentialGroup();
				HORbottomButtons.addComponent(btnFindAutoPrimers);
				HORbottomButtons.addComponent(btnBack);
				
				//new buttons
				GroupLayout.SequentialGroup selButtonsL = layout.createSequentialGroup();
				selButtonsL.addComponent(selectAllL);
				selButtonsL.addComponent(deselectAllL);
						
				GroupLayout.SequentialGroup selButtonsR = layout.createSequentialGroup();
				selButtonsR.addComponent(selectAllR);
				selButtonsR.addComponent(deselectAllR);
				
				GroupLayout.ParallelGroup listWithButL = layout.createParallelGroup();
				listWithButL.addGroup(GroupLayout.Alignment.CENTER, selButtonsL);
				listWithButL.addComponent(scrlLeft);
						
				GroupLayout.ParallelGroup listWithButR = layout.createParallelGroup();
				listWithButR.addGroup(GroupLayout.Alignment.CENTER, selButtonsR);
				listWithButR.addComponent(scrlRight);
				
				
				GroupLayout.SequentialGroup lists = layout.createSequentialGroup();
				lists.addGroup(listWithButL);
				lists.addGroup(listWithButR);
				//
				
				GroupLayout.ParallelGroup HORbigGroup = layout.createParallelGroup();
				HORbigGroup.addGroup(GroupLayout.Alignment.CENTER, lists);
				HORbigGroup.addComponent(GeneScrollPane, GroupLayout.Alignment.CENTER);
				HORbigGroup.addGroup(GroupLayout.Alignment.CENTER, HORbottomButtons);
				
				layout.setHorizontalGroup(HORbigGroup);
			
				
				//creating vertical group, X axis is compressed	

				//new buttons
				GroupLayout.ParallelGroup VselButtonsL = layout.createParallelGroup();
				VselButtonsL.addComponent(selectAllL);
				VselButtonsL.addComponent(deselectAllL);
				
				GroupLayout.ParallelGroup VselButtonsR = layout.createParallelGroup();
				VselButtonsR.addComponent(selectAllR);
				VselButtonsR.addComponent(deselectAllR);
						
				GroupLayout.SequentialGroup VlistWithButL = layout.createSequentialGroup();
				VlistWithButL.addGroup(VselButtonsL);
				VlistWithButL.addComponent(scrlLeft);
						
				GroupLayout.SequentialGroup VlistWithButR = layout.createSequentialGroup();
				VlistWithButR.addGroup(VselButtonsR);
				VlistWithButR.addComponent(scrlRight);
						
						
				GroupLayout.ParallelGroup listsV = layout.createParallelGroup();
				listsV.addGroup(VlistWithButL);
				listsV.addGroup(VlistWithButR);
				//
						
				GroupLayout.ParallelGroup VERbottomButtons = layout.createParallelGroup();
				VERbottomButtons.addComponent(btnFindAutoPrimers);
				VERbottomButtons.addComponent(btnBack);
				
				GroupLayout.SequentialGroup VERbigGroup = layout.createSequentialGroup();
				VERbigGroup.addGroup(listsV);
				VERbigGroup.addComponent(GeneScrollPane);
				VERbigGroup.addGroup(VERbottomButtons);
				
				layout.setVerticalGroup(VERbigGroup);
				
				scrlRight.setMinimumSize(new Dimension(25, 75));
				scrlLeft.setMinimumSize(new Dimension(25, 75));	
				GeneScrollPane.setMinimumSize(new Dimension(25, 35));
				
				layout.linkSize(selectAllR, deselectAllR, selectAllL, deselectAllL);
				layout.linkSize(btnFindAutoPrimers, btnBack);

	}
	
	public JTextArea getTxtShowGene(){
		return txtShowGene;
	}
}
