package implementation;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import form.BoardForm;

public class GameImplementationCompound extends JPanel {
	
	private static final long serialVersionUID = -4292001101477202865L;

	public GameImplementationCompound(GameImplementation game, GameImplementationModel model) {
		
		super();
		
		BoardForm boardView = new BoardForm(model);

		model.addWatcher(boardView);
		
		setLayout(new BorderLayout());
		
		add(boardView, BorderLayout.CENTER);

	}

}
