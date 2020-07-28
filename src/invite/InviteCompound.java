package invite;

import java.awt.BorderLayout;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gui.GameGUI;

public class InviteCompound extends JPanel {
	
	private static final long serialVersionUID = -1495579262782262907L;

	public InviteCompound(PrintStream server, String nick, JFrame frame) {
		
		super();

		GameGUI game = new GameGUI(("User " + nick + " invites you to play with him!"));
		Invite invite = new Invite(nick, server, frame);
		setLayout(new BorderLayout());

		add(game, BorderLayout.CENTER);
		add(invite, BorderLayout.SOUTH);
	}
}
