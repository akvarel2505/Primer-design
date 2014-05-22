package ru.mipt.cs.pd.references;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JTextPane;

@SuppressWarnings("serial")
public class InfoPane extends JPanel {

	public void fillInfo(String path, InfoPane pane)
			throws IOException {

		File f = new File(path);
		FileReader in = new FileReader(f);
		char[] arr = new char[(int) f.length()];
		int read = in.read(arr);
		in.close();

		JTextPane t = new JTextPane();
		t.setSize(230, 350);
		t.setVisible(true);
		t.setLayout(new BorderLayout());
		t.setEditable(false);
		t.setText(String.valueOf(arr, 0, read));
		pane.add(t, BorderLayout.CENTER);
				
	}
}
