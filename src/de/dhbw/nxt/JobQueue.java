package de.dhbw.nxt;

import java.util.ArrayList;
import java.util.Iterator;

public class JobQueue {
	private ArrayList<Job> queue;
	private Job currentJob;

	public JobQueue() {
		this.queue = new ArrayList<Job>();
		this.currentJob = null;
	}

	public void addJob(Job job) {
		this.queue.add(job);
	}

	public boolean currentJobFinished() {
		return (this.currentJob != null && this.currentJob.isDelivered());
	}

	public Job bestJob(int[] currentPos) {
		if (this.queue.isEmpty()) {
			return null;
		}

		Iterator<Job> iterator = this.queue.iterator();
		Job bestJob = iterator.next();

		while (iterator.hasNext()) {
			Job currJob = iterator.next();
			if (!currJob.isDelivered()) {
				if (currJob.distanceFrom(currentPos) < bestJob.distanceFrom(currentPos)) {
					bestJob = currJob;
				}
			}
		}

		iterator = this.queue.iterator();
		while (iterator.hasNext()) {
			Job currJob = iterator.next();
			if (currJob.isDelivered()) {
				this.queue.remove(currJob);
			}
		}
		
		return bestJob;
	}
}
