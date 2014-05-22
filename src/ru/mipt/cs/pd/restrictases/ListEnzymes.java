package ru.mipt.cs.pd.restrictases;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by futame on 08.05.14.
 */
public class ListEnzymes extends RenzymeParentWithRenzymeMass {
    JFrame frmListOfEnzymes;

    public ListEnzymes(){
        frmListOfEnzymes = new JFrame("Restriction enzymes list");
        ArrayList<Renzyme> renzymes = renzymeArrayList.getMainRenzymeStructure();

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridy = -1;
        constraints.gridx = 0;
        JLabel lblName = new JLabel("Name");
        mainPanel.add(lblName,constraints);

        constraints.gridx = 1;
        JLabel lblPlace = new JLabel("Place");
        mainPanel.add(lblPlace, constraints);

        for (int i = 0; i < renzymes.size(); ++i){
            constraints.gridy = i;
            constraints.gridx = 0;
            Label localLblName = new Label(renzymes.get(i).getName() + " ");
            mainPanel.add(localLblName,constraints);
            constraints.gridx = 1;
            Label localLblPlace = new Label(renzymes.get(i).getPlace());
            mainPanel.add(localLblPlace,constraints);
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);

        frmListOfEnzymes.setMinimumSize(new Dimension(300,min(500,renzymes.size()*40)));
        frmListOfEnzymes.add(scrollPane);
        frmListOfEnzymes.setVisible(true);
    }

    private int min(int a, int b){
        if (a < b) return a;
        return b;
    }
}
