package ru.mipt.cs.pd.references;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.*;

@SuppressWarnings("serial")
public class AmAcidPane extends JPanel {

	public void addAmAcid1(final JPanel pane, final InfoPane infoPane,
			String name, String path, final String pathF) {
		JLabel label1 = new JLabel(name);
		label1.setSize(100, 20);
		label1.setLocation(100, 20);
		JLabel imageLabel1 = new JLabel(new ImageIcon(path));
		imageLabel1.setSize(160, 160);
		imageLabel1.setLocation(20, 50);
		pane.add(imageLabel1);
		pane.add(label1);

		class MyMouseListener implements MouseListener {
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
				try {
					infoPane.fillInfo(pathF, infoPane);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		}
		imageLabel1.addMouseListener(new MyMouseListener());
	}

	public void addAmAcid2(final JPanel pane, final InfoPane infoPane,
			String name, String path, final String pathF) {
		JLabel label2 = new JLabel(name);
		label2.setSize(100, 20);
		label2.setLocation(280, 20);
		JLabel imageLabel2 = new JLabel(new ImageIcon(path));
		imageLabel2.setSize(160, 160);
		imageLabel2.setLocation(220, 50);
		pane.add(imageLabel2);
		pane.add(label2);
		class MyMouseListener implements MouseListener {
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
				try {
					infoPane.fillInfo(pathF, infoPane);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		}
		imageLabel2.addMouseListener(new MyMouseListener());
	
	}

	public void addAmAcid3(final JPanel pane, final InfoPane infoPane,
			String name, String path, final String pathF) {
		JLabel label3 = new JLabel(name);
		label3.setSize(100, 20);
		label3.setLocation(510, 20);
		JLabel imageLabel3 = new JLabel(new ImageIcon(path));
		imageLabel3.setSize(160, 160);
		imageLabel3.setLocation(420, 50);
		pane.add(imageLabel3);
		pane.add(label3);
		class MyMouseListener implements MouseListener {
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
				try {
					infoPane.fillInfo(pathF, infoPane);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		}
		imageLabel3.addMouseListener(new MyMouseListener());}

	public void addAmAcid4(final JPanel pane, final InfoPane infoPane,
			String name, String path, final String pathF) {
		JLabel label4 = new JLabel(name);
		label4.setSize(100, 20);
		label4.setLocation(100, 230);
		JLabel imageLabel4 = new JLabel(new ImageIcon(path));
		imageLabel4.setSize(160, 160);
		imageLabel4.setLocation(20, 255);
		pane.add(imageLabel4);
		pane.add(label4);
		class MyMouseListener implements MouseListener {
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
				try {
					infoPane.fillInfo(pathF, infoPane);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		}
		imageLabel4.addMouseListener(new MyMouseListener());
	}

	public void addAmAcid5(final JPanel pane, final InfoPane infoPane,
			String name, String path, final String pathF) {
		JLabel label5 = new JLabel(name);
		label5.setSize(100, 20);
		label5.setLocation(280, 230);
		JLabel imageLabel5 = new JLabel(new ImageIcon(path));
		imageLabel5.setSize(160, 160);
		imageLabel5.setLocation(220, 255);
		pane.add(imageLabel5);
		pane.add(label5);
		class MyMouseListener implements MouseListener {
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
				try {
					infoPane.fillInfo(pathF, infoPane);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		}
		imageLabel5.addMouseListener(new MyMouseListener());
	}

	public void addAmAcid6(final JPanel pane, final InfoPane infoPane,
			String name, String path, final String pathF) {
		JLabel label6 = new JLabel(name);
		label6.setSize(100, 20);
		label6.setLocation(480, 230);
		JLabel imageLabel6 = new JLabel(new ImageIcon(path));
		imageLabel6.setSize(160, 160);
		imageLabel6.setLocation(420, 255);
		pane.add(imageLabel6);
		pane.add(label6);
		class MyMouseListener implements MouseListener {
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
				try {
					infoPane.fillInfo(pathF, infoPane);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		}
		imageLabel6.addMouseListener(new MyMouseListener());
	}

}
