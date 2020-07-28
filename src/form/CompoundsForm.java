package form;

import java.awt.BorderLayout;
import java.io.PrintStream;

import javax.swing.JPanel;

public class CompoundsForm extends JPanel {

	private static final long serialVersionUID = 3254115439242540161L;
	private PrintStream printServer;
	private TableForm tableForm;
	
	public CompoundsForm(PrintStream server, Object[][] info) {
		
		super();
		
		this.printServer = server;
		
		tableForm = new TableForm(info);
		ButtonsForm buttonsForm = new ButtonsForm(tableForm, printServer);
		setLayout(new BorderLayout());
		
		add(tableForm, BorderLayout.CENTER);
		add(buttonsForm, BorderLayout.SOUTH);
	}
	
}