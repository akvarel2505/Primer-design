package ru.mipt.cs.pd.restrictases;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.ColorUIResource;

import ru.mipt.cs.pd.dna.SimpleExtract;

/**
 * Created by futame on 06.03.14.
 */

public class EnzymeSelector extends RenzymeParentWithRenzymeMass {
	
    static GridBagLayout gridBagLayout = new GridBagLayout();
    static GridBagConstraints gridBagConstraints = new GridBagConstraints();
    static ArrayList<Renzyme> renzymes;
    static JPanel northPane = new JPanel();
    static JFrame eFrame = new JFrame("Select enzyme");
    private static IntParentFrame parentFrame;

    static ActionListener actionListenerDeleteEnzymes;
    
    private static void createMenu() {
        JMenuBar eMenu = new JMenuBar();
        JMenu eFile = new JMenu("File");
        eMenu.add(eFile);

        ActionListener openFile = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FileFilter eFileFilter = new FileNameExtensionFilter("Text files", "txt");
                JFileChooser eFileChooser = new JFileChooser();
                eFileChooser.setAcceptAllFileFilterUsed(false);
                eFileChooser.addChoosableFileFilter(eFileFilter);
                int returnVal = eFileChooser.showOpenDialog(eFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    RenzymeMass.seteSelectedFile(eFileChooser.getSelectedFile());
                    renzymeArrayList = new RenzymeMass();
                }
                EnzymeSelector.repaintTable();
            }
        };

        JMenuItem eOpen = new JMenuItem("Open new enzymes file");
        eOpen.addActionListener(openFile);
        eFile.add(eOpen);

        JMenuItem eSaveDefault = new JMenuItem("Save current enzymes file as Default");
        eSaveDefault.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                RenzymeMass.setDefaultFile();
            }
        });
        eFile.add(eSaveDefault);


        JMenuItem eSave = new JMenuItem("Save enzymes");
        eSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                FileFilter eFileFilter = new FileNameExtensionFilter("Text files", "txt");
                JFileChooser eFileChooser = new JFileChooser();
                eFileChooser.setAcceptAllFileFilterUsed(false);
                eFileChooser.addChoosableFileFilter(eFileFilter);
                int returnVal = eFileChooser.showSaveDialog(eFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = eFileChooser.getSelectedFile();
                    try {
                        selectedFile.createNewFile();
                        FileOutputStream fos = new FileOutputStream(selectedFile, false);
                        PrintWriter printWriter = new PrintWriter(fos);
                        for (int i = 0; i < renzymes.size(); ++i) {
                            printWriter.println(renzymes.get(i).getName() + " " + renzymes.get(i).getPlace());
                        }
                        printWriter.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        eFile.add(eSave);
        JSeparator eF2 = new JSeparator();
        eFile.add(eF2);

        ActionListener lstnrExitBtn = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                EnzymeSelector.eFrame.dispose();
            }
        };
        JMenuItem eClose = new JMenuItem("Close selection dialog");
        eClose.addActionListener(lstnrExitBtn);
        eFile.add(eClose);

        JMenu eEnzymes = new JMenu("Enzymes");
        eMenu.add(eEnzymes);

        JMenuItem eNew = new JMenuItem("New enzyme...");
        ActionListener lstnrNew = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                NewEnzyme newEnzyme = new NewEnzyme();
            }
        };
        eNew.addActionListener(lstnrNew);
        eEnzymes.add(eNew);

        JMenuItem eDeleteEnz = new JMenuItem("Delete selected enzymes");
        eDeleteEnz.addActionListener(actionListenerDeleteEnzymes);
        eEnzymes.add(eDeleteEnz);

        JMenu eList = new JMenu("List");
        eMenu.add(eList);
        JMenuItem eShowEnz = new JMenuItem("List enzymes");
        ActionListener lsnrListEnzymes = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                ListEnzymes listEnzymes = new ListEnzymes();
            }
        };
        eShowEnz.addActionListener(lsnrListEnzymes);
        eList.add(eShowEnz);

        eFrame.setJMenuBar(eMenu);

    }

    private static void createNorth() {
        JPanel northPane = new JPanel();

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        eFrame.add(northPane, gridBagConstraints);
    }

    //TODO
    public static void repaintTable(){
        for (int i = 0; i < labels.length; ++i) {
            if (labels[i] != null){
                eFrame.remove(labels[i]);
            }
        }
        scrollPane.remove(mainPanel);
        eFrame.remove(scrollPane);
        eFrame.remove(southPane);
        createTable();
        eFrame.pack();
        eFrame.repaint();
    }


    static JScrollPane scrollPane;
    static JPanel southPane;
    static JPanel mainPanel;
    static Label[] labels;

    private static void createTable() {
    if (RenzymeMass.suffixAutomata.getWarning()) {
        eFrame.setBackground(Color.red);
    } else {
        eFrame.setBackground(new ColorUIResource(238,238,238));
        renzymes = renzymeArrayList.getMainRenzymeStructure();
        final int K = renzymes.size();

        final int xstep = 80;
        final int numOfRows = 8;
        final int numOfColumns = (K + numOfRows - 1)/numOfRows;
        labels = new Label[K];

        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        scrollPane = new JScrollPane(mainPanel);


        for (int j = 0; j < numOfColumns; ++j){
            for (int i = 0; i < numOfRows; ++i){
                if ((j * numOfRows + i) < K){
                    final int num = j * numOfRows + i;
                    labels[num] = new Label(renzymes.get(num).toString());
                    MouseListener changeColor = new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent mouseEvent) {
                            if (labels[num].getBackground() != Color.RED) {
                                labels[num].setBackground(Color.RED);
                            } else {
                                labels[num].setBackground(eFrame.getBackground());
                            }

                        }

                        @Override
                        public void mousePressed(MouseEvent mouseEvent) {

                        }

                        @Override
                        public void mouseReleased(MouseEvent mouseEvent) {

                        }

                        @Override
                        public void mouseEntered(MouseEvent mouseEvent) {

                        }

                        @Override
                        public void mouseExited(MouseEvent mouseEvent) {

                        }
                    };
                    labels[j * numOfRows + i].addMouseListener(changeColor);
                    constraints.gridx = i;
                    constraints.gridy = j;
                    mainPanel.add(labels[j * numOfRows + i],constraints);
                }
            }
        }

        actionListenerDeleteEnzymes = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                int N = renzymes.size() - 1;
                for (int i = 0; i < N; ++i){
                    if (labels[i].getBackground() == Color.RED) {
                        renzymeArrayList.deleteRenzyme(renzymes.get(i).getName());
                    }
                }
                EnzymeSelector.repaintTable();
            }
        };
        constraints.gridx = 0;
        constraints.gridy = 1;

        eFrame.add(scrollPane,constraints);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        //Create Bottom
        southPane = new JPanel();

        JButton eClearAll = new JButton("Clear All");
        ActionListener lstnClearAll = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                int size = renzymes.size();
                for (int i = 0; i < size; ++i){
                    labels[i].setBackground(eFrame.getBackground());
                }
            }
        };
        eClearAll.addActionListener(lstnClearAll);
        southPane.add(eClearAll);

        JButton ebtnHighlight = new JButton("Highlight");
        ActionListener actnLsntrHighlight = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	int N = renzymes.size() - 1;
            	ArrayList<SimpleExtract> res = new ArrayList<SimpleExtract>();
            	for (int i = 0; i < N; ++i){
            		if (labels[i].getBackground() == Color.RED){
            			res.addAll(renzymes.get(i).getPosPlaces());
            			parentFrame.highlightEnzymes(res);
            		}
            		}
            	}
        };
        ebtnHighlight.addActionListener(actnLsntrHighlight);
        southPane.add(ebtnHighlight);
        JScrollBar scrollBar = new JScrollBar(0);
        scrollPane.setVerticalScrollBar(scrollBar);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.anchor = GridBagConstraints.PAGE_END;
        eFrame.add(southPane, gridBagConstraints);

        eFrame.repaint();
    }
}

    public EnzymeSelector(IntParentFrame par) {
    	parentFrame = par;
    	
        eFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        gridBagLayout.preferredLayoutSize(eFrame);
        eFrame.setLayout(gridBagLayout);

        EnzymeSelector.createNorth();
        EnzymeSelector.createTable();
        EnzymeSelector.createMenu();
        eFrame.pack();
        eFrame.setMinimumSize(new Dimension(eFrame.getWidth(), eFrame.getHeight()));
        eFrame.setVisible(true);
    }

    public void show(){
    	eFrame.setVisible(true);
    }
}
