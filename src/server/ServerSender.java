package server;

import java.io.PrintStream;
import java.util.List;

import client.ClientData;
import report.Report;
import report.ReportTurn;

public class ServerSender extends Thread {
	
	private ReportTurn queue;
	private PrintStream clientPrint;
	private ClientData data;
	private boolean check = true;
	private final String victory = "victory";
	private final String defeat = "defeat";
	private final String draw = "draw";


	public ServerSender(ReportTurn queue, PrintStream clientPrint, ClientData data) {
		
		this.queue = queue;
		this.clientPrint = clientPrint;
		this.data = data;
	}

	public void run() {
		
		while (check) {
			Report msg = queue.accept();
			String mes = msg.getReport();
			
			if (mes.equals("i")) {
				clientPrint.println("information");
				String temp = Integer.toString(data.getNumberNick());
				clientPrint.println(temp);
				printInformation();

			} else if (mes.equals("Quit")) {
				check = false;
				clientPrint.println(mes);
				
			} else if (mes.equals(victory) || mes.equals(defeat) || mes.equals(draw)) {
				Report name = queue.accept();
				if (mes.equals(victory))
					data.addVictory(name.toString());
				else if (mes.equals(defeat))
					data.addDefeat((name.toString()));
				else if (mes.equals(draw))
					data.addDraw((name.toString()));
			
			} else{
				clientPrint.println(msg);
			}
		}
		clientPrint.close();
	}

	private void printNicknames() {

		List<String> names = data.getNicknames();

		for (int i = 0; i < names.size(); i++) {
			String temp = names.get(i);
			clientPrint.println(temp);
		}
	}

	private void printVictory() {
		
		List<Integer> win = data.getVictory();
		
		for (int i = 0; i < win.size(); i++) {
			clientPrint.println(Integer.toString(win.get(i)));
		}
	}

	private void printDefeat() {
		
		List<Integer> lost = data.getDefeat();
		
		for (int i = 0; i < lost.size(); i++) {
			clientPrint.println(Integer.toString(lost.get(i)));
		}
	}

	private void printDraw() {
		
		List<Integer> tie = data.getDraw();
		
		for (int i = 0; i < tie.size(); i++) {
			clientPrint.println(Integer.toString(tie.get(i)));
		}
	}

	private void printAvailable() {
		
		List<Boolean> available = data.getAvailable();
		
		for (int i = 0; i < available.size(); i++) {
			clientPrint.println(Boolean.toString(available.get(i)));
		}
	}
	

	private void printInformation() {

		printNicknames();
		printVictory();
		printDefeat();
		printDraw();
		printAvailable();
	}


}