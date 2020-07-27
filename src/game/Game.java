package game;

import javax.swing.JFrame;

import implementation.GameImplementation;
import implementation.GameImplementationCompound;
import implementation.GameImplementationModel;

public class Game {

	public GameImplementation board;
	private JFrame frame;
	private String nickOpponent;
	private boolean isInGame = false;
	private GameImplementationModel gIModel;
	
	public Game(GameImplementation board, GameImplementationModel model) {
		
		this.board = board;
		this.gIModel = model;

	}

	public void addOpponent(String nick) {
		nickOpponent = nick;
		gIModel.addNicknameOpponent(nickOpponent);
	}
	
	public String getOpponent() {
		return nickOpponent;
	}
	
	public void setFrameInvisible() {
		frame.dispose();
	}

	public void setEnable(boolean dis) {
		frame.setEnabled(dis);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void isInGame(boolean check) {
		isInGame = check;
	}

	@SuppressWarnings("static-access")
	public void isCross(String s) {
		
		if (s.equals("X")) {
			gIModel.addSignIn(board.CROSSWISE);
		} else if (s.equals("O")) {
			gIModel.addSignIn(board.EMPTY);
		}
		
	}

	public boolean isPlaying() {
		return isInGame;
	}
	
	public void gettingStarted(String s) {

		frame = new JFrame(s);
		GameImplementationCompound compound = new GameImplementationCompound(board, gIModel);
		frame.setSize(450, 450);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(compound);
		frame.setVisible(true);

	}

	public void step(int step) {

		int i = step / 10;
		int j = step % 10;
		gIModel.turn(i, j);

	}

}