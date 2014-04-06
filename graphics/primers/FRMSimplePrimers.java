package graphics.primers;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class FRMSimplePrimers extends JFrame{
	
	private JButton btnFindAutoPrimers, btnFindSecondPrimer; //bottomButtons
	private JButton btnAnalyseFalseSites, btnReference, btnREnzymes;  //threeButtons
	private JTextField txtShowGene, txtEditPrimer;
	private JLabel previousPrimer, infoAboutEdited;
	private JList<String> leftRenz, rightRenz;
	
	public FRMSimplePrimers(){
		initComponents();
	}
	
	private void initComponents(){
		
		//for lists
		String[] dataRight = {"Справа от гена","Right","dkgj;ad","fffffffffff","qwerty"};
		String[] dataLeft = {"Слева","Left","Left"};
		// it will be changed
		
		
		// creating components
		txtShowGene=new JTextField("actcgc");
		txtEditPrimer=new JTextField("Это пока не работает");
		btnFindAutoPrimers = new JButton("Подобрать оба праймера");
		btnFindSecondPrimer = new JButton("Подобрать второй праймер");
		btnAnalyseFalseSites = new JButton("Анализ залипаний");
		btnReference = new JButton("Справочная информация");
		btnREnzymes = new JButton("Что-то про рестриктазы");
		previousPrimer = new JLabel("Здесь будет информация о подобранном ранее праймере. Это относится к подборке праймера вручную (пока не работает)");
		infoAboutEdited = new JLabel("Это тоже относится к подборке праймера вручную (пока не работает)");
		
		leftRenz = new JList<String>(dataLeft);
		leftRenz.setLayoutOrientation(JList.VERTICAL_WRAP);
		leftRenz.setVisibleRowCount(0);
		leftRenz.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
       // leftRenz.setPreferredSize(new Dimension(100,150));
		
		rightRenz = new JList<String>(dataRight);
		rightRenz.setLayoutOrientation(JList.VERTICAL_WRAP);
		rightRenz.setVisibleRowCount(0);
		rightRenz.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		//rightRenz.setPreferredSize(new Dimension(100,150));/////////
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Simple Primers");
		
		
		// click on the button
		btnFindAutoPrimers.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				///////////////////////////
			}
		});
		
		// click on the button
		btnFindSecondPrimer.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				///////////////////////////
			}
		});
		
		
		// DESIGN OF THE FRAME
		
				
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
		lists.addComponent(leftRenz);
		lists.addComponent(rightRenz);
		
		GroupLayout.SequentialGroup HORbottomButtons = layout.createSequentialGroup();
		HORbottomButtons.addComponent(btnFindAutoPrimers);
		HORbottomButtons.addComponent(btnFindSecondPrimer);
		
		GroupLayout.ParallelGroup HORbigGroup = layout.createParallelGroup();
		HORbigGroup.addComponent(previousPrimer);
		HORbigGroup.addComponent(infoAboutEdited);
		HORbigGroup.addComponent(txtEditPrimer);
		HORbigGroup.addGroup(HORthreeButtons);
		HORbigGroup.addGroup(lists);
		HORbigGroup.addComponent(txtShowGene);
		HORbigGroup.addGroup(HORbottomButtons);
		
		layout.setHorizontalGroup(HORbigGroup);
		
		
		//creating vertical group, X axis is compressed	

		GroupLayout.ParallelGroup VERthreeButtons = layout.createParallelGroup();
		VERthreeButtons.addComponent(btnAnalyseFalseSites);
		VERthreeButtons.addComponent(btnREnzymes);
		VERthreeButtons.addComponent(btnReference);
		
		GroupLayout.ParallelGroup listsV = layout.createParallelGroup();
		listsV.addComponent(leftRenz);
		listsV.addComponent(rightRenz);
		
		GroupLayout.ParallelGroup VERbottomButtons = layout.createParallelGroup();
		VERbottomButtons.addComponent(btnFindAutoPrimers);
		VERbottomButtons.addComponent(btnFindSecondPrimer);
			
		GroupLayout.SequentialGroup VERbigGroup = layout.createSequentialGroup();
		VERbigGroup.addComponent(previousPrimer);
		VERbigGroup.addComponent(infoAboutEdited);
		VERbigGroup.addComponent(txtEditPrimer);
		VERbigGroup.addGroup(VERthreeButtons);
		VERbigGroup.addGroup(listsV);
		VERbigGroup.addComponent(txtShowGene);
		VERbigGroup.addGroup(VERbottomButtons);
		
		layout.setVerticalGroup(VERbigGroup);
		
		//finally
		pack();
		setVisible(true);
	}
	
	public static void main(String args[]){
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				FRMSimplePrimers x = new FRMSimplePrimers();
			}
		});
		
	}
}
