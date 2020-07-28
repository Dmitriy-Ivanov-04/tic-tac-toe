package gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GameGUI extends JPanel {

	private static final long serialVersionUID = -6600270165864656455L;

	public GameGUI(String info) {
		
		super();

		JLabel label = new JLabel(info, SwingConstants.CENTER);

		setLayout(new BorderLayout());
		add(label, BorderLayout.EAST);
		add(label);
	}

}