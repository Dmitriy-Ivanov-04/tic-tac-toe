package form;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import implementation.GameImplementation;
import implementation.GameImplementationModel;
import watcher.LookOn;
import watcher.Watcher;

public class BoardForm extends JPanel implements LookOn {

	private static final long serialVersionUID = -1257214174526820938L;
	private GameImplementationModel model;
	public JButton[][] cell;

	public BoardForm(GameImplementationModel model) {
		
		super();

		this.model = model;
		
		cell = new JButton[3][3];

		setLayout(new GridLayout(3, 3));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				cell[i][j] = new JButton(" ");
				final int x = i;
				final int y = j;
				cell[i][j].addActionListener(e -> model.turn(x, y));
				add(cell[i][j]);
			}
		}
	}

	@Override
	public void refresh(Watcher watcher, Object obj) {
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (model.getBoard(i, j) == GameImplementation.CROSSWISE) {
					cell[i][j].setText("X");
					cell[i][j].setEnabled(false);
				}
				else if (model.getBoard(i, j) == GameImplementation.EMPTY) {
					cell[i][j].setText("O");
					cell[i][j].setEnabled(false);
				}
				else {
					cell[i][j].setText(" ");
					boolean notOver = (model.whoDidWin() == GameImplementation.ZERO);
					cell[i][j].setEnabled(notOver);
				}
			}
		}
		repaint();
	}

}
