package server;

import java.io.BufferedReader;
import java.io.IOException;

import client.ClientData;
import report.Report;
import report.ReportTurn;

public class ServerReceiver extends Thread {
	
	private String clientNickname;
	private BufferedReader clientReader;
	private boolean notEnd = true;
	private String receiver;
	private String text;
	private ClientData data;
	private final String victory = "victory";
	private final String defeat = "defeat";
	private final String draw = "draw";
	private final String yes = "Yes";
	private final String no = "No";
	private final String info = "info";
	private final String end = "End";
	private final String gettingStartedFirst = "begin";
	private final String gettingStartedSecond = "start";
	private final String delete = "delete";
	private final String disable = "disable";
	private final String play = "Play";
	private final String quit = "Quit";

	public ServerReceiver(String nick, BufferedReader client, ClientData data) {
		
		clientNickname = nick;
		clientReader = client;
		this.data = data;
	}

	public void run() {
		
		try {
			while (notEnd) {
				receiver = clientReader.readLine();
				if (receiver != null && !receiver.equals("Quit")) {
					text = clientReader.readLine();
					if (text != null) {
						if (check(text)) {
							gameStep();
						} else if (text.equals(yes)) {
							acceptedInvite();
						} else if (text.equals(no)) {
							rejectedInvite();
						} else if (text.equals(info)) {
							sendInformation();
						} else if (text.equals(end)) {
							finishClient();
						} else if (text.equals(victory) || text.equals(defeat) || text.equals(draw)) {
							addScore();
						} else if (text.equals(delete)) {
							data.delete(clientNickname);
						} else if (text.equals(disable)) {
							disableReport();
						} else if (text.equals(play)) {
							playInvite();
						} else {
							simpleReport();
						}
					}
				} else {
					Report msg = new Report(quit);
					ReportTurn queue2 = data.getQueue(clientNickname);
					queue2.offerQueue(msg);
					notEnd = false;
				}
			}
			clientReader.close();
			System.out.println(clientNickname + " disconnected");
			clientReader.close();
		} catch (IOException e) {
			System.err.println("The client has some problems " + clientNickname + " " + e.getMessage());
		}
	}
	
	private boolean check(String s) {

		try {
			Integer.valueOf(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;

	}

	private void gameStep() {

		Report msg = new Report(text);
		ReportTurn queue2 = data.getQueue(receiver);

		if (queue2 != null) {
			queue2.offerQueue(msg);
		} else {
			System.err.println("Report for a non-existent client " + receiver + ": " + text);
		}

	}

	private void acceptedInvite() {
		
		data.deleteInvited(clientNickname);
		data.deleteInvited(receiver);
		data.setAvailability(clientNickname, false);
		data.setAvailability(receiver, false);
		
		Report name1 = new Report(clientNickname);
		Report msg1 = new Report(gettingStartedSecond);
		Report name2 = new Report(receiver);
		Report msg2 = new Report(gettingStartedFirst);
		ReportTurn queue = data.getQueue(clientNickname);
		ReportTurn queue2 = data.getQueue(receiver);

		if (queue != null) {
			queue.offerQueue(name2);
			queue.offerQueue(msg1);
			queue2.offerQueue(name1);
			queue2.offerQueue(msg2);
		} else {
			System.err.println("Report for a non-existent client " + receiver + ": " + text);
		}

	}

	private void rejectedInvite() {
		
		data.deleteInvited(clientNickname);
		data.deleteInvited(receiver);
		
		Report name2 = new Report(receiver);
		Report msg2 = new Report(text);

		ReportTurn queue2 = data.getQueue(receiver);

		if (queue2 != null) {
			queue2.offerQueue(name2);
			queue2.offerQueue(msg2);
		} else {
			System.err.println("Report for a non-existent client " + receiver + ": " + text);
		}
	}

	private void sendInformation() {
		
		Report msg2 = new Report("i");
		ReportTurn queue = data.getQueue(clientNickname);

		if (queue != null) {
			queue.offerQueue(msg2);
		} else {
			System.err.println("Report for a non-existent client " + receiver + ": " + text);
		}

	}

	private void finishClient() {
		
		Report msg2 = new Report("End");
		ReportTurn queue = data.getQueue(clientNickname);

		if (queue != null) {
			queue.offerQueue(msg2);
		} else {
			System.err.println("Report for a non-existent client " + receiver + ": " + text);
		}

	}

	private void addScore() {
		
		Report msg2 = new Report(text);
		Report name = new Report(receiver);
		data.setAvailability(clientNickname, true);
		ReportTurn queue = data.getQueue(receiver);

		if (queue != null) {
			queue.offerQueue(msg2);
			queue.offerQueue(name);
		} else {
			System.err.println("Report for a non-existent client " + receiver + ": " + text);
		}

	}

	private void disableReport() {
		
		Report msg = new Report("disable");
		ReportTurn queue = data.getQueue(clientNickname);

		if (queue != null) {
			queue.offerQueue(msg);
		} else {
			System.err.println("Report for a non-existent client " + receiver + ": " + text);
		}

	}

	private void playInvite() {
		
		if (data.getAvailability(receiver)) {	
			if (!data.checkInvited(receiver)) {
				if (!clientNickname.equals(receiver)) {
					Report msg = new Report(text);
					Report name = new Report(clientNickname);
					ReportTurn queue = data.getQueue(receiver);
					data.addNewInvited(receiver);
					data.addNewInvited(clientNickname);
					if (queue != null) {
						queue.offerQueue(name);
						queue.offerQueue(msg);
					} else {
						System.err.println("Report for a non-existent client " + receiver + ": " + text);
					}
				} else {
					Report msg = new Report("SamePlayer");
					Report name = new Report(clientNickname);
					ReportTurn queue = data.getQueue(clientNickname);

					if (queue != null) {
						queue.offerQueue(name);
						queue.offerQueue(msg);
					} else {
						System.err.println("Report for a non-existent client " + receiver + ": " + text);
					}
				}
			} else {
				Report msg = new Report("isInvited");
				Report name = new Report(clientNickname);
				ReportTurn queue = data.getQueue(clientNickname);

				if (queue != null) {
					queue.offerQueue(name);
					queue.offerQueue(msg);
				} else {
					System.err.println("Report for a non-existent client " + receiver + ": " + text);
				}
			}
		} else {
			Report msg = new Report("isPlaying");
			Report name = new Report(clientNickname);
			ReportTurn queue = data.getQueue(clientNickname);

			if (queue != null) {
				queue.offerQueue(name);
				queue.offerQueue(msg);
			} else {
				System.err.println("Report for a non-existent client " + receiver + ": " + text);
			}
		}
	}

	private void simpleReport() {
		
		Report name = new Report(receiver);
		Report msg = new Report(text);
		ReportTurn queue = data.getQueue(receiver);

		if (queue != null) {
			queue.offerQueue(name);
			queue.offerQueue(msg);
		} else {
			System.err.println("Report for a non-existent client " + receiver + ": " + text);
		}
	}
}