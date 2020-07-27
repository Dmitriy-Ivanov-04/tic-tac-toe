package button;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ButtonOk extends JPanel {
	
	private static final long serialVersionUID = 5413846271781170024L;
	JFrame frame;

	public ButtonOk( String name, JFrame frame) {
		
		super();
		
		GameGUI label = new GameGUI(name);
		this.frame = frame;
		
		JButton okay = new JButton("Ok");
		okay.addActionListener(e -> clickButtonOk());
		
		add(okay);		
		setLayout(new BorderLayout());
		add(label, BorderLayout.CENTER);
		add(okay, BorderLayout.SOUTH);
	}
	
	private void clickButtonOk() {
		frame.dispose();
	}
}
