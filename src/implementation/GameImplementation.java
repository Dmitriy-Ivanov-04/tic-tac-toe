package implementation;

public class GameImplementation {
	
	public static final int EMPTY = 0;
	public static final int CROSSWISE = 1;
	public static final int ZERO = 2;

	private boolean cross;
	private int[][] board;

	public GameImplementation() {

		board = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = EMPTY;
			}
		}
		cross = true;
	}
	
	public void toStartPlaying() {
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = EMPTY;
			}
		}
		cross = true;
	}
	
	public void change(int i, int j) {

		if (board[i][j] == EMPTY) {
			if (cross) {
				board[i][j] = CROSSWISE;
			} else {
				board[i][j] = ZERO;
			}
			cross = !cross;
		}

	}

	public int getBoard(int i, int j) {
		return board[i][j];
	}

	public boolean isCross() {
		return cross;
	}

	private boolean winner(int player) {
		
		    return (board[0][0] == player && board[0][1] == player && board[0][2] == player)
				|| (board[1][0] == player && board[1][1] == player && board[1][2] == player)
				|| (board[2][0] == player && board[2][1] == player && board[2][2] == player)
				|| (board[0][0] == player && board[1][0] == player && board[2][0] == player)
				|| (board[0][1] == player && board[1][1] == player && board[2][1] == player)
				|| (board[0][2] == player && board[1][2] == player && board[2][2] == player)
				|| (board[0][0] == player && board[1][1] == player && board[2][2] == player)
				|| (board[0][2] == player && board[1][1] == player && board[2][0] == player);
	}

	public int whoDidWin() {
		
		if (winner(CROSSWISE)) {
			return CROSSWISE;
		} else if (winner(ZERO)) {
			return ZERO;
		} else {
			return EMPTY;
		}
	}

	

}
