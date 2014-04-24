package restrictionEnzymes;
import javax.swing.*;

/**
 * Created by futame on 29.03.14.
 */
public class Error {
    private JPanel panel1;
    private JTextPane errorTextPane;
    private JButton okButton;

    public Error(String s){
        errorTextPane.setText(s);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
