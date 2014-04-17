
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

public class FRMSimplePrimers extends JFrame{
	
	private JButton btnFindAutoPrimers, btnFindSecondPrimer; //bottomButtons
	private JButton btnAnalyseFalseSites, btnReference, btnREnzymes;  //threeButtons
	private JTextField txtEditPrimer;
	private JTextArea txtShowGene;
	private JLabel previousPrimer, infoAboutEdited;
	private JList<String> leftRenz, rightRenz;
	private JScrollPane scrlRight, scrlLeft, GeneScrollPane;
	private TitledBorder brdLeft, brdRight, brgLblPrimer, brdLblEdition;
	
	private HandMadeDNA currentlyEdited; 
	private ArrayList<RenzymeWithANumber> selectedRight, selectedLeft; ////????????????????????????????????????????????????????
	private int geneBegin, geneEnd;
	private EnzymesWithInfo leftEnz, rightEnz;
	
	
	public FRMSimplePrimers(SimpleExtract passedGene, RenzymeMass restr){
		initComponents(passedGene, restr);
	}
	
	private void initComponents(SimpleExtract gene, RenzymeMass restr){
		
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
		
		//highlighters
		//for gene
		DefaultHighlightPainter yellowPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.green);
		//for restriction enzymes
		DefaultHighlightPainter redPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.red);
		
		//highlight gene
		try{
			txtShowGene.getHighlighter().addHighlight(gene.getBegin(), gene.getEnd(), yellowPainter);
		}
		catch (BadLocationException e) {}
		
		// now add it to a scroll pane
		GeneScrollPane = new JScrollPane(txtShowGene);
		GeneScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GeneScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		
		
		txtEditPrimer=new JTextField("Выделите что-нибудь в поле с геном");
		btnFindAutoPrimers = new JButton("Подобрать оба праймера");
		btnFindSecondPrimer = new JButton("Подобрать второй праймер");
		btnAnalyseFalseSites = new JButton("Анализ залипаний");
		btnReference = new JButton("Справочная информация");
		btnREnzymes = new JButton("Что-то про рестриктазы");
		previousPrimer = new JLabel("Здесь будет информация о подобранном ранее праймере");
		infoAboutEdited = new JLabel("Это тоже относится к подборке праймера вручную (пока не работает)");
		
		 
		brgLblPrimer = new TitledBorder("Информация о подобранных ранее праймерах");
		previousPrimer.setBorder(brgLblPrimer);
		
		brdLblEdition = new TitledBorder("Информация о редактируемом участке");
		infoAboutEdited.setBorder(brdLblEdition);
		
		leftRenz = new JList<String>(leftEnz.forList);
		leftRenz.setLayoutOrientation(JList.VERTICAL);
		leftRenz.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		scrlLeft=new JScrollPane(leftRenz);
		scrlLeft.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrlLeft.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		brdLeft = new TitledBorder("Рестриктазы слева от гена");
		scrlLeft.setBorder(brdLeft);
		
		/////////////
		
		rightRenz = new JList<String>(rightEnz.forList);
		rightRenz.setLayoutOrientation(JList.VERTICAL);
		rightRenz.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		scrlRight=new JScrollPane(rightRenz);
		scrlRight.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrlRight.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);		
		
		brdRight = new TitledBorder("Рестриктазы справа от гена");
		scrlRight.setBorder(brdRight);
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); ///именно dispose, а не exit
		setTitle("Simple Primers");

		/*============================================*/
		
		
		// click on the button
		// click on the button
		// click on the button
		
		btnReference.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				///////////////////////////
			}
		});
		
		btnREnzymes.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				///////////////////////////
			}
		});
		
		//щелчок по списку
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

		
		btnAnalyseFalseSites.addActionListener(new PressedBtnAnalyseFalseSites());		
		btnFindAutoPrimers.addActionListener(new PressedBtnFindAutoPrimers());
		btnFindSecondPrimer.addActionListener(new PressedBtnFindSecondPrimer());
		
		//обработка выделения текста в окне
		txtShowGene.addMouseListener(new java.awt.event.MouseListener(){

			public void mouseClicked(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				try {
					txtEditPrimer.setText(txtShowGene.getSelectedText());
					currentlyEdited = new HandMadeDNA(txtEditPrimer.getText());
					String str=String.format("Tm = %.1f C (nearest neighbour),   GC = %d per cent",currentlyEdited.getTm(),currentlyEdited.getPercentageGC());
					infoAboutEdited.setText(str);
				}
				catch (java.lang.NullPointerException e){
					
				}
				catch (java.lang.StringIndexOutOfBoundsException e){
					
				}
			}
		});
		
		
		txtEditPrimer.addKeyListener(new java.awt.event.KeyAdapter() {
			
		       public void keyReleased(KeyEvent e) {
		 
		    	   try {
		    		   String str1 = txtEditPrimer.getText();
		    		   currentlyEdited = new HandMadeDNA(str1); 
		    		   String str2=String.format("Tm = %.1f C (nearest neighbour),   GC = %d per cent",currentlyEdited.getTm(),currentlyEdited.getPercentageGC());
		    		   infoAboutEdited.setText(str2);
		    	   }
		    	   catch (java.lang.NullPointerException ff) {}
		    	   catch (java.lang.StringIndexOutOfBoundsException ff){}
		       }
		});
		
		
		// DESIGN OF THE FRAME
		// TODO		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
	
		
		
		
		//creating horizontal group, ordinate axis is compressed	
		
		GroupLayout.SequentialGroup HORthreeButtons = layout.createSequentialGroup();
		HORthreeButtons.addComponent(btnAnalyseFalseSites);
		HORthreeButtons.addComponent(btnREnzymes);
		HORthreeButtons.addComponent(btnReference);
		
		GroupLayout.SequentialGroup lists = layout.createSequentialGroup();
		lists.addComponent(scrlLeft);
		lists.addComponent(scrlRight);
		
		GroupLayout.SequentialGroup HORbottomButtons = layout.createSequentialGroup();
		HORbottomButtons.addComponent(btnFindAutoPrimers);
		HORbottomButtons.addComponent(btnFindSecondPrimer);
		
		GroupLayout.ParallelGroup HORbigGroup = layout.createParallelGroup();
		HORbigGroup.addComponent(previousPrimer, GroupLayout.Alignment.CENTER);
		HORbigGroup.addComponent(infoAboutEdited, GroupLayout.Alignment.CENTER);
		HORbigGroup.addComponent(txtEditPrimer, GroupLayout.Alignment.CENTER);
		HORbigGroup.addGroup(GroupLayout.Alignment.CENTER, HORthreeButtons);
		HORbigGroup.addGroup(GroupLayout.Alignment.CENTER, lists);
		HORbigGroup.addComponent(GeneScrollPane, GroupLayout.Alignment.CENTER);
		HORbigGroup.addGroup(GroupLayout.Alignment.CENTER, HORbottomButtons);
		
		layout.setHorizontalGroup(HORbigGroup);
	
		
		//creating vertical group, X axis is compressed	

		GroupLayout.ParallelGroup VERthreeButtons = layout.createParallelGroup();
		VERthreeButtons.addComponent(btnAnalyseFalseSites);
		VERthreeButtons.addComponent(btnREnzymes);
		VERthreeButtons.addComponent(btnReference);
		
		GroupLayout.ParallelGroup listsV = layout.createParallelGroup();
		listsV.addComponent(scrlLeft);
		listsV.addComponent(scrlRight);
		
		GroupLayout.ParallelGroup VERbottomButtons = layout.createParallelGroup();
		VERbottomButtons.addComponent(btnFindAutoPrimers);
		VERbottomButtons.addComponent(btnFindSecondPrimer);
			
		GroupLayout.SequentialGroup VERbigGroup = layout.createSequentialGroup();
		VERbigGroup.addComponent(previousPrimer);
		VERbigGroup.addComponent(infoAboutEdited);
		VERbigGroup.addComponent(txtEditPrimer);
		VERbigGroup.addGroup(VERthreeButtons);
		VERbigGroup.addGroup(listsV);
		VERbigGroup.addComponent(GeneScrollPane);
		VERbigGroup.addGroup(VERbottomButtons);
		
		layout.setVerticalGroup(VERbigGroup);
		
		//finally
		setSize(new Dimension(640,480));
		setVisible(true);
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
	
}


