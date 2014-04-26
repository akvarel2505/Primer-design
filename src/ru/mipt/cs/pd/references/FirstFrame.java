package ru.mipt.cs.pd.references;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class FirstFrame {
    
public static void createGUI() {
//        JFrame.setDefaultLookAndFeelDecorated(true);
          JFrame frame = new JFrame("Информация об аминокислотах");
          frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
          
          JLabel label1 = new JLabel("        Здесь будет информация об аминокислотах");
          frame.getContentPane().add(label1);
          
          frame.setPreferredSize(new Dimension(333, 222));
          
          frame.pack();
          frame.setVisible(true);          
     }
     
     public FirstFrame() {
          javax.swing.SwingUtilities.invokeLater(new Runnable() {
               public void run() {
                    createGUI();
               }
          });
     }
}
