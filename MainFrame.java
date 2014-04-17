
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.*;


public class MainFrame extends JFrame {

	//components
	private JPanel contentPane;
	private JButton btnPrimers;
	private JTextArea txtMain;
	private JScrollPane scrlMain;
	
	//objects
	private SimpleExtract passed;
	

	/**
	 * Launch the application.*/
	 
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		setContentPane(contentPane);
		
		btnPrimers = new JButton("Подобрать праймеры");
		
		txtMain=new JTextArea("tggcgaatgggacgcgccctgtagcggcgcattaagcgcggcgggtgtggtggttacgcgcagcgtgaccgctacacttgccagcgccctagcgcccgctcctttcgctttcttcccttcctttctcgccacgttcgccggctttccccgtcaagctctaaatcgggggctccctttagggttccgatttagtgctttacggcacctcgaccccaaaaaacttgattagggtgatggttcacgtagtgggccatcgccctgatagacggtttttcgccctttgacgttggagtccacgttctttaatagtggactcttgttccaaactggaacaacactcaaccctatctcggtctattcttttgatttataagggattttgccgatttcggcctattggttaaaaaatgagctgatttaacaaaaatttaacgcgaattttaacaaaatattaacgtttacaatttcaggtggcacttttcggggaaatgtgcgcggaacccctatttgtttatttttctaaatacattcaaatatgtatccgctcatgagacaataaccctgataaatgcttcaataa");
		txtMain.setLineWrap(true);
		
		scrlMain = new JScrollPane(txtMain);
		scrlMain.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrlMain.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		contentPane.add(btnPrimers);
		contentPane.add(scrlMain);
		
		TheMainDNAMolecule zy = new TheMainDNAMolecule(txtMain.getText());
		
		//обработка нажатия на кнопку. Вообще хорошо бы этот класс сделать нормальным, а не анонимным
		btnPrimers.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent evt) {

				SwingUtilities.invokeLater(new Runnable(){
					public void run(){
						int i,j;
						i=txtMain.getSelectionStart();
						j=txtMain.getSelectionEnd();
						passed=new SimpleExtract(i,j);
						
						RenzymeMass restrictionEnzymesNearGene = new RenzymeMass();
						
						FRMSimplePrimers x = new FRMSimplePrimers(passed, restrictionEnzymesNearGene);
					}
				});
				
			}
		});
		
		//DESIGN
		GroupLayout layout = new GroupLayout(contentPane);
		contentPane.setLayout(layout);
		
		//auto gaps
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		GroupLayout.ParallelGroup HORGroup = layout.createParallelGroup();
		HORGroup.addComponent(btnPrimers);
		HORGroup.addComponent(scrlMain);
		layout.setHorizontalGroup(HORGroup);
		
		GroupLayout.SequentialGroup VertGroup = layout.createSequentialGroup();
		VertGroup.addComponent(btnPrimers);
		VertGroup.addComponent(scrlMain);
		layout.setVerticalGroup(VertGroup);
	}

}
