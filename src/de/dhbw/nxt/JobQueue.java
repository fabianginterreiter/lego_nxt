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

	synchronized public void addJob(Job job) {
		this.queue.add(job);
	}

	synchronized public void removeJob(int id) {
		Iterator<Job> iter = this.queue.iterator();
		while (iter.hasNext()) {
			Job job = iter.next();
			if (job.getId() == id) {
				iter.remove();
				break;
			}
		}
	}
	
	synchronized public boolean currentJobFinished() {
		return (this.currentJob != null && this.currentJob.isDelivered());
	}

	synchronized public int size() {
		return this.queue.size();
	}
	
	synchronized public Job bestJob(int[] currentPos) {
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
				iterator.remove();
			}
		}
		
		// If the best job is already delivered, return null.
		return bestJob.isDelivered() ? null : bestJob;
	}
}
