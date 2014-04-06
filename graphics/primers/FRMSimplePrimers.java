package graphics.primers;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.GroupLayout.*;

public class FRMSimplePrimers extends JFrame{
	
	private JButton btnFindAutoPrimers, btnFindSecondPrimer;
	private JTextField txtShowGene;
	
	public FRMSimplePrimers(){
		initComponents();
	}
	
	private void initComponents(){
		
		// creating components
		txtShowGene=new JTextField("actcgc");
		btnFindAutoPrimers = new JButton("Оба праймера программно");
		btnFindSecondPrimer = new JButton("Только второй праймер");
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Simple Primers");
		
		
		// click on the button
		btnFindAutoPrimers.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				///////////////////////////
			}
		});
		
		// design of the frame
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		//creating horizontal group, ordinate axis is compressed	
		GroupLayout.SequentialGroup HORbottomButtons = layout.createSequentialGroup();
		HORbottomButtons.addComponent(btnFindAutoPrimers);
		HORbottomButtons.addComponent(btnFindSecondPrimer);
		
		GroupLayout.ParallelGroup HORbigGroup = layout.createParallelGroup();
		HORbigGroup.addComponent(txtShowGene);
		HORbigGroup.addGroup(HORbottomButtons);
		
		layout.setHorizontalGroup(HORbigGroup);
		
		
		
		//creating vertical group, X axis is compressed	

		GroupLayout.ParallelGroup VERbottomButtons = layout.createParallelGroup();
		VERbottomButtons.addComponent(btnFindAutoPrimers);
		VERbottomButtons.addComponent(btnFindSecondPrimer);
			
		GroupLayout.SequentialGroup VERbigGroup = layout.createSequentialGroup();
		VERbigGroup.addComponent(txtShowGene);
		VERbigGroup.addGroup(VERbottomButtons);
		
		layout.setVerticalGroup(VERbigGroup);
		
		//finally
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
