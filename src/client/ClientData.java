package client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import report.ReportTurn;

public class ClientData {

	private List<String> nicknames = new ArrayList<String>();
	private List<Integer> victory = new ArrayList<Integer>();
	private List<Integer> defeat = new ArrayList<Integer>();
	private List<Integer> draw = new ArrayList<Integer>();
	private List<String> invitedPlayers = new ArrayList<String>();
	private List<Boolean> availability = new ArrayList<Boolean>();
	private Map<String, ReportTurn> queueTable = new TreeMap<String, ReportTurn>();

	public void addNewPlayer(String nickname) {

		this.nicknames.add(nickname);
		availability.add(true);
		victory.add(0);
		defeat.add(0);
		draw.add(0);
	}

	public void addNewInvited(String nickname) {
		invitedPlayers.add(nickname);
	}

	public void deleteInvited(String nickname) {
		invitedPlayers.remove(nickname);	
	}

	public boolean checkInvited(String nickname) {
		if (invitedPlayers.contains(nickname)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean existPlayer(String nickname) {
		
		if (nicknames.contains(nickname)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void addVictory(String nickname) {
		
		int index = nicknames.indexOf(nickname);
		int temp = victory.get(index);
		victory.set(index, temp + 1);
		System.out.println("victory");
	}

	public void addDefeat(String nickname) {
		
		int index = nicknames.indexOf(nickname);
		int temp = defeat.get(index);
		defeat.set(index, temp + 1);
		System.out.println("defeat");
	}

	public void addDraw(String nickname) {
		
		int index = nicknames.indexOf(nickname);
		int temp = draw.get(index);
		draw.set(index, temp + 1);
		System.out.println("draw");
	}
	
	public void delete(String nickname) {
		
		int index = nicknames.indexOf(nickname);
		nicknames.remove(index);
		victory.remove(index);
		defeat.remove(index);
		draw.remove(index);
		availability.remove(index);
	}

	public int getNumberNick() {

		return nicknames.size();
	}

	public List<String> getNicknames() {
		return nicknames;
	}

	public List<Integer> getVictory() {
		return victory;
	}

	public List<Integer> getDefeat() {
		return defeat;
	}

	public List<Integer> getDraw() {
		return draw;
	}

	public List<Boolean> getAvailable() {
		return availability;
	}

	public boolean getAvailability(String nickname) {
		
		int index = nicknames.indexOf(nickname);
		return availability.get(index);
	}

	public void setAvailability(String nickname, boolean available) {
		
		int index = nicknames.indexOf(nickname);
		availability.set(index, available);
	}

	public void addToQueueTable(String nickname) {
		queueTable.put(nickname, new ReportTurn());
	}

	public ReportTurn getQueue(String nickname) {
		return queueTable.get(nickname);
	}

	public String getNicknamesTable() {
		
		Set<String> set = queueTable.keySet();
		String[] nicknames = (String[]) set.toArray();
		String nickname = "";
		for (int i = 0; i < nicknames.length; i++) {
			nickname += nicknames.toString() + ";   \n";
		}
		return "The clients which are connected: " + nickname;
	}

}