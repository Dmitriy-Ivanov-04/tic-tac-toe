package form;

import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import button.ButtonOk;

public class ButtonsForm extends JPanel {

	private static final long serialVersionUID = 4703716325510332380L;
	private TableForm tableForm;
	private PrintStream serverPrint;
	
	public ButtonsForm(TableForm tableForm, PrintStream serverPrint) {
		
		super();
		
		this.serverPrint = serverPrint;
		this.tableForm = tableForm;

		JButton refresh = new JButton("Update");
		refresh.addActionListener(e -> printUpdate());

		JButton exit = new JButton("Quit");
		exit.addActionListener(e -> printQuit());

		JButton request = new JButton("To invite");
		request.addActionListener(e -> printInvite());

		add(request);
		add(refresh);
		add(exit);

	}
	
	private void isNotAvailable(String nick) {
		
		JFrame frame = new JFrame("Connection interrupted");
		ButtonOk ok = new ButtonOk(nick + " is already playing", frame);

		frame.setSize(400, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.add(ok);
		frame.setVisible(true);

	}
	
	private void nothingSelected() {
		
		JFrame frame = new JFrame("Error");
		ButtonOk ok = new ButtonOk("You have not selected a player. Try it again.", frame);

		frame.setSize(400, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.add(ok);
		frame.setVisible(true);
	}
	
	private void printUpdate() {
		
		serverPrint.println("info");
		serverPrint.println("info");
	}

	private void printInvite() {
		
		if (tableForm.isSelected()) {
			String nick = tableForm.getSelectedNickname();
			
			if (tableForm.getAvailability().equals("true")) {
				serverPrint.println(nick);
				serverPrint.println("Play");
			} else {
				isNotAvailable(nick);
			}
		}
		else nothingSelected();
	}

	private void printQuit() {
		
		serverPrint.println("Game");
		serverPrint.println("End");
	}
	
}
