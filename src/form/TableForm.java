package form;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class TableForm extends JPanel {

	private static final long serialVersionUID = 8291986195121118240L;
	private JTable list;
	private Object[] columnNicknames = {"Nickname" , "Victories", "Defeat", "Drawn", "Availability"};
	private Object[][] clientInfo;
	

	public TableForm(Object[][] info) {
		
		super();
		this.clientInfo = info;
		
		DefaultTableModel model = new DefaultTableModel(clientInfo, columnNicknames);
		setLayout(new GridLayout(1,0));
		list = new JTable(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(new JScrollPane(list));
	}
	
	public String getAvailability() {
		
		int line = list.getSelectedRow();
		String verification = (String)list.getValueAt(line, 4);
		
		return verification;
	}
	
	public String getSelectedNickname() {
		
		int line = list.getSelectedRow();
		String nick = (String)list.getValueAt(line, 0);
		
		return nick;
	}
	
	public boolean isSelected() {
		
		int line = list.getSelectedRow();
		
		return (line != -1);
	}
	
}