package implementation;

import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import button.ButtonOk;
import watcher.Watcher;

public class GameImplementationModel extends Watcher {
	
	private GameImplementation gi;
	public boolean step = false;
	private PrintStream server;
	private String itsTurn;
	private int signIn;
	private String nicknameOpponent;
	private int counter = 0;

	public GameImplementationModel(GameImplementation gi, PrintStream server) {
		
		super();
		this.gi = gi;
		this.server = server;
	}
	
	public void addNicknameOpponent(String name) {
		nicknameOpponent = name;
	}

	public void addSignIn(int x) {
		signIn = x;
	}
	
	public void addStep(int i, int j) {
		
		itsTurn = "";
		itsTurn += Integer.valueOf(i);
		itsTurn += Integer.valueOf(j);
		spelling(itsTurn);
	}

	@SuppressWarnings("static-access")
	public void spelling(String msg) {
		
		if (signIn == gi.CROSSWISE && !isCrossTurn()) {
			server.println(nicknameOpponent);
			server.println(msg);
			
			if (whoDidWin() == gi.EMPTY) {
				server.println(nicknameOpponent);
				server.println("disable");
			}

		} else if (signIn == gi.ZERO && isCrossTurn()) {
			server.println(nicknameOpponent);
			server.println(msg);
			
			if (whoDidWin() == gi.EMPTY) {
				server.println(nicknameOpponent);
				server.println("disable");
			}

		}

	}

	public int getBoard(int i, int j) {
		return gi.getBoard(i, j);
	}

	public boolean isCrossTurn() {
		return gi.isCross();
	}
	
	@SuppressWarnings("static-access")
	public void checkWinner() {
		
				if (whoDidWin() != gi.EMPTY) {
					if (signIn != gi.CROSSWISE && whoDidWin() == gi.CROSSWISE) {
						server.println(nicknameOpponent);
						server.println("win");
						server.println(nicknameOpponent);
						server.println("finish");
						defeat();

					} else {
						server.println(nicknameOpponent);
						server.println("lost");
						server.println(nicknameOpponent);
						server.println("finish");
						victorious();
					}
					counter = 0;
					try {
						TimeUnit.SECONDS.sleep(3);

					} catch (InterruptedException e) {
						
					}
					newGame();

				} else if (counter == 9) {
					server.println(nicknameOpponent);
					server.println("draw");
					server.println(nicknameOpponent);
					server.println("finish");
					draw();
					counter = 0;
					try {
						TimeUnit.SECONDS.sleep(3);

					} catch (InterruptedException e) {
						
					}
					newGame();

				}
	}
	
	public void newGame() {
		
		gi.toStartPlaying();
		setVary();
		informWathcers();
	}

	public synchronized void turn(int i, int j) {

		counter++;
		gi.change(i, j);
		setVary();
		informWathcers();
		addStep(i, j);
		checkWinner();
	}
	
	public int whoDidWin() {
		return gi.whoDidWin();
	}

	private void victorious() {
		
		JFrame frame = new JFrame("Tic Tac Toe");
		ButtonOk ok = new ButtonOk(("Hooray! You won!"), frame);

		frame.setSize(400, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.add(ok);

		frame.setVisible(true);
	}

	private void defeat() {
		
		JFrame frame = new JFrame("Tic Tac Toe");
		ButtonOk ok = new ButtonOk(("You lost. You will definitely win soon!"), frame);

		frame.setSize(400, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.add(ok);

		frame.setVisible(true);
	}

	private void draw() {
		
		JFrame frame = new JFrame("Game");
		ButtonOk ok = new ButtonOk(("This game ended in a draw!"), frame);

		frame.setSize(400, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.add(ok);

		frame.setVisible(true);
	}

}
