    import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
    import javax.swing.table.AbstractTableModel;
    import javax.swing.table.TableColumn;
    import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
    import java.io.FileInputStream;
    import java.io.FileNotFoundException;
import java.io.FileReader;
    import java.util.ArrayList;
    import java.util.Scanner;

/**
 * Created by futame on 06.03.14.
 */


/* Мне нужно добавить названия
 */

public class EnzymeSelector extends RenzymeParentWithRenzymeMass{
    static JFrame eFrame = new JFrame("Select enzyme");

    static JLabel elblTest = new JLabel("abacaba");
    private static final int columnNum = 10;

    private static void createMenu(){
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
                }
            }
        };

        JMenuItem eOpen = new JMenuItem("Open new enzymes file");
        eOpen.addActionListener(openFile);
        eFile.add(eOpen);

        JMenuItem eSaveF = new JMenuItem("Save enzymes file");
        eFile.add(eSaveF);
        JSeparator eF1 = new JSeparator();
        eFile.add(eF1);
        JMenuItem eSaveDefault = new JMenuItem("Save current enzymes as Default");
        eFile.add(eSaveDefault);
        JSeparator eF2 = new JSeparator();
        eFile.add(eF2);
        JMenuItem eImport = new JMenuItem("Import enzymes from DNA Strider");
        eFile.add(eImport);
        JSeparator eF3 = new JSeparator();
        eFile.add(eF3);

        ActionListener lstnExitBtn = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                EnzymeSelector.eFrame.dispose();
            }
        };
        JMenuItem eClose = new JMenuItem("Close selection dialog   Ctrl+W");
        eClose.addActionListener(lstnExitBtn);
        eFile.add(eClose);

        JMenu eEnzymes = new JMenu("Enzymes");
        eMenu.add(eEnzymes);

        ActionListener lstnNewEnzyme = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
             //   NewEnzyme addEnzyme = new NewEnzyme();
            }
        };
        JMenuItem eNew = new JMenuItem("New enzyme...");
        eNew.addActionListener(lstnNewEnzyme);
        eEnzymes.add(eNew);

        JMenuItem eDeleteEnz = new JMenuItem("Delete selected enzymes");
        eEnzymes.add(eDeleteEnz);
        JSeparator eE1 = new JSeparator();
        eEnzymes.add(eE1);
        JMenuItem eNewGroup = new JMenuItem("New group...");
        eEnzymes.add(eNewGroup);
        JMenuItem eDeleteGroups = new JMenuItem("Delete groups...");
        eEnzymes.add(eDeleteGroups);
        JMenuItem eReadGroups = new JMenuItem("Read groups file...");
        eEnzymes.add(eReadGroups);

        JMenu eList = new JMenu("List");
        eMenu.add(eList);
        JMenuItem eShowEnz = new JMenuItem("List enzymes");
        eList.add(eShowEnz);
        JMenuItem eShowEnzAndSites = new JMenuItem("List enzymes + #sites");
        eList.add(eShowEnzAndSites);
        JMenuItem eShowEnzAndPos = new JMenuItem("List enzymes + site positions");
        eList.add(eShowEnzAndPos);
        JMenuItem eShowEnzAndSeq = new JMenuItem("List enzymes + recognition sequences");
        eList.add(eShowEnzAndSeq);

        eFrame.add(eMenu, BorderLayout.BEFORE_FIRST_LINE);
    }
    private  static void createNorth(){
        JPanel northPane = new JPanel();

        JLabel elblWindow = new JLabel("Window  ");
        northPane.add(elblWindow);
        //here! I have to do it dynamically
        JComboBox ecbFile = new JComboBox();
        ecbFile.setPreferredSize(new Dimension(350,20));
        northPane.add(ecbFile);

        //I don't know how to name it, so let's it will be ecbA
        JCheckBox ecbA = new JCheckBox();
        northPane.add(ecbA);

        JLabel elblSelection = new JLabel(" Selection 1 - 0 ");
        northPane.add(elblSelection);

        JCheckBox ecbB = new JCheckBox();
        northPane.add(ecbB);

        JLabel elblD = new JLabel("Dam/Dcm");
        northPane.add(elblD);

        eFrame.add(northPane, BorderLayout.LINE_START);
    } //I have to add strings in ComboBox
    private static void createTable(){
        ArrayList<Renzyme> renzymes = renzymeArrayList.getMainRenzymeStructure();
        JTable etblEnzyme = new JTable();
        int n = 0;
        try {
            n = renzymes.size();
        }
        catch (NullPointerException e){
            //Error!
        }
        int lengthOfRows = (n / columnNum) + 1;
        for (int i = 0; i < columnNum; ++i){
            TableColumn column = new TableColumn();
            etblEnzyme.addColumn(column);
        }
        eFrame.add(etblEnzyme,BorderLayout.PAGE_START);
    }
    private static void createBottom(){
        JPanel bottomPane = new JPanel(new BorderLayout());
        JPanel southPane = new JPanel();

        JLabel eSelectEnzymes = new JLabel("Select Enzymes:");
        southPane.add(eSelectEnzymes);

/*        JToolBar eNum = new JToolBar();
        eNum.setLayout(new BoxLayout(eNum, BoxLayout.Y_AXIS));
        southPane.add(eNum);
        JButton eUnique = new JButton("unique (1)");
        eNum.add(eUnique);
        JButton eAny = new JButton("any");
        eNum.add(eAny);

*/
        //I have to rename it, because I don't know what it does
        JComboBox ecbAll = new JComboBox();
        ecbAll.setPreferredSize(new Dimension(80,20));
        southPane.add(ecbAll);

        JButton eSelect = new JButton("Select");
        southPane.add(eSelect);

        JButton eDeselect = new JButton("de-Select");
        southPane.add(eDeselect);

        JButton eAND = new JButton("AND");
        southPane.add(eAND);

        JButton eClearAll = new JButton("Clear All");
        southPane.add(eClearAll);

        //I don't know, what this button have to do, I just copied it from ApE
        JButton eSelToMem = new JButton("Sel to Mem");
        southPane.add(eSelToMem);

        JPanel bottomPane2 = new JPanel(new BorderLayout());

        JPanel lowerPane= new JPanel();
        JLabel elblPerformAction = new JLabel("Perform action:");
        lowerPane.add(elblPerformAction);
        JButton ebtnGraphicMap = new JButton("Graphic map");
        lowerPane.add(ebtnGraphicMap);
        JButton ebtnGraphicMapU = new JButton("Graphic map + U");
        lowerPane.add(ebtnGraphicMapU);
        JButton ebtnDigest = new JButton("Digest");
        lowerPane.add(ebtnDigest);
        JButton ebtnHighlight = new JButton("Highlight");
        lowerPane.add(ebtnHighlight);
        JButton ebtnText = new JButton("Text");
        lowerPane.add(ebtnText);


        //eFrame.add(southPane,BorderLayout.SOUTH);

        JPanel lowestPane = new JPanel();
        JCheckBox ecbOpen = new JCheckBox();
        lowestPane.add(ecbOpen);
        JLabel elblOpen = new JLabel("Keep selector dialog open");
        lowestPane.add(elblOpen);
        //eFrame.add(lowestPane, BorderLayout.LINE_END)
        bottomPane2.add(lowerPane,BorderLayout.NORTH);
        bottomPane2.add(lowestPane,BorderLayout.EAST);
        bottomPane.add(southPane,BorderLayout.NORTH);
        bottomPane.add(bottomPane2,BorderLayout.SOUTH);
        eFrame.add(bottomPane,BorderLayout.SOUTH);
    }


    EnzymeSelector(){
        eFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        EnzymeSelector.createNorth();
        EnzymeSelector.createTable();
        EnzymeSelector.createBottom();
        EnzymeSelector.createMenu();

//        eFrame.pack();
        eFrame.setSize(700, 450);
        eFrame.setVisible(true);
    }

}
