package ru.mipt.cs.pd.primers;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.border.TitledBorder;

import ru.mipt.cs.pd.dna.Environment;
import ru.mipt.cs.pd.dna.primers.Primer;

public class PNLPreviousPrimers extends JPanel{
	
	private JList<String> listLeftPrimers, listRightPrimers;
	private TitledBorder brdLeft, brdRight;
	private JScrollPane scrlLeft, scrlRight;
	private DefaultListModel left, right;
	private ArrayList<Primer> leftPrimers=Environment.leftPrimers;
	private ArrayList<Primer> rightPrimers=Environment.rightPrimers;
	//private ArrayList<RenzymeWithANumber> leftRenzymes = Environment.leftRenzymes; 
	//private ArrayList<RenzymeWithANumber> rightRenzymes = Environment.rightRenzymes;
	 
	
	public PNLPreviousPrimers(){
		
		GridLayout layout = new GridLayout();
		setLayout(layout);
		
		organiseLists();
		
		this.add(scrlLeft);
		this.add(scrlRight);
		
		makeListeners();
		
		setVisible(true);
	}
	
	private void makeListeners() {
		
		listLeftPrimers.addMouseListener(new java.awt.event.MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int ind = ((JList)(arg0.getSource())).getSelectedIndex();
				FRMPrimerInfo x = new FRMPrimerInfo(leftPrimers.get(ind), left, Environment.leftPrimers);
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
	});

	listRightPrimers.addMouseListener(new java.awt.event.MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int ind = ((JList)(arg0.getSource())).getSelectedIndex();
				FRMPrimerInfo x = new FRMPrimerInfo(rightPrimers.get(ind), right, Environment.leftPrimers);
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
	});
	}
	
	private String[] makeList(ArrayList<Primer> list){
		int max=list.size();
		String[] res = new String[max];
		
		for (int i=0; i<max; i++){
			res[i]=list.get(i).toString(true);
		}
		
		return res;
	};
	
	private void organiseLists(){
		
		left = new DefaultListModel();
		listLeftPrimers = new JList<String>(makeList(leftPrimers));
		brdLeft=new TitledBorder(LabelsEN.brdLeftPrimers);
		scrlLeft=new JScrollPane(listLeftPrimers);
		scrlLeft.setBorder(brdLeft);
		listLeftPrimers.setModel(left);
		
		right = new DefaultListModel();
		listRightPrimers = new JList<String>(makeList(rightPrimers));
		brdRight=new TitledBorder(LabelsEN.brdRightPrimers);
		scrlRight=new JScrollPane(listRightPrimers);
		scrlRight.setBorder(brdRight);
		listRightPrimers.setModel(right);
		
	}

	public DefaultListModel getDefaultListModel() {
		return left;
	}

}
