package ru.mipt.cs.pd;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
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
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import ru.mipt.cs.pd.dna.AddFeature;
import ru.mipt.cs.pd.dna.Environment;
import ru.mipt.cs.pd.dna.Feature;
import ru.mipt.cs.pd.dna.MakeComplement;
import ru.mipt.cs.pd.dna.SimpleExtract;
import ru.mipt.cs.pd.io.InputFile;
import ru.mipt.cs.pd.io.NewFile;
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
	protected static JTextArea txtMain;
	private JScrollPane scrlMain;
	private JFileChooser fc;
	private MainFrame Self=this;
	private JScrollPane forFeatures;
	private JList<String> list;
	private DefaultListModel<String> listModel;

	// objects
	private SimpleExtract passed;

	JMenuBar menuBar;
	JMenu menu, submenu;
	JMenuItem menuItemOpen, menuItemNew, menuItemSave, menuItemFeature, menuItemComplement;
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

		fc = new JFileChooser();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Master of primers");

		btnPrimers.setText("Primers");

		btnRestrictaza.setText("Restriction enzymes");
		btnRestrictaza.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				EnzymeSelector e = new EnzymeSelector();
			}
		});
		btnCertificate.setText("Open reference"); //справка
		btnCertificate.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
			}
		});
	}

	public MainFrame() {
		
		initComponents();
		this.setExtendedState(MAXIMIZED_BOTH);
		txtMain = new JTextArea(InputFile.zy);          
		txtMain.setLineWrap(true);

		scrlMain = new JScrollPane(txtMain);
		scrlMain.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrlMain.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		listModel = new DefaultListModel();
		list = new JList<String>(listModel);
		forFeatures = new JScrollPane(list);
	    forFeatures.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    forFeatures.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);		
		
		showFeatures();

	    
	    forFeatures.setMaximumSize(new Dimension(100000,45));
	    forFeatures.setMinimumSize(new Dimension(10,45));	    
		
		// Create the menu bar.
		menuBar = new JMenuBar();

		// Build the first menu.
		menu = new JMenu("Menu");
		menu.setMnemonic(KeyEvent.VK_A);
		menuBar.add(menu);

		menuItemOpen = new JMenuItem("Open file", KeyEvent.VK_T);
		menuItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				ActionEvent.CTRL_MASK));
		menu.add(menuItemOpen);
		menuItemOpen.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Self.actionPerformed(arg0);
			}
		});

		menuItemNew = new JMenuItem("Create new");
		menuItemNew.setMnemonic(KeyEvent.VK_B);
		menuItemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));
		menu.add(menuItemNew);
		menuItemNew.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				NewFile.OpenNewFile();   
			}
		});

		menuItemSave = new JMenuItem("Save changes");
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
		menu = new JMenu("Editing");
		menu.setMnemonic(KeyEvent.VK_N);
		JMenuItem menuItem = null;
		
		menuItemFeature = new JMenuItem("Create new Feature");
		menuItemFeature.setMnemonic(KeyEvent.VK_Z);
		menuItemFeature.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
				ActionEvent.CTRL_MASK));
		menu.add(menuItemFeature);
		menuItemFeature.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg1) {
				int begin = txtMain.getSelectionStart();
				int end = txtMain.getSelectionEnd();
				AddFeature x = new AddFeature();
				Feature res;
				res = x.CreateFeature(begin, end);
				highlightAFeature(res);
				InputFile.feature.add(res);
			}
		});
		
		menuItemComplement = new JMenuItem("Paste Complement");
		menuItemComplement.setMnemonic(KeyEvent.VK_Z);
		menuItemComplement.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				ActionEvent.CTRL_MASK));
		menu.add(menuItemComplement);
		menuItemFeature.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg1) {
				int begin = txtMain.getSelectionStart();
				int end = txtMain.getSelectionEnd();
				String zy = txtMain.getText();
				MakeComplement.ComplementExtract(begin, end, zy);        //TODO
			}
		});
		
        menuItem = new JMenuItem(new DefaultEditorKit.CutAction());
        menuItem.setText("Cut");
        menuItem.setMnemonic(KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.CTRL_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem(new DefaultEditorKit.CopyAction());
        menuItem.setText("Copy");
        menuItem.setMnemonic(KeyEvent.VK_C);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.CTRL_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem(new DefaultEditorKit.PasteAction());
        menuItem.setText("Paste");
        menuItem.setMnemonic(KeyEvent.VK_P);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				ActionEvent.CTRL_MASK));
        menu.add(menuItem);
        
        //////////////////////////////////////////// undo
       	final UndoManager undo = new UndoManager();
		Document doc = txtMain.getDocument();
		doc.addUndoableEditListener(new UndoableEditListener() {
		     public void undoableEditHappened(UndoableEditEvent evt) {
		           undo.addEdit(evt.getEdit());
		     }
		 });
		 txtMain.getActionMap().put("Undo",
		  new AbstractAction("Undo") {
		     public void actionPerformed(ActionEvent evt) {
		            try {
		               if (undo.canUndo()) {
		                       undo.undo();
		                }
		            } catch (CannotUndoException e) {
		     }
		  }
		 });
		 txtMain.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "Undo");
         ////////////////////////////////////////////////////////////////////// redo
		 txtMain.getActionMap().put("Redo",
			        new AbstractAction("Redo") {
			            public void actionPerformed(ActionEvent evt) {
			                try {
			                    if (undo.canRedo()) {
			                        undo.redo();
			                    }
			                } catch (CannotRedoException e) {
			                }
			            }
			        });
		txtMain.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "Redo");
        ////////////////////////////////////////////////////////////////////////
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
		// ///////////////////////////////////	
		
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
		HORGroup.addComponent(forFeatures);
		HORGroup.addComponent(scrlMain);
		layout.setHorizontalGroup(HORGroup);

		GroupLayout.SequentialGroup VertGroup = layout.createSequentialGroup();
		VertGroup.addComponent(btnPrimers);
		VertGroup.addComponent(btnRestrictaza);
		VertGroup.addComponent(btnCertificate);
		VertGroup.addComponent(forFeatures);
		VertGroup.addComponent(scrlMain);
		layout.setVerticalGroup(VertGroup);
	}

	public void actionPerformed(ActionEvent e) {
        //Handle open button action.
        if (e.getSource() == menuItemOpen) {
            int returnVal = fc.showOpenDialog(Self);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
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
            	showFeatures();
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
	
	public void highlightAFeature(Feature buf){
		Highlighter h = txtMain.getHighlighter();
		DefaultHighlightPainter p = new DefaultHighlighter.DefaultHighlightPainter(Color.blue);
	    	try {
	          h.addHighlight(buf.getBegin(), buf.getEnd(), p);
	        } catch (BadLocationException exc) {       }	  
	 
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
	
	private void showFeatures(){
		int numberOfFeatures = InputFile.feature.size();
		String feat[] = new String[numberOfFeatures];
		for (int count = 0; count < numberOfFeatures; count++){
			feat[count] = InputFile.feature.get(count).toString();
			System.out.println(feat[count]);
			listModel.addElement(feat[count]);
		}
	}
	
}    
