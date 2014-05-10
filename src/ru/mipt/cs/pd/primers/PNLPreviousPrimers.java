package ru.mipt.cs.pd.primers;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import ru.mipt.cs.pd.dna.Environment;
import ru.mipt.cs.pd.dna.primers.AutoPrimers;
import ru.mipt.cs.pd.dna.primers.Primer;
import ru.mipt.cs.pd.primers.interfaces.IntPNLPreviousPrimers;

public class PNLPreviousPrimers extends JPanel implements IntPNLPreviousPrimers{
	
	private JList<String> listLeftPrimers, listRightPrimers;
	private TitledBorder brdLeft, brdRight;
	private JScrollPane scrlLeft, scrlRight;
	private DefaultListModel<String> left, right;
	private ArrayList<Primer> leftPrimers=Environment.leftPrimers;
	private ArrayList<Primer> rightPrimers=Environment.rightPrimers;
	private AutoPrimers autoRes;
	private IntPNLPreviousPrimers self=this;
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
				int ind = ((JList<String>)(arg0.getSource())).getSelectedIndex();
				new FRMPrimerInfo(leftPrimers.get(ind), left, Environment.leftPrimers, self);
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
				new FRMPrimerInfo(rightPrimers.get(ind), right, Environment.rightPrimers, self);
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
		
		left = new DefaultListModel<String>();
		listLeftPrimers = new JList<String>(makeList(leftPrimers));
		brdLeft=new TitledBorder(LabelsEN.brdLeftPrimers);
		scrlLeft=new JScrollPane(listLeftPrimers);
		scrlLeft.setBorder(brdLeft);
		listLeftPrimers.setModel(left);
		
		right = new DefaultListModel<String>();
		listRightPrimers = new JList<String>(makeList(rightPrimers));
		brdRight=new TitledBorder(LabelsEN.brdRightPrimers);
		scrlRight=new JScrollPane(listRightPrimers);
		scrlRight.setBorder(brdRight);
		listRightPrimers.setModel(right);
		
	}

	public DefaultListModel<String> getDefaultListModel() {
		return left;
	}

	
	public void showPrimers(AutoPrimers res) {
		
		autoRes=res;
		
		int max = res.left.size();
		int i;
		String str;
		for (i=0; i<max; i++){
			str = res.left.get(i).toString(true);
			left.addElement(str);
			Environment.leftPrimers.add(res.left.get(i));
		}
		max = res.right.size();
		for (i=0; i<max; i++){
			str = res.right.get(i).toString(true);
			right.addElement(str);
			Environment.rightPrimers.add(res.right.get(i));
		}
	}
	
	public void clearAllAuto(){
		int ind;
		Primer curr;
		try{
		while (!autoRes.left.isEmpty()) {
			curr = autoRes.left.remove(0);
			ind = Environment.leftPrimers.indexOf(curr);
			Environment.leftPrimers.remove(ind);
			left.remove(ind);
			curr.die();
		}
		while (!autoRes.right.isEmpty()) {
			curr = autoRes.right.remove(0);
			ind = Environment.rightPrimers.indexOf(curr);
			Environment.rightPrimers.remove(ind);
			right.remove(ind);
			curr.die();
		}
		}
		catch (NullPointerException e) {}
	}
	
	public void clearAll(){
		clearAllAuto();
		clearArray(Environment.DNAs);
		clearArray(Environment.leftPrimers);
		clearArray(Environment.rightPrimers);
		left.clear();
		right.clear();
	}
	
	private void clearArray(ArrayList ar){
		int max=ar.size();
		for (int i=max; i>0; i--){
			ar.remove(i-1); 
		}
	}

	@Override
	public AutoPrimers getListsOfAutoPrimers() {
		return autoRes;
	}

}
