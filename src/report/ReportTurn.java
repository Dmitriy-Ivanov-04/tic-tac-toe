package report;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ReportTurn {

	private BlockingQueue<Report> queue = new LinkedBlockingQueue<Report>();

	public Report accept() {

		while (true) {
			
			try {
				return (queue.take());
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}

		}
	}
	
	public void offerQueue(Report report) {
		
		queue.offer(report);
	}
	
}
