package de.dhbw.nxt;

import lejos.nxt.LCD;


public class Robot {
	private Thread btHandlerThread;
	private JobQueue queue;
	private BTHandler btHandler;

	public Robot() {
		this.btHandler = new BTHandler(this);
		this.btHandler.connect();
		
		this.btHandlerThread = new Thread(this.btHandler);
		this.btHandlerThread.start();
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setQueue(new JobQueue());
		
//		Job job = new Job(3, 3, 4, 4);
//		this.queue.addJob(job);
	}
	
	public void processQueue() {
		int[] currentPos = new int[]{0, 0};
		int i = 0;
		
		while (true) {
			synchronized (this.btHandler.newJobs) {
				for (Job j : this.btHandler.newJobs) {
					this.getQueue().addJob(j);
				}
				
				this.btHandler.newJobs.clear();
			}
			
			Job nextJob = this.getQueue().bestJob(currentPos);

			LCD.clear();
			
			LCD.drawString("Queue Size: " + this.queue.size(), 0, 0);
			LCD.drawString("" + nextJob, 0, 1);
			
			if (nextJob != null) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
//					System.out.println("Thread.sleep 3000");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (nextJob.isFetched()) {
					LCD.drawString("Deliver: " + nextJob.getDeliverX() + "/" + nextJob.getDeliverY(), 0, 2);
					nextJob.setDelivered();
				} else {
					LCD.drawString("Fetch: " + nextJob.getDeliverX() + "/" + nextJob.getDeliverY(), 0, 2);
					nextJob.setFetched();
				}
			}

			LCD.drawString("Waiting... " + i, 0, 3);
			
			i++;
			
			try {
//				System.out.println("Thread.sleep 1000");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
