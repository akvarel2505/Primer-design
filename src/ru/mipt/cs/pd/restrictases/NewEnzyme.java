package ru.mipt.cs.pd.restrictases;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by futame on 20.05.14.
 */
public class NewEnzyme extends RenzymeParentWithRenzymeMass{

    JFrame frmNewEnzyme = new JFrame("Add new enzyme");

    public NewEnzyme(){
    	GridBagLayout layout = new GridBagLayout();
        JPanel mainPane = new JPanel(layout);
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;

        JLabel lblName = new JLabel("Name:");
        mainPane.add(lblName,constraints);

        constraints.gridx = 1;
        constraints.ipadx = 150;
        final JTextField txtFieldName = new JTextField();
        mainPane.add(txtFieldName,constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.ipadx = 0;
        JLabel lblPlace = new JLabel("Cleavage site:");
        mainPane.add(lblPlace,constraints);

        constraints.gridx = 1;
        constraints.ipadx = 150;
        final JTextField txtFieldPlace = new JTextField();
        mainPane.add(txtFieldPlace,constraints);


        final JLabel lblWarning = new JLabel("Please only 'g','c','t','a' in site field ");

        constraints.gridy = 2;
        constraints.gridx = 1;
        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                boolean b = true;
                String place = txtFieldPlace.getText();
                for (int i = 0; i < place.length(); ++i){
                    if ((place.charAt(i) != 'a')&&(place.charAt(i) != 'g')&&(place.charAt(i) != 'c')&&(place.charAt(i) != 't')){
                        b = false;

                        break;
                    }
                }
                if (b){
                    renzymeArrayList.addRenzyme(txtFieldName.getText(), place);
                    lblWarning.setForeground(Color.black);
                    EnzymeSelector.repaintTable();
                } else {
                    lblWarning.setForeground(Color.RED);
                }

            }
        });
        mainPane.add(btnAdd,constraints);

        constraints.gridy = 3;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        mainPane.add(lblWarning,constraints);

        frmNewEnzyme.setMinimumSize(new Dimension(500, 200));
        frmNewEnzyme.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frmNewEnzyme.add(mainPane);
        frmNewEnzyme.setVisible(true);
    }

}
