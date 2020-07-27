package implementation;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class GameImplementationCompound extends JPanel {
	
	private static final long serialVersionUID = -4292001101477202865L;

	public GameImplementationCompound(GameImplementation game, GameImplementationModel model) {
		
		super();
		
		BoardForm board = new BoardForm(model);

		model.addWatcher(board);
		
		setLayout(new BorderLayout());
		
		add(board, BorderLayout.CENTER);

	}

}
