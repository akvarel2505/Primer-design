package frmPrimers;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

import frmPrimers.interfaces.intFRMSimplePrimers;
import restrictionEnzymes.RenzymeMass;
import restrictionEnzymes.RenzymeWithANumber;
import DNA.HandMadeDNA;
import DNA.SimpleExtract;


public class pnlGeneAndRenzymes extends JPanel{

	private JButton btnFindAutoPrimers, btnFindSecondPrimer, btnBack; //bottomButtons
	private JButton selectAllR, deselectAllR, selectAllL, deselectAllL;
	private JTextArea txtShowGene;
	private JList<String> leftRenz, rightRenz;
	private JScrollPane scrlRight, scrlLeft, GeneScrollPane;
	private TitledBorder brdLeft, brdRight;
	
	private ArrayList<RenzymeWithANumber> selectedRight, selectedLeft; 
	private int geneBegin, geneEnd;
	private EnzymesWithInfo leftEnz, rightEnz;

	private intFRMSimplePrimers parentFrame;
	
	public pnlGeneAndRenzymes(SimpleExtract gene, RenzymeMass restr, intFRMSimplePrimers parent){
		
		parentFrame=parent;
		
		//creating the buttons
		btnFindAutoPrimers = new JButton(labelsEN.btnFindAutoPrimers);
		btnFindSecondPrimer = new JButton(labelsEN.btnFindSecondPrimer);
		btnBack = new JButton(labelsEN.btnBack);
		selectAllR = new JButton(labelsEN.selectAll); 
		deselectAllR = new JButton(labelsEN.deselectAll);
		selectAllL = new JButton(labelsEN.selectAll);
		deselectAllL = new JButton(labelsEN.deselectAll);
		
		btnFindAutoPrimers.addActionListener(new PressedBtnFindAutoPrimers());
		btnFindSecondPrimer.addActionListener(new PressedBtnFindSecondPrimer());	
		
		//TODO
		selectAllR.addActionListener(null);
		deselectAllR.addActionListener(null);
		selectAllL.addActionListener(null);
		deselectAllL.addActionListener(null);
		
		btnBack.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				parentFrame.dispose();
			}
		});

		
		// work with the lists of restriction enzymes
		
		geneBegin=gene.getBegin();
		geneEnd=gene.getEnd();
		
		leftEnz = new EnzymesWithInfo(restr.findLeft(geneBegin),true);
		rightEnz = new EnzymesWithInfo(restr.findRight(gene.getEnd()),false);
			 
		selectedRight = new ArrayList<RenzymeWithANumber>();
		selectedLeft = new ArrayList<RenzymeWithANumber>();
		
		txtShowGene=new JTextArea();		
		txtShowGene.setEditable(false);
		
		String stringGene = gene.getMainDNA();
		txtShowGene.setText(stringGene);
		txtShowGene.setCaretPosition(0);
		txtShowGene.setLineWrap(true);
		
		//highlighter for gene
		DefaultHighlightPainter yellowPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.green);
		
		//highlight gene
		try{
			txtShowGene.getHighlighter().addHighlight(gene.getBegin(), gene.getEnd(), yellowPainter);
		}
		catch (BadLocationException e) {}
		
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
		
		brdLeft = new TitledBorder(labelsEN.brdRightEnz);
		scrlLeft.setBorder(brdLeft);
		
		///////////// right
		
		rightRenz = new JList<String>(rightEnz.forList);
		rightRenz.setLayoutOrientation(JList.VERTICAL);
		rightRenz.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrlRight=new JScrollPane(rightRenz);
		scrlRight.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrlRight.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);		
		
		brdRight = new TitledBorder(labelsEN.brdRightEnz);
		scrlRight.setBorder(brdRight);

		
		//MOUSE CLICKS on the JLists (followed by highlighting restriction sites in txtShowGene)
		
		leftRenz.addMouseListener(new java.awt.event.MouseListener(){
				@Override
				public void mouseClicked(MouseEvent arg0) {
					
					int indLeft=leftRenz.getSelectedIndex();
					RenzymeWithANumber buf=leftEnz.array[indLeft];
					
					//System.out.println(buf.renzyme.getPlace());
					
					if (selectedLeft.contains(buf)) {
						selectedLeft.remove(buf);
						dehighlight(leftEnz,indLeft);
					}
					else {
						selectedLeft.add(buf);
						highlight(leftEnz,indLeft);
					}
					//TODO//////////////////////////////////////////
					//какие-то дествия относительно подбора праймеров - добавить
					// и то же самое для правых рестриктаз
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
						//TODO//////////////////////////////////////////
						//какие-то дествия относительно подбора праймеров - добавить
					}

					public void mouseEntered(MouseEvent arg0) {}
					public void mouseExited(MouseEvent arg0) {}
					public void mousePressed(MouseEvent arg0) {}
					public void mouseReleased(MouseEvent arg0) {}
		});


		// end: work with the lists of restriction enzymes
		
		// work with highlighted text in the Main Molecule
		
		txtShowGene.addMouseListener(new java.awt.event.MouseListener(){
			public void mouseClicked(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}

			public void mouseReleased(MouseEvent arg0) {
				
				JTextField txtEditPrimer = parentFrame.getTxtEditPrimer();
				JLabel infoAboutEdited = parentFrame.getInfoAboutEdited();
				try {
					txtEditPrimer.setText(txtShowGene.getSelectedText());
					HandMadeDNA currentlyEdited = new HandMadeDNA(txtEditPrimer.getText());
					String str=String.format(labelsEN.formatInfoAboutEdited, currentlyEdited.getTm(), currentlyEdited.getPercentageGC(), currentlyEdited.getLength());
					infoAboutEdited.setText(str);
				}
				catch (java.lang.NullPointerException e){
					
				}
				catch (java.lang.StringIndexOutOfBoundsException e){
					
				}
			}
		});

		
		//DESIGN
		
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		
		//creating horizontal group, ordinate axis is compressed	
		
		GroupLayout.SequentialGroup HORbottomButtons = layout.createSequentialGroup();
		HORbottomButtons.addComponent(btnFindAutoPrimers);
		HORbottomButtons.addComponent(btnFindSecondPrimer);
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
		VERbottomButtons.addComponent(btnFindSecondPrimer);
		VERbottomButtons.addComponent(btnBack);
		
		GroupLayout.SequentialGroup VERbigGroup = layout.createSequentialGroup();
		VERbigGroup.addGroup(listsV);
		VERbigGroup.addComponent(GeneScrollPane);
		VERbigGroup.addGroup(VERbottomButtons);
		
		layout.setVerticalGroup(VERbigGroup);
		
		layout.linkSize(selectAllR, deselectAllR, selectAllL, deselectAllL);
		layout.linkSize(btnFindAutoPrimers, btnFindSecondPrimer, btnBack);
		setVisible(true);

	}
	
	
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
				forList[i]=arr[i].toString();
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
		}
		
	}
	
	private void highlight(EnzymesWithInfo arrRestr, int ind) {
		
		//highlighter for restriction enzymes
		DefaultHighlightPainter redPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.red);
		
		Object currH;
		
		//highlight renzyme
		int beg=arrRestr.beginings[ind];
		int end=beg+arrRestr.lengths[ind];
		try{
				currH=txtShowGene.getHighlighter().addHighlight(beg, end, redPainter);
				arrRestr.tags[ind]=currH;
			}
		catch (BadLocationException e) {}
		
	}

	private void dehighlight(EnzymesWithInfo arrRestr, int ind) {	
				txtShowGene.getHighlighter().removeHighlight(arrRestr.tags[ind]);
	}

}
