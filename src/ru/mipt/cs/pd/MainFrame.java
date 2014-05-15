package ru.mipt.cs.pd;

import java.awt.Color;
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
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter.HighlightPainter;

import ru.mipt.cs.pd.dna.Environment;
import ru.mipt.cs.pd.dna.Feature;
import ru.mipt.cs.pd.dna.SimpleExtract;
import ru.mipt.cs.pd.io.InputFile;
import ru.mipt.cs.pd.io.SaveFile;
import ru.mipt.cs.pd.primers.FRMSimplePrimers;
import ru.mipt.cs.pd.restrictases.EnzymeSelector;
import ru.mipt.cs.pd.restrictases.RenzymeMass;

public class MainFrame extends JFrame implements ActionListener{

	// components
	private JPanel contentPane;
	private JButton btnPrimers;
	private JButton btnRestrictaza;
	private JButton btnCertificate;
	protected JTextArea txtMain;
	private JScrollPane scrlMain;
	private JFileChooser fc;
	private MainFrame Self=this;

	// objects
	private SimpleExtract passed;

	JMenuBar menuBar;
	JMenu menu, submenu;
	JMenuItem menuItemOpen, menuItemNew, menuItemSave, menuItemFeature;
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

		btnPrimers.setText("Primers");

		btnRestrictaza.setText("Restriction enzymes");
		btnRestrictaza.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				EnzymeSelector e = new EnzymeSelector();
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

		

		txtMain = new JTextArea(InputFile.zy);          //TODO
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

		menuItemOpen = new JMenuItem("Открыть файл", KeyEvent.VK_T);
		menuItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				ActionEvent.CTRL_MASK));
		menu.add(menuItemOpen);
		menuItemOpen.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Self.actionPerformed(arg0);
			}
		});

		menuItemNew = new JMenuItem("Создать новый");
		menuItemNew.setMnemonic(KeyEvent.VK_B);
		menuItemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));
		menu.add(menuItemNew);

		menuItemSave = new JMenuItem("Сохранить изменения");
		menuItemSave.setMnemonic(KeyEvent.VK_H);
		menuItemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		menu.add(menuItemSave);
		menuItemSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				SaveFile.createFile(txtMain.getText());
			}
			
		});

		// Build second menu "Edition"
		menu = new JMenu("Редактирование");
		menu.setMnemonic(KeyEvent.VK_N);
		JMenuItem menuItem = null;
		
		menuItemFeature = new JMenuItem("Создать новую Feature");
		menuItemFeature.setMnemonic(KeyEvent.VK_Z);
		menuItemFeature.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
				ActionEvent.CTRL_MASK));
		menu.add(menuItemFeature);
		menuItemFeature.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg1) {
				
			}
			
		});
		
        menuItem = new JMenuItem(new DefaultEditorKit.CutAction());
        menuItem.setText("Вырезать");
        menuItem.setMnemonic(KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.CTRL_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem(new DefaultEditorKit.CopyAction());
        menuItem.setText("Копировать");
        menuItem.setMnemonic(KeyEvent.VK_C);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.CTRL_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem(new DefaultEditorKit.PasteAction());
        menuItem.setText("Вставить");
        menuItem.setMnemonic(KeyEvent.VK_P);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				ActionEvent.CTRL_MASK));
        menu.add(menuItem);

		menuBar.add(menu);
		this.setJMenuBar(menuBar);
		// ///////////////////////////////////	
		
		// describing open and save buttons
		final String newline = "\n";

		///////////////////////////////

		contentPane.add(btnPrimers);
		contentPane.add(scrlMain);

		btnPrimers.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				Environment.setMainDNA(txtMain.getText());
				
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						int i, j;
						i = txtMain.getSelectionStart();
						j = txtMain.getSelectionEnd();
						passed = new SimpleExtract(i, j);

						RenzymeMass restrictionEnzymesNearGene = new RenzymeMass();

						FRMSimplePrimers x = new FRMSimplePrimers(passed, restrictionEnzymesNearGene); 
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
                String path = file.getPath();
            	String format="";
            	int i = 0, k;
            	while (i < name.length()){
            		if (name.charAt(i) == '.'){
            			k = i + 1;
            			format=name.substring(k);
            		}
            		i++;
            	}
            	InputFile.OpenFile(format, path, txtMain);
            	highlightFeatures();
            	}
            }
        }
	public void highlightFeatures(){
		Highlighter h = txtMain.getHighlighter();
		DefaultHighlightPainter painters[] = new DefaultHighlighter.DefaultHighlightPainter[5];
		initColors(painters);
		h.removeAllHighlights();
	    int numberOfFeatures = InputFile.feature.size();
	    for (int j = 0; j < numberOfFeatures; j += 1) {
	    	Feature buf = InputFile.feature.get(j);
	    	try {
	          h.addHighlight(buf.getBegin(), buf.getEnd(), painters[buf.getType(true)]);
	        } catch (BadLocationException exc) {
	        }
	  }
	}
	private void initColors(DefaultHighlightPainter[] painters) {
		painters[0] = new DefaultHighlighter.DefaultHighlightPainter(Color.gray);
		painters[1] = new DefaultHighlighter.DefaultHighlightPainter(Color.pink);
		painters[2] = new DefaultHighlighter.DefaultHighlightPainter(Color.green);
		painters[3] = new DefaultHighlighter.DefaultHighlightPainter(Color.orange);
		painters[4] = new DefaultHighlighter.DefaultHighlightPainter(Color.yellow);
	}


	public void highlightEnzymes(SimpleExtract[] array){
		Highlighter h = txtMain.getHighlighter();
	    h.removeAllHighlights();
	    int len=array.length;
	    SimpleExtract buf;
	    for (int j = 0; j < len; j += 1) {
	        buf = array[j];
	    	try {
	          h.addHighlight(buf.getBegin(), buf.getEnd(), DefaultHighlighter.DefaultPainter);
	        } catch (BadLocationException exc) {
	        }
	  }
	}
	
	}    
