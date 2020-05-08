package ResultParser;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.FlowLayout;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Dimension;

public class ResultFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel JL_Path = null;
	private JTextField JT_Path = null;
	private JButton JB_SEL = null;
	private JButton JB_Analysis = null;
	private String path = null;

	public ResultFrame() {
		super();
		initialize();
	}

	private void initialize() {
		this.setSize(300, 200);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			JL_Path = new JLabel();
			JL_Path.setText("Test Result Path:");
			jContentPane = new JPanel();
			jContentPane.setLayout(new FlowLayout());
			jContentPane.add(JL_Path, null);
			jContentPane.add(getJT_Path(), null);
			jContentPane.add(getJB_SEL(), null);
			jContentPane.add(getJB_Analysis(), null);
		}
		return jContentPane;
	}

	private JTextField getJT_Path() {
		if (JT_Path == null) {
			JT_Path = new JTextField(30);
		}
		return JT_Path;
	}

	private JButton getJB_SEL() {
		if (JB_SEL == null) {
			JB_SEL = new JButton();
			JB_SEL.setText("...");
			JB_SEL.setPreferredSize(new Dimension(43, 22));
			JB_SEL.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String dir = "";
					if (path == null) {
						dir = System.getProperty("user.dir");
					} else {
						dir = path;
					}
					JFileChooser chooser = new JFileChooser(dir);
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int returnVal = chooser.showOpenDialog(null);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						path = chooser.getSelectedFile().getAbsolutePath();
						JT_Path.setText(path);
					}
				}
			});
		}
		return JB_SEL;
	}

	private JButton getJB_Analysis() {
		if (JB_Analysis == null) {
			JB_Analysis = new JButton();
			JB_Analysis.setText("Analysis");
			JB_Analysis.setPreferredSize(new Dimension(82, 22));
			JB_Analysis.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					new Delivery(JT_Path.getText());
					new Overhead(JT_Path.getText());
					new Delay(JT_Path.getText());
					new Throughput(JT_Path.getText());
					new Accuracy(JT_Path.getText());
					new Random().getDeliveryRatio(JT_Path.getText());

					JOptionPane.showMessageDialog(null, "Analysis finish");
				}
			});
		}
		return JB_Analysis;
	}

	public static void main(String[] args) {
		new ResultFrame();
	}
}
