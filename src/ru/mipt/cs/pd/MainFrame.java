package ru.mipt.cs.pd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame implements ActionListener{

	// components
	private JPanel contentPane;
	private JButton btnPrimers;
	private JButton btnRestrictaza;
	private JButton btnCertificate;
	private JTextArea txtMain;
	private JScrollPane scrlMain;
	private JFileChooser fc;
	private MainFrame Self=this;

	// objects
	private SimpleExtract passed;

	JMenuBar menuBar;
	JMenu menu, submenu;
	JMenuItem menuItemOpen, menuItemNew, menuItemSave;
	JRadioButtonMenuItem rbMenuItem;
	JCheckBoxMenuItem cbMenuItem;

	
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
	

	private void initComponents() {

		contentPane = new JPanel();
		setContentPane(contentPane);
		btnPrimers = new JButton();
		btnRestrictaza = new JButton();
		btnCertificate = new JButton();

		fc=new JFileChooser();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Master of primers");

		btnPrimers.setText("Подобрать праймеры");
		btnPrimers.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// btnPrimersActionPerformed(evt);
			}
		});

		btnRestrictaza.setText("Подобрать рестриктазу");
		btnRestrictaza.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// btnRestrictazaActionPerformed(evt);
			}
		});
		btnCertificate.setText("Открыть справку");
		btnCertificate.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// btnCertificateActionPerformed(evt);
			}
		});

	}

	public MainFrame() {

		initComponents();

		setBounds(100, 100, 450, 300);

		

		txtMain = new JTextArea(InputFile.zy);
		txtMain.setLineWrap(true);

		scrlMain = new JScrollPane(txtMain);
		scrlMain.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrlMain.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		// ///////////////////////
		// creating the menu
		
		// Create the menu bar.
		menuBar = new JMenuBar();

		// Build the first menu.
		menu = new JMenu("Меню");
		menu.setMnemonic(KeyEvent.VK_A);
		menuBar.add(menu);

		// a group of JMenuItems
		menuItemOpen = new JMenuItem("Открыть файл", KeyEvent.VK_T);
		menuItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
				ActionEvent.ALT_MASK));
		menu.add(menuItemOpen);
		menuItemOpen.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Self.actionPerformed(arg0);
				
			}
			
		});

		menuItemNew = new JMenuItem("Создать новый");
		menuItemNew.setMnemonic(KeyEvent.VK_B);
		menu.add(menuItemNew);

		cbMenuItem = new JCheckBoxMenuItem("Сохранить изменения");
		cbMenuItem.setMnemonic(KeyEvent.VK_H);
		menu.add(cbMenuItem);

		// Build second menu "Edition"
		menu = new JMenu("Редактировать");
		menu.setMnemonic(KeyEvent.VK_N);
		menuBar.add(menu);
		
		
		this.setJMenuBar(menuBar);
		// ///////////////////////////////////
		
		//////////////////////
		// describing open and save buttons
		final String newline = "\n";

		///////////////////////////////

		contentPane.add(btnPrimers);
		contentPane.add(scrlMain);

		TheMainDNAMolecule zyyy = new TheMainDNAMolecule(txtMain.getText());

		// обработка нажатия на кнопку. Вообще хорошо бы этот класс сделать
		// нормальным, а не анонимным
		btnPrimers.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						int i, j;
						i = txtMain.getSelectionStart();
						j = txtMain.getSelectionEnd();
						passed = new SimpleExtract(i, j);

						RenzymeMass restrictionEnzymesNearGene = new RenzymeMass();

						FRMSimplePrimers x = new FRMSimplePrimers(passed,
								restrictionEnzymesNearGene); 
					}
				});

			}
		});

		// DESIGN
		GroupLayout layout = new GroupLayout(contentPane);
		contentPane.setLayout(layout);

		// auto gaps
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		GroupLayout.ParallelGroup HORGroup = layout.createParallelGroup();
		HORGroup.addComponent(btnPrimers);
		HORGroup.addComponent(btnRestrictaza);
		HORGroup.addComponent(btnCertificate);
		HORGroup.addComponent(scrlMain);
		layout.setHorizontalGroup(HORGroup);

		GroupLayout.SequentialGroup VertGroup = layout.createSequentialGroup();
		VertGroup.addComponent(btnPrimers);
		VertGroup.addComponent(btnRestrictaza);
		VertGroup.addComponent(btnCertificate);
		VertGroup.addComponent(scrlMain);
		layout.setVerticalGroup(VertGroup);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		 
        //Handle open button action.
        if (e.getSource() == menuItemOpen) {
        	System.out.println("some");
            int returnVal = fc.showOpenDialog(Self);
 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //This is where a real application would open the file.
                String name = file.getName();
            	String format="";
            	int i = 0, k;
            	while (i < name.length()){
            		if (name.charAt(i) == '.'){
            			k = i + 1;
            			format=name.substring(k);
            		}
            		i++;
            	}
            	System.out.println(format);
            
        //Handle save button action.
       // } else if (e.getSource() == menuItemSave) {
         //   int returnVal = fc.showSaveDialog(MainFrame.this);
            
            
            }
            }
        }
	}    

