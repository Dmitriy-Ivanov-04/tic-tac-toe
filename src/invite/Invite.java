package invite;

import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Invite  extends JPanel {

	private static final long serialVersionUID = 5669674006341235897L;
	JFrame frame;
	PrintStream printServer;
	String nick;
	
	public Invite(String nick, PrintStream printServer, JFrame frame) {
		
		super();
		
		this.frame = frame;
		this.nick = nick;
		this.printServer = printServer;
		
		JButton consent = new JButton("Yes");
		consent.addActionListener(e -> withdrawalOfConsent());
		
		JButton renouncement = new JButton("No");
		renouncement.addActionListener(e -> withdrawalOfRenouncement());
		
		add(consent);
		add(renouncement);
	}
	
	private void withdrawalOfConsent() {
		
		printServer.println(nick);
		printServer.println("Yes");
		frame.dispose();
	}
	
	private void withdrawalOfRenouncement() {
		
		printServer.println(nick);
		printServer.println("No");
		frame.dispose();
	}
	
}