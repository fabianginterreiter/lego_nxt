package de.dhbw.nxt;

import lejos.nxt.LCD;
import lejos.nxt.comm.RConsole;


public class Robot {
	private Thread btHandlerThread;
	private JobQueue queue;
	private BTHandler btHandler;

	public Robot() {
		this.setQueue(new JobQueue());

		this.btHandler = new BTHandler(this);
		this.btHandler.connect();
		
		new Thread(this.btHandler).start();
	}
	
	public void processQueue() {
		int[] currentPos = new int[]{0, 0};
		int i = 0;
		
		while (true) {
			Job nextJob = this.getQueue().bestJob(currentPos);
			
			if (nextJob != null) {
				RConsole.println("[QUEUE] [ID=" + nextJob.getId() + "] Fetched: " + nextJob.isFetched() + ", Delivered: " + nextJob.isDelivered());
				
				if (nextJob.isFetched()) {
					RConsole.println("[QUEUE] [ID=" + nextJob.getId() + "] Delivered To: " + nextJob.getDeliverX() + "/" + nextJob.getDeliverY());
					nextJob.setDelivered();
				} else {
					RConsole.println("[QUEUE] [ID=" + nextJob.getId() + "] Fetched From: " + nextJob.getDeliverX() + "/" + nextJob.getDeliverY());
					nextJob.setFetched();
				}
			}
		}
	}

	public JobQueue getQueue() {
		return queue;
	}

	public void setQueue(JobQueue queue) {
		this.queue = queue;
	}
}
