package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JFrame;

import button.ButtonOk;
import form.CompoundsForm;
import game.Game;
import invite.InviteCompound;

public class ClientReceiver extends Thread {

	private BufferedReader serverReader;
	private Game game;
	private boolean quit = true;
	private boolean first = true;
	private boolean ifBegin = true;
	private PrintStream toServerPrint;
	private JFrame frameSecond;
	private final String nickname;
	private final String gettingStartedFirst = "begin";
	private final String gettingStartedSecond = "start";
	private final String playerExists = "A player with the same name already exists. Please choose another name.";
	private final String finish = "Finish";
	private final String exist = "Exist";
	private final String no = "No";
	private final String play = "Play";
	private final String information = "information";
	private final String idemPlayer = "SamePlayer";
	private final String isInvited = "isInvited";
	private final String isPlaying = "isPlaying";
	
	ClientReceiver(BufferedReader serverReader, Game game, PrintStream toServerPrint, String nickname) {
		this.serverReader = serverReader;
		this.game = game;
		this.toServerPrint = toServerPrint;
		this.nickname = nickname;
	}

	public void runGame() {

		try {
			while (quit) {
				String s = serverReader.readLine();
				if (s == null || s.equals(finish)) {
					quit = false;
				} else if (s.equals(exist)) {
					System.out.println("\n");
					System.out.println(playerExists);
					System.out.println("\n");
					quit = false;
				} else {
					ifBegin = false;
					String s2 = serverReader.readLine();
					if (s2 != null && s != null) {

						if (s2.equals(gettingStartedFirst)) {
							first(s);
						} else if (s2.equals(gettingStartedSecond)) {
							second(s);
						} else if (s2.equals(no)) {
							rejected("Player");
						} else if (s2.equals(play)) {
							request(s);
						} else if (s.equals(information)) {
							write(s2);
						} else if (s2.equals(idemPlayer)) {
							askedYourself();
						} else if (s2.equals(isInvited)) {
							playerIsInvited();
						} else if(s2.equals(isPlaying))
						{	
							playerIsPlaying();
						}
					}
				}

			}
			if (!ifBegin)
				frameSecond.dispose();
		} catch (IOException e) {
			System.exit(1);
		}
	}

	private void playing() {
		
		try {
			boolean notEnd = true;
			frameSecond.setEnabled(false);
			
			while (notEnd) {
				String s2 = serverReader.readLine();
				if (s2 != null) {
					game.setEnable(true);
					if (verification(s2)) {
						game.step(Integer.valueOf(s2));
					} else if (s2.equals("end")) {
						frameSecond.setEnabled(true);
						notEnd = false;
						game.setFrameInvisible();
					} else if (s2.equals("disable")) {
						game.setEnable(false);
					}
				} else {
					serverReader.close();
					throw new IOException("Got null from server");
				}
			}
		} catch (IOException e) {
			System.exit(1);
		}
	}
	
	private boolean verification(String s) {

		try {
			Integer.valueOf(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}


	private void write(String s) {
		
		int number = Integer.parseInt(s);
		Object[][] data = new Object[number][5];

		try {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < number; j++) {
					data[j][i] = serverReader.readLine();
				}
			}
			lobby(data);
		} catch (IOException e) {
			System.exit(1);
		}
	}
	
	private void playerIsPlaying() {
		
		JFrame frame = new JFrame("Game deactivation");
		ButtonOk ok = new ButtonOk(
				("Sorry! Player is already in the game. Update your board to see availability"), frame);

		frame.setSize(650, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(ok);
		frame.setVisible(true);
	}

	private void playerIsInvited() {
		
		JFrame frame = new JFrame("Game deactivation");
		ButtonOk ok = new ButtonOk(
				("Sorry! Player is already invited to play, \n or invited to play another player."), frame);

		frame.setSize(650, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(ok);
		frame.setVisible(true);
	}

	private void request(String nickname) {
		
		JFrame frame = new JFrame("Game request");
		InviteCompound comp = new InviteCompound(toServerPrint, nickname, frame);

		frame.setSize(400, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(comp);
		frame.setVisible(true);
	}

	private void rejected(String nickname) {
		
		JFrame frame = new JFrame("Game deactivation");
		ButtonOk ok = new ButtonOk((nickname + " rejected to play against you"), frame);

		frame.setSize(400, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(ok);
		frame.setVisible(true);
	}

	private void lobby(Object[][] info) {
		
		if (!first)
			frameSecond.dispose();
		frameSecond = new JFrame("[" + nickname + "]" );
		frameSecond.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		CompoundsForm comp = new CompoundsForm(toServerPrint, info);
		frameSecond.setSize(400, 200);
		frameSecond.add(comp);
		frameSecond.setVisible(true);
		first = false;
	}

	private void askedYourself() {
		
		JFrame frame = new JFrame("Game deactivation");
		ButtonOk ok = new ButtonOk(("You cannot request yourself"), frame);

		frame.setSize(400, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(ok);
		frame.setVisible(true);
	}

	private void first(String nick) {
		
		game.gettingStarted("Begin ("+nickname+ ") X");
		game.isCross("X");
		game.addOpponent(nick);
		playing();
	}

	private void second(String nick) {
		
		game.gettingStarted("Second ("+nickname+ ") O");
		game.isCross("O");
		game.addOpponent(nick);
		game.setEnable(false);
		playing();
	}
}